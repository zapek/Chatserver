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

package com.zapek.chatserver.service;

import com.zapek.chatserver.database.model.Profile;
import com.zapek.chatserver.database.model.Trust;
import com.zapek.chatserver.database.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProfileService
{
	@Autowired
	private ProfileRepository profileRepository;

	public boolean hasOwnProfile()
	{
		return profileRepository.findFirstByTrustIs(Trust.ULTIMATE).isPresent();
	}

	public Profile getOwnProfile()
	{
		return profileRepository.findFirstByTrustIs(Trust.ULTIMATE).orElse(null);
	}

	public Optional<Profile> findByProfileIdentifier(long profileIdentifier)
	{
		return profileRepository.findByPgpIdentifier(profileIdentifier);
	}

	@Transactional
	public Optional<Profile> create(Profile profile)
	{
		try
		{
			return Optional.of(profileRepository.save(profile));
		}
		catch (IllegalArgumentException e)
		{
			return Optional.empty();
		}
	}

	public List<Profile> getAllRemovableProfiles()
	{
		return profileRepository.findAllByTrustIsOrderByCreatedDesc(Trust.NEVER);
	}

	@Transactional
	public void delete(Profile profile)
	{
		profileRepository.delete(profile);
	}
}
