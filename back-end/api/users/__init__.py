from flask import abort
from flask import json
from flask import jsonify
from flask import request

from api import app
from api.db.CtrlFactory import CtrlFactory
from api.db.DB import DB
from api.login.basic_auth import requires_auth
from api.users import UserCtrl


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/users/<int:user_id>', methods=['GET'])
#s@requires_auth
def get_user(user_id):
    dbconf = json.loads(open("api/db/db.json").read())

    user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
    user = user_ctrl.get(user_id)
    if user is None:
        abort(404)
    else:
        return jsonify(user.__dict__), 200
