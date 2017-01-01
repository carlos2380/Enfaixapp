import urllib

from flask import abort, json, jsonify, make_response
from flask import request

from api import app
from api.db.DB import DB
from api.db.CtrlFactory import get_user_ctrl, get_colla_ctrl
from api.users import user_service


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
        user = user_service.get_all_info(user)
        return make_response(jsonify(user.__dict__), 200)


@app.route('/users/<int:user_id>/belongs', methods=['POST'])
def add_belong(user_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    user = user_ctrl.get(user_id)
    if user is None:
        abort(404)
    else:
        colla_id = request.args.get('colla_id')
        if colla_id is not None:
            colla = get_colla_ctrl(DB(db_configuration).get_database_connection()).get(colla_id)
            if user_service.can_join(user, colla):
                user_service.add_belonging_colla(user, colla)
                user = user_service.get_all_info(user)
                return make_response(jsonify(user.__dict__), 200)
            else:
                abort(409)
        abort(400)
