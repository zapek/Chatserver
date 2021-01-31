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

package com.zapek.chatserver.database.model;

import javax.persistence.*;
import java.util.Arrays;

@Table(name = "prefs")
@Entity
public class Prefs
{
	@SuppressWarnings("unused")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // MySQL needs objects there, with primitives it seems confused if it's a persisted entity or a new one

	private byte[] identity;

	private Long lobbyId;

	private String password;

	public Prefs()
	{
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public byte[] getIdentity()
	{
		return identity;
	}

	public void setIdentity(byte[] identity)
	{
		this.identity = identity;
	}

	public Long getLobbyId()
	{
		return lobbyId;
	}

	public void setLobbyId(Long lobbyId)
	{
		this.lobbyId = lobbyId;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	@Override
	public String toString()
	{
		return "Prefs{" +
				"id=" + id +
				", identity=" + Arrays.toString(identity) +
				", lobbyId=" + lobbyId +
				'}';
	}
}
