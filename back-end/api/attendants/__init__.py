from flask import abort
from flask import json
from flask import make_response

from api import app
import api.db.CtrlFactory
from api.db.DB import DB



@app.route('/attendants', methods=['GET'])
# @requires_auth
def get_attendants():
    db_configuration = json.loads(open("api/db/db.json").read())
    attendants_ctrl = api.db.CtrlFactory.get_attendants_ctrl(DB(db_configuration).get_database_connection())
    attendants = attendants_ctrl.get_attendants()
    if not attendants:
        abort(404)
    json_attendants_list = json.dumps([attendant.__dict__ for attendant in attendants], ensure_ascii=False, encoding="utf-8")
    return make_response(json_attendants_list, 200)