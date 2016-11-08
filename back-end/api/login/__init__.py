from flask import abort, jsonify
from flask import request, make_response
from flask import json
from api import app


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
from api.db.CtrlFactory import CtrlFactory
from api.db.DB import DB
from api.login.auth_ctrl import exist_user, create_user, check_password, create_token


@app.route('/login', methods=['POST'])
def log_in():
    body = json.dumps(request.data)
    email = body['email']
    password = body['password']
    token = None
    if (exist_user(email)):
        dbconf = json.loads(open("api/db/db.json").read())
        user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
        user = user_ctrl.get_by_email(email)
        if (check_password(email,password)):
            token = create_token(email, user.id)

    return jsonify({'session-token':token})


@app.route('/signin', methods=['POST'])
def sign_in():
    body = json.dumps(request.data)
    email = body['email']
    password = body['password']
    nom = body['name']
    cognoms = body['surnames']
    collesPertany = body['belongs']
    collesSeguides = body['follows']
    token = None
    if (not exist_user(email)):
        user = create_user(nom,cognoms,email,password,collesPertany,collesSeguides)
        token = create_token(email, user.id)

    return jsonify({'session-token':token})

