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
        user = User(result[0][1], result[0][2], result[0][3])

        return user
