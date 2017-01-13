from unittest import TestCase

from flask import json

from api.db.DB import DB


class DBTest(TestCase):
    def testInit(self):
        json_data = json.loads(open("../db.json").read())
        db = DB(json_data)

        self.assertEquals("enfaixappDB_test", db.database)
        self.assertEquals("enfaixapp_server", db.user)
        self.assertEquals("", db.password)

    def testGetConnection(self):
        json_data = json.loads(open("../db.json").read())
        cnx = DB(json_data).get_database_connection()

        self.assertIsNotNone(cnx)
