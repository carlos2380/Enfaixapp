import MySQLdb

config = {
    'user': 'scott',
    'password': 'tiger',
    'host': '127.0.0.1',
    'database': 'employees',
    'raise_on_warnings': True,
}


def get_database():
    cnx = MySQLdb.connect('localhost', 'dbUser', 'dbPass', 'dbName')
    db = cnx.cursor()
    return db
