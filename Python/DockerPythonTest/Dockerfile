FROM python:3.7-slim-buster
RUN mkdir /opt/testapp
WORKDIR /opt/testapp
COPY main.py ./
COPY Pipfile ./
RUN pip install pipenv
RUN export PIPENV_PIPFILE=Pipfile
RUN pipenv install
CMD [ "pipenv", "run", "python", "./main.py" ]