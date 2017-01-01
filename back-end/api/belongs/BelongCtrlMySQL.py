from api.belongs.BelongCtrl import BelongCtrl
from api.colles.Colla import Colla


class BelongCtrlMySQL(BelongCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def insert(self, user, colla):
        sql = "INSERT INTO belongsTo (id_user, id_colla) VALUES (%s, %s)"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (user.id, colla.id))
        self.cnx.commit()
        return

    def insert_belonging_batch(self, belonging_list, user_id):
        params = []
        for belonging in belonging_list:
            params.append((user_id, belonging))

        sql = "INSERT INTO belongsTo (id_user, id_colla) VALUES ('%s', '%s')"
        cursor = self.cnx.cursor()

        cursor.executemany(sql, params)
        self.cnx.commit()
        return

    def get_name_belonging_colles_by_user(self, user_id):
        sql = "SELECT c.name FROM belongsTo b LEFT JOIN colles c ON b.id_colla=c.id WHERE b.id_user = %s" % user_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        names = []
        for tuple in result:
            names.append(tuple[0])
        return names

    def get_id_belonging_colles_by_user(self, user_id):
        sql = "SELECT c.id FROM belongsTo b LEFT JOIN colles c ON b.id_colla=c.id WHERE b.id_user = %s" % user_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        ids = []
        for tuple in result:
            ids.append(tuple[0])
        return ids

    def get_belonging_colles_by_user(self, user_id):
        sql = "SELECT c.id, c.name, c.uni, c.color, c.img_path FROM belongsTo b LEFT JOIN colles c ON b.id_colla = c.id WHERE b.id_user = %s" % user_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        tuples = cursor.fetchall()

        result = []
        for (id, name, uni, color, img_path) in tuples:
            colla = Colla(colla_id=id, name=name, uni=uni, color=color, img=img_path)
            result.append(colla)

        return result
