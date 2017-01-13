from flask import abort
from flask import json
from flask import jsonify
from flask import make_response
from flask import request

from api import app
from api.wall.rss import rss_info, busca_rss
from api.db.DB import DB
from api.db.CtrlFactory import get_follow_ctrl, get_belong_ctrl, get_user_ctrl


@app.route('/wall', methods=['GET'])
def get_wall():
    user_id = request.args.get('user_id')
    noticies = []
    if user_id is not None:
        db_configuration = json.loads(open("api/db/db.json").read())
        user_ctrl = get_user_ctrl(DB(db_configuration).get_database_connection())
        user = user_ctrl.get(user_id)
        if user:
            follow_ctrl = get_follow_ctrl(DB(db_configuration).get_database_connection())
            colles_follow = follow_ctrl.get_name_followed_colles_by_user(user_id)
            belong_ctrl = get_belong_ctrl(DB(db_configuration).get_database_connection())
            colles_belongs = belong_ctrl.get_name_belonging_colles_by_user(user_id)
            colles = colles_follow + colles_belongs
            noticies = busca_rss("http://www.cccc.cat/continguts/rss/noticies-1", colles)
        else:
            abort(404)
    else:
        noticies = rss_info("http://www.cccc.cat/continguts/rss/noticies-1")
    return make_response(jsonify(noticies), 200)