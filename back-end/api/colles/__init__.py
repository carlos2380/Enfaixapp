from flask import json
from flask import request

from api import app
from api.db.DB import DB
from api.login.basic_auth import requires_auth


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/colles', methods=['GET'])
#@requires_auth
def get_colles():
    dbconf = json.loads(open("api/db/db.json").read())

    tipus_colla = request.args.get('type')
    from api.db.CtrlFactory import CtrlFactory
    ctrl_user = CtrlFactory().getCollaCtrl(DB(dbconf).getDatabaseConnection())
    if tipus_colla == 'uni':
        colles = ctrl_user.getUniversitaries()
    elif tipus_colla == 'conv':
        colles = ctrl_user.getConvencionals()
    else:
        colles = ctrl_user.getAll()

    json_string = json.dumps([colla.__dict__ for colla in colles])

    return json_string, 200


@app.route('/colles', methods=['POST'])
#@requires_auth
def create_colla():
    # create new instance of user
    # ask the controller to create this instance in db
    # return the resource created and 201 creation code
    return "Not implemented yet"

