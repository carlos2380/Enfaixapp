from flask import abort, json, make_response, request

from api import app
from api.db.DB import DB
from api.colles import colla_service


@app.route('/colles', methods=['GET'])
#@requires_auth
def get_colles():
    db_configuration = json.loads(open("api/db/db.json").read())
    tipus_colla = request.args.get('type')
    from api.db.CtrlFactory import get_colla_ctrl
    ctrl_colla = get_colla_ctrl(DB(db_configuration).get_database_connection())

    if tipus_colla == 'uni':
        colles = colla_service.get_universitaries()
    elif tipus_colla == 'conv':
        colles = colla_service.get_convencionals()
    else:
        colles = colla_service.get_all()

    json_colla_list = json.dumps([colla.__dict__ for colla in colles], ensure_ascii=False, encoding="utf-8")
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


@app.route('/colles', methods=['POST'])
#@requires_auth
def create_colla():
    abort(501)

