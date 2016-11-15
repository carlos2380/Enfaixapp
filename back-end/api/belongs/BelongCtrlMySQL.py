from api.belongs.BelongCtrl import BelongCtrl


class BelongCtrlMySQL(BelongCtrl):

    def __init__(self, database_connection):
        self.cnx = database_connection

    def insert_belonging_batch(self, belonging_list, user_id):
        params = []
        for belonging in belonging_list:
            params.append((user_id, belonging))

        sql = "INSERT INTO belongsTo (id_user, id_colla) VALUES ('%s', '%s')"
        cursor = self.cnx.cursor()

        cursor.executemany(sql, params)
        self.cnx.commit()
        return

    def get_belonging_colles(self, user_id):
        return
