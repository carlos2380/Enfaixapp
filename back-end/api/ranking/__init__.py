from flask import json
from flask import make_response

from api import app
from api.ranking import ranking_service


@app.route("/ranking", methods=["GET"])
def get_ranking():
    ranking = ranking_service.get_ranking()

    ranking_json = json.dumps(ranking)

    return make_response(ranking_json, 200)
