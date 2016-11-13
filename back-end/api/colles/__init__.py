from flask import json
from flask import make_response
from flask import request

from api import app
from api.db.DB import DB


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/colles', methods=['GET'])
#@requires_auth
def get_colles():
    db_configuration = json.loads(open("api/db/db.json").read())
    tipus_colla = request.args.get('type')
    from db.CtrlFactory import get_colla_ctrl
    ctrl_user = get_colla_ctrl(DB(db_configuration).get_database_connection())

    if tipus_colla == 'uni':
        colles = ctrl_user.get_universitaries()
    elif tipus_colla == 'conv':
        colles = ctrl_user.get_convencionals()
    else:
        colles = ctrl_user.get_all()

    json_string = json.dumps([colla.__dict__ for colla in colles])

    return make_response(json_string, 200)


@app.route('/colles', methods=['POST'])
#@requires_auth
def create_colla():
    # create new instance of user
    # ask the controller to create this instance in db
    # return the resource created and 201 creation code
    return "Not implemented yet"

