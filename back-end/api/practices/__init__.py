import urllib
from datetime import datetime
from time import strptime, mktime

from flask import abort, jsonify, json, make_response, request

from api import app
from api.practices.Practice import Practice
from api.colles import colla_service
from api.practices import practice_service
from api.db.DB import DB


@app.route('/practices', methods=['GET'])
def get_practices():
    colla_id = request.args.get('colla_id')
    if colla_id is None:
        abort(400)
    else:
        colla = colla_service.get_some_info_colla(colla_id)
        if colla:
            practices = practice_service.get_practices_by_colla(colla.id)
            json_practices_list = json.dumps([practice.__dict__ for practice in practices],
                                             ensure_ascii=False, encoding="utf-8", indent=4)
            response = make_response(json_practices_list, 200)
            response.headers[0] = ('Content-Type', 'application/json; charset=utf-8')
            return response
        else:
            abort(404)


@app.route('/practices/<practice_id>', methods=['GET'])
def get_attendants(practice_id):
    practice = practice_service.get_practice(practice_id)
    if practice:
        return make_response(jsonify(practice.__dict__), 200)
    else:
        abort(404)


@app.route('/practices/<practice_id>', methods=['DELETE'])
def delete_practice(practice_id):
    practice = practice_service.get_practice(practice_id)
    if practice is None:
        abort(404)
    else:
        practice_service.delete_practice(practice_id)
        return make_response(jsonify({}), 200)


@app.route('/practices/<practice_id>', methods=['PUT'])
def update_practice(practice_id):
    practice = practice_service.get_practice(practice_id)
    if practice:
        try:
            body = json.loads(urllib.unquote(request.data))
            description = body['description']
            date = datetime.fromtimestamp(mktime(strptime(body['date'], '%Y-%d-%m %H:%M:%S')))
            address = body['address']

            practice.description = description
            practice.date = date
            practice.address = address

            practice_service.update(practice)
            return make_response(jsonify(practice.__dict__), 200)
        except KeyError:
            abort(400)
    abort(404)


@app.route('/practices', methods=['POST'])
# @requires_auth
def create_practice():
    try:
        body = json.loads(urllib.unquote(request.data))
        date = datetime.fromtimestamp(mktime(strptime(body['date'], '%Y-%d-%m %H:%M:%S')))
        description = body['description']
        address = body['address']
        id_colla = body['id_colla']

        colla = colla_service.get_some_info_colla(id_colla)
        if colla:
            practice = Practice(date=date, description=description, address=address, id_colla=id_colla)
            practice = practice_service.insert(practice)
            return make_response(jsonify(practice.__dict__), 201)
        else:
            abort(404)
    except KeyError:
        abort(400)


@app.route('/practices/<int:practice_id>/attendants', methods=['POST'])
def add_attendant(practice_id):
    user_id = request.args.get('user_id')
    if user_id:
        practice = practice_service.get_practice(practice_id)
        from api.db.CtrlFactory import get_user_ctrl
        db_configuration = json.loads(open("api/db/db.json").read())
        user = get_user_ctrl(DB(db_configuration).get_database_connection()).get(user_id)
        if practice and user:
            practice = practice_service.insert_attendant(user, practice)
            return make_response(jsonify(practice.__dict__), 201)
        else:
            abort(404)
    else:
        abort(400)


@app.route('/practices/<int:practice_id>/attendants', methods=['DELETE'])
def delete_attendant(practice_id):
    user_id = request.args.get('user_id')
    if user_id:
        practice = practice_service.get_practice(practice_id)
        from api.db.CtrlFactory import get_user_ctrl
        db_configuration = json.loads(open("api/db/db.json").read())
        user = get_user_ctrl(DB(db_configuration).get_database_connection()).get(user_id)
        practice = practice_service.get_practice(practice.id)
        if practice and user:
            practice_service.delete_attendant(user, practice)
            practice = practice_service.get_practice(practice.id)
            return make_response(jsonify(practice.__dict__), 200)
        else:
            abort(404)
    else:
        abort(400)
