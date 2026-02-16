# Learning Docker Compose Notes

## Procedural vs Declarative Approaches

### Procedural
- Series of ordered steps
- Based on assumptions about previous steps
- Easy to introduce errors
- Example: 
  - Building and running a Docker container works the first time
  - Second time may fail because old container needs to be stopped first

### Declarative
- Specify desired results
- System determines steps to execute
- Produces same result each time
- Handles startup and cleanup automatically
- Benefits:
  - Config files can be version controlled
  - Self-documenting
  - Easier to manage multiple Docker environments

## Docker Compose Limitations
- Designed for single hosted server
- Best suited for local, staging, or CI testing environments
- Not designed for distributed systems
- No tooling for running containers across multiple hosts
- Not ideal for production environments
- No support for independently scaling services or auto-scaling

## Scaling Challenges
- Scaling multiple instances requires running Docker Compose config on multiple hosts
- Can waste resources (e.g., scaling up scheduler when it's not needed)
- Better solutions: Docker Swarm or Kubernetes

## Docker Service Lifecycle Commands
```bash
# Start services
docker-compose up
# Build the image, create containers, start application

# Clean up
docker-compose down
# Stop all containers, delete containers and images, remove artifacts

# Pause services
docker-compose stop
# Saves battery life and frees up memory

# Restart services
docker-compose restart
# Stops and starts all running containers
```

# Environment Variables vs Build Args
Env vars: Accessible inside running Docker container

Build args: Only available at build time (e.g., build tool versions, cloud platform config)

# Setting Environment Variables
export runtime_ev=dev

# Volumes
Docker constructs for persistent storage

Prevents important data from being deleted when containers stop

## Structure: source:target:access_mode

Target: Path inside container

Source: Path on host machine (if not specified, Compose creates automatically)

Access modes: rw (default), ro (read-only)

```
Path Syntax
./ - Current directory

../ - Parent directory

/ - Absolute path on host
```

## Named Volumes Recommendation
Advantages:

Data persists between container restarts

Auto-copies data from old to new container

Cleanup:
```
docker compose down --volumes
```

# Networking
By default, containers are isolated

Need to expose ports for communication:

Between containers

With outside world

Service Dependencies
Use depends_on to specify startup order

Example:
``` 
depends_on:
  - database
``` 

Note: Only guarantees dependent container has started, not that it's healthy

# Service Profiles
Group related services that don't depend on each other

Example groups:

Storefront and purchasing services

Scheduling services

Benefits:

Share containers between profiles

Easier integration testing

Conceptually one system

Running with Profiles

```
docker compose --profile storefront_services up
docker compose --profile scheduling_services up
```

# Multiple Compose Files
Useful for distinct behaviors that won't coincide

Common use cases:

Different environments (testing vs staging)

Different components of a system

Default files read:

docker-compose.yaml

docker-compose.override.yaml

Running with Override Files
docker-compose -f docker-compose.yaml -f docker-compose.local.yaml up

Environment Variables in Compose
Syntax: ${VAR_NAME} or ${VAR_NAME}

Uses:

Image tags

Software versions

Ports

# Setting defaults:

Inline in compose file: image: "mysql:${TAG:-latest}"

Via .env file in project root

Required values: image: "mysql:${TAG?Error: TAG is required}"

Accessing Running Containers

docker ps
docker exec -it <container_name_or_id> /bin/bash

Multi-stage Build Example

```
FROM client-base AS client-dev
CMD ["yarn", "dev"]
```
```
FROM client-base AS client-build
RUN yarn build
```

Building and Running

```
docker build --target client-dev -t my-client-dev .
docker run -p 3000:3000 my-client-dev
```