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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class InvitationService
{
	private static final Logger log = LoggerFactory.getLogger(InvitationService.class);

	private static final int TRIM_DELAY_SECONDS = 10; // time to schedule a trim after a friend is added
	private static final int TRIM_RUN_SECONDS = 30; // time to wait between trim runs

	@Autowired
	private RetroshareService retroshareService;

	@Autowired
	private ProfileService profileService;

	private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

	private Instant lastTrimRun = Instant.now().minusSeconds(TRIM_RUN_SECONDS);

	public enum Invitation
	{
		ADDED,
		DUPLICATE,
		ERROR
	}

	public Invitation addFriend(String shortInvite)
	{
		long profileIdentifier = retroshareService.checkInvite(shortInvite);
		if (profileService.findByProfileIdentifier(profileIdentifier).isPresent())
		{
			return Invitation.DUPLICATE;
		}
		if (retroshareService.addFriend(shortInvite))
		{
			Profile profile = Profile.createProfile(profileIdentifier);
			if (profileService.create(profile).isPresent())
			{
				log.info("Added friend {}", profile);
				return Invitation.ADDED;
			}
			log.error("A friend was added but not recorded in the database. This can potentially be a problem if it happens too often.");
		}
		return Invitation.ERROR;
	}

	public void trimFriends(int maxFriends)
	{
		executorService.schedule(() -> doTrim(maxFriends), TRIM_DELAY_SECONDS, TimeUnit.SECONDS);
	}

	private void doTrim(int maxFriends)
	{
		Instant now = Instant.now();

		if (Duration.between(lastTrimRun, now).getSeconds() > TRIM_RUN_SECONDS)
		{
			profileService.getAllRemovableProfiles().stream()
					.skip(maxFriends)
					.forEach(profile -> {
						if (retroshareService.removeFriend(profile.getPgpIdentifier()))
						{
							log.info("TRIM: removed profile {}", profile);
							profileService.delete(profile);
						}
						else
						{
							log.error("Couldn't remove friend from retroshare-service. This can potentially be a problem if it happens too often.");
						}
					});
			lastTrimRun = now;
		}
	}
}
