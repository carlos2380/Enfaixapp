class User:
    def __init__(self, nom, surname, email):
        self.email = email
        self.name = nom
        self.surname = surname

    def getCompleteName(self):
        return self.name, " ", self.surname
