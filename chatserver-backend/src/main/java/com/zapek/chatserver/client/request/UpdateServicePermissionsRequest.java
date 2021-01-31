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

public class UpdateServicePermissionsRequest
{
	private static class Permissions
	{
		@JsonProperty("mServiceName")
		private String serviceName;
		@JsonProperty("mDefaultAllowed")
		private boolean defaultAllowed;

		public String getServiceName()
		{
			return serviceName;
		}

		public void setServiceName(String serviceName)
		{
			this.serviceName = serviceName;
		}

		public boolean isDefaultAllowed()
		{
			return defaultAllowed;
		}

		public void setDefaultAllowed(boolean defaultAllowed)
		{
			this.defaultAllowed = defaultAllowed;
		}
	}

	private int serviceId;
	private Permissions permissions;

	public UpdateServicePermissionsRequest(int serviceId, boolean enable)
	{
		this.serviceId = serviceId;
		this.permissions = new Permissions();
		permissions.setDefaultAllowed(enable);
	}

	public int getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(int serviceId)
	{
		this.serviceId = serviceId;
	}

	public Permissions getPermissions()
	{
		return permissions;
	}

	public void setPermissions(Permissions permissions)
	{
		this.permissions = permissions;
	}
}
