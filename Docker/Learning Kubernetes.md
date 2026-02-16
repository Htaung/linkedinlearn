# Cloud Native Technologies

Cloud Native technologies empower organizations to build and run scalable applications in modern dynamic environments such as **public**, **private**, and **hybrid clouds**.

## Key Technologies

- 🐳 **Containers**  
- 🔗 **Service Meshes**  
- 🧩 **Microservices**  
- 🏗️ **Immutable Infrastructure**  
- 📝 **Declarative APIs**  

## Benefits

> These technologies are designed to allow technologists to use cloud computing services to automatically deploy and scale applications.

## About CNCF

The <span style="color:#2b8cbe;font-weight:bold">Cloud Native Computing Foundation (CNCF)</span> supports these technologies and aims to make cloud native computing ubiquitous.

---

cloud native technology => open source project =>
=> to used cloud computing services to automatically deploy and scale applications

CNCF Project Maturiy Designations
Graduated(ready to used in prod), Incubating (limited no. of company), Sandbox(new experiment)

## minikube create a cluster from scratch
``` minikube start ```

## to check the status of kubernetes
```	kubectl cluster-info	```

## List all nodes in the cluster
``` kubectl get nodes ```

## namespaces
- let you isolate and organized your workloads
### List all namespaces (short format)
``` 
kubectl get ns 
kubectl get namespaces
```

pods and services that are installed



## Pods
- pods how container runs in kuebernetes
- pods are sw required to run kubernetes cluster itself 
- pods are kubernetes resources that run app and microservices 
- organizatied the pods using kubernetes deployment

### List all the pods in every name space (-A check pods in every namespace)
``` kubectl get pods -A ```

## to check running service
- serivce acts a load balancer withing cluster and direct traffics to pods 
``` kubectl get services -A ```



## Cloud Native Technologies (often paired with kubernetes)
- Infrastructure as Code(IaC)
- GitOps

## YAML Development

namespace.yaml file
```
---
apiVersion: v1
kind: Namespace
metadata:
  name: development
```

### To create namespace
``` 
kubectl apply -f namespace.yaml

kubectl delete -f namespace.yaml 
```


#### kind is kubernetes object
``` kind: development ```

``` kubectl apply -f deployment.yaml ```

#### To get info of deployments by namespace development
``` kubectl get deployments -n development ```

#### To get list of pods
``` kubectl get pods -n development ```

#### To delete pod 
``` kubectl delete pod pod-info-deployment-68c8476764-h87wz -n development ```

#### whenever deleted a pod kubernetes create 3 pods coz have setup in replicas as 3

#### to get the info of pod (to find out the pods is up and running correctly for troubleshooting)
``` kubectl describe pod pod-info-deployment-68c8476764-pbk9j  -n development	```

##### to verify App development is running deploy the busy box and ssh into those busy box and 
##### check with wget to verify existing app deployemnt
#### to check app is up and running
``` kubectl apply -f busybox.yaml ```

#### to get the ips of development
``` kubectl get pods -n development -o wide ```

#### to get bin/bash to busy box (it => iteractive terminal)
``` 
kubectl exec -it busu -- /bin/sh
wget 10.244.0.9:3000
cat index.html
``` 

#### When a Kubernetes cluster starts, it binds to a specific localhost:port (like 127.0.0.1:61371
#### If Minikube restarts, the port can change, but your kubeconfig file might still point to the old dead port, making kubectl fail
#### minikube stop before shutting down your machine.
``` 
minikube stop && minikube start && minikube update-context
docker context use default
minikube status

```

kubectl logs podName -n development

#### Challenges
quote.yaml
Named the development and name app label quote-service
-- development namespace
-- quote-container
-- two replicas
-- use image datawire/quote:0.5.0
-- set container to accept traffics at port 7080 (mine docker use 8080)
-- optional => use busy box to test app can accept traffic from inside the cluster

#### kubernetes services
- load balancer, direct traffic from internet to kubernetes port
- public ip and static ip (dynamic) - service ip remain the same


``` 
  ---
apiVersion: v1
kind: Service
metadata:
  name: demo-service
  namespace: development
spec:
  selector:
    app: pod-info
  ports:
    - port: 80
      targetPort: 3000
  type: LoadBalancer
```
service port => 80 => service directing port to 3000

```
minikube tunnel
kubectl get services -n development
NAME           TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)        AGE
demo-service   LoadBalancer   10.110.48.109   127.0.0.1     80:30835/TCP   49s
```
##### minikube tunnel manually sets up an IP for you and routes traffic from your local machine to the Kubernetes service
##### minikube tunnel creates a network bridge between your local machine (Windows/macOS/Linux) and the Kubernetes LoadBalancer IPs inside Minikube.

how much memory and cpu to reserve on a worker node 
how to set cpu, memory requests and limits on the containers in your pods
resources => amount of available cpu and memory on the worker nodes running your pods

https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/
#### copy yaml file content from kubernetes doc
```
resources:
      requests:
        memory: "64Mi"
        cpu: "250m"
      limits:
        memory: "128Mi"
        cpu: "500m"
```
#### don't schedule node unless it has at least memory 64Mb and cpud 250m
#### stop running container if exceed limis of memory 128 and cpu 500m

if you deploy a pod without set of resource requests, can be schedule on node that doesn't have enough processing power or memory, cause node to failed
can start a pod on node without set of resource limits => can be failed due to more loading
Failed nodes => cause outages


minikube is resource intensive app
best to clean your memory and stop no using 

#### delete pods
kubectrl delete -f file.yaml

#### delete the name space last ( to delete all pods associated with name space )
``` kubectl delete -f  namespace.yaml	```


#### delte minikube cluster
``` minikube delete ```

## instance of kubernetes is called cluster, each cluster have control plane and at least one worker node
- control plane is like air traffice control tower with people overlooking cluster
- and make sure pods and nodes are CRUD without any issues

### Kuberntes control plane
- is etcd, open source, HA key vlaue store
- it save all the data about the state of kubernetes cluster 
- Only Kube API Server can communicate directly with etcd
- etcd is run as pod
- how etcd works find in logs
-- can see etcd most recent operation
``` kubectl logs etcd-minikube -n kube-system | jq .	```
-- contains components that manage cluster and enable resilency and automation that make kubernetes popular container orchstrator 
-- if you are using managed kubenetes services (AWS's EKS or Google's GKE)
-- you will not be able to see your control plane nodes using kubectl 
-- those are hidden because cloud provider handles all the maintenance of those components for you

### Kube API Server
- expose kubernetes api
- each kubernetes obj like pods, deployment and horizontal pod autoscaler have api end point
- kubectl and kubeadm are cli tools to communicate with kubernetes api via http requests

### Kubernetes API
- kubernetes api have rest interface and kubectl
- kubernetes api is a containerized app run as pod
- kubernetes api is a kubenetes component that handle most request from user and inside the cluster without it kubenetes cluster cannot exists


### Kube Scheduler
- identify newly created pod that have not been assigned a worker node and then choose a node for pod to run on
- run as pod
`` kubectl describe pod kube-scheduler-minikube -n kube-system ``

### Kube Controller Manager
- loop that runs continually and check the status of cluster - to make sure things are running properly
- check all workers nodes are up and running
- if it finds sth broken - remove the broken node and replace with new worker node
- create and check serveral other things in cluster 

### Cloud Controller Manager
- let you connect your cluster with cloud provider's API to use cloud resources (AWS, GCP, Azure, or any public cloud)

#### To see all kubernetes obj and their api version
``` kubectl api-resources ```

#### To list all pods
``` kubectl get pods -A	```

#### To list kube-system 
``` kubectl -n kube-system get pods ```



## Components that make up kubernetes worker nodes
- Kuberntes is air port
- Control plane is Air traffic control tower
- Worker Nodes are busy Terminal where plane park and passenger load
- To get HA most kubernetes cluster run with a minimum of 3 worker nodes
- worker nodes are where pods are scheduled and run
- Each worker node have 3 components
  - Kubelet
    - is agent runs on every worker node
	- Makes sure containers in a pod are running and healthy
	- communicate directly with API Server in control plane, is looking for newly assigned pods
	- The kubelet is the only worker node component that communicates with the kube-apiserver.
  - Container Runtime
    - Once the kubelet has been assigned a new pod, start the container or container using Container Runtime Interface (CRI)
	  - CRI enable the kubelet to create containers with the engine
	    - Containerd
		- CRI-O
		- Kata Containers
		- AWS Firecracker
	- in kubernetes v1.24, dockershim was removed docker engine can no longer be used as container runtime 
	but docker images still work in kubernetes coz docker images and docker containers are two diff things
  - Kube-proxy
    - Make sure pods and services can communicate with other pods and services on nodes and in control plane
	- each Kube-proxy communicate directly with kube-api server
	


	
	
### how a pod gets scheduled onto a node
- one operaion in kuberntes cluster 
- Kube api server regularly processes hundreds or thousands of requests to keep your cluster up and running
- more ref => 2021 KubeCon North America Conference (Beyound Block Diagrams)
- see in time_seq_diagram_kubernetes.png
- deployment.yaml file give permission to to kubectl to communicate with our Kubernetes cluster 
- kubectl command send the info to Kube Api server


## Kubernetes Cluster Components  
- A Comprehensive Glossary of Kubernetes Cluster Components

### Location in Cluster: Control Plane

- Cloud Controller Manager: Connects a Kubernetes cluster to a cloud provider's API, managing cloud-specific resources and ensuring proper integration with the underlying infrastructure
- etcd: A key-value store that saves all data about the state of the cluster; only the kube-apiserver can communicate directly with etcd
- kube-apiserver: The kube-apiserver is a key component of Kubernetes that exposes the Kubernetes API, handles most requests, and manages interactions with the cluster by processing and validating API requests, making it essential for the cluster's operation
- kube-controller-manager: Monitors the Kubernetes cluster's state, running processes to ensure the current state matches the desired state
- kube-scheduler: Identifies a newly created pod that has not been assigned a worker node and assigns it to a specific node

### Location in Cluster: Worker Nodes 

- Container Runtime: Pulls container images, creates and manages containers, and ensures they run properly and securely as directed by the Kubernetes control plane
- kube-proxy: A network proxy that runs on each node in a Kubernetes cluster, maintaining network rules and enabling communication between pods and services within the node and the control plane, while also communicating directly with the kube-apiserver
- kubelet: An agent that runs on each node in a Kubernetes cluster, ensuring containers in a pod are running and healthy while communicating with the API server in the control plane to maintain the desired state of the node

#### What function does the Kubelet component perform on a worker load?
- Check that the containers are healthy.

#### When you launch a pod with a new container image, which component pulls the image from the image repository?
- Kubelet


#### When the user is applying a new deployment, which two Kubernetes components are involved in the actual step of binding a pod to a node?
- api-server and kubelet

#### The kube-proxy is the only worker node component that communicates with the kube-apiserver.
- False

#### Which of these is not a component of the Kubernetes Control Plane?
- kubelet



kubernetes deployment is to deploy containerized apps

## deployment
- replicas
- kubernetes can keep old version up and running and roll out new version, ensure new pods are running and remove the old pods
- no downtime upgrades, automate features 

## daemonset
- DaemonSets allow you to run one pod per node, which works well for running pods implementing background processes such as agents.
- Another way to deploy pods is daemonset 	
- One pod per node (put one copy of a container on every node running in cluster, can't directly control)
- background processes (run containers that are agents or daemons, running processes in the background, run a program
  that collects metrics about underlying node and other pods on that node)
- final way to deploy more than one pod at a time is kubernetes job

## job
- Runs until completion (will create one or more pods and run the container inside of them until it has successfully completed its task)
- eg. is app that you deploy in a testing cluster that will generate a batch of data for your testing framework
- Delete pods (only need to generate that data once in a while and you can delete app once it's done)

## Database
- 1. db independent of your cluster( db that is running outside of your cluster)
- 2. kubernetes Persistence Volumes (making stateful app work in your cluster)
  - type of data storage that exists in your cluster and remain even after a pod is destroyed
  - can use kubernetes object called a stateful set, make sure updated app can communicate with same voulume as previous pod
- data storage in kubernetes is big topic 
- A statefulSet is an object that lets an updated Kubernetes application communicate with the same volume as the previous pod.
  
## kubernetes Security
- Any internet server is susceptible to attack
- threads are always changing
- best practices 

- hackers are trying to achieve one of three things:
  - steal data from inside the Cluster
  - hardness the power computation cluster, to setup cryptocurrency mining operaton or DDoS attack
  06_03
- add security context to the pod
``` 
	securityContext:
          allowPrivilegeEscalation: false
          runAsNonRoot: true
          capabilities:
            drop:
              - ALL
          readOnlyRootFilesystem: true
```
- scan kubernetes yaml with a security tool ``` snyk iac test ```
  - scan your Infrastructure as code file including kubernetes manifests
  - ``` snyk iac test deployment.yaml  ```
  - curl https://static.snyk.io/cli/latest/snyk-win.exe -o snyk.exe 
  - To scan your open-source packages for vulnerabilities ensure all dependencies are installed or there is a supported lockfile. Then, run: 
    - snyk monitor --all-projects --org=8d9d3785-0741-42d5-b201-7d2923717e2a
  - Scan your Infrastructure as Code (IaC) files for vulnerabilities. Run:
    - snyk iac test --report --org=8d9d3785-0741-42d5-b201-7d2923717e2a
	- snyk iac test secure-deployment.yaml 
  - regularly update kubernetes version when security patches and updates are released
  - kubernetes hardening guide released by US National Security Agency
  
 ## Conclustion
 - recommend to follow up https://www.cncf.io/
 - Master Cloud-Native Infrastructure with kubernetes
 - watch talk from past KubeCon Conference https://www.youtube.com/c/cloudnativefdn
 - excellent way to practice working with a cluster is by taking one of the linux Foundation
   - kubernetes certification exams
     - kubernetes and Cloud Native Associate exam,
	 - Certified Kubernetes App developer Exam,
	 - Certified Kubernetes Adminsitrator Exam,




https://www.linkedin.com/learning/learning-kubernetes-16086900/ways-to-manage-kubernetes-pods?autoSkip=true&resume=false&u=116771770


https://www.linkedin.com/learning/gitops-foundations/exercise-file-setup?autoSkip=true&resume=false&u=116771770