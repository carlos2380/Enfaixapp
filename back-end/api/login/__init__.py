# encoding: utf-8
import json
import urllib

from flask import abort, jsonify
from flask import request, make_response

from api import app
from api.db.DB import DB
from api.login.auth_ctrl import create_user, check_password, create_token, get_token_by_user_id, delete_token
from api.db.CtrlFactory import get_user_ctrl, get_admin_ctrl
from api.belongs import belong_service
from api.follows import follow_service
from api.login.UserEncoder import UserEncoder


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
            user.admin = get_admin_ctrl(DB(db_configuration).get_database_connection()).is_admin(user.id)
            user. follows = follow_service.get_some_info_followed_colles_by_user(user.id)
            user.belongs = belong_service.get_some_info_belonging_colles_by_user(user.id)
            response = make_response(json.dumps(user, encoding='utf-8', cls=UserEncoder, indent=4), 200)
            response.headers[0] = ('Content-Type', 'application/json; charset=utf-8')
            return response
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
            new_user.follows = follow_service.get_some_info_followed_colles_by_user(new_user.id)
            new_user.belongs = belong_service.get_some_info_belonging_colles_by_user(new_user.id)
            new_user.admin = get_admin_ctrl(DB(db_configuration).get_database_connection()).is_admin(new_user.id)
            response = make_response(json.dumps(new_user, encoding='utf-8', cls=UserEncoder, indent=4), 201)
            response.headers[0] = ('Content-Type', 'application/json; charset=utf-8')
            return response
        abort(409)
    except KeyError:
        abort(500)


@app.route('/login/<string:token>', methods=['DELETE'])
def log_out(token):
    delete_token(token)
    return make_response(jsonify({}), 204)
