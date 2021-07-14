# Docker-Reference
Docker Reference for basic commands and Dockerfile examples

# Table of Contents
1. [Scope](#Scope)
2. [Installing Docker](#installing-docker)

   2.1 [Windows Desktop](#windows-desktop)
   
   2.2 [Windows Server](#windows-server)

   2.3 [Linux Server](#Linux Server)

   2.4 [Additional steps on Linux Server](#additional-steps-on-linux-server)
3. [Third Example](#third-example)
4. [Fourth Example](#fourth-examplehttpwwwfourthexamplecom)

## Scope
This technical introduction to Docker and quick reference for basic commands

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

Please note CentOS/[Rocky Linux](https://rockylinux.org/) 8+ has its own implementation called [Podman](https://podman.io/)

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

## Running containers

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


- Now open `C:\docker` and create file `index.php`. Use this command `New-Item index.php -type file` in PowerShell


- Open `index.php` and add `<?php phpinfo(); ?>'\` on the first line and save


- Open [http://localhost:8080/index.php](http://localhost:8080/index.php) in your browser. 
  You should see information about PHP version


### Demo. Running postgres database in Docker

This time we're going to run [postgres](https://hub.docker.com/_/postgres) database as docker **container** 

- Open PowerShell and `cd C:\docker` on your Windows machine


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

- Option `-e` means 'set environment variable inside container'


- `POSTGRES_PASSWORD` and `PGDATA` predefined variables from [postgres](https://hub.docker.com/_/postgres) Docker documentation


- Now you can use database management tool like [DBeaver Community](https://dbeaver.io/) or [pgAdmin 4](https://www.pgadmin.org/download/) 
to connect to `postgres` database
  

- Connection String is `jdbc:postgresql://localhost:3333/postgres`. Password is `secret`


- Run `select version();` to make sure it works and shows you something like `PostgreSQL 13.3 (Debian 13.3-1.pgdg100+1) on x86_64-pc-linux-gnu, compiled by gcc (Debian 8.3.0-6) 8.3.0, 64-bit`


- Version should contain `Debian`, not `Windows`


- Now run `CREATE DATABASE test;`. You should see new database


- Database `test` survives restart. You can run `docker stop my-postgres` and `docker start my-postgres` to see this


- Data, WALs and configs available on host machine in `C:\docker\postgres\pgdata` directory

---
***NOTE***: `${PWD}` is Windows PowerShell command that shows current directory.

You can use command like this instead `docker run -d -p 8080:80 -v /c/docker:/var/www/html php:7.2-apache`

---

### Automatically restart containers

Sometimes applications crash and services go down. 
To avoid this situation you can use `--restart` option.

---
***NOTE***:
If you set your Docker instance to automatically restart after reboot
(please see section *installing Docker*), then it could even survive system restart.
You don't need `shell` scripts and `crontab` to restart your service

---

| Argument       | Description                                                                               |
| -------------- | ----------------------------------------------------------------------------------------- |
| no             | This is the default value, it means that the containers would not be restarted            |
| on-failure     | This would restart the container in case that there is an error and the container crashes |
| always         | Always restart the container if it stops                                                  |
| unless-stopped | The container would always be restarted unless it was manually stopped                    |

Here is example how to automatically restart postgres instance with `--restart=on-failure` option
```
docker run -d \ 
--name my-postgres \
--restart=on-failure \
-e POSTGRES_PASSWORD=secret \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v /c/docker/postgres:/var/lib/postgresql/data \
-p 3333:5432 \
postgres
```

## Creating Docker Images for Apps

Real power of Docker comes with ability to create image for any application you would like to. 
All you need to do is just create *Dokerfile* named `Dockerfile` (yes literally) and without extension    

---
***NOTE***:
You can create image for any application you want to not only for apps you develop yourself.
For instance you can create image for [kafka](https://kafka.apache.org/) or [trino](https://trino.io/).
Process is exactly the same

---

Let's talk about Docker file format and basic commands

Full description available [here](https://docs.docker.com/engine/reference/builder/#format)

Here is minimal `Dockerfile` example for deploying `Hello world` java application

```
FROM amazoncorretto:11
RUN mkdir /opt/DockerJavaTest
WORKDIR /opt/DockerJavaTest
COPY ./build/libs/DockerJavaTest-1.0-SNAPSHOT.jar ./
CMD [ "java", "-jar", "DockerJavaTest-1.0-SNAPSHOT.jar" ]
```

File starts with [FROM](https://docs.docker.com/engine/reference/builder/#from) command.
It selects *Base* image for you project from [Docker Hub](https://hub.docker.com/). 
Most of the time you should prefer Debian Buster based image. 
In this case we use 
[Amazon Corretto version 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/what-is-corretto-11.html)
*Base* image which OpenJDK 11(current) implementation from Amazon with long term support (LTS).

[RUN](https://docs.docker.com/engine/reference/builder/#run) 
command executes command *inside* new image with permanent results. 
In this case we create new work directory `/opt/DockerJavaTest` for our application.
This step is optional, but its good idea to put your app in dedicated directory.
Directory `/opt` on linux is for **opt**ional apps.

[WORKDIR](https://docs.docker.com/engine/reference/builder/#workdir)
command set any directory as root folder for all following commands.
`/opt/DockerJavaTest` is our new root

[COPY](https://docs.docker.com/engine/reference/builder/#copy)
command copies our `Hello world` application into root folder.
I assume we use `Gradle` as build system, so our `jar` file is here `./build/libs/DockerJavaTest-1.0-SNAPSHOT.jar`. 
Please note we copy it to `./` root folder which is actually `/opt/DockerJavaTest` as we set it in previous step. 

[CMD](https://docs.docker.com/engine/reference/builder/#cmd)
command set our `jar` file as ***main*** executable process. 
Command `CMD [ "java", "-jar", "DockerJavaTest-1.0-SNAPSHOT.jar" ]` has two parameters.
First one `-jar` is standard JVM parameter. Second one is our app name.

---
***NOTE***:
Difference between 
[CMD](https://docs.docker.com/engine/reference/builder/#cmd) 
and
[ENTRYPOINT](https://docs.docker.com/engine/reference/builder/#entrypoint)

CMD sets your process to be executed as demon/service 

ENTRYPOINT allows you to provide arguments for interactive use 

---

### Demo. Compressing files with RAR using ENTRYPOINT command

We can use [klutchell/rar](https://hub.docker.com/r/klutchell/rar) repository for this demo

[klutchell](https://hub.docker.com/u/klutchell) is community user who shows how to pack some standard apps in containers

Here is Docker file from his [GitHub](https://github.com/klutchell/docker-rar/blob/master/Dockerfile)

```
FROM alpine
RUN apk add --no-cache make
RUN wget http://www.rarlab.com/rar/rarlinux-5.4.0.tar.gz && \
	tar -xzvf rarlinux-5.4.0.tar.gz && \
	cd rar && \
	make && \
	mv rar_static /usr/local/bin/rar
ENTRYPOINT ["rar"]
```

We can use dockerized rar like this 
`docker run --rm -v ${PWD}:/files -w /files klutchell/rar a /files/index.rar /files/index.php`
Before running this command `cd` to `c:\docker` then you should be able to compress `index.php`
from previous demos with `rar`. Option `-w` sets working directory much like `[WORKDIR]` in `Dockerfile`.
Parameters `a /files/index.rar /files/index.php` is `rar` archiver parameters and not a Docker ones

### Creating images for Python apps

Let's continue with more advanced examples involving Python package manages

Here is sample `Dockerfile`

```
FROM python:3.7-slim-buster
RUN mkdir /opt/testapp
WORKDIR /opt/testapp
COPY main.py ./
COPY Pipfile ./
RUN pip install pipenv
RUN export PIPENV_PIPFILE=Pipfile
RUN pipenv install
CMD [ "pipenv", "run", "python", "./main.py" ]
```

---
***NOTE***:
I'm using `3.7-slim-buster` here just to save some time on download.
For production use you should prefer plain `buster` image 

---

Everything should be familiar here. We use `python:3.7-slim-buster` as starter image.
Then create and set `/opt/testapp` as workdir. Then copy `main.py` and `Pipfile` to work dir. 
Command `RUN pip install pipenv` installs [PipEnv](https://pypi.org/project/pipenv/) virtual environment.
Commands `RUN export PIPENV_PIPFILE=Pipfile` and `RUN pipenv install` install dependencies from `Pipfile`. 
Finally, we use `CMD [ "pipenv", "run", "python", "./main.py" ]` to run `python` from out virtual environment 
to run our `main.py` script which prints message with fancy font on console.

Pipfile
```
[[source]]
url = "https://pypi.org/simple"
verify_ssl = true
name = "pypi"

[packages]
pyfiglet = "==0.7"

[dev-packages]

[requires]
python_version = "3.7"
```

main.py

```
from pyfiglet import Figlet

if __name__ == '__main__':
    logo = Figlet(font='slant')
    print(logo.renderText("Hello Docker"))
```

---
***NOTE***:
All demo apps available in `app` folder in this repository

---

#### Advanced example. Python Flask App with MS ODBC Driver

Sometimes we need additional soft in our projects that we have compile and install on every machine 
to make our project work. Good example of such dependency is Microsoft ODBC for linux. 
With Docker, you do this exercise only once when preparing `Dockerfile` and not on every server

---
***NOTE***:

Here is information about [Python SQL Server Driver](https://docs.microsoft.com/en-us/sql/connect/python/pyodbc/step-1-configure-development-environment-for-pyodbc-python-development?view=sql-server-ver15)

Here is instruction how to install it [how to install it on linux](https://docs.microsoft.com/en-us/sql/connect/odbc/linux-mac/installing-the-microsoft-odbc-driver-for-sql-server?view=sql-server-ver15)

---

Here is full `Dockerfile` for demo [Flask](https://flask.palletsprojects.com/en/2.0.x/) application
with MS ODBC Driver

```
FROM python:3.7-buster
EXPOSE 8080
RUN mkdir /opt/dockerflaskdemo
WORKDIR /opt/dockerflaskdemo
RUN mkdir logs
RUN mkdir services
RUN mkdir templates
COPY app.py ./
COPY Pipfile ./
COPY services/*.py services/
COPY templates/*.html templates/
RUN pip install pipenv

###########################################################################
# ODBC DRIVER

RUN curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add -
RUN curl https://packages.microsoft.com/config/debian/10/prod.list > /etc/apt/sources.list.d/mssql-release.list
RUN apt-get update
RUN ACCEPT_EULA=Y apt-get install -y msodbcsql17

# optional: for bcp and sqlcmd
RUN ACCEPT_EULA=Y apt-get install -y mssql-tools
RUN export PATH="$PATH:/opt/mssql-tools/bin"
RUN apt-get install -y unixodbc-dev
RUN apt-get install -y libgssapi-krb5-2
###########################################################################

RUN export PIPENV_PIPFILE=Pipfile
RUN pipenv install
CMD [ "pipenv", "run", "gunicorn","-w 4","-b :8080", "app:app" ]
```

Here are some notes

[EXPOSE](https://docs.docker.com/engine/reference/builder/#expose)
command allows you to use container internal port with external host port 
using `-p` option on `docker run`. 
Basically it tells Docker that your app listens to this port

Section `# ODBC DRIVER` just copied from official 
[documentation](https://docs.microsoft.com/en-us/sql/connect/odbc/linux-mac/installing-the-microsoft-odbc-driver-for-sql-server?view=sql-server-ver15)
as-is. This is the latest version for now, and it works with Python 3.7, 3.8, 3.9

Command `CMD [ "pipenv", "run", "gunicorn","-w 4","-b :8080", "app:app" ]` starts production ready
[Green Unicorn](https://gunicorn.org/) Python web server with 4 workers on port 8080.
Instruction `app:app` tells gunicorn that variable `app` in module `app` is your Flask application

---
***NOTE***:
Flask by default uses single-threaded development server.
For production, you probably need production ready multi-threaded Python WSGI HTTP Server

---

---
***NOTE***:
Connection string in example above looks like this 
```
self.__connection_string = f'DRIVER={{ODBC Driver 17 for SQL Server}};' \
                           f'SERVER=YOUR_SERVER_ADDRESS_HERE;' \
                           f'DATABASE=YOUR_DATABASE_NAME_HERE;' \
                           f'Uid=YOUR_USER_NAME_HERE;' \
                           f'Pwd=YOUR_PASSWORD_HERE;' \
                           f'Encrypt=no;'
```
Note that `ODBC Driver 17 for SQL Server` is version `17` now

---

### ***.dockerignore*** file

When adding files to your image you should **not** copy some files.
This may include `*.obj`, `*.class`, `__pycache__` and many other similar files.

To achieve this just put `.dockerignore` file in the root of your project.
It's also possible to put other `.dockerignore` files in subdirectories to clarify/correct copy rules.

This file works just like `.gitignore` file if you familiar with `git`

Full documentation available [here](https://docs.docker.com/engine/reference/builder/#dockerignore-file)

## Docker Compose

*Docker compose* is technology that allows you to run several services with just one command.
Also it allows you to create shared volumes used by number of images amd create virtual networks to connect 
(and/or) isolate containers. This allows you to unlock paradigm ***infrastructure as a code***

To create service descriptions you use [YAML](https://yaml.org/) files much like when working with Kubernetes.

Current specification is version 3 and available
[here](https://docs.docker.com/compose/compose-file/compose-file-v3/)

---
***NOTE***:
Please don't use version 2, since it doesn't contain many important commands

---

Basically you need to create file called `docker-compose.yml` and put commands there you typically use
with `docker run` command

Here is example how to run Python Flask application along with Postrgres database

```
version: '3'

services:
  docker_flask_demo:
    image: docker_flask_demo
    restart: always
    build:
      context: ./
      dockerfile: Dockerfile
    ports:
      - "2222:8080"
    volumes:
      - /c/docker/DockerFlaskDemo/logs/instance_1:/opt/dockerflaskdemo/logs
    container_name: docker_flask_demo
    
  postgres_database:
    image: postgres:13.3-buster
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      PGDATA: /var/lib/postgresql/data/pgdata
    volumes:
      - "/c/docker/postgres:/var/lib/postgresql/data"
    ports:
      - "1111:5432"
    container_name: postgres_database
```

It starts with `services` section here you can add your containers

Section `docker_flask_demo` describes **Flask** web app. 
We build it from local `Dockerfile` in Python project and expose port `8080` 
inside **container** as port `2222` on host machine. Also we mount `/opt/dockerflaskdemo/logs` 
logs directory as `/c/docker/DockerFlaskDemo/logs/instance_1` on host **Windows** machine

---
***NOTE***:
Dash `-` in YAML files means 'list'. You can add more entries in `ports` and `volumes` sections

---

Section `postgres_database` describes Postgres database for project as separate container.
Section name `postgres_database` is Docker local virtual network **DNS** name. 
You can use in connection strings like this `postgresql://postgres:postgres@postgres_database/postgres`
here `@postgres_database` is a host name for database

You can create shared volumes and virtual networks to isolate you apps. 
For instance, you can setup NGINX as load balancer and create rule that
redirects to two different instannces of your app with it's own database running on isolated virtual networks
You can use CUSTOMER CODE to redirect to correct tenant database.

Here is quick example how to run Postgres with setup like this

```
version: '3'

services:
  postgres_database_1:
    image: postgres:13.3-buster
    volumes:
      - my-vol:/var/lib/postgresql/data"
    ports:
      - "1111:5432"
    networks:
      - app1_net
    container_name: postgres_database_1
    
  postgres_database_2:
    image: postgres:13.3-buster
    ports:
      - "1112:5432"
    networks:
      - app2_net
    container_name: postgres_database_2

volumes:
  my-vol:
    name: my-vol
    
networks:
  app1_net:
  app2_net:
```
We use `volumes` and `networks` here

### Docker compose commands

Basically you use standard `docker compose up` command 
in directory which contains `docker-compose.yml` file to start services

To stop all services you can use `docker compose down` command

Full docker compose CLI reference available [here](https://docs.docker.com/compose/reference/)

## Docker Hub Basics

When you finished creating and debugging your image it's time to distribute it.

Basically 3 options here

1. Distribute your app as source code in git repository and build image from Docker file on a target device


2. Use image registry like [Docker Registry](https://docs.docker.com/registry/) or your Kubernetes registry


3. Use third party Docker Image hosting like [Docker Hub](https://hub.docker.com/) 
   or [Amazon Elastic Container Registry](https://aws.amazon.com/ecr/)
   
Building your image starts with `docker build` command and this command exactly the same for all options

***Example***: Let's build our `DockerJavaTest` java application

Command looks like this `docker build -t dockerjavatest:latest -t dockerjavatest:version-1.0.0 .`

We assign two tags here `latest` and `version-1.0.0`. Please refer section 'tags'

Also, please note dot `.` at the end which means 'current directory'

From now, I'm going to use Docker Hub as image registry. 
It provides 1 private repository after registration and unlimited public repositories

As next step login into Docker Hub from console. Use `docker login` command. 
Use `docker logout` if you already logged in

After successful login you can `push` your image much like you push your work using `git push`

To do so run `docker image ls` to check your image name

Then run `docker tag dockerjavatest opotapenkoatssnc/dockerjavatest`
Your local repository name is your image name, so it just `dockerjavatest`.
My Docker Hub public account name is `opotapenkoatssnc` and `opotapenkoatssnc/dockerjavatest` 
is my public repository name

Now we can run `docker image ls` and see new repository `opotapenkoatssnc/dockerjavatest` with tag `latest'

To push it run `docker push opotapenkoatssnc/dockerjavatest`. 
Optionally you can add tag `docker push opotapenkoatssnc/dockerjavatest:latest`

You can assign tags to this new `opotapenkoatssnc/dockerjavatest` repository before pushing it

Now you can download and run it locally using `docker run -it opotapenkoatssnc/dockerjavatest:latest`

Docker Hub Repository available [here](https://hub.docker.com/r/opotapenkoatssnc/dockerjavatest) 

Some tips available 
[here](https://stackoverflow.com/questions/41984399/denied-requested-access-to-the-resource-is-denied-docker)