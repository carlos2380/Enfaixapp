import urllib

from flask import abort, json, jsonify, make_response
from flask import request

from api import app
from api.db.DB import DB
from api.db.CtrlFactory import get_user_ctrl, get_admin_ctrl, get_token_ctrl, get_belonging_ctrl, get_following_ctrl, \
    get_colla_ctrl


@app.route('/users/<int:user_id>', methods=['GET'])
# @requires_auth
def get_user(user_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    user = user_ctrl.get(user_id)
    if user is None:
        abort(404)
    else:
        return make_response(jsonify(user.__dict__), 200)


@app.route('/users/<int:user_id>', methods=['PUT'])
def update_user(user_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    user = user_ctrl.get(user_id)
    if user is None:
        abort(404)
    else:
        body = json.loads(urllib.unquote(request.data))
        new_password = body['password']
        new_name = body['name']
        new_surname = body['surname']
        user.name = new_name
        user.surname = new_surname
        user.password = new_password
        user_ctrl.update(user)
        user.password = None
        user.admin = get_admin_ctrl(DB(db_configuration).get_database_connection()).is_admin(user.id)
        user.session_token = get_token_ctrl(DB(db_configuration).get_database_connection()).get(user.id)
        user.belongs = get_belonging_ctrl(
            DB(db_configuration).get_database_connection()).get_id_belonging_colles_by_user(user.id)
        user.follows = get_following_ctrl(
            DB(db_configuration).get_database_connection()).get_id_followed_colles_by_user(user.id)
        return make_response(jsonify(user.__dict__), 200)
