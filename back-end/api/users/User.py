from flask import json


class User(json.JSONEncoder):
    def __init__(self, name, surname, email, belongs, follows):
        self.email = email
        self.name = name
        self.surname = surname
        self.password = ' '
        self.belongs = []
        self.follows = []

    def get_complete_name(self):
        return self.name, " ", self.surname

    def set_password(self, password):
        self.password = password

    def get_follows(self):
        return self.follows

    def get_belongs(self):
        return self.belongs
