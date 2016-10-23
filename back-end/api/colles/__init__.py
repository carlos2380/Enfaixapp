from flask import jsonify

from api import app
from api.login.basic_auth import requires_auth


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/colles/<int:colla_id>', methods=['GET'])
@requires_auth
def get_colla(id):
    return "Not implemented yet"


@app.route('/colles', methods=['POST'])
@requires_auth
def create_colla():
    # create new instance of user
    # ask the controller to create this instance in db
    # return the resource created and 201 creation code
    return "Not implemented yet"

