from flask import json


class Colla(json.JSONEncoder):
    def __init__(self, id, nom, uni, color, img):
        self.id = id
        self.name = nom
        self.uni = uni
        self.color = color
        self.img = img

    def isUniversitary(self):
        return self.uni
