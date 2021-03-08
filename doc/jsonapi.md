# Some note about JSON API

## Interesting links

https://gitlab.com/elRepo.io/elRepo.io-android/-/blob/master/lib/retroshare.dart

https://gitlab.com/elRepo.io/Tier1

To get API documentation, just search the Retroshare source code for "@jsonapi".

The API is located at http://127.0.0.1:9092/

## Startup

Create a location:

POST http://127.0.0.1:9092/rsLoginHelper/createLocationV2
```json
{
	"locationName": "JsonTest",
	"pgpName": "ZapekJson",
	"apiUser": "apiUser",
	"apiPass": "1234"
}
```
Response: 
```json
{
	"locationId": "1e90dc0233e8435cd8ffc5c0bc016354",
	"pgpId": "DD3AD3EEC808D9E6",
	"retval": {
		"errorNumber": 0,
		"errorCategory": "generic",
		"errorMessage": "Success"
	}
}
```

^- record the LocationId. We need it to login again.

GET http://127.0.0.1:9092/rsLoginHelper/isLoggedIn
```json
{
	"retval": true
}
```

Then if I run RS again, I would have to do:

POST http://127.0.0.1:9092/rsLoginHelper/attemptLogin
```json
{
	"account": "1e90dc0233e8435cd8ffc5c0bc016354",
	"password": "1234"
}
```
Response:
```json
{
	"retval": 0
}
```

Then check if the core is ready:

GET http://127.0.0.1:9092/rsControl/isReady
```json
{
	"retval": true
}
```

The next endpoints require authentication (http basic auth) with apiuser and apipassword.

Creating an identity:
POST http://127.0.0.1:9092/rsIdentity/createIdentity
```json
{
	"name": "ChatServer",
	"pseudonimous": false,
	"pgpPassword": "1234"
}
```
Response:
```json
{
	"id": "ab551f979c4900f5e03c50cc539d892e",
	"retval": true
}
```
		
(the pgpPassword signing fails somehow... API claims it's not implemented :/)

To get the details of an id:
POST http://127.0.0.1:9092/rsIdentity/getIdDetails
```json
{
	"id": "ab551f979c4900f5e03c50cc539d892e"
}
```
Response:
```json
{
	"details": {
		"mId": "ab551f979c4900f5e03c50cc539d892e",
		"mNickname": "ChatServer",
		"mFlags": 14,
		"mPgpId": "1E51890D0A7D4E61",
		"mReputation": {
		"mOwnOpinion": 1,
		"mFriendsPositiveVotes": 0,
		"mFriendsNegativeVotes": 0,
		"mFriendAverageScore": 1.0,
		"mOverallReputationLevel": 2 },
		"mAvatar": {
			"mData": {
				"base64": ""
			}
		},
		"mPublishTS": {
			"xint64": 1611395120,
			"xstr64": "1611395120"
		},
		"mLastUsageTS": {
			"xint64": 1611395120,
			"xstr64": "1611395120"
		},
		"mUseCases": [
		{
			"key": {
				"mServiceId": 529,
				"mUsageCode": 14,
				"mGrpId": "00000000000000000000000000000000",
				"mMsgId": "0000000000000000000000000000000000000000",
				"mAdditionalId": {
					"xint64": 0,
					"xstr64": "0"
				},
				"mComment": "",
				"mHash": "92a8b5cb2982a117cb98f6cc47a23734977a46af"
			},
			"value": {
				"xint64": 1611395120,
				"xstr64": "1611395120"
			}
		}
		]
	},
	"retval": true
}
```

To get our own signed ids:
GET http://127.0.0.1:9092/rsIdentity/getOwnSignedIds
```json
{
	"ids": [
		"ab551f979c4900f5e03c50cc539d892e"
	],
	"retval": true
}
```

Well, somehow it thinks it's signed...

To get the list of PGP profiles:
GET http://127.0.0.1:9092/rsAccounts/GetPGPLogins

Then to add a friend:

First parse the short invite to extract the id, see below, then:

POST http://127.0.0.1:9092/rsPeers/acceptInvite
```json
{
	"invite": "ABDu/Eq/Yf+lxejB4lI+0XGUAxRlJRB9kRNjE6tVrb0eUYkNCn1OYQEJWmFwZWtKc29ukgb+2cNVaFkEA4FTIQ==",
	"flags": 0
}
```
Response:
```json
{
	"retval": true
}
```

To remove a friend (PGP, which means all its locations. We have to do that otherwise he would be able to connect forever from other locations):
POST http://127.0.0.1:9092/rsPeers/removeFriend
```json
{
	"pgpId": "008705C1C2803F94"
}
```
Response:
```json
{
	"retval": true
}
```
To get our own short invite:
POST http://127.0.0.1:9092/rsPeers/getShortInvite
```json
{
	"sslId": "00000000000000000000000000000000",
	"inviteFlags": 13
}
```
Response:
```json
{
	"invite": "ABDu/Eq/Yf+lxejB4lI+0XGUAxRlJRB9kRNjE6tVrb0eUYkNCn1OYQEJWmFwZWtKc29ukgb+2cNVaFkEA4FTIQ==",
	"retval": true
}
```

### Misc

To parse a ShortInvite:
POST http://127.0.0.1:9092/rsPeers/parseShortInvite

```json
{
	"invite": "ABDu/Eq/Yf+lxejB4lI+0XGUAxRlJRB9kRNjE6tVrb0eUYkNCn1OYQEJWmFwZWtKc29ukgb+2cNVaFkEA4FTIQ=="
}
```
Response:
```json
{
	"details": {
		"isOnlyGPGdetail": false,
		"id": "eefc4abf61ffa5c5e8c1e2523ed17194",
		"gpg_id": "1E51890D0A7D4E61",
		"name": "ZapekJson",
		"email": "",
		"location": "",
		"org": "",
		"issuer": "0000000000000000",
		"fpr": "6525107D91136313AB55ADBD1E51890D0A7D4E61",
		"authcode": "",
		"gpgSigners": [
			"1E51890D0A7D4E61"
		],
		"trustLvl": 5,
		"validLvl": 5,
		"ownsign": true,
		"hasSignedMe": true,
		"accept_connection": false,
		"service_perm_flags": 0,
		"state": 0,
		"actAsServer": false,
		"connectAddr": "",
		"connectPort": 0,
		"isHiddenNode": false,
		"hiddenNodeAddress": "",
		"hiddenNodePort": 0,
		"hiddenType": 0,
		"localAddr": "",
		"localPort": 0,
		"extAddr": "85.195.217.254",
		"extPort": 26713,
		"dyndns": "",
		"ipAddressList": [],
		"netMode": 0,
		"vs_disc": 0,
		"vs_dht": 0,
		"lastConnect": 0,
		"lastUsed": 0,
		"connectState": 0,
		"connectStateString": "",
		"connectPeriod": 0,
		"foundDHT": false,
		"wasDeniedConnection": false,
		"deniedTS": {
			"xint64": 0,
			"xstr64": "0"
		},
		"linkType": 1
	},
	"err_code": 16,
	"retval": true
}
```

To turn off the server POST http://127.0.0.1:9092/rsControl/rsGlobalShutDown
```json
{
	"pgpIds": [
		"1E51890D0A7D4E61"
	],
	"retval": 1
}
```