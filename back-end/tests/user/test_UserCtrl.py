from unittest import TestCase
from flask import json

from api.db.CtrlFactory import get_user_ctrl
from api.db.DB import DB
from api.users.User import User


class UserCtrlTest(TestCase):
    def setUp(self):
        self.db_configuration = json.loads(open("../db.json").read())
        self.cnx = DB(self.db_configuration).get_database_connection()

    def tearDown(self):
        self.cnx.rollback()

    def test_get(self):
        sql = "INSERT INTO users (name, surnames, email, password) " \
              "VALUES ('%s','%s','%s','%s')" % ('Test_Name', 'Test Surname', 'tests@mail.com', 'tests password')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        last_id = cursor.lastrowid

        user = get_user_ctrl(self.cnx).get(last_id)

        self.assertIsNotNone(user)
        self.assertEquals(last_id, user.id)
        self.assertEquals('Test_Name', user.name)
        self.assertEquals('Test Surname', user.surname)
        self.assertEquals('tests@mail.com', user.email)

    def test_get_but_doesnt_exist(self):
        user = get_user_ctrl(self.cnx).get(0)

        self.assertIsNone(user)

    def test_create(self):
        created_user = User("Test_Name", "Test Surname", "tests@mail.com", "tests password")
        ctrl_user = get_user_ctrl(self.cnx)

        created_user = ctrl_user.insert(created_user)

        db_user = ctrl_user.get(created_user.id)
        self.assertIsNotNone(db_user)
        self.assertEquals("Test_Name", db_user.name)
        self.assertEquals("Test Surname", db_user.surname)
        self.assertEquals("tests@mail.com", db_user.email)

    def test_exists_by_email_return_true(self):
        sql = "INSERT INTO users (name, surnames, email, password) " \
              "VALUES ('%s','%s','%s','%s')" % ('Test_Name', 'Test Surname', 'tests@mail.com', 'tests password')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        exists = get_user_ctrl(self.cnx).exists_by_email('tests@mail.com')
        self.assertTrue(exists)

    def test_exists_by_email_return_false(self):
        sql = "INSERT INTO users (name, surnames, email, password) " \
              "VALUES ('%s','%s','%s','%s')" % ('Test_Name', 'Test Surname', 'tests@mail.com', 'tests password')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        exists = get_user_ctrl(self.cnx).exists_by_email('test2@mail.com')
        self.assertFalse(exists)
