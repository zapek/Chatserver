# Chat Server

This is a chat server (also called introduction server) for [Retroshare](https://retroshare.cc). It allows trying out Retroshare for the first
time without having to organise a group of friends. It can also be a kind of "bootstrap" into the Retroshare network.

This server is running at https://retroshare.ch

## Dependencies

### Backend

- Java 11 (also tested with 15)
- a MySQL server instance (tested with 5.7 and 8.0)

### Frontend

- Angular 10

## Development

Run the backend locally, either with your IDE using the spring _dev_ profile or with gradle:
	
	./gradlew bootRun

If you want to run a Retroshare-service instance, do:

	docker build -t retroshare --build-arg KEEP_SOURCE=true .
(this can take a while, go have a coffee or something)

Then run it with:

	docker run --rm --name retroshare -it -p 127.0.0.1:9092:9092 --mount source=retroshare,target=/root/.retroshare retroshare retroshare-service --jsonApiPort 9092 --jsonApiBindAddress 127.0.0.1

For the frontend:

	ng serve

And open a browser to http://localhost:4200

## Building

Building for production is typically done by dockerizing the components (but it doesn't necessarily have to be).

If you want to go with docker, install docker-compose and do:

	./gradlew bootBuildImage

	docker-compose up -d

The frontend has to be deployed manually. Make sure your server is listed in ```environment.prod.ts```, build it with

	ng build --prod

Then copy the content of ```dist/chatserver-frontend``` in your webserver root. Don't forget to setup a proxy
in case the backend is running on the same host, for example with Apache:

	ProxyPass /v1 http://127.0.0.1:8080/v1
	ProxyPassReverse /v1 http://127.0.0.1:8080/v1

You can put the following environment variables into docker-compose.yml to configure the backend to your taste.

## Environment variables

### Database

- DATABASE_HOST:
  the hostname to connect to (default: localhost)

- DATABASE_NAME:
  the database name (default: chatserver)

- DATABASE_USER:
  the database username (default: chatserver)

- DATABASE_PASSWORD:
  the database password (default: chatserver)

- DATABASE_USE_SSL:
  use SSL for the database connection, not needed for localhost (default: false)

### Misc

- CHATSERVER_RETROSHARE_SERVICE_URL:
  the URL of the retroshare-service API (default: http://localhost:9092)

- CHATSERVER_LOCATION_NAME:
  the name of the location (default: ChatServer)

- CHATSERVER_PROFILE_NAME:
  the name of the PGP profile (default: retroshare.ch)

- CHATSERVER_API_USERNAME:
  the username of the retroshare-service API (default: apiuser)

- CHATSERVER_API_PASSWORD:
  the password of the retroshare-service API (default: randomly generated and stored in the database)

- CHATSERVER_LOBBY_NAME:
  the name of the public lobby (default: retroshare.ch Key Exchange)

- CHATSERVER_LOBBY_TOPIC:
  the topic of the public lobby (default: https://retroshare.ch)

- CHATSERVER_LOBBY_CREATE:
  if true, create the public lobby after 10 minutes of not finding one already on the network. If false, search for it forever (default: true)

- CHATSERVER_MAX_FRIENDS:
  the maximum number of friends before starting to purge the old ones (default: 30)

- WEB_PASSWORD:
  password for the API's protected endpoints (default: random per session, printed in the logs upon startup)

## Docker images

There are 2 readily available docker images if you don't want to build yourself

	docker pull zapek/retroshare-service:0.6.6-rc2

	docker pull zapek/chatserver:0.1.1

Or use the following docker-compose.yml

```yaml
version: '3.7'

services:

  chatserver:
    image: zapek/chatserver:0.1.1
    environment:
      - SERVER_PORT=8080
    network_mode: "host"

  retroshare-service:
    image: zapek/retroshare-service:0.6.6-rc2
    network_mode: "host"
    volumes: 
      - retroshare:/root/.retroshare
    command: retroshare-service --jsonApiPort 9092 --jsonApiBindAddress 127.0.0.1

volumes:
  retroshare:
```

Don't forget to have a MySQL instance somewhere.

## Note

Retroshare-service fails to use UPNP for some reasons. Make sure you redirect the ports as needed. You can pass ```--port 6000``` to use port 6000 for example, as otherwise it'll pickup a random port which will require you to grep the logs to find out.

If you want to transfer docker images between hosts, use:

	docker save -o foo.bar zapek/chatserver:0.1.1

then on the target host

	docker load -i foo.tar

If you want the server to stay friend with you forever, change your trust level in the MySQL
profile table to _MARGINAL_ or _FULL_.

Everything was tested on Windows 10 and Linux (Ubuntu 18.04/20.04).
