#!/usr/bin/env make
.PHONY: run_website stop_website install_kind

run_website:
	docker build -t explorecalifornia.com . && \
		docker run -p 5000:80 -d --name explorecalifornia.com --rm explorecalifornia.com
stop_website:
	docker stop explorecalifornia.com

install_kind:
	curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.29.0/kind-linux-amd64 && \
	kind --version
