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
        sql = 'INSERT INTO users (name, uni, color, img_path) ' \
              'VALUES ("%s", "%s", "%s", "%s")' % ('Test_Name', 'TRUE', 'FFFFFF','~/TestProjectes')
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        last_id = cursor.lastrowid

        colla = CtrlFactory().getCollaCtrl(self.cnx).get(last_id)

        self.assertIsNotNone(colla)
        self.assertEquals('Test_Name', colla.name)
        self.assertEquals('TRUE', colla.uni)
        self.assertEquals('FFFFFF', colla.color)
        self.assertEquals('~/TestProjectes', colla.img_path)


def main():
    unittest.main()


if __name__ == '__main__':
    main()
