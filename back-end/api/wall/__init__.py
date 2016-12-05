from flask import abort
from flask import json
from flask import jsonify
from flask import make_response

from api import app
from api.wall.rss import rss_info, busca_rss
from api.db.DB import DB
from api.db.CtrlFactory import get_follow_ctrl, get_belong_ctrl


@app.route('/wall', methods=['GET'])
# @requires_auth
def get_wall():
    noticies = rss_info("http://www.cccc.cat/continguts/rss/noticies-1")
    if noticies is None:
        abort(404)
    else:
        return make_response(jsonify(noticies), 200)


@app.route('/wall/<int:user_id>', methods=['GET'])
def get_wall_by_favourite(user_id):
    dbconfiguration = json.loads(open("api/db/db.json").read())
    follow_ctrl = get_follow_ctrl(DB(dbconfiguration).get_database_connection())
    colles_follow = follow_ctrl.get_name_followed_colles_by_user_name(user_id)
    belong_ctrl = get_belong_ctrl(DB(dbconfiguration).get_database_connection())
    colles_belongs = belong_ctrl.get_name_belonging_colles_by_user_name(user_id)
    colles = colles_follow + colles_belongs
    if not colles:
        abort(404)
    noticies = busca_rss("http://www.cccc.cat/continguts/rss/noticies-1", colles)
    if not noticies:
        abort(404)
    return make_response(jsonify(noticies), 200)
