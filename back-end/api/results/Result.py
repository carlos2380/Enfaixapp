class Result:
    def __init__(self, colla):
        self.colla = colla
        self.rounds = []

    def __dict__(self):
        return {
            "colla": self.colla,
            "rounds": [rondes.__dict__() for rondes in self.rounds]
        }
