from api.db import DB
from api.colles.Colla import Colla
from api.users.UserCtrl import UserCtrl


class CollaCtrlMySQL(UserCtrl):

    def __init__(self, databaseConnection):
        self.cnx = databaseConnection

    def get(self, id_colla):
        sql = 'SELECT * FROM users WHERE id=%s' % id_colla
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        colla = Colla(result[0][1], result[0][2], result[0][3],result[0][4])

        return colla

    def getUniversitaries(self):
        sql = 'SELECT * FROM users WHERE uni=%s' % 'TRUE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        universitaries = []
        for i in result.size():
            colla = Colla(result[i][1], result[i][2], result[i][3],result[i][4])
            universitaries.append(colla)

        return universitaries


    def getConvencionals(self):
        sql = 'SELECT * FROM users WHERE uni=%s' % 'FALSE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        convencionals = []
        for i in result.size():
            colla = Colla(result[i][1], result[i][2], result[i][3],result[i][4])
            convencionals.append(colla)

        return convencionals

