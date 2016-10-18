from api.db import get_database


def insert_user(user):
    db = get_database()

    sql = "INSERT INTO Usuari(email, nom, congnom) VALUES ('%s', '%s', '%s')" % (user.email, user.nom, user.cognom)
    db.execute(sql)
