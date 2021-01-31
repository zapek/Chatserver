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

package com.zapek.chatserver.util;

import java.util.Locale;

public final class Id
{
	private static final char[] hex = "0123456789abcdef".toCharArray();

	private Id()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	public static String toString(byte[] id)
	{
		if (id == null)
		{
			return null;
		}

		StringBuilder sb = new StringBuilder(id.length * 2);

		for (byte b : id)
		{
			sb.append(hex[(b & 0xf0) >> 4])
					.append(hex[b & 0x0f]);
		}
		return sb.toString();
	}

	public static byte[] toBytes(String id)
	{
		byte[] out = new byte[id.length() / 2];

		for (int i = 0; i < out.length; i++)
		{
			int index = i * 2;
			out[i] = (byte) Integer.parseUnsignedInt(id.substring(index, index + 2), 16);
		}
		return out;
	}

	public static String toString(long id)
	{
		return Long.toHexString(id).toUpperCase(Locale.ROOT);
	}
}
