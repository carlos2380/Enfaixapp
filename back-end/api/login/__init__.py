from flask import abort
from flask import request, make_response

from api import app


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
@app.route('/login', methods=['POST'])
def log_in():
    body = json.dumps(request.data)
    email = body['email']
    password = body['password']
    token = None
    if (existUser(email)):
        if (checkPassword(email,password)):
            token = createToken(email)

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
    if (not existUser(email)):
        createUser(nom,cognoms,email,password,collesPertany,collesSeguides)
        token = createToken(email)

    return jsonify({'session-token':token})

