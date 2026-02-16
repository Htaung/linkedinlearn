#!/usr/bin/env make

.PHONY: run_website install_kind install_kubectl create_kind_cluster \
	create_docker_registry connect_registry_to_kind_network connect_registry_to_kind \
	create_kind_cluster_with_registry

run_website:
	docker build -t explorecalifornia.com . && \
		docker run -p 5000:80 -d --name explorecalifornia.com --rm explorecalifornia.com

install_kubectl:
	brew install kubectl || true;

install_kind:
	curl -o ./kind https://github.com/kubernetes-sigs/kind/releases/download/v0.11.1/kind-darwin-arm64

create_kind_cluster: install_kind install_kubectl create_docker_registry
	#kind create cluster --image=kindest/node:v1.21.12 --name explorecalifornia.com --config ./kind_config.yaml || true
	#kind create cluster --name explorecalifornia.com --config ./kind_config.yaml || true
	#kubectl get nodes
	if ! kind get clusters | grep -q 'explorecalifornia.com'; \
	then kind create cluster --name explorecalifornia.com --config ./kind_config.yaml || true && kubectl get nodes; \
	else echo "---> deleting local-registry and creating local-registry" && kind delete cluster --name explorecalifornia.com && \
	kind create cluster --name explorecalifornia.com --config ./kind_config.yaml || true && kubectl get nodes; \
	fi

create_docker_registry:
	if ! docker ps | grep -q 'local-registry'; \
	then docker run -d -p 5000:5000 --name local-registry --restart=always registry:2; \
	else echo "---> deleting local-registry and creating local-registry" && docker rm -f local-registry && \
	docker run -d -p 5000:5000 --name local-registry --restart=always registry:2;  \
	fi

connect_registry_to_kind_network:
	docker network connect kind local-registry || true

connect_registry_to_kind: connect_registry_to_kind_network
	kubectl apply -f ./kind_configmap.yaml

create_kind_cluster_with_registry:
	$(MAKE) create_kind_cluster && $(MAKE) connect_registry_to_kind


