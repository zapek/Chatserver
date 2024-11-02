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

import jakarta.validation.constraints.NotNull;

public class CreateIdentityRequest
{
	@NotNull
	private String name;
	private boolean pseudonimous;
	private String pgpPassword;

	public CreateIdentityRequest(String name, String pgpPassword)
	{
		this.name = name;
		this.pgpPassword = pgpPassword;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isPseudonimous()
	{
		return pseudonimous;
	}

	public void setPseudonimous(boolean pseudonimous)
	{
		this.pseudonimous = pseudonimous;
	}

	public String getPgpPassword()
	{
		return pgpPassword;
	}

	public void setPgpPassword(String pgpPassword)
	{
		this.pgpPassword = pgpPassword;
	}
}
