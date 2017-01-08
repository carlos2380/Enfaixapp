# encoding: utf-8
from flask import json

from api.users.User import User
from api.colles.Colla import Colla


class UserEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, User):
            return {
                "id": obj.id,
                "email": obj.email,
                "name": obj.name,
                "surname": obj.surname,
                "password": obj.password,
                "belongs": obj.belongs,
                "follows": obj.follows,
                "session_token": obj.session_token,
                "admin": obj.admin
            }
        elif isinstance(obj, Colla):
            return {
                "id": obj.id,
                "name": obj.name,
                "uni": obj.uni,
                "description": obj.description,
                "phoneNumber": obj.phoneNumber,
                "email": obj.email,
                "web": obj.web,
                "address": obj.address,
                "color": obj.color,
                "img": obj.img
            }
        return json.JSONEncoder.default(self, obj)
