from api import app


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/wall', methods=['GET'])
# @requires_auth
def get_wall():
    pass