from flask import Flask, jsonify, make_response

app = Flask(__name__)


@app.route('/')
def hello():
    return "Hello World!"


@app.errorhandler(404)
def not_found():
    return make_response(jsonify({'error': 'Resource not found'}), 404)


@app.errorhandler(403)
def not_authorized(e):
    return make_response(jsonify({'error': 'Unauthorized access'}), 403)


import api.users
import api.login
