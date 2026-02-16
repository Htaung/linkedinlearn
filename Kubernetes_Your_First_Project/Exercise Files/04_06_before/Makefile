#!/usr/bin/env make

.PHONY: run_website install_kind install_kubectl create_kind_cluster create

run_website:
	docker build -t explorecalifornia.com . && \
		docker run -p 5000:80 -d --name explorecalifornia.com --rm explorecalifornia.com

install_kubectl:
	brew install kubectl || true;

install_kind:
	curl -o ./kind https://github.com/kubernetes-sigs/kind/releases/download/v0.11.1/kind-darwin-arm64

create_kind_cluster: install_kind install_kubectl create_docker_registry
	kind create cluster --image=kindest/node:v1.21.12 --name explorecalifornia.com --config ./kind_config.yaml || true
	kubectl get nodes

create_docker_registry:
	if docker ps | grep -q 'local-registry'; \
	then echo "--> local-registry already created; skipping"; \
	else docker run --name local-registry  -d --restart=always -p 5000:5000 registry:2; \
	fi
