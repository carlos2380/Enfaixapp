from api.events.EventCtrl import EventCtrl
from api.events.Event import Event


class EventCtrlMySQL(EventCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def insert(self, event):
        pass

    def get_all(self):
        sql = "SELECT * FROM events"
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        events = []
        for (id, title, description, path, address, user_id, colla_id) in result:
            event = Event(id=id, title=title, description=description, address=address, img=path, user_id=user_id,
                          colla_id=colla_id)
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
                          img=result[3], address=result[4], user_id=result[5],
                          colla_id=result[6])

        return event

    def insert (self, event):
        sql = "INSERT INTO events (title, description, path, address, id_user, id_colla) " \
              "VALUES ('%s','%s','%s','%s','%s','%s')" % (event.title, event.description, event.img, event.address, event.user_id, event.colla_id)
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        self.cnx.commit()

        last_id = cursor.lastrowid
        event.id = last_id
