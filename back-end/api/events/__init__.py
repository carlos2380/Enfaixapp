from flask import abort
from flask import json, jsonify
from flask import make_response

from api import app
import api.db.CtrlFactory
from api.db.DB import DB


@app.route("/events", methods=["GET"])
def get_events():
    db_configuration = json.loads(open("api/db/db.json").read())
    events_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
    events = events_ctrl.get_all()
    json_event_list = json.dumps([event.__dict__ for event in events], ensure_ascii=False, encoding="utf-8")

    return make_response(json_event_list, 200)


@app.route("/events", methods=["POST"])
def create_event():
    abort(501)


@app.route("/events/<int:event_id>", methods=["GET"])
def get_event(event_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    events_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
    event = events_ctrl.get(event_id)
    if event is None:
        abort(404)
    return make_response(jsonify(event.__dict__), 200)
