import pyodbc

from config import SQL_SERVER_CONNECTION_STRING


class DataService:
    def __init__(self, timeout=5):
        self.__connection_string = SQL_SERVER_CONNECTION_STRING
        self.__connection = pyodbc.connect(self.__connection_string)
        self.__connection.timeout = timeout
        self.__connection.autocommit = True
        self.__cursor = self.__connection.cursor()

    def get_query_results(self, sql_query):
        headers = []
        data = []
        model = {
            'query_error': False,
            'query_error_message': '',
            'headers': headers,
            'data': data
        }
        try:
            self.__cursor.execute(sql_query)
            if self.__cursor.description is None:
                self.__cursor.nextset()
            for row in self.__cursor.description:
                headers.append(row[0])
            for row in self.__cursor.fetchall():
                current_row = []
                for column in row:
                    current_row.append(column)
                data.append(current_row)
        except pyodbc.OperationalError as ex:
            model['query_error'] = True
            model['query_error_message'] = ex.args[1]
        return model
