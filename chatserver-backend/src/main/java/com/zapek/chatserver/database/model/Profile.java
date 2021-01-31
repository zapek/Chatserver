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
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Arrays;

import static com.zapek.chatserver.database.model.Trust.UNKNOWN;

@Table(name = "profiles")
@Entity
public class Profile
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private long pgpIdentifier;
	private byte[] locationIdentifier;

	@Enumerated(EnumType.STRING)
	private Trust trust = UNKNOWN;

	private Instant created;

	protected Profile()
	{
	}

	public static Profile createOwnProfile(long pgpIdentifier, byte[] locationIdentifier)
	{
		return new Profile(pgpIdentifier, locationIdentifier, Trust.ULTIMATE);
	}

	public static Profile createProfile(long pgpIdentifier)
	{
		return new Profile(pgpIdentifier, null, Trust.NEVER);
	}

	private Profile(long pgpIdentifier, byte[] locationIdentifier, Trust trust)
	{
		this.pgpIdentifier = pgpIdentifier;
		this.locationIdentifier = locationIdentifier;
		this.trust = trust;
		this.created = Instant.now();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public long getPgpIdentifier()
	{
		return pgpIdentifier;
	}

	public void setPgpIdentifier(long pgpIdentifier)
	{
		this.pgpIdentifier = pgpIdentifier;
	}

	public byte[] getLocationIdentifier()
	{
		return locationIdentifier;
	}

	public void setLocationIdentifier(byte[] locationId)
	{
		this.locationIdentifier = locationId;
	}

	public Instant getCreated()
	{
		return created;
	}

	public void setCreated(Instant created)
	{
		this.created = created;
	}

	public Trust getTrust()
	{
		return trust;
	}

	public void setTrust(Trust trust)
	{
		this.trust = trust;
	}

	@Override
	public String toString()
	{
		return "Profile{" +
				"pgpIdentifier=" + com.zapek.chatserver.util.Id.toString(pgpIdentifier) +
				", locationIdentifier=" + com.zapek.chatserver.util.Id.toString(locationIdentifier) +
				", trust=" + trust +
				", created=" + created +
				'}';
	}
}
