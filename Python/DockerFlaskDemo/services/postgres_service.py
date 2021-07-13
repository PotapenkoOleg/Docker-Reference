from datetime import datetime

from sqlalchemy import (MetaData, Table, Column, Integer, Numeric, String, DateTime, ForeignKey, create_engine, select)

from config import POSTGRES_CONNECTION_STRING


class PostgresService:
    def __init__(self):
        self.__metadata = MetaData()
        self.__cookies = Table('cookies', self.__metadata,
                               Column('cookie_id', Integer(), primary_key=True),
                               Column('cookie_name', String(50), index=True),
                               Column('cookie_recipe_url', String(255)),
                               Column('cookie_sku', String(55)),
                               Column('quantity', Integer()),
                               Column('unit_cost', Numeric(12, 2))
                               )

        # self.__users = Table('users', self.__metadata,
        #                      Column('user_id', Integer(), primary_key=True),
        #                      Column('customer_number', Integer(), autoincrement=True),
        #                      Column('username', String(15), nullable=False, unique=True),
        #                      Column('email_address', String(255), nullable=False),
        #                      Column('phone', String(20), nullable=False),
        #                      Column('password', String(25), nullable=False),
        #                      Column('created_on', DateTime(), default=datetime.now),
        #                      Column('updated_on', DateTime(), default=datetime.now, onupdate=datetime.now)
        #                      )

        # self.__orders = Table('orders', self.__metadata,
        #                       Column('order_id', Integer(), primary_key=True),
        #                       Column('user_id', ForeignKey('users.user_id'))
        #                       )

        # self.__line_items = Table('line_items', self.__metadata,
        #                           Column('line_items_id', Integer(), primary_key=True),
        #                           Column('order_id', ForeignKey('orders.order_id')),
        #                           Column('cookie_id', ForeignKey('cookies.cookie_id')),
        #                           Column('quantity', Integer()),
        #                           Column('extended_cost', Numeric(12, 2))
        #                           )

        self.__engine = create_engine(POSTGRES_CONNECTION_STRING)
        self.__connection = self.__engine.connect()
        self.__metadata.create_all(self.__engine)

    def init_data(self):
        ins = self.__cookies.insert()
        inventory_list = [
            {
                'cookie_name': 'peanut butter',
                'cookie_recipe_url': 'http://some.aweso.me/cookie/peanut.html',
                'cookie_sku': 'PB01',
                'quantity': '24',
                'unit_cost': '0.25'
            },
            {
                'cookie_name': 'oatmeal raisin',
                'cookie_recipe_url': 'http://some.okay.me/cookie/raisin.html',
                'cookie_sku': 'EWW01',
                'quantity': '100',
                'unit_cost': '1.00'
            },
            {
                'cookie_name': 'chocolate chip',
                'cookie_recipe_url': 'http://some.aweso.me/cookie/recipe.html',
                'cookie_sku': 'CC01',
                'quantity': '12',
                'unit_cost': '0.50'
            },
            {
                'cookie_name': 'dark chocolate chip',
                'cookie_recipe_url': 'http://some.aweso.me/cookie/recipe_dark.html',
                'cookie_sku': 'CC02',
                'quantity': '1',
                'unit_cost': '0.75'
            }
        ]
        self.__connection.execute(ins, inventory_list)

    def get_data_as_list(self):
        sel = select([self.__cookies.c.cookie_name, self.__cookies.c.quantity])
        rp = self.__connection.execute(sel)
        result = rp.fetchall()
        return result

    def insert_data(self, dto):
        ins = self.__cookies.insert()
        inventory_list = [dto]
        self.__connection.execute(ins, inventory_list)
