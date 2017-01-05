from flask import json

from api.db.CtrlFactory import get_follow_ctrl
from api.db.DB import DB
from api.colles import colla_service


def get_some_info_followed_colles_by_user(user_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    ids_followed_colles = get_follow_ctrl(DB(db_configuration).get_database_connection()).get_id_followed_colles_by_user(
        user_id)
    followed_colles = []
    for id in ids_followed_colles:
        colla = colla_service.get_some_info_colla(id)
        followed_colles.append(colla)

    return followed_colles
