from flask import json
from flask import request

from api import app
from api.db.DB import DB
from api.login.basic_auth import requires_auth


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/wall', methods=['GET'])
#@requires_auth
def get_wall():
    dbconf = json.loads(open("api/db/db.json").read())


    return json_string, 200
