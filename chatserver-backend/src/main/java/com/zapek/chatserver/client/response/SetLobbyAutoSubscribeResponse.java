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

package com.zapek.chatserver.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zapek.chatserver.client.common.LobbyId;

public class SetLobbyAutoSubscribeResponse
{
	@JsonProperty("lobby_id")
	private LobbyId lobbyId;

	@JsonProperty("autoSubscribe")
	private boolean autoSubscribe;

	public LobbyId getLobbyId()
	{
		return lobbyId;
	}

	public void setLobbyId(LobbyId lobbyId)
	{
		this.lobbyId = lobbyId;
	}

	public boolean isAutoSubscribe()
	{
		return autoSubscribe;
	}

	public void setAutoSubscribe(boolean autoSubscribe)
	{
		this.autoSubscribe = autoSubscribe;
	}

	@Override
	public String toString()
	{
		return "SetLobbyAutoSubscribeResult{" +
				"lobbyId=" + lobbyId +
				", autoSubscribe=" + autoSubscribe +
				'}';
	}
}
