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

import com.zapek.chatserver.database.model.Lobby;
import com.zapek.chatserver.util.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class LobbyService implements Runnable
{
	private static final Logger log = LoggerFactory.getLogger(LobbyService.class);

	private static final int TRIES_TIMES = 20;
	private static final int TRIES_DELAY = 30;

	@Autowired
	private RetroshareService retroshareService;

	@Autowired
	private PrefsService prefsService;

	private String lobbyName;
	private String lobbyTopic;
	private boolean createLobby;

	private Thread thread;

	public void start(String lobbyName, String lobbyTopic, boolean createLobby)
	{
		this.lobbyName = lobbyName;
		this.lobbyTopic = lobbyTopic;
		this.createLobby = createLobby;

		thread = new Thread(this, "Lobby Service");
		thread.start();
	}

	public void stop()
	{
		if (thread != null)
		{
			log.info("Stopping lobby service...");
			thread.interrupt();
		}
	}

	@Override
	public void run()
	{
		Optional<Lobby> foundLobby = Optional.empty();

		// Try during 10 minutes
		for (int i = 0; (!createLobby || i < TRIES_TIMES) && foundLobby.isEmpty(); i++)
		{
			if (i > 0)
			{
				delay(TRIES_DELAY);
			}
			log.info("Watching for lobby {} ...", lobbyName);

			foundLobby = retroshareService.getListOfNearbyLobbies().stream()
					.filter(lobby -> lobby.getName().equals(lobbyName) && lobby.getTopic().equals(lobbyTopic))
					.findFirst();
		}

		if (foundLobby.isEmpty())
		{
			log.info("Lobby not found, creating...");

			foundLobby = Optional.of(retroshareService.createChatLobby(lobbyName, lobbyTopic, Id.toString(prefsService.getIdentity())));
		}

		boolean subscribed = retroshareService.setLobbyAutoSubscribe(foundLobby.get().getLobbyId(), true);
		if (subscribed)
		{
			log.info("Successfully subscribed to lobby {} ({})", foundLobby.get().getLobbyId(), foundLobby.get().getName());

			prefsService.saveLobby(foundLobby.get().getLobbyId());
		}
		else
		{
			log.error("Couldn't subscribe to lobby {}", foundLobby.get().getLobbyId());
		}
	}

	private void delay(long seconds)
	{
		try
		{
			TimeUnit.SECONDS.sleep(seconds);
		}
		catch (InterruptedException e)
		{
			Thread.currentThread().interrupt();
		}
	}

}
