#!/usr/bin/env make

.PHONY: run_website install_kind stop_website create_kind_cluster install_kubectl

run_website:
	docker build -t explorecalifornia.com . && \
		docker run -p 5000:80 -d --name explorecalifornia.com --rm explorecalifornia.com

install_kind:
	curl -o ./kind https://github.com/kubernetes-sigs/kind/releases/download/v0.11.1/kind-darwin-arm64

stop_website:
	docker stop explorecalifornia.com

create_kind_cluster: install_kind install_kubectl
	kind create cluster --name explorecalifornia.com && \
	kubectl get nodes

install_kubectl:
	choco install kubernetes-cli