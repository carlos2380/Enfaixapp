from flask import json


class Attendant(json.JSONEncoder):
    def __init__(self, id_user, id_practice):
        self.id = id
        self.id_user = id_user
        self.id_practice = id_practice



