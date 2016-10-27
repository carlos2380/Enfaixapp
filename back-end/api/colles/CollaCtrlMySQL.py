from api.colles.CollaCtrl import CollaCtrl
from api.colles.Colla import Colla


class CollaCtrlMySQL(CollaCtrl):
    def __init__(self, databaseConnection):
        self.cnx = databaseConnection

    def getAll(self):
        sql = 'SELECT * FROM colles'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        colles = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(id=id_colla, nom=name_colla, uni=is_uni, color=color, img=path)
            colles.append(colla)

        return colles

    def getUniversitaries(self):
        sql = 'SELECT * FROM colles WHERE uni=%s' % 'TRUE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        universitaries = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(id=id_colla, nom=name_colla, uni=is_uni, color=color, img=path)
            universitaries.append(colla)

        return universitaries

    def getConvencionals(self):
        sql = 'SELECT * FROM colles WHERE uni=%s' % 'FALSE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        convencionals = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(id=id_colla, nom=name_colla, uni=is_uni, color=color, img=path)
            convencionals.append(colla)

        return convencionals
