from flask import abort, jsonify, json, make_response, request

from api import app
from api.db.DB import DB
from db.CtrlFactory import get_user_ctrl, get_colla_ctrl, get_admin_ctrl


@app.route('/admin', methods=['POST'])
def grant_admin():
    user_id = request.args.get('user_id')
    colla_id = request.args.get('colla_id')
    if colla_id is None or user_id is None:
        abort(400)
    db_configuration = json.loads(open("api/db/db.json").read())
    user = get_user_ctrl(DB(db_configuration).get_database_connection()).get(user_id)
    colla = get_colla_ctrl(DB(db_configuration).get_database_connection()).get(colla_id)
    if colla is None or user is None:
        abort(404)
    try:
        get_admin_ctrl(DB(db_configuration).get_database_connection()).insert(user.id, colla.id)
    except Exception:
        abort(409)
    return make_response(jsonify({}), 202)


@app.route('/admin', methods=['DELETE'])
def revoke_admin():
    user_id = request.args.get('user_id')
    colla_id = request.args.get('colla_id')
    if colla_id is None or user_id is None:
        abort(400)
    db_configuration = json.loads(open("api/db/db.json").read())
    user = get_user_ctrl(DB(db_configuration).get_database_connection()).get(user_id)
    colla = get_colla_ctrl(DB(db_configuration).get_database_connection()).get(colla_id)
    if colla is None or user is None:
        abort(404)
    get_admin_ctrl(DB(db_configuration).get_database_connection()).delete(user.id, colla.id)
    return make_response(jsonify({}), 202)
