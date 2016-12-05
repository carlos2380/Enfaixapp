import json
import urllib

from flask import abort, jsonify
from flask import request, make_response

from api import app
from api.db.DB import DB
from api.login.auth_ctrl import create_user, check_password, create_token, get_token_by_user_id, delete_token
from api.db.CtrlFactory import get_user_ctrl


@app.route('/login', methods=['POST'])
def log_in():
    body = json.loads(request.data)
    email = body['email']
    password = body['password']
    db_configuration = json.loads(open("api/db/db.json").read())
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    user = user_ctrl.get_by_email(email)
    if user is not None:
        if check_password(email, password):
            token = get_token_by_user_id(user_id=user.id)
            user.session_token = token
            return make_response(jsonify(user.__dict__), 200)
    return abort(403)


@app.route('/signin', methods=['POST'])
def sign_in():
    try:
        body = json.loads(urllib.unquote(request.data))
        email = body['email']
        password = body['password']
        name = body['name']
        surname = body['surname']
        colles_that_belongs_to = body['belongs']
        colles_followed = body['follows']
        db_configuration = json.loads(open("api/db/db.json").read())
        user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
        new_user = user_ctrl.get_by_email(email)
        if new_user is None:
            new_user = create_user(name=name, surname=surname, email=email, password=password,
                                   belonging_list=colles_that_belongs_to, following_list=colles_followed)
            token = create_token(email, new_user.id)
            new_user.session_token = token
            new_user.follows = colles_followed
            new_user.belongs = colles_that_belongs_to
            return make_response(jsonify(new_user.__dict__), 201)
        abort(409)
    except KeyError:
        abort(500)


@app.route('/login/<string:token>', methods=['DELETE'])
def log_out(token):
    delete_token(token)
    return make_response(jsonify({}), 204)
