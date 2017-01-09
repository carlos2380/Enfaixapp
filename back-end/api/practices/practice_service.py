from flask import json

from api.db.DB import DB


def get_practices_by_colla(colla_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_practices_ctrl
    practices_ctrl = get_practices_ctrl(DB(db_configuration).get_database_connection())
    practices = practices_ctrl.get_practices(colla_id)
    from api.db.CtrlFactory import get_belong_ctrl
    belong_ctrl = get_belong_ctrl(DB(db_configuration).get_database_connection())
    for practice in practices:
        practice.people = practices_ctrl.get_attendants(practice.id)
        practice.total = belong_ctrl.get_number_of_users_in_colla(colla_id)
    return practices


def get_practice(practice_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_practices_ctrl
    practices_ctrl = get_practices_ctrl(DB(db_configuration).get_database_connection())
    practice = practices_ctrl.get_practice_by_id(practice_id)
    if practice:
        practice.people = practices_ctrl.get_attendants(practice.id)
        from api.db.CtrlFactory import get_belong_ctrl
        belong_ctrl = get_belong_ctrl(DB(db_configuration).get_database_connection())
        practice.total = belong_ctrl.get_number_of_users_in_colla(practice.id_colla)
    return practice


def delete_practice(practice_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_practices_ctrl
    practices_ctrl = get_practices_ctrl(DB(db_configuration).get_database_connection())
    practices_ctrl.delete_practice(practice_id)
    return


def update(practice):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_practices_ctrl
    practices_ctrl = get_practices_ctrl(DB(db_configuration).get_database_connection())
    practice = practices_ctrl.update(practice)
    return practice


def delete_attendant(user, practice):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_practices_ctrl
    practices_ctrl = get_practices_ctrl(DB(db_configuration).get_database_connection())
    practices_ctrl.delete_attendant(practice.id, user.id)
    return


def insert(practice):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_practices_ctrl
    practices_ctrl = get_practices_ctrl(DB(db_configuration).get_database_connection())
    practice = practices_ctrl.insert(practice)
    return practice


def insert_attendant(user, practice):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_practices_ctrl
    practices_ctrl = get_practices_ctrl(DB(db_configuration).get_database_connection())
    practices_ctrl.insert_attendant(user.id, practice.id)
    practice = get_practice(practice.id)
    return practice
