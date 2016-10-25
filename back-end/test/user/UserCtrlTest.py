import unittest

from flask import json

from api.db.CtrlFactory import CtrlFactory
from api.db.DB import DB
from api.users.User import User


class UserCtrlTest(unittest.TestCase):
    def setUp(self):
        self.dbconf = json.loads(open("../db.json").read())
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

    def test_create(self):
        created_user = User("Test_Name", "Test Surname", "test@mail.com")
        ctrl_user = CtrlFactory().getUserCtrl(self.cnx)

        created_user = ctrl_user.insert(created_user)

        db_user = ctrl_user.get(created_user.id)
        self.assertIsNotNone(db_user)
        self.assertEquals("Test_Name", db_user.name)
        self.assertEquals("Test Surname", db_user.surname)
        self.assertEquals("test@mail.com", db_user.email)


def main():
    unittest.main()


if __name__ == '__main__':
    main()
