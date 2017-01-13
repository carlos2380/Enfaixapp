from unittest import TestCase

from flask import json

from api.db.CtrlFactory import get_colla_ctrl
from api.db.DB import DB
from api.colles.Colla import Colla


class CollaCtrlTest(TestCase):
    def setUp(self):
        self.db_configuration = json.loads(open("../db.json").read())
        self.cnx = DB(self.db_configuration).get_database_connection()

    def tearDown(self):
        self.cnx.rollback()

    def test_insert(self):
        colla = Colla(name="Test_Name", uni=0, color="#123456")

        db_colla = get_colla_ctrl(self.cnx).insert(colla)

        self.assertIsNotNone(db_colla.id)
        self.assertEquals("Test_Name", db_colla.name)
        self.assertFalse(db_colla.uni)
        self.assertEquals("#123456", db_colla.color)

    def test_get_universitaries(self):
        sql = "INSERT INTO colles (name, uni, color, img_path) " \
              "VALUES ('%s','%s','%s','%s')" % ('Test_Name', 1, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name2', 1, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name3', 0, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        colles = get_colla_ctrl(self.cnx).get_universitaries()

        self.assertIsNotNone(colles)
        self.assertEquals(2, len(colles))

    def test_get_convencionals(self):
        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name', 1, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name2', 1, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name3', 0, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        colles = get_colla_ctrl(self.cnx).get_convencionals()

        self.assertIsNotNone(colles)
        self.assertEquals(1, len(colles))

    def test_get_all(self):
        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name', 1, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name2', 1, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        sql = 'INSERT INTO colles (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name3', 0, 'FFFFFF', '~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        colles = get_colla_ctrl(self.cnx).get_all()

        self.assertIsNotNone(colles)
        self.assertEquals(3, len(colles))
