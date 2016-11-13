from follows.FollowCtrl import FollowCtrl


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

    def get_followed_colles_by_user(self, id_user):
        return
