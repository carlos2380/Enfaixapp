from flask import json

from api.db.CtrlFactory import get_belong_ctrl
from api.db.DB import DB
from api.colles import colla_service


def get_some_info_belonging_colles_by_user(user_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    ids_belongs_colles = get_belong_ctrl(
        DB(db_configuration).get_database_connection()).get_id_belonging_colles_by_user(user_id)
    belonging_colles = []
    for id in ids_belongs_colles:
        colla = colla_service.get_some_info_colla(id)
        belonging_colles.append(colla)

    return belonging_colles
