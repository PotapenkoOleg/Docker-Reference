import json
import logging
import sys
from datetime import datetime

import requests
from flasgger import Swagger
from flask import Flask, jsonify, request, render_template, redirect, url_for

from config import EXTERNAL_API_URL
from services.data_service import DataService
from services.postgres_service import PostgresService

app = Flask(__name__)
app.config['DEBUG'] = True
Swagger(app)

logging.basicConfig(filename='./logs/example.log', level=logging.DEBUG)

postgresService = None


@app.route('/')
def index():
    date_time = datetime.today().strftime('%Y-%m-%d %H:%M:%S')
    model = {
        'date': f'Today is {date_time}',
        'python_version': f' Python version {sys.version}'
    }
    logging.debug(model['date'])
    return render_template('index.html', model=model)


@app.route('/external_api')
def external_api():
    response = requests.get(EXTERNAL_API_URL)
    model = json.loads(response.content)['value']['quote']
    return render_template('external_api_result.html', model=model)


@app.route('/query_table', methods=['GET'])
def query_table():
    return render_template('query_table.html')


@app.route('/process_table', methods=['POST'])
def process_table():
    sql_query = request.form['query']
    data_service = DataService(timeout=5)
    model = data_service.get_query_results(sql_query)
    return render_template("results_table.html", model=model)


@app.route('/query_json', methods=['GET'])
def query_json():
    return render_template('query_json.html')


@app.route('/process_json', methods=['POST'])
def process_json():
    sql_query = request.form['query']
    data_service = DataService(timeout=5)
    model = data_service.get_query_results(sql_query)
    return jsonify(model)


@app.route('/create_cookie_shop', methods=['GET'])
def create_cookie_shop():
    global postgresService
    if postgresService is None:
        postgresService = PostgresService()
    postgresService.init_data()
    return jsonify('ok')


@app.route('/cookie_shop_list', methods=['GET'])
def cookie_shop_list():
    global postgresService
    if postgresService is None:
        postgresService = PostgresService()
    model = postgresService.get_data_as_list()
    return render_template("cookie_shop_list.html", model=model)


@app.route('/add_cookie', methods=['GET'])
def add_cookie():
    return render_template('add_cookie.html')


@app.route('/add_cookie', methods=['POST'])
def add_cookie_post():
    dto = {
        'cookie_name': request.form['cookie_name'],
        'cookie_recipe_url': request.form['cookie_recipe_url'],
        'cookie_sku': request.form['cookie_sku'],
        'quantity': request.form['quantity'],
        'unit_cost': request.form['unit_cost'],
    }
    global postgresService
    if postgresService is None:
        postgresService = PostgresService()
    postgresService.insert_data(dto)
    return redirect(url_for('cookie_shop_list'))


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
