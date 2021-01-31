/*
 * Copyright (c) 2021 by David Gerber - https://zapek.com
 *
 * This file is part of Chatserver.
 *
 * Chatserver is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Chatserver is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Chatserver.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.zapek.chatserver.service;

import com.zapek.chatserver.client.RetroshareClient;
import com.zapek.chatserver.client.common.LobbyId;
import com.zapek.chatserver.client.request.*;
import com.zapek.chatserver.client.response.*;
import com.zapek.chatserver.database.model.Lobby;
import com.zapek.chatserver.database.model.Profile;
import com.zapek.chatserver.util.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RetroshareService
{
	private static final Logger log = LoggerFactory.getLogger(RetroshareService.class);

	@Autowired
	private RetroshareClient retroshareClient;

	private static final Duration TIMEOUT = Duration.ofSeconds(20);

	public Profile createLocation(String locationName, String profileName, String apiUsername, String apiPassword)
	{
		var createLocationRequest = new CreateLocationRequest(
				locationName,
				profileName,
				apiUsername,
				apiPassword);

		CreateLocationResponse result = retroshareClient.createLocation(createLocationRequest).blockOptional(TIMEOUT).orElseThrow();

		if (!result.isOk())
		{
			throw new IllegalStateException("Couldn't create location: " + result.getErrorMessage());
		}
		return Profile.createOwnProfile(Long.parseUnsignedLong(result.getPgpId(), 16), Id.toBytes(result.getLocationId()));
	}

	public boolean login(byte[] locationId, String password)
	{
		var attemptLoginRequest = new AttemptLoginRequest(Id.toString(locationId), password);
		RsResponse rsResponse = retroshareClient.attemptLogin(attemptLoginRequest).blockOptional(TIMEOUT).orElseThrow();

		return rsResponse.getRetval().equals(0);
	}

	public boolean isLoggedIn()
	{
		return retroshareClient.isLoggedIn().blockOptional(TIMEOUT).orElseThrow().getRetval().equals(true);
	}

	public boolean isCoreReady()
	{
		boolean isReady = false;
		int counter = 0;

		while (!isReady && counter < 10)
		{
			if (counter > 0)
			{
				try
				{
					TimeUnit.SECONDS.sleep(1);
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
			}
			isReady = retroshareClient.isReady().blockOptional(TIMEOUT).orElseThrow().getRetval().equals(true);
			counter++;
		}
		return isReady;
	}

	public boolean updateService(int serviceId, boolean enable)
	{
		var updateRequest = new UpdateServicePermissionsRequest(serviceId, enable);
		return retroshareClient.updateServicePermissions(updateRequest).blockOptional(TIMEOUT).orElseThrow().getRetval().equals(true);
	}

	/**
	 * Create a new identity.
	 * <br><br>Note that retroshare-service currently has a bug where it fails to sign
	 * the identities with the profile (it still marks it as a signed identity internally, though).
	 * @param name the identity's name
	 * @param password the profile's password
	 * @return the identity's identifier
	 */
	public String createIdentity(String name, String password)
	{
		var identityRequest = new CreateIdentityRequest(name, password);
		CreateIdentityResponse createIdentityResponse = retroshareClient.createIdentity(identityRequest).blockOptional(TIMEOUT).orElseThrow();

		if (!createIdentityResponse.isOk())
		{
			throw new IllegalStateException("Couldn't create identity: " + createIdentityResponse.getErrorMessage());
		}
		return createIdentityResponse.getIdentity();
	}

	public long checkInvite(String shortInvite)
	{
		var parseRequest = new ParseShortInviteRequest(shortInvite);
		ParseShortInviteResponse parseResult = retroshareClient.parseShortInvite(parseRequest).blockOptional(TIMEOUT).orElseThrow();
		if (parseResult.isOk())
		{
			return Long.parseUnsignedLong(parseResult.getDetails().getPgpIdentifier(), 16);
		}
		return 0;
	}

	public boolean addFriend(String shortInvite)
	{
		// We redo the same as checkInvite() which is suboptimal but that way we don't have to pass
		// parseRequest around
		var parseRequest = new ParseShortInviteRequest(shortInvite);
		ParseShortInviteResponse parseResult = retroshareClient.parseShortInvite(parseRequest).blockOptional(TIMEOUT).orElseThrow();
		if (parseResult.isOk())
		{
			var addSslOnlyRequest = new AddSslOnlyFriendRequest(
					parseResult.getDetails().getLocationIdentifier(),
					parseResult.getDetails().getPgpIdentifier(),
					parseResult.getDetails());
			return retroshareClient.addSslOnlyFriend(addSslOnlyRequest).blockOptional(TIMEOUT).orElseThrow().isOk();
		}
		return false;
	}

	public boolean removeFriend(long profileIdentifier)
	{
		var removeFriend = new RemoveFriendRequest(Id.toString(profileIdentifier));
		return retroshareClient.removeFriend(removeFriend).blockOptional(TIMEOUT).orElseThrow().isOk();
	}

	public String getOwnInvitation()
	{
		return retroshareClient.getOwnShortInvite().blockOptional(TIMEOUT).orElseThrow().getInvite();
	}

	public List<Lobby> getListOfNearbyLobbies()
	{
		var lobbies = new ArrayList<Lobby>();

		GetListOfNearbyLobbiesResponse lobbiesResult = retroshareClient.getListOfNearbyChatLobbies().blockOptional(TIMEOUT).orElseThrow();

		Arrays.stream(lobbiesResult.getLobbies())
				.forEach(lobbyDetail -> lobbies.add(
						new Lobby(lobbyDetail.getId().getIdString(),
								lobbyDetail.getName(),
								lobbyDetail.getTopic())));

		return lobbies;
	}

	public boolean setLobbyAutoSubscribe(String lobbyId, boolean autoSubscribe)
	{
		var request = new SetLobbyAutoSubscribeRequest(new LobbyId(lobbyId), autoSubscribe);

		SetLobbyAutoSubscribeResponse setLobbyAutoSubscribeResponse = retroshareClient.setLobbyAutoSubscribe(request).blockOptional(TIMEOUT).orElseThrow();
		log.info("AutoSubscribe result: {}", setLobbyAutoSubscribeResponse);
		return true; // Autosubscribe always "works", even though it sometimes returns it didn't
	}

	public Lobby createChatLobby(String name, String topic, String ownIdentity)
	{
		var request = new CreateChatLobbyRequest(name, topic, ownIdentity);

		String lobbyId = retroshareClient.createChatLobby(request).blockOptional(TIMEOUT).orElseThrow().getId();
		return new Lobby(lobbyId, name, topic);
	}
}
