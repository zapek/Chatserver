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

package com.zapek.chatserver.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateChatLobbyRequest
{
	@JsonProperty("lobby_name")
	private String lobbyName;

	@JsonProperty("lobby_identity")
	private String lobbyIdentity;

	@JsonProperty("lobby_topic")
	private String lobbyTopic;

	@JsonProperty("invited_friends")
	private String[] invitedFriends = new String[0];

	@JsonProperty("lobby_privacy_type")
	private int privacyType;

	public CreateChatLobbyRequest(String name, String topic, String ownIdentity)
	{
		this.lobbyName = name;
		this.lobbyTopic = topic;
		this.lobbyIdentity = ownIdentity;
	}

	public String getLobbyName()
	{
		return lobbyName;
	}

	public void setLobbyName(String lobbyName)
	{
		this.lobbyName = lobbyName;
	}

	public String getLobbyIdentity()
	{
		return lobbyIdentity;
	}

	public void setLobbyIdentity(String lobbyIdentity)
	{
		this.lobbyIdentity = lobbyIdentity;
	}

	public String getLobbyTopic()
	{
		return lobbyTopic;
	}

	public void setLobbyTopic(String lobbyTopic)
	{
		this.lobbyTopic = lobbyTopic;
	}

	public String[] getInvitedFriends()
	{
		return invitedFriends;
	}

	public void setInvitedFriends(String[] invitedFriends)
	{
		this.invitedFriends = invitedFriends;
	}

	public int getPrivacyType()
	{
		return privacyType;
	}

	public void setPrivacyType(int privacyType)
	{
		this.privacyType = privacyType;
	}
}
