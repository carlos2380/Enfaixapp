from _bsddb import api, DB

from flask import json

import api.db.CtrlFactory
from api.db.DB import DB
from api.events.EventCtrl import EventCtrl
from api.events.Event import Event


class EventCtrlMySQL(EventCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def get_all(self):
        sql = "SELECT * FROM events"
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        events = []
        for (id, title, description, path, date, address, user_id, colla_id) in result:
            event = Event(id=id, title=title, description=description, date=date, address=address, img=path,
                          user_id=user_id, colla_id=colla_id)
            events.append(event)

        return events

    def get_events_follows(self, user_id):
        db_configuration = json.loads(open("api/db/db.json").read())
        follow_ctrl = api.db.CtrlFactory.get_follow_ctrl(DB(db_configuration).get_database_connection())
        id_colles_follow = follow_ctrl.get_id_followed_colles_by_user(user_id)
        events = []
        for id_colla in id_colles_follow:
            sql = "SELECT * FROM events WHERE id_colla = %s" % id_colla
            cursor = self.cnx.cursor()
            cursor.execute(sql)

            result = cursor.fetchone()
            event = None
            if result is not None:
                event = Event(title=result[0], description=result[1], img=result[2], date=result[3],
                      address=result[4], user_id=result[5], colla_id=result[6])
                events.append(event)

        return events

    def get_event_belongs(self, user_id):
        db_configuration = json.loads(open("api/db/db.json").read())
        belong_ctrl = api.db.CtrlFactory.get_belong_ctrl(DB(db_configuration).get_database_connection())
        id_colles_belongs = belong_ctrl.get_id_belonging_colles_by_user(user_id)
        events = []
        for id_colla in id_colles_belongs:
            sql = "SELECT * FROM events WHERE id_colla = %s" % id_colla
            cursor = self.cnx.cursor()
            cursor.execute(sql)

            result = cursor.fetchone()
            event = None
            if result is not None:
                event = Event(title=result[0], description=result[1], img=result[2], date=result[3],
                              address=result[4], user_id=result[5], colla_id=result[6])
                events.append(event)

        return events

    def get(self, event_id):
        sql = "SELECT * FROM events WHERE id = %s" % event_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchone()
        event = None
        if result:
            event = Event(id=result[0], title=result[1], description=result[2],
                          img=result[3], date=result[4], address=result[5], user_id=result[6],
                          colla_id=result[7])

        return event

    def insert(self, event):
        sql = "INSERT INTO events (title, description, path, date, address, id_user, id_colla) " \
              "VALUES ('%s','%s','%s','%s','%s','%s','%s')" % (event.title, event.description, event.img, event.date,
                                                               event.address, event.user_id, event.colla_id)
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        self.cnx.commit()

        last_id = cursor.lastrowid
        event.id = last_id

        return event
