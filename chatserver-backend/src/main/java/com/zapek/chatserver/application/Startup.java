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

package com.zapek.chatserver.application;

import com.zapek.chatserver.database.model.Profile;
import com.zapek.chatserver.properties.ChatserverProperties;
import com.zapek.chatserver.service.LobbyService;
import com.zapek.chatserver.service.PrefsService;
import com.zapek.chatserver.service.ProfileService;
import com.zapek.chatserver.service.RetroshareService;
import com.zapek.chatserver.util.Id;
import com.zapek.chatserver.util.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class Startup
{
	private static final Logger log = LoggerFactory.getLogger(Startup.class);

	@Autowired
	private ChatserverProperties chatserverProperties;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private PrefsService prefsService;

	@Autowired
	private RetroshareService retroshareService;

	@Autowired
	private LobbyService lobbyService;

	public void start()
	{
		boolean needsConfiguration;

		if (profileService.hasOwnProfile())
		{
			needsConfiguration = false;

			if (!retroshareService.isLoggedIn())
			{
				login();
			}
		}
		else
		{
			needsConfiguration = true;

			createLocation();
		}

		if (!retroshareService.isCoreReady())
		{
			throw new IllegalArgumentException("retroshare-service is not ready");
		}

		log.info("retroshare-service is ready");

		if (needsConfiguration)
		{
			disableNonEssentialServices();
			createIdentity();
		}
		subscribeToChatRoom();

		log.info("Waiting for commands");
	}

	private void login()
	{
		boolean login = retroshareService.login(
				profileService.getOwnProfile().getLocationIdentifier(),
				prefsService.getPassword());

		if (!login)
		{
			throw new IllegalArgumentException("Login attempt failed, check retroshare-service logs");
		}
		log.info("Successfully logged in");
	}

	private void createLocation()
	{
		String password = chatserverProperties.getApiPassword();
		if (password == null)
		{
			password = PasswordGenerator.generate(12);
		}

		Profile profile = retroshareService.createLocation(
				chatserverProperties.getLocationName(),
				chatserverProperties.getProfileName(),
				chatserverProperties.getApiUsername(),
				password);

		log.info("Created profile {}", profile);

		prefsService.savePassword(password);

		profileService.create(profile).orElseThrow();
	}

	private void disableNonEssentialServices()
	{
		log.info("Disabling services not needed for a chat server");
		retroshareService.updateService(33558784, true); // disc
		retroshareService.updateService(33559040, true); // chat
		retroshareService.updateService(33560064, true); // heartbeat
		retroshareService.updateService(33562624, true); // serviceinfo
		retroshareService.updateService(33689856, true); // gxsid
		retroshareService.updateService(34607360, true); // rtt
		retroshareService.updateService(33559296, false); // msg
		retroshareService.updateService(33559552, false); // turtle
		retroshareService.updateService(33560320, false); // ft
		retroshareService.updateService(33560576, false); // Global Router
		retroshareService.updateService(33560832, false); // file_database
		retroshareService.updateService(33562880, false); // bandwidth_ctrl
		retroshareService.updateService(33564672, false); // GxsTunnels
		retroshareService.updateService(33620224, false); // banlist
		retroshareService.updateService(33620480, false); // status
		retroshareService.updateService(33690880, false); // gxsforums
		retroshareService.updateService(33691136, false); // gxsposted
		retroshareService.updateService(33691392, false); // gxschannels
		retroshareService.updateService(33691648, false); // gxscircle
		retroshareService.updateService(33691904, false); // gxsreputation
		retroshareService.updateService(33697792, false); // GXS Mails
	}

	private void createIdentity()
	{
		String identity = retroshareService.createIdentity(chatserverProperties.getLocationName(), prefsService.getPassword());
		log.info("Created identity {}", identity);
		prefsService.saveIdentity(Id.toBytes(identity));
	}

	private void subscribeToChatRoom()
	{
		if (!prefsService.hasLobby())
		{
			lobbyService.start(
					chatserverProperties.getLobby().getName(),
					chatserverProperties.getLobby().getTopic(),
					chatserverProperties.getLobby().isCreate());
		}
	}

	@PreDestroy
	private void shutdown()
	{
		lobbyService.stop();
	}
}
