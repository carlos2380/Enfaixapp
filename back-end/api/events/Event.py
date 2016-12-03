from flask import json


class Event(json.JSONEncoder):
    def __init__(self, title, user_id, colla_id, date, id=None, description=None, img=None, address=None):
        self.img = img
        self.description = description
        self.id = id
        self.user_id = user_id
        self.colla_id = colla_id
        self.date = date
        self.address = address
        self.title = title
