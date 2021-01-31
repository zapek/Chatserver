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

package com.zapek.chatserver.client;

import com.zapek.chatserver.client.request.*;
import com.zapek.chatserver.client.response.*;
import com.zapek.chatserver.properties.ChatserverProperties;
import com.zapek.chatserver.service.PrefsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;

/**
 * Client for the Retroshare API exposed by retroshare-service.
 */
@Component
public class RetroshareClient
{
	private static final Logger log = LoggerFactory.getLogger(RetroshareClient.class);

	@Autowired
	private ChatserverProperties chatserverProperties;

	@Autowired
	private PrefsService prefsService;

	@Autowired
	private WebClient.Builder webClientBuilder;

	private WebClient webClient;

	private static final String LOGIN_HELPER_PATH = "/rsLoginHelper";
	private static final String CONTROL_PATH = "/rsControl";
	private static final String IDENTITY_PATH = "/rsIdentity";
	private static final String PEERS_PATH = "/rsPeers";
	private static final String SERVICE_CONTROL_PATH = "/rsServiceControl";
	private static final String MSGS_PATH = "/rsMsgs";

	@PostConstruct
	private void init()
	{
		if (log.isTraceEnabled())
		{
			HttpClient httpClient = HttpClient.create().wiretap(true);
			webClient = WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
		}
		else
		{
			webClient = webClientBuilder
					.baseUrl(chatserverProperties.getRetroshareService().getUrl())
					.build();
		}
	}

	private void setAuthentication(HttpHeaders httpHeaders)
	{
		httpHeaders.setBasicAuth(chatserverProperties.getApiUsername(), prefsService.getPassword());
	}

	public Mono<CreateLocationResponse> createLocation(CreateLocationRequest request)
	{
		return webClient.post()
				.uri(LOGIN_HELPER_PATH + "/createLocationV2")
				.bodyValue(request)
				.retrieve()
				.bodyToMono(CreateLocationResponse.class);
	}

	public Mono<RsResponse> isLoggedIn()
	{
		return webClient.get()
				.uri(LOGIN_HELPER_PATH + "/isLoggedIn")
				.retrieve()
				.bodyToMono(RsResponse.class);
	}

	public Mono<RsResponse> attemptLogin(AttemptLoginRequest request)
	{
		return webClient.post()
				.uri(LOGIN_HELPER_PATH + "/attemptLogin")
				.bodyValue(request)
				.retrieve()
				.bodyToMono(RsResponse.class);
	}

	public Mono<RsResponse> isReady()
	{
		return webClient.get()
				.uri(CONTROL_PATH + "/isReady")
				.headers(this::setAuthentication)
				.retrieve()
				.bodyToMono(RsResponse.class);
	}

	public Mono<CreateIdentityResponse> createIdentity(CreateIdentityRequest request)
	{
		return webClient.post()
				.uri(IDENTITY_PATH + "/createIdentity")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(CreateIdentityResponse.class);
	}

	public Mono<RsResponse> acceptInvite(AcceptInviteRequest request)
	{
		return webClient.post()
				.uri(PEERS_PATH + "/acceptInvite")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(RsResponse.class);
	}

	public Mono<GetShortInviteResponse> getOwnShortInvite()
	{
		var request = new GetShortInviteRequest("00000000000000000000000000000000", 13);

		return webClient.post()
				.uri(PEERS_PATH + "/getShortInvite")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(GetShortInviteResponse.class);
	}

	public Mono<ParseShortInviteResponse> parseShortInvite(ParseShortInviteRequest request)
	{
		return webClient.post()
				.uri(PEERS_PATH + "/parseShortInvite")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(ParseShortInviteResponse.class);
	}

	public Mono<RsResponse> addSslOnlyFriend(AddSslOnlyFriendRequest request)
	{
		return webClient.post()
				.uri(PEERS_PATH + "/addSslOnlyFriend")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(RsResponse.class);
	}

	public Mono<RsResponse> removeFriend(RemoveFriendRequest request)
	{
		return webClient.post()
				.uri(PEERS_PATH + "/removeFriend")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(RsResponse.class);
	}

	public Mono<RsResponse> updateServicePermissions(UpdateServicePermissionsRequest request)
	{
		return webClient.post()
				.uri(SERVICE_CONTROL_PATH + "/updateServicePermissions")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(RsResponse.class);
	}

	public Mono<GetListOfNearbyLobbiesResponse> getListOfNearbyChatLobbies()
	{
		return webClient.get()
				.uri(MSGS_PATH + "/getListOfNearbyChatLobbies")
				.headers(this::setAuthentication)
				.retrieve()
				.bodyToMono(GetListOfNearbyLobbiesResponse.class);
	}

	public Mono<SetLobbyAutoSubscribeResponse> setLobbyAutoSubscribe(SetLobbyAutoSubscribeRequest request)
	{
		return webClient.post()
				.uri(MSGS_PATH + "/setLobbyAutoSubscribe")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(SetLobbyAutoSubscribeResponse.class);
	}

	public Mono<RsResponse> createChatLobby(CreateChatLobbyRequest request)
	{
		return webClient.post()
				.uri(MSGS_PATH + "/createChatLobby")
				.headers(this::setAuthentication)
				.bodyValue(request)
				.retrieve()
				.bodyToMono(RsResponse.class);
	}
}
