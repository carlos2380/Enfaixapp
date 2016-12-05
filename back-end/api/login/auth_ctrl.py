import hashlib

from flask import json

from api.db.DB import DB
from api.users.User import User
from api.db.CtrlFactory import get_user_ctrl, get_following_ctrl, get_belonging_ctrl, get_token_ctrl


def create_user(name, surname, email, password, belonging_list, following_list):
    db_configuration = json.loads(open("api/db/db.json").read())
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    user = User(name, surname, email, password=password)
    user = user_ctrl.insert(user)
    user.password = None
    belonging_ctrl = get_belonging_ctrl(DB(db_configuration).get_database_connection())
    belonging_ctrl.insert_belonging_batch(belonging_list, user.id)
    following_ctrl = get_following_ctrl(DB(db_configuration).get_database_connection())
    following_ctrl.insert_following_batch(following_list, user.id)
    return user


def check_password(email, password):
    db_configuration = json.loads(open("api/db/db.json").read())
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    result = user_ctrl.check_password(email, password)
    return result


def create_token(email, user_id):
    m = hashlib.md5(email)
    token = m.hexdigest()
    db_configuration = json.loads(open("api/db/db.json").read())
    user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
    user_ctrl.add_token(user_id, token)
    return token


def get_token_by_user_id(user_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    token_ctrl = get_token_ctrl(DB(db_configuration).get_database_connection())
    token = token_ctrl.get(user_id)
    return token


def delete_token(token):
    db_configuration = json.loads(open("api/db/db.json").read())
    token_ctrl = get_token_ctrl(DB(db_configuration).get_database_connection())
    token_ctrl.delete(token)
    return
