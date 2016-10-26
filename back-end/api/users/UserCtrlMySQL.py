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
        user = User(name=result[0][1], surname=result[0][2], email=result[0][3])

        return user

    def insert(self, user):
        sql = 'INSERT INTO users (name, surnames, email) ' \
              'VALUES ("%s", "%s", "%s")' % (user.name, user.surname, user.email)
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        #self.cnx.commit()

        last_id = cursor.lastrowid
        user.id = last_id

        return user
