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

package com.zapek.chatserver.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration
{
	@Value("${security.csrf}")
	private boolean csrfEnabled;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		if (!csrfEnabled)
		{
			http.csrf(AbstractHttpConfigurer::disable);
		}
		http.cors(Customizer.withDefaults())
				.authorizeHttpRequests(authorize -> authorize.requestMatchers("/v1/invitation", "/v1/actuator/health").permitAll()
						.anyRequest().authenticated())
				.httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults())
				.logout(Customizer.withDefaults());

		return http.build();
	}
}
