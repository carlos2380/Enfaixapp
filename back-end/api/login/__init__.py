from flask import abort
from flask import request, make_response

from api import app


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/login', methods=['POST'])
def sign_in():
    cookies = request.cookies
    mail = cookies['mail']
    # if AuthCtrl().exists(mail):
    #     abort()
    # else:
    # body = request.data
    # token = AuthCtrl().insert(body.name, body.surnames)
    token = '123456789'
    response = make_response()
    response.set_cookie("token", value=token)
    return response

# @app.route('/login/<token>', methods=['POST'])
# def create_user():
#     # create new instance of user
#     # ask the controller to create this instance in db
#     # return the resource created and 201 creation code
#     return "Not implemented yet"
