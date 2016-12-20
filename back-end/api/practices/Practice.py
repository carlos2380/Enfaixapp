from flask import json


class Practice(json.JSONEncoder):
    def __init__(self, id, date, id_colla, description=None, address=None, people=None):
        self.id = id
        self.date = date
        self.id_colla = id_colla
        self.description = description
        self.address = address
        self.people = people



