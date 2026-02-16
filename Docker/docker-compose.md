https://www.linkedin.com/learning/learning-docker-compose/implement-docker-compose?contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&u=116771770

Procedural
Series of Ordered Steps
Based on assumptions about previous step
Easy to introduce errors

build and run docker container worked on first time
1st => Procedural

2nd time => won't run as expected, coz old container have to stopped first

Declarative
Specify and results
sys will determine which steps to execute
Produce same result each time
start up and clean up steps

config file => can be version control
Self documenting
Easier to manage multiple docker envs

docker compose was designed for single hosted server
suited for local, staging or CI testing env
not designed for distributed systems
no tooling for running containers across multiple hosts
is not ideal tool for prod env
doesn't have support for independently scaling services or any kind of auto scaling

Scaling in docker compose is all for nothing

way to scale up multiple instance of storefront is to run Docker compose  config on mutiple hosts,
will scale up the schecular at the same time, waste of resources
since schecular is not seeing any traffice

Orchestration Tools
Docker Swarm or Kubernetes are designed to solve these problems


https://www.linkedin.com/learning/learning-docker-compose/writing-a-docker-compose-configuration?autoSkip=true&contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&resume=false&u=116771770

common docker service lifercycle: up, down, stop, restart

# docker-compose up
Build the image
Create the containers
Start the application

# docker-compose down
Stop all containers
Delete all containers and images
Remove all artifacts

#docker compose stop 
Save Battery life 
Free up memory

#docker compose restart => stop and start
stop and start all running containers

# Env vars
Access inside running docker container

# Build args
only at build time
build tool versions
cloud platform config
Eg: AWS region


#specify env in host machine same as specifying docker file
export runtime_ev=dev

#Volumes
- are docker constructs used for persistent storage
- containers stop important data doesnot get deleted
- target => file dir path inside running container where volume data lives
- source => file dir path on host machine outside a container where volume data lives
 - if not specify a source => compose will create a source volume automatically
- Specifying non default source => ./mysql:/var/lib/mysql:ro => mapping source:target:access mode

#Bash syntax
- ./ => current dir
- ../ => Parent dir
- / => absolute path on host machine

#Access mode
- default => rw => best to access ro => read only


# Recommend to use named volumes
nameless volume => persist any db written inside container and store it on host machine
- create volume randomly each time docker compose up runs

volumes:
 kinetic
 
 - advantange of using named volume with compose when up or restart=>
 auto copy data from old to new container -> no data will be lost
 
 #docker compose down --volumes => auto delete any named volumes
 
adv of using containerization tool like docker => entire app can be fully encapsulated inside of single container
outside of container cannot access or communicate with anything running inside of container
containerize services need to communicate with each other or with outside world
exposing port 
D:\Learning\Linkedin Learn\Docker\Ex_Files_Learning_Docker_Compose\Ex_Files_Learning_Docker_Compose\Exercise Files\Ch03\03_04\kineteco 


# Services dependency
App have serivce dependency => one service cannot operate without another
Startup Order:
1 => run db container
2 => run app container

depends_on:
      - database

services can have any no. of dependencies and many services can share single dependency

# start service individually
docker compose up storefront
not gurantee on dependent container are running healthy latest version of compose
only gurantee that it have started
coz in distributed system, not possible to know that dependent service are up and running
All docker container should have resilency when dependencies become unavailable 	

# Tight coupling
third party wrapper will resolve => wrap your container's initialization cmd
so container will not start if dependency is unhealthy
not recommend to rely 

#Utility Named subsets of services
clusters of docker containers that are frequently run together
don't depends on each other 

eg.
1st group: store front and purchasing => all services support purchasing
2nd group: scheduling => all services suport installation

To save processing power
store front don't want scheduler containers 
scheduling group don't want store front
# doesn't want separe docker compose files 

# Adv of single compose 
- Share containers
- Easier integration tesing
- Conceptually One System

# Service Profile
- put docker service in one or more category 

D:\Learning\Linkedin Learn\Docker\Ex_Files_Learning_Docker_Compose\Ex_Files_Learning_Docker_Compose\Exercise Files\Ch04\04_01\kineteco

both profile => depends on db 
want db to be included in both profile
if don't assign profile to db => auto included in default profile => run with every service profile 
- docker compose only apply to a service if its profile is explicitly enabled 
- docker compose up only run services that are part of default profile

# docker compose --profile storefront_services up
# docker compose --profile scheduling_services up


# Multiple Composed files
serveral instances where having multiple composed files is more valuable than another org tool like service Profile
- any situation where there are two distinct sets of desired behaviour, that will never coincide(occur at the same time.)
- Distinct desire behaviors that do not coincide
- Diff env (eg. testing vs staging) - never have local and staging configs
- running on same hosts at the same time
- diff components of single system (use single composed file instead)

# by default docker compose read two config => docker-compose.yaml and 
# docker-compose.override.yaml => inherit from main config file
# docker compose merge two files together 
# Arrays Fileds [original + override]
# single value fields: pref to override
# override file must be relative to primary config file
# override file doesn't need to be complete to be valid, contains snippets of config 
# can be easily shared bet multiple projects or repositories
# docker-compose.local.yaml => docker-compose.staging.yaml with env override
# to run config file => docker-compose -f [primaryFile] -f [overrideFile] [command]
# to run all containers with local env override
# docker-compose -f docker-compose.yaml -f docker-compose.local.yaml up


#if docker-compose config need to have diff behaviors in diff env, don't want to support diff config override for every env
# alternative is using env variables
# passing env vars to running docker container
# pass env vars from host machine shell to composed config

# Usages of env vars
- Image tags
- Software version
- Ports

# - syntax for accessing env vars from shell is dollar sign ($envName) => ${TAG} => curly brance ({}) => is optional
 database: 
    image: "mysql:${TAG}"
# tag vars is not set in host env => default empty string

# ways to set default 
 => inline in docker compose config => :-latest => image: "mysql:-latest" => docker-compose file read a file .env in project root dir
 => .env file => create .env file in root dir => add TAG=latest => if it is not in root => docker-compose --env-file pathof .env 
 => throws error if missing (no default) => "mysql:? TAG is a require value"
 
# any env var that is set in shell always override a default value

https://www.linkedin.com/learning/learning-docker-compose/multiple-compose-files?autoSkip=true&contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&resume=false&u=116771770



SSH to existing running container
docker ps 
docker exec -it <container_name_or_id> /bin/bash

# 
FROM client-base AS client-dev
CMD ["yarn", "dev"]

# yarn dev => npm run dev

FROM client-base AS client-build
RUN yarn build
# RUN: Executes yarn build to compile the app into static files (HTML, CSS, JS).
# No CMD is specified, so this stage is used for building, not running.
#
docker build --target client-dev -t my-client-dev .
docker run -p 3000:3000 my-client-dev



