import json

from api.results.Round import Round
from api.results.Result import Result
from api.results.Performance import Performance


class MyEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Round):
            return {
                'num': obj.num,
                'tries': obj.tries,
                'castell': obj.castell,
                'result': obj.result
            }
        elif isinstance(obj, Result):
            return {
                "colla": obj.colla,
                "rounds": obj.rounds
            }
        elif isinstance(obj, Performance):
            return {
                'id': obj.id,
                'title': obj.title,
                'date': obj.date.isoformat(),
                'results': obj.results
            }
        return json.JSONEncoder.default(self, obj)
