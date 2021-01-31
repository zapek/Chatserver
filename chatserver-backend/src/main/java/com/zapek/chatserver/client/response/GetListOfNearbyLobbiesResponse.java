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

public class GetListOfNearbyLobbiesResponse
{
	public static class LobbyDetail
	{
		@JsonProperty("lobby_id")
		private LobbyId id;

		@JsonProperty("lobby_name")
		private String name;

		@JsonProperty("lobby_topic")
		private String topic;

		@JsonProperty("total_number_of_peers")
		private int peerCount;

		public LobbyId getId()
		{
			return id;
		}

		public void setId(LobbyId id)
		{
			this.id = id;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getTopic()
		{
			return topic;
		}

		public void setTopic(String topic)
		{
			this.topic = topic;
		}

		public int getPeerCount()
		{
			return peerCount;
		}

		public void setPeerCount(int peerCount)
		{
			this.peerCount = peerCount;
		}
	}

	@JsonProperty("public_lobbies")
	private LobbyDetail[] lobbies;

	public LobbyDetail[] getLobbies()
	{
		return lobbies;
	}

	public void setLobbies(LobbyDetail[] lobbies)
	{
		this.lobbies = lobbies;
	}
}
