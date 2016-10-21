import MySQLdb


class DB:
    def __init__(self, JSONconf):
        self.database = JSONconf["database"]
        self.user = JSONconf["user"]
        self.password = JSONconf["password"]

    def getDatabaseConnection(self):
        cnx = MySQLdb.connect(user=self.user, passwd=self.password, db=self.database)
        return cnx
