from api.practices.PracticeCtrl import PracticeCtrl
from api.practices.Practice import Practice


class PracticeCtrlMySQL(PracticeCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def get_practices(self, colla_id):
        sql = "select p.id, p.description, p.date, p.address, p.id_colla " \
              "from practice p " \
              "WHERE p.id_colla = %s " \
              "group by p.id;" % colla_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        practice_list = []
        for (id, description, date, address, id_colla) in result:
            practice = Practice(id=id, description=description, date=date, address=address, id_colla=id_colla)
            practice_list.append(practice)

        return practice_list

    def get_attendants(self, practice_id):
        sql = "select distinct u.name, u.surnames from users u join attendants a on u.id = a.id_user " \
              "where a.id_practice = %s" % practice_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        attendants = []
        for tuple in result:
            attendants.append(str(tuple[0] + " " + tuple[1]))

        return attendants

    def get_practice_by_id(self, id_practice):
        sql = "select * from practice where id = %s" % id_practice
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        result = cursor.fetchone()
        practice = None
        if result is not None:
            practice = Practice(id=result[0], description=result[2], date=result[1], address=result[3], id_colla=result[4])
        return practice

    def delete_practice(self, id_practice):
        sql2 = "delete from attendants where id_practice = %s" % id_practice
        cursor = self.cnx.cursor()
        cursor.execute(sql2)
        sql = "delete from practice where id = %s" % id_practice
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        self.cnx.commit()
        return

    def delete_attendant(self, id_practice, id_user):
        sql2 = "delete from attendants where id_practice = %s and id_user = %s"
        cursor = self.cnx.cursor()
        cursor.execute(sql2, (id_practice, id_user))
        self.cnx.commit()
        return

    def insert(self, practice):
        sql = "INSERT INTO practice (date, description, address, id_colla) " \
              "VALUES (%s,%s,%s,%s)"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (practice.date, practice.description, practice.address, practice.id_colla))
        self.cnx.commit()

        last_id = cursor.lastrowid
        practice.id = last_id

        return practice

    def insert_attendant(self, user_id, practice_id):
        sql = "INSERT INTO attendants (id_user, id_practice) " \
              "VALUES (%s,%s)"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (user_id, practice_id))
        self.cnx.commit()
        return

    def update(self, practice):
        sql = "UPDATE practice SET description = %s, date = %s, address = %s WHERE id = %s"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (practice.description, practice.date, practice.address, practice.id))
        self.cnx.commit()

        return practice
