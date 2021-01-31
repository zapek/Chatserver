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

import com.zapek.chatserver.database.model.Prefs;
import com.zapek.chatserver.database.repository.PrefsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional(readOnly = true) // MySQL needs @Transactional on all saving methods, not just save()
public class PrefsService
{
	private static final Logger log = LoggerFactory.getLogger(PrefsService.class);

	@Autowired
	private PrefsRepository prefsRepository;

	private Prefs prefs;

	@PostConstruct
	private void init()
	{
		prefs = prefsRepository.findAll().stream().findFirst().orElseGet(() -> prefsRepository.save(new Prefs()));
	}

	@Transactional
	public void save()
	{
		prefs = prefsRepository.save(prefs);
	}

	public byte[] getIdentity()
	{
		return prefs.getIdentity();
	}

	@Transactional
	public void saveIdentity(byte[] identity)
	{
		prefs.setIdentity(identity);
		save();
	}

	public boolean hasIdentity()
	{
		return prefs.getIdentity() != null;
	}

	public boolean hasLobby()
	{
		return prefs.getLobbyId() != null;
	}

	@Transactional
	public void saveLobby(String lobbyId)
	{
		prefs.setLobbyId(Long.parseUnsignedLong(lobbyId));
		save();
	}

	public String getPassword()
	{
		return prefs.getPassword();
	}

	@Transactional
	public void savePassword(String password)
	{
		prefs.setPassword(password);
		save();
	}
}
