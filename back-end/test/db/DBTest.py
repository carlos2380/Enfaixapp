import unittest

from flask import json

from api.db.DB import DB


class DBTest(unittest.TestCase):
    def testInit(self):
        json_data = json.loads(open("../db.json").read())
        db = DB(json_data)

        self.assertEquals("enfaixappDB_test", db.database)
        self.assertEquals("root", db.user)
        self.assertEquals("", db.password)

    def testGetConnection(self):
        json_data = json.loads(open("../db.json").read())
        cnx = DB(json_data).getDatabaseConnection()

        self.assertIsNotNone(cnx)


def main():
    unittest.main()


if __name__ == '__main__':
    main()
