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

package com.zapek.chatserver.job;

import com.zapek.chatserver.application.Startup;
import com.zapek.chatserver.service.RetroshareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;

@Component
public class WaitForRetroshareServiceJob
{
	private static final Logger log = LoggerFactory.getLogger(WaitForRetroshareServiceJob.class);

	@Autowired
	private RetroshareService retroshareService;

	@Autowired
	private Startup startup;

	private boolean started;

	@Scheduled(initialDelay = 2 * 1000, fixedDelay = 15 * 1000)
	private void checkForRetroshareService()
	{
		if (started)
		{
			return;
		}

		try
		{
			log.info("Searching for retroshare-service...");
			retroshareService.isLoggedIn();
			startup.start();
			started = true;
		}
		catch (WebClientRequestException e)
		{
			log.debug("Server failed to respond, trying again...");
		}
	}
}
