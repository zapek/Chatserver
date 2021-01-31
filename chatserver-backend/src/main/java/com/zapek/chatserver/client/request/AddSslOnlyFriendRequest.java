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

import com.zapek.chatserver.client.response.ParseShortInviteResponse;

public class AddSslOnlyFriendRequest
{
	private String sslId;
	private String pgpId;
	private ParseShortInviteResponse.ShortInviteDetails details;

	public AddSslOnlyFriendRequest(String sslId, String pgpId, ParseShortInviteResponse.ShortInviteDetails details)
	{
		this.sslId = sslId;
		this.pgpId = pgpId;
		this.details = details;
	}

	public String getSslId()
	{
		return sslId;
	}

	public void setSslId(String sslId)
	{
		this.sslId = sslId;
	}

	public String getPgpId()
	{
		return pgpId;
	}

	public void setPgpId(String pgpId)
	{
		this.pgpId = pgpId;
	}

	public ParseShortInviteResponse.ShortInviteDetails getDetails()
	{
		return details;
	}

	public void setDetails(ParseShortInviteResponse.ShortInviteDetails details)
	{
		this.details = details;
	}
}
