from token.TCtrl import TokenCtrl


class TokenCtrlMySQL(TokenCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def get(self, id_user):
        sql = "SELECT token FROM tokens WHERE id_user='%s'" % id_user
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        token = cursor.fetchone()
        return token

