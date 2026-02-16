https://guide.bash.academy/

https://bigstarcollectibles.com/

apps works on their machines with different config

able to download docker image
no need to install everything, can run app locally
improves the time it takes dev to get started on new project

Python and Flask will used 

#remove any intermediate container, when you build a new image, docker create a temporary or intermediate container of each instructions
#once the command is run in intermediate container that container is save as a new image layer 	
docker build --force-rm=true .
#--force-rm=true remove any intermediate container if exist even if the build is unsuccessful

#intermediate container will not remove if unsuccessful build, allows debugging that last intermediate container
#or committing as intermediate container
<code> docker build rm=true . </code>

#All previous image layers are docker cache and can be reused
#if your installation depends on external resources cache can cause issue
# use --no-cache => skipe the cache and rebuild entire image

docker cache is to speed up subsequent image build by reusing image layers

#to search images
<code> docker search python </code>
<code> docker search --filter "is-official=true" python </code>
<code> docker search --filter "stars=100" python </code>
<code> docker search --filter "stars=100" --filter "is-official=true" python </code>
<code> docker search --limit 4 python </code>

# to read full images description
<code> docker search --no-trunc python </code>
<code> docker search --format "{{.Name}}: {{.Description}}" --no-trunc python </code>
<code> docker search --format "table {{.Name}} \t {{.Description}}" --no-trunc python </code>

<code> docker image pull python </code>
<code> docker image ls -a </code>
<code> docker image ls --no-trunc </code>
<code> docker image ls --filter reference=python* </code>

# include info about the image
<code> docker image ls --digests </code>

# before used to filter images created before another image to compare creation date between images
<code> docker image ls --filter before=python </code>

# show list of image created
<code> docker image ls --filter since=python </code>

# to isolate images that aren't being used such as intermediate images
<code> docker image ls --filter dangling=true </code>

# based on image label
<code> docker image ls --filter label=python* </code>


# tags are used to identify version of images


<code> docker build -t image_name:tag .

#default tag will be latest 
docker build -t bigstarcollectibles .

#--no-cache to ensure to rebuild the image => get new image id
docker build --no-cache -t bigstarcollectibles:v1 -t bigstarcollectibles  .

#to add tag to image => docker tag existing-image:tag target image:tag => using same image id
<code> docker tag bigstarcollectibles:latest bigstarcollectibles:v2 </code>

# to add meta data to image using label in docker file
<code> LABEL "com.example.vendor"="bigstarcollectibles" </code>
<code> LABEL version="1.0" </code>
<code> LABEL description="The Big star collectibles Web Site using python based web site"</code>

# to add meta data to image using label in docker file
<code> LABEL "com.example.vendor"="bigstarcollectibles" version="1.0" description="The Big star collectibles Web Site using python based web site"</code>

<code> 
LABEL "com.example.vendor"="bigstarcollectibles" \ 
version="1.0" \ 
description="The Big star collectibles Web Site using python based web site"
</code>

# to filter image base on label
<code> docker image ls --filter label="com.example.vendor"="bigstarcollectibles" </code>


registry which is interchangeablity with repository
Private repo is good for keep your docker images safe and secure
big-star-collectibles-repo

aungtrt1018/big-star-collectibles-repo


# View repository using VS code registry panel

#1st Tag local image using the name of your new repository to keep things organized
# <code> docker tag bigstarcollectibles:v2 aungtrt1018/big-star-collectibles-repo:v2 </code>

# name_of_your_repo:name_of_the_image
#  2nd <code> docker push aungtrt1018/big-star-collectibles-repo:v2 </code>

# pull images from docker hub
<code> docker pull aungtrt1018/big-star-collectibles-repo:v2 </code>

  View a summary of image vulnerabilities and recommendations → docker scout quickview aungtrt1018/big-star-collectibles-repo:v2


docker image ls 
docker image inspect imageid

RepoTags => all the tags associated with image id

Config => 	if you encounter error you can check those config path
Cmd => when you start container based on image
Labels

docker image inspect --format='{{json .Config.Labels}}' bigstarcollectibles:v2

#remove image
docker rmi -f tag:version

docker images --digests
docker rmi reponame@digest
remaining image tag associated with image id remain

# to start a container docker start -- used to start any stop container and 

# docker run -it [OPTION] IMAGE:[TAG] [COMMAND] [ARG]-- create new container and start it immediately
we have set a default command in docker file so [COMMAND] don't need [ARG] additional args to command

docker run -it -d -p 5000:5000 -v ${PWD}:/app/code big-star-collectibles

#gracefully teminate a proces
docker stop

#kill child process doesn't notify its parent, receive kill signal can cause error
docker kill

https://www.linkedin.com/learning/docker-your-first-project/inspecting-images?autoSkip=true&contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&resume=false&u=116771770

# list all the container running on machines
docker ps 
docker ps -a (including stop container)
docker ps -n 1(no. of last container created)
docker ps -q(only display container id)
docker ps -s(total size of all container)
docker ps -l(last created container)
docker ps --no-trunc(last created container)
docker ps --filter(can used filter)

# docker ps --filter ancestor=bigstarcollectibles  => display container based on image or descendant of it

docker inspect containerId or name
id => containerId
LogPath => shift log central rotation and enable log rotation
restartCount => to find container restart policy
hostconfig => to manage cpu, memory, device driver path,
config => runtime config


docker logs option containerId

volume and bind mount
used to share data bet host machines and container

# docker volume 
<code> docker volume create bigstars </code>
A volume is a directory on the host machine managed by Docker.
Data in a volume is NOT stored inside the container’s filesystem.
Data in a volume persists even if the container is deleted or recreated.

# You can list and inspect volumes:
<code> docker volume ls </code>
<code> docker volume inspect bigstars </code>

# Look for the volume path:
# "Mountpoint": "/var/lib/docker/volumes/bigstars/_data"

# Attaching volume to a container: use -v 
# This mounts the Docker-managed volume bigstars to /app/data inside the container.
<code> docker run -it -d -p 5000:5000 -v bigstars:/app/data aungtrt1018/big-star-collectibles-repo </code>
<code>docker run -it -d -p 5000:5000 -v bigstars:/app/data aungtrt1018/big-star-collectibles-repo containerId</code>

#Remove Volume
<code>docker volume rm name </code>

# 📦 Dockerfile Volume Declaration:
<code> VOLUME /app/data </code>

# use -v to specify bind mount
# A bind mount maps a specific host directory or file to a path inside the container.
# Useful for development: you can edit files on the host and see changes inside the container immediately.
# Great for sharing config files, logs, or source code between host and container.
<code>
	  docker run -it -d -p 5000:5000 -v "$PWD/test":/app/test aungtrt1018/big-star-collectibles-repo:v2
</code>

# Unlike volumes, bind mount data is stored directly on the host, not managed by Docker. 
# The data is outside the container, so it persists as long as the host directory exists.
# if you remove container data in bind mount will be lost
# is useful when you need to share data or config file bet host machine
# and container during development or testing

# To verify bind mount is working
# docker exec -it containerid sh

<code> docker build -t ch04_06:v2 . </code>

# Run container from image
docker run -it -d -p <host_port>:<container_port> <image_name>
<code> docker run -it -d -p 5000:5000 -v "$PWD/test":/app/test ch04_06:v2 </code>


<code> docker image prune </code>

# To remove all images
<code> docker image prune -a </code>
<code> docker container prune</code>
<code>docker volume prune -f</code>
<code>docker volume prune -f --filter label="com.example.vendor"="Big Star Collectibles" </code>

# by pass or force flag
<code> docker image prune -a -f </code>

# prune all unused images, container, volume and networks
<code> docker system prune --volumes -f </code>

docker container logs are crucial tools
https://www.linkedin.com/learning/docker-your-first-project/reviewing-container-log-files?autoSkip=true&contextUrn=urn%3Ali%3AlyndaLearningPath%3A65eb4388345061d17bc1cba4&resume=false&u=116771770


## 🐳 Docker Volume Notes

```bash
docker volume create bigstars
```

- A **volume** is a directory on the **host machine** managed by Docker.
- Data in a **volume is NOT stored inside the container’s filesystem**.
- **Data in a volume persists** even if the container is **deleted or recreated**.
- You can list and inspect volumes:

```bash
docker volume ls
docker volume inspect bigstars
```

- Look for the volume path:
  - `"Mountpoint": "/var/lib/docker/volumes/bigstars/_data"`

### 🛠️ Attaching volume to a container:

```bash
docker run -it -d -p 5000:5000 -v bigstars:/app/data aungtrt1018/big-star-collectibles-repo
```

- This mounts the Docker-managed volume `bigstars` to `/app/data` inside the container.

### 📽️ Removing a volume:

```bash
docker volume rm bigstars
```

### 📆 Dockerfile Volume Declaration:

```Dockerfile
VOLUME /app/data
```

- This tells Docker that `/app/data` is a mount point for external data.

---

## 🖀 Bind Mount Notes

- A **bind mount** maps a **specific host directory or file** to a path inside the container.

```bash
docker run -it -d -p 5000:5000 -v "$PWD/test":/app/test aungtrt1018/big-star-collectibles-repo:v2
```

### ✅ Use Cases:

- Useful for **development**: you can edit files on the host and see changes inside the container immediately.
- Great for **sharing config files, logs, or source code** between host and container.

### ⚠️ Clarification:

**Incorrect:**

> "unlike docker volume data inside bind mount is not stored outside the container"

**Corrected:**

> "Unlike volumes, bind mount data is stored **directly on the host**, not managed by Docker. The data is **outside the container**, so it persists as long as the host directory exists."

### ⚠️ Warning:

- If you bind-mount a **temporary or non-existing host path**, Docker might create an empty folder.
- Be careful: if you **delete the host directory** manually, the data is lost.

