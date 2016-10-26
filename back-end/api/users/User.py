from flask import json


class User(json.JSONEncoder):
    def __init__(self, name, surname, email):
        self.email = email
        self.name = name
        self.surname = surname

    def getCompleteName(self):
        return self.name, " ", self.surname