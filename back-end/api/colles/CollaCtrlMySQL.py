# encoding: utf-8
from __future__ import unicode_literals

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
        sql = 'SELECT c.id, c.name, c.uni, c.color, c.img_path FROM colles c'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        colles = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(colla_id=id_colla, name=name_colla, uni=is_uni, color=color, img=path)
            colles.append(colla)

        return colles

    def get_universitaries(self):
        sql = 'SELECT c.id, c.name, c.uni, c.color, c.img_path FROM colles c WHERE uni=%s' % 'TRUE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        universitaries = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(colla_id=id_colla, name=name_colla, uni=is_uni, color=color, img=path)
            universitaries.append(colla)

        return universitaries

    def get_convencionals(self):
        sql = 'SELECT c.id, c.name, c.uni, c.color, c.img_path FROM colles c WHERE uni=%s' % 'FALSE'
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        convencionals = []
        for (id_colla, name_colla, is_uni, color, path) in result:
            colla = Colla(colla_id=id_colla, name=name_colla, uni=is_uni, color=color, img=path)
            convencionals.append(colla)

        return convencionals

    def get(self, colla_id):
        sql = "SELECT c.id, c.name, c.uni, c.color, c.img_path FROM colles c WHERE id = %s" % colla_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchone()
        colla = None
        if result:
            colla = Colla(colla_id=result[0], name=result[1], uni=result[2], color=result[3], img=result[4])

        return colla

    def get_full(self, colla_id):
        sql = "SELECT * FROM colles WHERE id = %s" % colla_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchone()
        colla = None
        if result:
            colla = Colla(colla_id=result[0], name=result[1], uni=result[2], description=result[3],
                          phoneNumber=result[4], email=result[5], web=result[6], address=result[7], color=result[8],
                          img=result[9])

        return colla

    def update(self, colla):
        sql = "UPDATE colles SET name = %s, uni = %s, description = %s, phoneNumber = %s, email = %s, web = %s, address = %s, color = %s, img_path = %s WHERE id = %s"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (colla.name, colla.uni, colla.description, colla.phoneNumber,
                             colla.email, colla.web, colla.address, colla.color, colla.img, colla.id))
        self.cnx.commit()
        return

    def get_by_name(self, colla_name):
        sql = 'SELECT * FROM colles WHERE name = "%s"' % colla_name.encode('utf-8')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchone()
        colla = None
        if result:
            colla = Colla(colla_id=result[0], name=result[1], uni=result[2], phoneNumber=result[3], email=result[4],
                          web=result[5], address=result[6], color=result[7], img=result[8])
        return colla

