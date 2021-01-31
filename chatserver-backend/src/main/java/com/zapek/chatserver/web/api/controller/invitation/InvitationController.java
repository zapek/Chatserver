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

package com.zapek.chatserver.web.api.controller.invitation;

import com.zapek.chatserver.properties.ChatserverProperties;
import com.zapek.chatserver.service.InvitationService;
import com.zapek.chatserver.service.RetroshareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Invitation", description = "Retroshare short invites handling")
@RestController
@RequestMapping(value = "/v1/invitation", produces = MediaType.APPLICATION_JSON_VALUE)
public class InvitationController
{
	private static final Logger log = LoggerFactory.getLogger(InvitationController.class);

	@Autowired
	private InvitationService invitationService;

	@Autowired
	private RetroshareService retroshareService;

	@Autowired
	private ChatserverProperties chatserverProperties;

	@PostMapping
	@Operation(summary = "Add friend invitation")
	@ApiResponse(responseCode = "200", description = "Invitation added successfully", headers = @Header(name = "Invitation", description = "The invitation of the chatserver, to add in the client", schema = @Schema(type = "string")))
	public ResponseEntity<InvitationResponse> addInvitation(@Valid @RequestBody InvitationRequest request)
	{
		switch (invitationService.addFriend(request.getInvite()))
		{
			case ADDED:
				invitationService.trimFriends(chatserverProperties.getMaxFriends());
				return ResponseEntity.ok(new InvitationResponse(retroshareService.getOwnInvitation()));

			case DUPLICATE:
				return ResponseEntity.status(HttpStatus.CONFLICT).build();

			default:
				return ResponseEntity.unprocessableEntity().build();
		}
	}
}
