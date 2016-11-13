from unittest import TestCase

from flask import json

from api.db.DB import DB
from api.colles.Colla import Colla
from api.db.CtrlFactory import get_user_ctrl, get_colla_ctrl, get_following_ctrl
from api.users.User import User


class TestFollowCtrlMySQL(TestCase):
    def setUp(self):
        self.db_configuration = json.loads(open("../db.json").read())
        self.cnx = DB(self.db_configuration).get_database_connection()

    def tearDown(self):
        self.cnx.rollback()

    def test_get_followed_colles_by_user(self):
        test_user = User(name='Test_name', surname='Test surnames', email='tests@mail.com', password='')
        user_ctrl = get_user_ctrl(self.cnx)
        user_ctrl.insert(test_user)

        colla1 = Colla(name='Test colla 1', uni=0, color='#FFFFFF')
        colla2 = Colla(name='Test colla 2', uni=1, color='#FFFFFF')
        colla3 = Colla(name='Test colla 3', uni=1, color='#FFFFFF')

        colla_ctrl = get_colla_ctrl(self.cnx)
        colla_ctrl.insert(colla1)
        colla_ctrl.insert(colla2)
        colla_ctrl.insert(colla3)

        sql = "INSERT INTO follows(id_user, id_colla) VALUES ('%s','%s')"
        data = [(test_user.id, colla1.id), (test_user.id, colla2.id)]

        self.cnx.cursor().executemany(sql, data)

        following_ctrl = get_following_ctrl(self.cnx)

        following_colles = following_ctrl.get_followed_by_user(test_user.id)

        self.assertEquals(2, len(following_colles))
        self.assertEquals(colla1, following_colles[0])
        self.assertEquals(colla2, following_colles[1])
