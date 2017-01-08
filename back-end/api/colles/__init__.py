import urllib

from flask import abort, json, make_response, request, jsonify

from api import app
from api.colles import colla_service


@app.route('/colles', methods=['GET'])
#@requires_auth
def get_colles():
    tipus_colla = request.args.get('type')
    if not tipus_colla:
        colles = colla_service.get_all()
    elif tipus_colla == 'uni':
        colles = colla_service.get_universitaries()
    elif tipus_colla == 'conv':
        colles = colla_service.get_convencionals()
    else:
        abort(400)

    json_colla_list = json.dumps([colla.__dict__ for colla in colles], ensure_ascii=False, encoding="utf-8", indent=4)
    response = make_response(json_colla_list, 200)
    response.headers[0] = ('Content-Type', 'application/json; charset=utf-8')
    return response


@app.route('/colles/<int:colla_id>', methods=["GET"])
def get_colla(colla_id):
    colla = colla_service.get_all_info_colla(colla_id)
    if colla:
        colla = json.dumps(colla.__dict__, ensure_ascii=False, encoding="utf-8")
        response = make_response(colla, 200)
        response.headers[0] = ('Content-Type', 'application/json; charset=utf-8')
        return response
    abort(404)


@app.route('/colles/<int:colla_id>', methods=["PUT"])
def modify_colla(colla_id):
    colla = colla_service.get_all_info_colla(colla_id)
    if colla:
        try:
            body = json.loads(urllib.unquote(request.data))
            colla = colla_service.update(colla, body)
            return make_response(jsonify(colla.__dict__), 200)
        except IOError:
            abort(500)
    abort(404)


@app.route('/colles', methods=['POST'])
#@requires_auth
def create_colla():
    abort(501)

