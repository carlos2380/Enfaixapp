from api.follows.FollowCtrl import FollowCtrl


class FollowCtrlMySQL(FollowCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def insert_following_batch(self, following_list, user_id):
        params = []
        for following in following_list:
            params.append((user_id, following))

        sql = "INSERT INTO follows (id_user, id_colla) VALUES ('%s', '%s')"
        cursor = self.cnx.cursor()

        cursor.executemany(sql, params)
        self.cnx.commit()

        return

    def get_name_followed_colles_by_user(self, id_user):
        sql = "SELECT c.name FROM follows f LEFT JOIN colles c ON f.id_colla=c.id WHERE f.id_user = %s" % id_user
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        names = []
        for tuple in result:
            names.append(tuple[0])
        return names

    def get_id_followed_colles_by_user(self, id_user):
        sql = "SELECT c.id FROM follows f LEFT JOIN colles c ON f.id_colla=c.id WHERE f.id_user = %s" % id_user
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        ids = []
        for tuple in result:
            ids.append(tuple[0])
        return ids
