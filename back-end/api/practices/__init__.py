import urllib

import flask
from flask import abort, jsonify
from flask import json
from flask import make_response
from flask import request

from api import app
import api.db.CtrlFactory
from api.db.DB import DB
from api.practices.Practice import Practice
from api.practices.Attendant import Attendant


@app.route('/practices', methods=['GET'])
# @requires_auth
def get_practices():
    db_configuration = json.loads(open("api/db/db.json").read())
    practices_ctrl = api.db.CtrlFactory.get_practices_ctrl(DB(db_configuration).get_database_connection())
    colla_id = request.args.get('colla_id')
    if colla_id is None:
        abort(404)
    else:
        practices = practices_ctrl.get_practices(colla_id)
        json_practices_list = json.dumps([practice.__dict__ for practice in practices], ensure_ascii=False, encoding="utf-8")
        return make_response(json_practices_list, 200)



@app.route('/practices/<int:id_practice>', methods=['GET'])
# @requires_auth
def get_attendants(id_practice):
    db_configuration = json.loads(open("api/db/db.json").read())
    practices_ctrl = api.db.CtrlFactory.get_practices_ctrl(DB(db_configuration).get_database_connection())
    attendants = practices_ctrl.get_attendants(id_practice)
    json_attendants_list = json.dumps([attendant for attendant in attendants], ensure_ascii=False, encoding="utf-8")
    return make_response(json_attendants_list, 200)


@app.route('/practices/<int:id_practice>', methods=['DELETE'])
# @requires_auth
def delete_practice(id_practice):
    db_configuration = json.loads(open("api/db/db.json").read())
    practices_ctrl = api.db.CtrlFactory.get_practices_ctrl(DB(db_configuration).get_database_connection())
    if practices_ctrl.get_practice_by_id(id_practice) is None:
        abort(404)
    else:
        practices_ctrl.delete_practice(id_practice)
    return make_response(jsonify({}), 200)

@app.route('/practices', methods=['DELETE'])
# @requires_auth
def delete_attendants():
    db_configuration = json.loads(open("api/db/db.json").read())
    practices_ctrl = api.db.CtrlFactory.get_practices_ctrl(DB(db_configuration).get_database_connection())
    id_practice = request.args.get('id_practice')
    if practices_ctrl.get_practice_by_id(id_practice) is None:
        abort(404)
    id_user = request.args.get('id_user')
    user_ctrl = api.db.CtrlFactory.get_user_ctrl(DB(db_configuration).get_database_connection())
    if user_ctrl.get(id_user) is None:
        abort(404)
    else:
        if practices_ctrl.get_practice_by_id(id_practice) is None:
            abort(404)
        else:
            practices_ctrl.delete_attendants(id_practice, id_user)
    return make_response(jsonify({}), 200)

@app.route('/practices/<int:id_practice>', methods=['PUT'])
# @requires_auth
def modify_practice(id_practice):
    pass

@app.route('/practices', methods=['POST'])
# @requires_auth
def create_practice():
    body = json.loads(urllib.unquote(request.data))
    date = body['date']
    description = body['description']
    address = body['address']
    id_colla = body['id_colla']
    db_configuration = json.loads(open("api/db/db.json").read())
    practice_ctrl = api.db.CtrlFactory.get_practices_ctrl(DB(db_configuration).get_database_connection())
    practice = Practice(date=date, description=description, address=address, id_colla=id_colla)
    practice = practice_ctrl.insert(practice)
    return make_response(jsonify(practice.__dict__), 201)


@app.route('/practices/<int:id_practice>', methods=['POST'])
# @requires_auth
def create_assistance(id_practice):
    body = json.loads(urllib.unquote(request.data))
    id_user = body['id_user']
    db_configuration = json.loads(open("api/db/db.json").read())
    practice_ctrl = api.db.CtrlFactory.get_practices_ctrl(DB(db_configuration).get_database_connection())
    attendant = Attendant(id_user = id_user, id_practice =id_practice)
    attendant = practice_ctrl.insert_attendant(attendant)
    return make_response(jsonify(attendant.__dict__), 201)
