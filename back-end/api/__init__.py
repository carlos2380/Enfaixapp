from flask import Flask, jsonify, make_response

app = Flask(__name__)


@app.route('/')
def hello():
    return "Hello World!\n"


@app.errorhandler(404)
def not_found(e):
    return make_response(jsonify({'error': 'Resource not found'}), 404)


@app.errorhandler(403)
def not_authorized(e):
    return make_response(jsonify({'error': 'Unauthorized access'}), 403)


@app.errorhandler(409)
def conflict_in_resource(e):
    return make_response(jsonify({'error': 'The resource is duplicated'}), 409)


@app.errorhandler(500)
def server_error(e):
    return make_response(jsonify({'error': 'Internal server error'}), 500)


import api.users
import api.login
import api.colles
import api.wall
