import unittest

from flask import json

from api.db.CtrlFactory import CtrlFactory
from api.db.DB import DB


class UserCtrlTest(unittest.TestCase):
    def setUp(self):
        self.dbconf = json.loads(open("../db.JSON").read())
        self.cnx = DB(self.dbconf).getDatabaseConnection()

    def tearDown(self):
        self.cnx.rollback()

    def test_get(self):
        sql = 'INSERT INTO users (name, surnames, email) ' \
              'VALUES ("%s", "%s", "%s")' % ('Test_Name', 'Test Surname', 'test@mail.com')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        last_id = cursor.lastrowid

        user = CtrlFactory().getUserCtrl(self.cnx).get(last_id)

        self.assertIsNotNone(user)
        self.assertEquals('Test_Name', user.name)
        self.assertEquals('Test Surname', user.surname)
        self.assertEquals('test@mail.com', user.email)


def main():
    unittest.main()


if __name__ == '__main__':
    main()
