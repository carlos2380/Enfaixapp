from flask import json


class User(json.JSONEncoder):
    def __init__(self, name, surname, email):
        self.email = email
        self.name = name
        self.surname = surname
        self.password = ' '

    def get_complete_name(self):
        return self.name, " ", self.surname

    def set_password(self, password):
        self.password = password
