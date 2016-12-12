from flask import abort
from flask import json
from flask import jsonify
from flask import make_response

from api import app
from api.wall.rss import rss_info, busca_rss
from api.db.DB import DB
from api.db.CtrlFactory import get_follow_ctrl, get_belong_ctrl
from api.results.results import rss_results


@app.route('/results', methods=['GET'])
# @requires_auth
def get_results():
    results = rss_results("http://www.cccc.cat/rss-resultats")
    if results is None:
        abort(404)
    else:
        return make_response(results, 200)