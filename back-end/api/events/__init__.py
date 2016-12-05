import urllib

from flask import abort
from flask import json, jsonify
from flask import make_response
from flask import request

from api import app
import api.db.CtrlFactory
from api.db.DB import DB
from api.events.Event import Event


@app.route("/events", methods=["GET"])
#/events
#   Tots els esdeveniments
#/events?user_id="1"
#   Esdeveniments preferits del usuari 1
def get_events():
    db_configuration = json.loads(open("api/db/db.json").read())
    events_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
    user_id = request.args.get('user_id')
    print user_id
    if user_id is None:
        events = events_ctrl.get_all()
    else:
        eventsFollows = events_ctrl.get_events_follows(user_id)
        eventsBelongs = events_ctrl.get_event_belongs(user_id)
        events = eventsFollows + eventsBelongs
    if not events:
        abort(404)
    json_event_list = json.dumps([event.__dict__ for event in events], ensure_ascii=False, encoding="utf-8")
    return make_response(json_event_list, 200)


@app.route("/events", methods=["POST"])
def create_event():
    try:
        body = json.loads(urllib.unquote(request.data))
        title = body['title']
        description = body['description']
        img = body['img']
        date = body['date']
        address = body['address']
        user_id = body['user_id']
        colla_id = body['colla_id']
        db_configuration = json.loads(open("api/db/db.json").read())
        event_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
        event = Event(title=title, description=description, img=img, date=date,
                      address=address, user_id=user_id, colla_id=colla_id)
        event = event_ctrl.insert(event)
        return make_response(jsonify(event.__dict__), 201)
    except KeyError:
        abort(500)


@app.route("/events/<int:event_id>", methods=["GET"])
def get_event(event_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    events_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
    event = events_ctrl.get(event_id)
    if event is None:
        abort(404)
    return make_response(jsonify(event.__dict__), 200)
