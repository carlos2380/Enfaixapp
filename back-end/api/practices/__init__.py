import flask
from flask import abort, jsonify
from flask import json
from flask import make_response
from flask import request

from api import app
import api.db.CtrlFactory
from api.db.DB import DB



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
