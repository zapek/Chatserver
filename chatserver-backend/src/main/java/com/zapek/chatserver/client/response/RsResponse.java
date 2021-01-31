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

package com.zapek.chatserver.client.response;

import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RsResponse
{
	private Object retval;

	public Object getRetval()
	{
		return retval;
	}

	public void setRetval(Object retval)
	{
		this.retval = retval;
	}

	public boolean isOk()
	{
		if (retval instanceof Map)
		{
			Map<String, Object> map = (Map) retval;
			return map.containsKey("errorNumber") && map.get("errorNumber").equals(0);
		}
		else if (retval instanceof Integer)
		{
			return (Integer) retval == 0;
		}
		else if (retval instanceof Boolean)
		{
			return (Boolean) retval;
		}
		return false;
	}

	public String getErrorMessage()
	{
		if (retval instanceof Map)
		{
			Map<String, Object> map = (Map) retval;
			if (map.containsKey("errorMessage"))
			{
				return (String) map.get("errorMessage");
			}
		}
		return "Unknown error";
	}

	public String getId()
	{
		if (retval instanceof Map)
		{
			Map<String, Object> map = (Map) retval;
			if (map.containsKey("xstr64"))
			{
				return (String) map.get("xstr64");
			}
		}
		return "";
	}
}
