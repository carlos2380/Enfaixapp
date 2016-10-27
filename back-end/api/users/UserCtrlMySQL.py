from api.db import DB
from api.users.User import User
from api.users.UserCtrl import UserCtrl


class UserCtrlMySQL(UserCtrl):
    def __init__(self, databaseConnection):
        self.cnx = databaseConnection

    def get(self, id_user):
        sql = 'SELECT * FROM users WHERE id=%s' % id_user
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        user = None
        if len(result) != 0:
            user = User(name=result[0][1], surname=result[0][2], email=result[0][3])

        return user

    def insert(self, user):
        sql = 'INSERT INTO users (name, surnames, email, password) ' \
              'VALUES ("%s", "%s", "%s","%s")' % (user.name, user.surname, user.email, user.password)
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        # self.cnx.commit()

        last_id = cursor.lastrowid
        user.id = last_id

        return user

    def exists_by_mail(self, email):
        # This is because MySQL has problems with @, so email has to be surrounded by ''
        param = "'" + email + "'"
        sql = 'SELECT COUNT(*) FROM users WHERE email=%s' % param
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        count = cursor.fetchone()[0]
        return count > 0
