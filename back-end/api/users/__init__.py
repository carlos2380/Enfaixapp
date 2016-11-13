from flask import abort
from flask import json
from flask import jsonify
from flask import make_response

from api import app
from api.db.DB import DB
from api.users import UserCtrl


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/users/<int:user_id>', methods=['GET'])
# s@requires_auth
def get_user(user_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    from db.CtrlFactory import get_user_ctrl
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    user = user_ctrl.get(user_id)
    if user is None:
        abort(404)
    else:
        return make_response(jsonify(user.__dict__), 200)
