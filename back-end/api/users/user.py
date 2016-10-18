class User:
    def __init__(self, email, nom, cognom):
        self.email = email
        self.nom = nom
        self.cognom = cognom

    def get_nom_complet(self):
        return self.nom, " ", self.cognom
