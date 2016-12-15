from flask import abort
from flask import json
from flask import make_response

from api import app
import api.db.CtrlFactory
from api.db.DB import DB



@app.route('/practices', methods=['GET'])
# @requires_auth
def get_practices():
    db_configuration = json.loads(open("api/db/db.json").read())
    practices_ctrl = api.db.CtrlFactory.get_practices_ctrl(DB(db_configuration).get_database_connection())
    practices = practices_ctrl.get_practices()
    if not practices:
        abort(404)
    json_practices_list = json.dumps([practice.__dict__ for practice in practices], ensure_ascii=False, encoding="utf-8")
    return make_response(json_practices_list, 200)