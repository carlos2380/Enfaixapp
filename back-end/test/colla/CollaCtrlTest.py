import unittest

from flask import json

from api.db.CtrlFactory import CtrlFactory
from api.db.DB import DB


class UserCtrlTest(unittest.TestCase):
    def setUp(self):
        self.dbconf = json.loads(open("../db.json").read())
        self.cnx = DB(self.dbconf).getDatabaseConnection()

    def tearDown(self):
        self.cnx.rollback()

    def test_get_universitaries(self):
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

        colles = CtrlFactory().getCollaCtrl(self.cnx).getUniversitaries()

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

        colles = CtrlFactory().getCollaCtrl(self.cnx).getConvencionals()

        self.assertIsNotNone(colles)
        self.assertEquals(1, len(colles))

        def test_get_All(self):
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

            colles = CtrlFactory().getCollaCtrl(self.cnx).getAll()

            self.assertIsNotNone(colles)
            self.assertEquals(3, len(colles))


def main():
    unittest.main()


if __name__ == '__main__':
    main()
