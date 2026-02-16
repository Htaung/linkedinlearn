#!/usr/bin/env make

run_website:
	docker build -t explorecalifornia.com . && \
		docker run -p 5000:80 -d --name explorecalifornia.com --rm -d explorecalifornia.com

delete_website:
	docker rm -f explorecalifornia.com 2>/dev/null || echo Container not found && \
    docker image rm -f explorecalifornia.com  2>/dev/null || echo Image not found

stop_website:
	docker stop explorecalifornia.com
