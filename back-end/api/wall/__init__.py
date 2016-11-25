from flask import abort
from flask import jsonify
from flask import make_response

from api import app
from api.wall.rss import rss_info


@app.route('/wall', methods=['GET'])
# @requires_auth
def get_wall():
    noticies = rss_info("http://www.cccc.cat/continguts/rss/noticies-1")
    if noticies is None:
        abort(404)
    else:
        return make_response(jsonify(noticies), 200)