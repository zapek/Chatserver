# Dockerfile taken from https://gitlab.com/elRepo.io/Tier1/

FROM ubuntu:18.04 as builder

ARG CACHEBUST=0
RUN \
	apt-get update -y && apt-get upgrade -y && \
	apt-get install -y build-essential libssl-dev libbz2-dev libsqlite3-dev \
		libsqlite3-dev libupnp-dev pkg-config libz-dev \
		qt5-default libxapian-dev qttools5-dev doxygen rapidjson-dev \
		git cmake

ARG FRESHCLONE=0
#ARG REPO_URL=https://gitlab.com/g10h4ck/RetroShare.git
ARG REPO_URL=https://github.com/RetroShare/RetroShare.git
ARG REPO_BRANCH=master
ARG REPO_DEPTH="--depth 2000"
ARG KEEP_SOURCE=false
RUN git clone $REPO_DEPTH $REPO_URL -b $REPO_BRANCH && cd RetroShare && \
	git fetch --tags && cd ..
RUN \
	mkdir RetroShare-build && cd RetroShare-build && \
	qmake ../RetroShare CONFIG+=no_libresapilocalserver \
		CONFIG+=no_retroshare_android_notify_service \
		CONFIG+=no_retroshare_plugins \
		CONFIG+=no_sqlcipher \
		CONFIG+=no_retroshare_nogui CONFIG+=no_retroshare_gui \
		CONFIG+=retroshare_service CONFIG+=no_libresapi \
		CONFIG+=no_tests CONFIG+=no_libresapihttpserver \
		CONFIG+=rs_jsonapi CONFIG+=no_retroshare_android_service && \
	(make -j$(nproc) || make -j$(nproc) || make) && make install && \
	cd .. && rm -rf RetroShare-build && ($KEEP_SOURCE || rm -rf RetroShare)
FROM ubuntu:18.04
RUN apt-get update -y && apt-get upgrade -y && \
	apt-get install -y libsqlite3-0 libssl1.1
COPY --from=builder /usr/bin/retroshare-service /usr/bin
RUN mkdir /usr/share/retroshare
COPY bdboot.txt /usr/share/retroshare/