from functools import wraps

from flask import request, abort


def check_auth(email, password):
    # asks the controller if the user is authorized
    # returns if the user with email and password is authorized
    return email == "me" and password == "me"


def authenticate():
    abort(403)


def requires_auth(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        auth = request.authorization
        if not auth or not check_auth(auth.username, auth.password):
            return authenticate()
        return f(*args, **kwargs)

    return decorated
