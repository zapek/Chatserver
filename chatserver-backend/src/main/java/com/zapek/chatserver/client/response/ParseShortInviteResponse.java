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

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParseShortInviteResponse extends RsResponse
{
	public static class ShortInviteDetails
	{
		@JsonProperty("isOnlyGPGdetail")
		private boolean gpgOnly;

		@JsonProperty("id")
		private String locationIdentifier;

		@JsonProperty("gpg_id")
		private String pgpIdentifier;

		private String name;
		private String email;
		private String location;
		private String org;
		private String issuer;

		@JsonProperty("fpr")
		private String fingerPrint;

		@JsonProperty("authcode")
		private String authCode;

		//private long[] gpgSigners;
		private int trustLvl;
		private int validLvl;

		@JsonProperty("ownsign")
		private boolean ownSign;

		@JsonProperty("hasSignedMe")
		private boolean signedMe;

		@JsonProperty("accept_connection")
		private boolean acceptConnection;

		@JsonProperty("service_perm_flags")
		private int servicePermFlags;

		private int state;

		private boolean actAsServer;

		@JsonProperty("connectAddr")
		private String connectionAddress;

		@JsonProperty("connectPort")
		private int connectionPort;

		@JsonProperty("isHiddenNode")
		private boolean hiddenNode;

		private String hiddenNodeAddress;
		private int hiddenNodePort;
		private int hiddenType;

		@JsonProperty("localAddr")
		private String localAddress;

		private int localPort;

		@JsonProperty("extAddr")
		private String externalAddress;

		@JsonProperty("extPort")
		private int externalPort;

		@JsonProperty("dyndns")
		private String dynDns;

		private String[] ipAddressList;

		private int netMode;

		@JsonProperty("vs_disc")
		private int vsDisc;

		@JsonProperty("vs_dht")
		private int vsDht;

		private int lastConnect;
		private int lastUser;
		private int connectState;
		private String connectStateString;
		private int connectPeriod;

		@JsonProperty("foundDHT")
		private boolean foundDht;

		private boolean wasDeniedConnection;
		// XXX: missing 2 fields here... should be ok
		private int linkType;


		public boolean isGpgOnly()
		{
			return gpgOnly;
		}

		public void setGpgOnly(boolean gpgOnly)
		{
			this.gpgOnly = gpgOnly;
		}

		public String getLocationIdentifier()
		{
			return locationIdentifier;
		}

		public void setLocationIdentifier(String locationIdentifier)
		{
			this.locationIdentifier = locationIdentifier;
		}

		public String getPgpIdentifier()
		{
			return pgpIdentifier;
		}

		public void setPgpIdentifier(String pgpIdentifier)
		{
			this.pgpIdentifier = pgpIdentifier;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public String getEmail()
		{
			return email;
		}

		public void setEmail(String email)
		{
			this.email = email;
		}

		public String getLocation()
		{
			return location;
		}

		public void setLocation(String location)
		{
			this.location = location;
		}

		public String getOrg()
		{
			return org;
		}

		public void setOrg(String org)
		{
			this.org = org;
		}

		public String getIssuer()
		{
			return issuer;
		}

		public void setIssuer(String issuer)
		{
			this.issuer = issuer;
		}

		public String getFingerPrint()
		{
			return fingerPrint;
		}

		public void setFingerPrint(String fingerPrint)
		{
			this.fingerPrint = fingerPrint;
		}

		public String getAuthCode()
		{
			return authCode;
		}

		public void setAuthCode(String authCode)
		{
			this.authCode = authCode;
		}

		public int getTrustLvl()
		{
			return trustLvl;
		}

		public void setTrustLvl(int trustLvl)
		{
			this.trustLvl = trustLvl;
		}

		public int getValidLvl()
		{
			return validLvl;
		}

		public void setValidLvl(int validLvl)
		{
			this.validLvl = validLvl;
		}

		public boolean isOwnSign()
		{
			return ownSign;
		}

		public void setOwnSign(boolean ownSign)
		{
			this.ownSign = ownSign;
		}

		public boolean isSignedMe()
		{
			return signedMe;
		}

		public void setSignedMe(boolean signedMe)
		{
			this.signedMe = signedMe;
		}

		public boolean isAcceptConnection()
		{
			return acceptConnection;
		}

		public void setAcceptConnection(boolean acceptConnection)
		{
			this.acceptConnection = acceptConnection;
		}

		public int getServicePermFlags()
		{
			return servicePermFlags;
		}

		public void setServicePermFlags(int servicePermFlags)
		{
			this.servicePermFlags = servicePermFlags;
		}

		public int getState()
		{
			return state;
		}

		public void setState(int state)
		{
			this.state = state;
		}

		public boolean isActAsServer()
		{
			return actAsServer;
		}

		public void setActAsServer(boolean actAsServer)
		{
			this.actAsServer = actAsServer;
		}

		public String getConnectionAddress()
		{
			return connectionAddress;
		}

		public void setConnectionAddress(String connectionAddress)
		{
			this.connectionAddress = connectionAddress;
		}

		public int getConnectionPort()
		{
			return connectionPort;
		}

		public void setConnectionPort(int connectionPort)
		{
			this.connectionPort = connectionPort;
		}

		public boolean isHiddenNode()
		{
			return hiddenNode;
		}

		public void setHiddenNode(boolean hiddenNode)
		{
			this.hiddenNode = hiddenNode;
		}

		public String getHiddenNodeAddress()
		{
			return hiddenNodeAddress;
		}

		public void setHiddenNodeAddress(String hiddenNodeAddress)
		{
			this.hiddenNodeAddress = hiddenNodeAddress;
		}

		public int getHiddenNodePort()
		{
			return hiddenNodePort;
		}

		public void setHiddenNodePort(int hiddenNodePort)
		{
			this.hiddenNodePort = hiddenNodePort;
		}

		public int getHiddenType()
		{
			return hiddenType;
		}

		public void setHiddenType(int hiddenType)
		{
			this.hiddenType = hiddenType;
		}

		public String getLocalAddress()
		{
			return localAddress;
		}

		public void setLocalAddress(String localAddress)
		{
			this.localAddress = localAddress;
		}

		public int getLocalPort()
		{
			return localPort;
		}

		public void setLocalPort(int localPort)
		{
			this.localPort = localPort;
		}

		public String getExternalAddress()
		{
			return externalAddress;
		}

		public void setExternalAddress(String externalAddress)
		{
			this.externalAddress = externalAddress;
		}

		public int getExternalPort()
		{
			return externalPort;
		}

		public void setExternalPort(int externalPort)
		{
			this.externalPort = externalPort;
		}

		public String getDynDns()
		{
			return dynDns;
		}

		public void setDynDns(String dynDns)
		{
			this.dynDns = dynDns;
		}

		public String[] getIpAddressList()
		{
			return ipAddressList;
		}

		public void setIpAddressList(String[] ipAddressList)
		{
			this.ipAddressList = ipAddressList;
		}

		public int getNetMode()
		{
			return netMode;
		}

		public void setNetMode(int netMode)
		{
			this.netMode = netMode;
		}

		public int getVsDisc()
		{
			return vsDisc;
		}

		public void setVsDisc(int vsDisc)
		{
			this.vsDisc = vsDisc;
		}

		public int getVsDht()
		{
			return vsDht;
		}

		public void setVsDht(int vsDht)
		{
			this.vsDht = vsDht;
		}

		public int getLastConnect()
		{
			return lastConnect;
		}

		public void setLastConnect(int lastConnect)
		{
			this.lastConnect = lastConnect;
		}

		public int getLastUser()
		{
			return lastUser;
		}

		public void setLastUser(int lastUser)
		{
			this.lastUser = lastUser;
		}

		public int getConnectState()
		{
			return connectState;
		}

		public void setConnectState(int connectState)
		{
			this.connectState = connectState;
		}

		public String getConnectStateString()
		{
			return connectStateString;
		}

		public void setConnectStateString(String connectStateString)
		{
			this.connectStateString = connectStateString;
		}

		public int getConnectPeriod()
		{
			return connectPeriod;
		}

		public void setConnectPeriod(int connectPeriod)
		{
			this.connectPeriod = connectPeriod;
		}

		public boolean isFoundDht()
		{
			return foundDht;
		}

		public void setFoundDht(boolean foundDht)
		{
			this.foundDht = foundDht;
		}

		public boolean isWasDeniedConnection()
		{
			return wasDeniedConnection;
		}

		public void setWasDeniedConnection(boolean wasDeniedConnection)
		{
			this.wasDeniedConnection = wasDeniedConnection;
		}

		public int getLinkType()
		{
			return linkType;
		}

		public void setLinkType(int linkType)
		{
			this.linkType = linkType;
		}
	}

	private ShortInviteDetails details;

	public ShortInviteDetails getDetails()
	{
		return details;
	}

	public void setDetails(ShortInviteDetails details)
	{
		this.details = details;
	}
}
