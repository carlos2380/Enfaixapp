from flask import json


class Practice(json.JSONEncoder):
    def __init__(self, date, id_colla, id=None, description=None, address=None, attendants=[], total=0):
        self.id = id
        self.date = date
        self.id_colla = id_colla
        self.description = description
        self.address = address
        self.people = attendants
        self.total = total



