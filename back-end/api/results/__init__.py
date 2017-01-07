# encoding: utf-8
from __future__ import unicode_literals
from flask import json
from flask import request

from api import app
from api.results import result_service
from api.results.MyEncoder import MyEncoder


@app.route('/results', methods=['GET'])
def get_last_results():
    count = request.args.get('count')
    offset = request.args.get('offset')
    diades = []
    if count and offset:
        diades = result_service.get_results(count=count, offset=offset)
    elif count and not offset:
        diades = result_service.get_results(count=count)
    elif not count and offset:
        diades = result_service.get_results(offset=offset)
    else:
        diades = result_service.get_results()

    return json.dumps(diades, cls=MyEncoder, indent=4)
