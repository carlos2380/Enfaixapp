import MySQLdb


class DB:
    def __init__(self, json_configuration):
        self.database = json_configuration["database"]
        self.user = json_configuration["user"]
        self.password = json_configuration["password"]

    def get_database_connection(self):
        cnx = MySQLdb.connect(user=self.user, passwd=self.password, db=self.database)
        return cnx
