# Docker-Reference
Docker Reference for basic commands and Dokerfile examples

## Installing Docker

### Windows Desktop
1. Install Docker Desktop from here [here](https://www.docker.com/products/docker-desktop)
2. Install the Linux kernel update package from [here](https://docs.microsoft.com/en-us/windows/wsl/install-win10#step-4---download-the-linux-kernel-update-package)

P.S. You don't need to follow the whole instructions in step 2, just go to step 4 and download package

### Windows Server
Docker EE for Windows requires Windows Server **2016** or later

Additional information about Docker EE is [here](https://docker-docs.netlify.app/install/windows/docker-ee/#install-docker-ee)

### Linux Server

Instructions how to install Docker Community Edition on CentOS/[Rocky Linux](https://rockylinux.org/) available [here](https://docker-docs.netlify.app/install/linux/docker-ce/centos/)

Please note CentOS/[Rocky Linux](https://rockylinux.org/) 8+ has it's own implementation called [Podman](https://podman.io/)

How to install [Podman](https://podman.io/getting-started/installation)

Podman is compatible with Docker and uses the same commands

### Additional steps on Linux Server

After you finished install you should consider some other steps to make you install more robust

[Manage Docker as a non-root user](https://docker-docs.netlify.app/install/linux/linux-postinstall/#manage-docker-as-a-non-root-user)

[Configure Docker to start on boot](https://docker-docs.netlify.app/install/linux/linux-postinstall/#configure-docker-to-start-on-boot)

Last step allows your containers survive system crash/restart

### Install Docker Compose on Linux

By default linux install doesn't contain Docker Compose

You can install it following instructions [here](https://docs.docker.com/compose/install/)

---

## Running your containers

### Terminology

Let start with some basic terminology and ideas behind Docker


- Docker **image** is executable file like notepad on Windows. 
  It's file system is immutable. 
  All changes would be lost when container stops
  

- Docker **container** is running instance of your image.
  For example, you can start many notepad instances and all of them would be different. 
  Same apples to containers. 
  You can run two **containers** with the same application **image** like database 


- Docker ***runs only one process in container***. 
  When this process finishes container would stop


- Docker image consist of many layers like onion or Russian Doll. 
  For instance, you can add MySql database to basic Debian image, 
  then add Apache, then PHP and finally add your Application to this image. 
  It's not uncommon to have images with 33+ layers.
  But remember Docker runs only ***one*** process, so ***never*** add ***all*** dependencies to your image.
  In example above your ***container*** would be broken if MySql would stop.
  Having MySql as separate ***container*** is **always** safe bet 


- Docker can be used in two modes. First is ***interactive*** used primary for debugging and second is ***demon*** mode for services like web server


- Docker doesn't expose any ports to host system by default. You **must** do it yourself


- Docker allows you to map any directory inside it's filesystem to any **host** directory. This allows you save your data like logs, configs etc and make them editable


- Most Docker images use `root` user by default, so you don't need elevation inside your container

### Demo. Running *Ubuntu* in interactive mode

`docker run -it ubuntu /bin/bash`

---
NOTE: If you on linux and didn't setup Docker as a non-root user,
you should use `sudo` in all of your commands like this `sudo docker run -it ubuntu /bin/bash`

---

Explanation: `run` starts your **container** from **image** called *ubuntu*.

Option `-it` means *open interactive console*. 

Command `/bin/bash` runs bash interpreter as main process. (*Remember container **always** runs only **one** process*)

Just open PowerShell by right-clicking on start icon on Windows (or other terminal) and run command above. 
You should have ubuntu running on your machine. Try to replace `/bin/bash` with `ps -ef` like this `docker run -it ubuntu ps -ef`
Now you should see process list in ubuntu, and your container should stop. 
(*Remember container stops when **main** process is finished*)

### Demo. Using basic Docker commands

In this demo I'm assuming you still running `ubuntu` in interactive mode

Let's try basic Docker management commands. Please open second PowerShell

---
NOTE: use `docker COMMAND --help` to get more information about command and it's options

*Example:* `docker start --help`

---

- `docker image ls` shows all **images** in you Docker. 
  `IMAGE ID` is the most important column. It allows you to reference your image using this ID
  

- `docker ps -a` shows all **containers**. If you need only running **containers** just remove `-a` option like this `docker ps`

---
NOTE: Please take a look at `CONTAINER ID` column - this is you **container** handle to manage container.
Also it has `NAMES` column which contains randomly assigned name like *infallible_mayer* which you can use too.
You can assign your own name to any container when you start it

By the way you shouldn't type all `ID` or `NAME`. Most of the time first 2-3 letters should be fine

`IMAGE` column contains **image** for your **container**

`COMMAND` is main command(process) this **container** executes

---

- `docker start [IMAGE_ID]` this command starts new **container** from `IMAGE_ID`. 
  You can get ubuntu IMAGE_ID with `docker image ls`. Then start it. 
  You can exit ubuntu anytime by typing `exit` in terminal
  
  
- `docker attach [CONTAINER_ID]` you can open terminal for running **container** with this command.
Use `docker ps` to find your `CONTAINER_ID`. Type `exit` to exit. 
  It closes **container** if you running it in interactive mode
  
  
- `docker exec -it [CONTAINER_ID] [COMMAND]` interactively runs command on running **container**.
*Example:* `docker exec -it 39 ps -ef` runs `ps -ef` on container `39..`
  

- `docker logs [CONTAINER_ID] -f` interactively shows console output for your running **container**. 
Option `-f` show output continuously much like `more -f` linux command. 
  Try to run `ls` command in you PowerShell ubuntu **container**. 
  Press `CTRL+C` to stop showing output.
  

- `docker inspect [CONTAINER_ID]` shows **container** configuration


- `docker stop [CONTAINER_ID]` stops **container**


- `docker rm [CONTAINER_ID]` removes **container**


- `docker rmi [IMAGE_ID]` remove **image**. 
  You **must** stop and remove all **containers** using this **image**.
  Option `-f` forcefully removes image, but it's not recommended and can damage your Docker installation

---
*Example:* Running named ubuntu image as demon with automatic container removal

`docker run -it -d --rm --name my-ubuntu ubuntu /bin/bash` This command runs interactive ubuntu **container** as demon.
You can open terminal with `docker attach` or execute commands with `docker exec`

- Option `-d` means 'run as demon(service)' and not block current terminal.
You should get container ID like this `7ccdfb2a0a472bc30fab9f8810f01bb28b8985b6269f0f81e2fd302aabcc209f`


- Option `--rm` means remove **container** when it finished


- Option `--name` allows you to provide meaningful name for your image. In this case `my-ubuntu`


- `ubuntu` is the image name


- `/bin/bash` is the main image command

---

### Fantastic Images and Where to Find Them

To find images you should visit [Docker Hub](https://hub.docker.com/)

Then you can use search in top left conner. Try `postgres` or `amazon` or `Windows Server Core`
(*Please note `Windows Server Core` could have it's own **License***)

To download image on your local machine use `pull` command like this`docker pull postgres`.
You should see such command on the right. This is much like using `pull` with **git**

Basically you ***don't*** need to pull image before using it. 
`docker run` automatically downloads your image and all it's layers (*Do you still remember that typical image consist of many layers*)

Also, you can scroll down to find most popular images. 
Each image contains instruction how to use it

### Common starter images and which one I should choose

There are 3 common images used as starters 

- [Alpine](https://hub.docker.com/_/alpine)

- [Buster-slim](https://hub.docker.com/_/debian) and [Buster](https://hub.docker.com/_/debian)

---
***What is buster?*** 

[Buster](https://en.wikipedia.org/wiki/Debian_version_history#Debian_10_(Buster)) is code name for 
[Debian](https://en.wikipedia.org/wiki/Debian) version 10, 
which is current version


---

- *Alpine* is the smallest one and it just 40 MB. 
  It's commonly used for small embedded systems


- *Buster-slim* is Debian 10 image. It's size around 100 MB. 
  But it contains only kernel and ***no*** additional libraries and utilities like `ps` or shells like `bash`
  

- *Buster* is standard image for most applications. 
  It's size around 800 MB, and it contains most libraries, utilities and package manager
  
---
***OK, which one should I use?***

Most time you should use ***Buster*** image. Several reasons for that

- It contains all libraries, tools and package manager.
So you never get error because `stdlib.so` is missing
  

- It has all tools, so debug is easy. It just plain old linux


- It contains package manager, so installing additional software like python's `pipenv` is not an issue 


- It shared among all your images. So if you install [postgres 13.3-buster](https://hub.docker.com/_/postgres) 
  and [python 3.9.6-buster](https://hub.docker.com/_/python), your `buster` image most likely be shared. 
  Thanks to layered image structure

---

### Tags and image names

Each image can have multiple names known as 'tags' in addition to image hash that uniquely identifies image.

For instance, [python](https://hub.docker.com/_/python) image can have many tags like this `3.9.6-buster, 3.9-buster, 3-buster, buster`

You can use either name since all of them refers to the same image

Tag `latest` is special for docker. 
It automatically downloads image with this tag when you issue command like this `docker pull python`.
Also this tag can be reassigned over time. 
So image `3.10-buster` would be `latest` some day

When you're building your images you can use `-t` option to assign tag(name/names) to your image like this `docker built -t appname:latest`


You can use `docker push` to upload you image to dockerhub.
Currently, only one private repository available for registered users.

### Port Forwarding and Volumes

Nowadays most server-side applications are web-apps and they should expose some ports for end users.
Also we always need some data to be stored between application restarts.
It could include config files, logs and many other things.

#### Port Forwarding
`docker run -d -p 8080:80 httpd` This command runs [Apache](https://hub.docker.com/_/httpd) web server on your Docker machine
Open [http://localhost:8080/](http://localhost:8080/) from your browser to see "It works!" message from Apache

Command option `-p` forwards port `80` in **container** to port `8080` on your **host** machine.

You can use any port, not only `8080`. 
For instance command `docker run -d -p 7777:80 httpd` runs Apache on **host** port `7777`

#### Volumes

`docker run -it -v /c/docker:/root ubuntu /bin/bash` This command mounts `C:\docker` directory on Windows to `/root` directory in `ubuntu` image


You can run `cd /root` inside your Docker **container** and run `ps -ef >> ps.txt` to get linux process list in your `C:\docker` Windows directory

This file will be available on linux and Windows even after **container** stop/restart

### Demo. Using Apache with PHP

Let's run [PHP](https://hub.docker.com/_/php) image as container to demonstrate port forwarding and volumes

- As always open PowerShell and `cd C:\docker` on your Windows machine


- Run this command `docker run -d -p 8080:80 -v ${PWD}:/var/www/html php:7.2-apache` 

---
NOTE: `${PWD}` is Windows PowerShell command that shows current directory. 

You can use command like this instead `docker run -d -p 8080:80 -v /c/docker:/var/www/html php:7.2-apache`

---


- Open [http://localhost:8080/](http://localhost:8080/) You should see "It works!" page from Apache


- Now open `C:\docker` and create file `index.php`. Just use this command `New-Item index.php -type file`


- Open `index.php` and add `<?php phpinfo(); ?>'\` on the first line and save


- Open [http://localhost:8080/index.php](http://localhost:8080/index.php) in your browser. 
  You should see information about PHP version


### Demo. Running postgres database in Docker

This time we're going to run [postgres](https://hub.docker.com/_/postgres) database as docker container 

- As always open PowerShell and `cd C:\docker` on your Windows machine


- Run this command `mkdir postgres` to create directory for postgres data files and config


- Run following command to run postgres with password `secret` on port `3333`

```
docker run -d \ 
--name my-postgres \
-e POSTGRES_PASSWORD=secret \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v /c/docker/postgres:/var/lib/postgresql/data \
-p 3333:5432 \
postgres
```

- Option `-e` means 'set environment variable inside container'.


- `POSTGRES_PASSWORD` and `PGDATA` predefined variables from [postgres](https://hub.docker.com/_/postgres) Docker documentation


- Now you can use database management tool like [DBeaver Community](https://dbeaver.io/) or [pgAdmin 4](https://www.pgadmin.org/download/) 
to connect to `postgres` database
  

- Connection String is `jdbc:postgresql://localhost:3333/postgres`. Password is `secret`


- Run `select version();` to make sure it works and shows you something like `PostgreSQL 13.3 (Debian 13.3-1.pgdg100+1) on x86_64-pc-linux-gnu, compiled by gcc (Debian 8.3.0-6) 8.3.0, 64-bit`


- Vesion should be `Debian`, not `Windows`


- Now run `CREATE DATABASE test;`. You should see new database


- Database `test` survives restart. Yo can run `docker stop my-postgres` and `docker start my-postgres` to see this


- Data, WALs and configs available on host machine in `C:\docker\postgres\pgdata` directory

## Creating Docker images for your apps

To be continued ...

## Docker Compose

To be continued ...