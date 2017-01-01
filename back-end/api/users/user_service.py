from flask import json

from api.db.CtrlFactory import get_belonging_ctrl, get_admin_ctrl, get_token_ctrl, get_user_ctrl, get_following_ctrl
from api.db.DB import DB


def can_join(user, colla):
    db_configuration = json.loads(open("api/db/db.json").read())
    belonging_ctrl = get_belonging_ctrl(DB(db_configuration).get_database_connection())
    list_colles = belonging_ctrl.get_belonging_colles_by_user(user.id)
    if len(list_colles) == 2:
        return False
    elif len(list_colles) == 1:
        belong_colla = list_colles[0]
        if (belong_colla.uni and colla.uni) or (not belong_colla.uni and not colla.uni):
            return False
    return True


def add_belonging_colla(user, colla):
    db_configuration = json.loads(open("api/db/db.json").read())
    belonging_ctrl = get_belonging_ctrl(DB(db_configuration).get_database_connection())
    belonging_ctrl.insert(user, colla)
    return


def get_all_info(user):
    db_configuration = json.loads(open("api/db/db.json").read())
    bd_user = get_user_ctrl(DB(db_configuration).get_database_connection()).get(user.id)
    user = bd_user
    user.admin = get_admin_ctrl(DB(db_configuration).get_database_connection()).is_admin(user.id)
    user.session_token = get_token_ctrl(DB(db_configuration).get_database_connection()).get(user.id)
    user.belongs = get_belonging_ctrl(
        DB(db_configuration).get_database_connection()).get_id_belonging_colles_by_user(user.id)
    user.follows = get_following_ctrl(
        DB(db_configuration).get_database_connection()).get_id_followed_colles_by_user(user.id)
    return user


def remove_belong(user, colla):
    db_configuration = json.loads(open("api/db/db.json").read())
    get_belonging_ctrl(DB(db_configuration).get_database_connection()).delete(user.id, colla.id)
    return


def add_following(user_id, colla_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    following_ctrl = get_following_ctrl(DB(db_configuration).get_database_connection())
    if not following_ctrl.exists(user_id, colla_id):
        following_ctrl.insert(user_id, colla_id)
    return


def remove_following(user_id, colla_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    following_ctrl = get_following_ctrl(DB(db_configuration).get_database_connection())
    following_ctrl.delete(user_id, colla_id)
    return
