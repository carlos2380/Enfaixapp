from flask import json


class Practice(json.JSONEncoder):
    def __init__(self, date, id_colla, description=None, address=None):
        self.date = date
        self.id_colla = id_colla
        self.description = description
        self.address = address



