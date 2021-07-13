FROM python:3.7-buster
#EXPOSE 8000
RUN mkdir /opt/dockerflaskdemo
WORKDIR /opt/dockerflaskdemo
RUN mkdir logs
RUN mkdir services
RUN mkdir templates
COPY app.py ./
COPY config.py ./
COPY Pipfile ./
COPY services/*.py services/
COPY templates/*.html templates/
RUN pip install pipenv
###########################################################################
# ODBC DRIVER
# https://docs.microsoft.com/en-us/sql/connect/python/pyodbc/step-1-configure-development-environment-for-pyodbc-python-development?view=sql-server-ver15
# https://docs.microsoft.com/en-us/sql/connect/odbc/linux-mac/installing-the-microsoft-odbc-driver-for-sql-server?view=sql-server-ver15

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