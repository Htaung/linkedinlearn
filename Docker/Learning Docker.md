package app into images that run on container, images are created from light weight config files
container are viruatalized os that are configure enough to run your app

container viruatalized os kernels

VM run on platform called hypervisor, translate operations on emulated hw within vm like memory,processors,disk, etc
VM are actual V computer, need to install os on each vm, confguring app,
this make possible to run many diff apps together on same hypervisor securely

Container run on container run time
container run time works with OS to allocate hw and copy files and dir including parts with your app in it
container run time do not translate anything, every app and container uses same hw and os as system they are running on
container run time do not need bootup like VM do, allows app to start very quickly
container run time do not need v memory, v discs to work
take less space
allow to you run more apps at same time than vm
container can only run one app at a time
container share same os as their host, can interact with their host, can modify the host they are running on
security issue, mostly solve fortunately



container
run in container run time
work along side os
not require os config
run one app at a time

VM
run on top of hypervisor
need hw emulation
require os config
can run many apps at once

container composed of two things:
linux name space(Host) - limits what you can see 
and linux control group(Kernel) - limits how much any resource you can see


linux name space(Host) - limits what you can see 
linux Kernel feaure that provides ability to expose diff views of your system to app running within it
can be say route super user with access to entire file system or user AA with access to single folder or super user
linux Kernel provides 8 names spaces

USERNS - User lists
MOUNT  - Access to file system
NET    - Network Communication
IPC    - Interprocess Communication	
TIME   - ablity to change time (Not supported by docker)
PID    - process id management 
CGROUP - Create Control Group
UTC    - Create host/domain names


linux control group(Kernel) - limits how much any resource you can see
- monitor and restrict cpu usage
- monitor and restrict network and disk bandwidth
- monitor and restrict memory consumption
makes it easy to prevent busier, larger container from eating all sys's resources and slowing other container down 
without having to carve up significant amounts of memory like VM do
-- cannot use assign disk quotas (Not supported by docker)
-- but some container storage solution allow you do it 

-- docker only run natively on linux


container can run anything but their images are tied, images are bound to their parent os
container created from container images configured for linux Kernel can only run on linux
-- there's been workaround to solve those issue

chroot or change root syscall
-- allowing admin to change what apps can see even when running as root (JAIL)
Chroot Jail: Running isolated applications, testing software, or restricting untrusted users.
Chroot Jail (Linux): Used to create an isolated environment for applications or users, limiting access to the rest of the system.
Chroot Jail: Restricts a process or user to a specific directory, preventing access to files outside that directory.
Chroot Jail: Increases security by limiting what a process can do.
https://linuxhint.com/setup-linux-chroot-jails/


BSD jail and solaris zone allow admin to create entire virtual environment for apps
build on chroot in big way => without needing emulate hw like VM can

like today admin could restrict what process saw or used in jail or zone, without process knowing that are being restricted

linux gain superpower in 2007 with linux container or LXC for short

Linux container used control group and namespaces to provide much of same functionality as BSD jail can 
https://linuxcontainers.org/lxc/getting-started/


Docker builds on LXC (though it now uses its own runtime, containerd) to provide a standardized, 
user-friendly way to build, distribute, and run containers.

docker makes configuring and packaging apps and their env really easy
docker user docker file to package app and its env into images
docker makes it easy to share images
user can share app images through docker hub
global repo of images maintained by docker
docker command line make it easy to start your app in container 

Docker Machine
use VBox to create VMs that only run Docker
user Oracle VBox to create small linux based VM to run only docker engine
once created user need to run small shell script to connect their docker cli with VM
have two key problems
1. User need to know how to use VBox, VBox manage knowledge
2. Slower than docker on linux, slow networking performance when exposing port

these twos are dependant on VBox, are out of Docker's control

docker release docker desktop
use much smaller and must faster VM that run on apples's native hypervisor called virtual kit or hyper v on windows - more tightly  integrated VM
automaticallhy handles mounting volume and network port mapping
comes with nice GUI
creating kubernetes clusters

Licensing change in 2021 created community of docker desktop alternative

Alternative to Docker Desktop
Use Docker Engine inside a Linux virtual machine (e.g., WSL2 on Windows).

Free for Personal and Small Business Use
Paid Plans for Larger Businesses
Enterprise Use Requires a Subscription
use Docker Engine on Linux for free without license restrictions.


install docker on linux
curl -o /tmp/get-docker.sh https://get.docker.com/
sh /tmp/get-docker.sh

sudo usermod -aG docker username or $USER


container are created from container images, compressed and pre-package file system, app along with its env and config,with an instruction 
how to start app => that instruction is called entry point

# 1. create container from image, if image not exists on your computer docker will try to retrieve it from container image registry
default => docker tried to pull from docker hub

docker container create => create container => doesn't start container

to get specific version of linux use => tagging with :
to get specific version of linux use => tagging with :
docker container create hello-world:linux


# <code> docker ps --all </code>

# to start container
<code> docker container start containerId </code>


#docker logs containerId

#To see logs in terminal -- attach terminal to to container's output
<code> docker container start --attach containerId or suffixOfContainerId </code>


solve all the logs on console and start the container
- docker run automatically create a container from hello-world image, started it, attached it to container, to show as its output immediately
#docker run hello-world

#docker run --name containerName -d imagename:tag

#docker run = docker container create + start + attach


chapter 03_05 => 

FROM => can be existing image either local or internet => default fetch image from docker hub
LABEL => 
USER root => default docker use root user
COPY ./entrypoint.bash / =>  copy file from dir provided to docker build command to container image 
						 => dir provided to docker build is called context => context is usually working dir
RUN apt -y update =>  commands that customized image => place to install additional s/w or config files needed by app
example script need curl and bash => RUN command used to installed those programs

ENTRYPOINT [ "/entrypoint.bash" ] => container created from this image should run => can use CMD command as well

default docker use root

# -t tag list . current dir
docker build -t first-image .

docker build -t first-image /path/to/app

#docker file look for Dockerfile by default

if Dockerfile was rename to app.DockerFile use # docker build -t first-image -f app.DockerFile

docker images are just layer of images compressed together

docker create image for every command in Dockerfile called intermediate images,
after finish squash all images to single image, final image Container created from Dockerfile

#docker run first-image

chapter 03_06 => 
server.Dockerfile
# COPY ./server.bash /
docker build -f server.Dockerfile -t first-server .
docker run first-server
#docker run attach to terminal after it started
container will not accept keystroke from terminal even if attached to them

docker ps --all
docker kill containerId

#detach 
docker run -d first-server

#docker exec 3ba date
can use to start terminal within the container

# to start terminal within the container
docker exec --interactive --tty 3ba bash


stop at here
https://www.linkedin.com/learning/learning-docker-17236240/stopping-and-removing-the-container?autoSkip=true&contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&resume=false&u=116771770


docker stop containerId or prefix

#force stop will be data loss
docker stop -t 0
docker stop -t 0 containerId or prefix
docker rm containerId or prefix

#show only id
docker ps -aq 

#reomve every container that list is docker ps # xargs take each id from left # linux commands
docker ps -aq | xargs docker rm 

#list dockcer images
docker images

#docker delete image
docker rm image-name

#force delete docker images
docker rm -f first-server


run in ubuntu or linux
03_08

# . current working directory
docker build -t our-web-server -f web-server.Dockerfile .

#have change the web-server.Dockerfile
FROM ubuntu:22.04

docker run -d our-web-server

#to name container 
docker run -d --name our-web-server-container our-web-server

#docker logs containerName
docker logs our-web-server-container

#docker remove Container
docker rm our-web-server-container

#bind port outside:inside
docker run -d --name our-web-server-container -p 5001:5000 our-web-server

#it was run and created in docker container not on your local laptop
docker run --rm --entrypoint sh ubuntu -c "echo 'Hello there' > /tmp/file && cat /tmp/file"


#-v => voulme(disk) , outsidePath:insidePath  => outsidePath mean your local storage
docker run --rm --entrypoint sh -v /tmp/container:/tmp ubuntu -c "echo 'Hello there' > /tmp/file && cat /tmp/file"

# will not get error, it will automatically created folder
docker run --rm --entrypoint sh -v /tmp/not_exist:/tmp ubuntu -c "echo 'Hello there' > /tmp/file && cat /tmp/file"

push your images to container images registry
images are track by tags name:version

register in docker hub

#docker login


#rename docker images username/
docker tag our-web-server aungtrt1018/web-server:1

#push image to docker hub
docker push aungtrt1018/web-server:1


docker tag our-web-server aungtrt1018/web-server:2

#  Layer already exists if same images push again
docker push aungtrt1018/web-server:2


# to delete images on docker hub 
go to docker hub and click repo setting and then delete

# NGINX

Name container => website
http://localhost:8080
container is remove when done

03_14 inside
MAP $PWD/website to /usr/share/nginx/html if you volume mount


cd D:\Learning\Linkedin Learn\Docker\Ex_Files_Learning_Docker\Exercise Files\03_14_before

docker build -t website -f web-server.Dockerfile .


#stop here have to study again
https://www.linkedin.com/learning/learning-docker-17236240/solution-starting-nginx?autoSkip=true&contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&resume=false&u=116771770

docker run --name website -v "%cd%/website:/usr/share/nginx/html" -p 8082:5000 --rm nginx

docker run --name website -v "$PWD/website":/usr/share/nginx/html -p 8082:80 --rm nginx

You ran docker run without providing the --interactive option and cannot stop the web server hosting your web app.

Correct

Nice one. In order to provide keystrokes to your container, you need to provide --interactive to tell Docker to enable keystrokes for this container.

# docker system prune
remove all stopped container
all networks not used by at least one container
all dangling images
all dangling build cache

Ctrl + l => clear screen
Ctrl + r => type your command to search and then type left arrow to choose => to search again press Ctrl +R

docker run -name=app --rm openjdk:19

https://www.linkedin.com/learning/learning-docker-17236240/help-my-container-is-really-slow?autoSkip=true&contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&resume=false&u=116771770

#docker debugging

docker run --name=alpine --entrypoint=sleep -d alpine infinity

docker exec -i -t alpine sh
docker exec -d alpine sleep infinity
docker stats alpine
docker top alpine
docker inspect alpine
docker inspect alpine | less => during less can search by using /Host
docker exec -d alpine hostname

-i -t 

docker build -i -t challenge .
docker run -it --name=app challenge

# use tools called Container images scanner to avoid unverified docker images
# clair trivy dagda

#Avoid tagging as latest
#Use non root user 
#default USER nobody
#docker run --rm -it --user non-root image:1.0.0

#in Dockerfile 
when creating image

COPY /app.sh /
#run as root
RUN chmod +x /app.sh 

#run as non root
USER nobody
ENTRYPOINT [ "/app.sh" ]


#Multiple components have dependency on each other
Roll them into one giant docker image (don't)
Create bunch of container and link them together (Time consuming and error prone)

#Docker composed => docker-compose up solve #Multiple components have dependency on each other
- make starting and connecting multiple containers as easy as docker-compose up

#Challenges
difficult to link docker networks together across hosts
Controlling docker container across multiple hosts is cubersome
No built in solutions for moving containers from host to host
Prod concerns (Load balancing and securing traffic) difficult with docker client alone

#Container Orchestrator solve those Challenges
- create, move, scale containers across clusters of hosts
- service discovery
#Products => Docker Swarm, Marathon, HashiCorp Nomad, Cloud offering(AWS Elastic Container service, Azure container service)

#kubernetes is popular container orchestrator capable of managing large numbers of containers
use distributed architecture to run and connect hundreds of thousands of containers with minimal hw

kubernetes also makes grouping, scaling and connecting Containers with outside world really easy
Load balancing and securing container traffic to and from the ouside world are much easier with kubernetes
kubernetes is the delivery serivces of docker containers


SSH to existing running container
docker ps 
docker exec -it <container_name_or_id> /bin/bash
