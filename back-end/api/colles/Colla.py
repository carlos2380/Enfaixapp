from flask import json


class Colla(json.JSONEncoder):
    def __init__(self, name, uni, colla_id=None, color=None, img=None, description=None, phoneNumber=None, email=None,
                 web=None, address=None):
        self.id = colla_id
        self.name = name
        self.uni = uni
        self.description = description
        self.phoneNumber = phoneNumber
        self.email = email
        self.web = web
        self.address = address
        self.color = color
        self.img = img
