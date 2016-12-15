from flask import json


class Attendant(json.JSONEncoder):
    def __init__(self, id_user, id_colla, date):
        self.id_user = id_user
        self.id_colla = id_colla
        self.date = date



