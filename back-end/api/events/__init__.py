import base64
import os
import urllib

import datetime
from flask import abort
from flask import json, jsonify
from flask import make_response
from flask import request

from api import app
import api.db.CtrlFactory
from api.db.DB import DB
from api.events.Event import Event


# /events
#   Tots els esdeveniments
# /events?user_id="1"
#   Esdeveniments preferits del usuari 1
@app.route("/events", methods=["GET"])
def get_events():
    db_configuration = json.loads(open("api/db/db.json").read())
    events_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
    user_id = request.args.get('user_id')
    events = []
    if user_id is None:
        events = events_ctrl.get_all()
    else:
        user_ctrl = api.db.CtrlFactory.get_user_ctrl(DB(db_configuration).get_database_connection())
        user = user_ctrl.get(user_id)
        if user:
            events_follows = events_ctrl.get_events_follows(user_id)
            events_belongs = events_ctrl.get_event_belongs(user_id)
            events = events_follows + events_belongs
        else:
            abort(404)
    for event in events:
        encoded_img = None
        if event.img is not None:
            with open(event.img, "rb") as fh:
                encoded_img = base64.b64encode(fh.read())
        event.img = encoded_img
    json_event_list = json.dumps([event.__dict__ for event in events], ensure_ascii=False, encoding="utf-8")
    return make_response(json_event_list, 200)


@app.route("/events", methods=["POST"])
def create_event():
    try:
        body = json.loads(urllib.unquote(request.data))
        title = body['title']
        description = body['description']
        img = body['img']
        image_name = None
        if img is not None:
            image_name = os.path.expanduser("~/images") + "/" + datetime.datetime.today().strftime(
                "%Y-%m-%d_%H:%M:%S") + ".png"
            with open(image_name, "wb") as fh:
                fh.write(img.decode('base64'))
        date = body['date']
        address = body['address']
        user_id = body['user_id']
        colla_id = body['colla_id']
        db_configuration = json.loads(open("api/db/db.json").read())
        event_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
        event = Event(title=title, description=description, img=image_name, date=date,
                      address=address, user_id=user_id, colla_id=colla_id)
        event = event_ctrl.insert(event)
        event.img = img
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
    else:
        encoded_img = None
        if event.img is not None:
            with open(event.img, "rb") as fh:
                encoded_img = base64.b64encode(fh.read())
        event.img = encoded_img
        return make_response(jsonify(event.__dict__), 200)


@app.route('/events/<int:event_id>', methods=['PUT'])
def update_event(event_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    events_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
    event = events_ctrl.get(event_id)
    if event:
        try:
            body = json.loads(urllib.unquote(request.data))
            title = body['title']
            description = body['description']
            img = body['img']
            date = body['date']
            address = body['address']
            user_id = body['user_id']
            colla_id = body['colla_id']

            image_name = None
            if img is not None:
                os.remove(event.img)
                image_name = os.path.expanduser("~/images") + "/" + datetime.datetime.today().strftime(
                    "%Y-%m-%d_%H:%M:%S") + ".png"
                with open(image_name, "wb") as fh:
                    fh.write(img.decode('base64'))
            event.title = title
            event.description = description
            event.img = image_name
            event.date = date
            event.address = address
            event.user_id = user_id
            event.colla_id = colla_id

            events_ctrl.update(event)
            return make_response(jsonify(event.__dict__), 200)
        except KeyError:
            abort(500)
    abort(404)


@app.route('/events/<int:event_id>', methods=['DELETE'])
def delete(event_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    events_ctrl = api.db.CtrlFactory.get_event_ctrl(DB(db_configuration).get_database_connection())
    event = events_ctrl.get(event_id)
    if event:
        if event.img is not None and os.path.exists(event.img):
            os.remove(event.img)
        events_ctrl.delete(event)
        return make_response(jsonify({}), 204)
    else:
        abort(404)
