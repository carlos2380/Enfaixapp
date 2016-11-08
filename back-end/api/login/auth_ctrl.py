from flask import json

from api.db.CtrlFactory import CtrlFactory
from api.db.DB import DB

def existUser(email):
    dbconf = json.loads(open("api/db/db.json").read())
    user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
    user = user_ctrl.getByEmail(email)
    if user is None:
        return True
    else:
        return False

def createUser(name, surname, email, password, belongs, follows):
    dbconf = json.loads(open("api/db/db.json").read())
    user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
    user = User(name, surname, email, password, belongs, follows)
    user = user_ctrl.insert(user)
    return user.token

def checkPassword( email, password):
    dbconf = json.loads(open("api/db/db.json").read())
    user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
    token = email*123454321
    result = user_ctrl.checkPassword( email, password)
    return result