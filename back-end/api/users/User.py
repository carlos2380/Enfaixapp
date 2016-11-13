from flask import json


class User(json.JSONEncoder):

    def __init__(self, name, surname, email, user_id=None, password=None):
        self.id = user_id
        self.email = email
        self.name = name
        self.surname = surname
        self.password = password
        self.belongs = []
        self.follows = []
        self.session_token = None

    def get_complete_name(self):
        return self.name, " ", self.surname

