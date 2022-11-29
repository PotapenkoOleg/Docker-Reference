FROM python:3.11.0-bullseye
ARG DEBIAN_FRONTEND=noninteractive
RUN apt update -y && apt upgrade -y
RUN /usr/local/bin/python -m pip install --upgrade pip
RUN pip install pipenv
