from flask import abort
from flask import json
from flask import jsonify
from flask import make_response

from api import app
from api.db.DB import DB


# ask the controller to retrieve the user identified by id
# return the resource that represents a user if found
# otherwise, return 404 error code
from wall.rss import rssInfo


@app.route('/wall', methods=['GET'])
# @requires_auth
def get_wall():
    noticies = rssInfo("http://www.cccc.cat/continguts/rss/noticies-1")
    if noticies is None:
        abort(404)
    else:
        return make_response(jsonify(noticies.__dict__), 200)