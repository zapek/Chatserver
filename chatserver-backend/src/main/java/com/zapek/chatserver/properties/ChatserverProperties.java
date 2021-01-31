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

package com.zapek.chatserver.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Configuration
@ConfigurationProperties(prefix = "chatserver")
@Validated
public class ChatserverProperties
{
	public static class RetroshareService
	{
		@NotNull(message = "An URL for the JSON API must be provided (usually http://localhost:9092)")
		@Size(min = 1, max = 512)
		private String url;

		public String getUrl()
		{
			return url;
		}

		public void setUrl(String url)
		{
			this.url = url;
		}
	}

	public static class Lobby
	{
		@NotNull(message = "A lobby name must be provided")
		@Size(min = 1, max = 64)
		private String name;
		private String topic;
		private boolean create;

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

		public boolean isCreate()
		{
			return create;
		}

		public void setCreate(boolean create)
		{
			this.create = create;
		}
	}

	@Valid
	private RetroshareService retroshareService = new RetroshareService();

	@Valid
	private Lobby lobby = new Lobby();

	@NotNull(message = "A location name must be provided, eg. \"Cool Server\"")
	@Size(min = 1, max = 64)
	private String locationName;

	@NotNull(message = "A profile name must be provided, eg. \"Foo bar\"")
	@Size(min = 1, max = 64)
	private String profileName;

	@NotNull(message = "An API username must be provided, eg. \"admin\"")
	@Size(min = 1, max = 64)
	private String apiUsername;

	private String apiPassword;

	private int maxFriends = 30;

	public RetroshareService getRetroshareService()
	{
		return retroshareService;
	}

	public void setRetroshareService(RetroshareService retroshareService)
	{
		this.retroshareService = retroshareService;
	}

	public Lobby getLobby()
	{
		return lobby;
	}

	public void setLobby(Lobby lobby)
	{
		this.lobby = lobby;
	}

	public String getLocationName()
	{
		return locationName;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	public String getProfileName()
	{
		return profileName;
	}

	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
	}

	public String getApiUsername()
	{
		return apiUsername;
	}

	public void setApiUsername(String apiUsername)
	{
		this.apiUsername = apiUsername;
	}

	public String getApiPassword()
	{
		return apiPassword;
	}

	public void setApiPassword(String apiPassword)
	{
		this.apiPassword = apiPassword;
	}

	public int getMaxFriends()
	{
		return maxFriends;
	}

	public void setMaxFriends(int maxFriends)
	{
		this.maxFriends = maxFriends;
	}
}
