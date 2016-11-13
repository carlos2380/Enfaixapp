from flask import json


class Colla(json.JSONEncoder):
    def __init__(self, name, uni, colla_id=None, color=None, img=None):
        self.id = colla_id
        self.name = name
        self.uni = uni
        self.color = color
        self.img = img
