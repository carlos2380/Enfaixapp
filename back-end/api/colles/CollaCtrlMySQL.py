from api.colles.CollaCtrl import CollaCtrl
from api.colles.Colla import Colla


class CollaCtrlMySQL(CollaCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def insert(self, colla):
        sql = "INSERT INTO colles (name, uni, color) " \
              "VALUES ('%s','%s','%s')" % (colla.name, colla.uni, colla.color)
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        last_id = cursor.lastrowid
        colla.id = last_id

        return colla

    def get_all(self):
        sql = 'SELECT * FROM colles'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        colles = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(colla_id=id_colla, name=name_colla, uni=is_uni, color=color, img=path)
            colles.append(colla)

        return colles

    def get_universitaries(self):
        sql = 'SELECT * FROM colles WHERE uni=%s' % 'TRUE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        universitaries = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(colla_id=id_colla, name=name_colla, uni=is_uni, color=color, img=path)
            universitaries.append(colla)

        return universitaries

    def get_convencionals(self):
        sql = 'SELECT * FROM colles WHERE uni=%s' % 'FALSE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        convencionals = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(colla_id=id_colla, name=name_colla, uni=is_uni, color=color, img=path)
            convencionals.append(colla)

        return convencionals
