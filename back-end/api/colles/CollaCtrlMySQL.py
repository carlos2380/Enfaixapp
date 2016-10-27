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
        for tuple in result:
            colla = Colla(id=tuple[0], nom=tuple[1], uni=tuple[2], color=tuple[3], img=tuple[4])
            colles.append(colla)

        return colles

    def getUniversitaries(self):
        sql = 'SELECT * FROM colles WHERE uni=%s' % 'TRUE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        universitaries = []
        for tuple in result:
            colla = Colla(id=tuple[0], nom=tuple[1], uni=tuple[2], color=tuple[3], img=tuple[4])
            universitaries.append(colla)

        return universitaries

    def getConvencionals(self):
        sql = 'SELECT * FROM colles WHERE uni=%s' % 'FALSE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        convencionals = []
        for tuple in result:
            colla = Colla(id=tuple[0], nom=tuple[1], uni=tuple[2], color=tuple[3], img=tuple[4])
            convencionals.append(colla)

        return convencionals
