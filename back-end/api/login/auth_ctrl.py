import hashlib
import md5

from flask import json

from api.db.CtrlFactory import CtrlFactory
from api.db.DB import DB
from api.users.User import User


def exist_user(email):
    dbconf = json.loads(open("api/db/db.json").read())
    user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
    return user_ctrl.exists_by_mail(email)

def create_user(name, surname, email, password, belongs, follows):
    dbconf = json.loads(open("api/db/db.json").read())
    user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
    user = User(name, surname, email, password, belongs, follows)
    user = user_ctrl.insert(user)
    return user.token

def check_password( email, password):
    dbconf = json.loads(open("api/db/db.json").read())
    user_ctrl = CtrlFactory().getUserCtrl(DB(dbconf).getDatabaseConnection())
    result = user_ctrl.checkPassword( email, password)
    return result


def create_token(self, email, user_id):
    #calcul token
    m = hashlib.md5(email)
    token = m.hexdigest()
    sql = 'INSERT INTO token (user_id, token) ' \
          'VALUES ("%s", "%s", "%s","%s")' % (user_id, token)
    cursor = self.cnx.cursor()
    cursor.execute(sql)
    # self.cnx.commit()
    return token
