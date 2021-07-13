EXTERNAL_API_URL = 'https://quoters.apps.pcfone.io/api/random'

# LocalHost
# POSTGRES_CONNECTION_STRING = 'postgresql://postgres:postgres@localhost/postgres'

# Docker
POSTGRES_CONNECTION_STRING = 'postgresql://postgres:postgres@postgres_database/postgres'

SQL_SERVER_CONNECTION_STRING_TEMPLATE = f'''
DRIVER={{ODBC Driver 17 for SQL Server}};
SERVER=YOUR_SERVER_ADDRESS_HERE;
DATABASE=YOUR_DATABASE_NAME_HERE;
Uid=YOUR_USER_NAME_HERE;
Pwd=YOUR_PASSWORD_HERE;
Encrypt=no;
'''

SQL_SERVER_CONNECTION_STRING = f''
