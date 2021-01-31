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

import javax.validation.constraints.NotNull;

public class CreateLocationRequest
{
	@NotNull(message = "Missing location name")
	private String locationName;
	@NotNull(message = "Missing location name")
	private String pgpName;
	private String apiUser;
	private String apiPass;

	public CreateLocationRequest(String locationName, String pgpName, String apiUser, String apiPass)
	{
		this.locationName = locationName;
		this.pgpName = pgpName;
		this.apiUser = apiUser;
		this.apiPass = apiPass;
	}

	public String getLocationName()
	{
		return locationName;
	}

	public void setLocationName(String locationName)
	{
		this.locationName = locationName;
	}

	public String getPgpName()
	{
		return pgpName;
	}

	public void setPgpName(String pgpName)
	{
		this.pgpName = pgpName;
	}

	public String getApiUser()
	{
		return apiUser;
	}

	public void setApiUser(String apiUser)
	{
		this.apiUser = apiUser;
	}

	public String getApiPass()
	{
		return apiPass;
	}

	public void setApiPass(String apiPass)
	{
		this.apiPass = apiPass;
	}
}
