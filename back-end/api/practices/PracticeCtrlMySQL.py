from api.practices.PracticeCtrl import PracticeCtrl
from api.practices.Practice import Practice


class PracticeCtrlMySQL(PracticeCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def get_practices(self, colla_id):
        sql = "select p.id, p.description, p.date, p.address, p.id_colla, count(a.id_user) as people " \
              "from practice p left join attendants a on p.id = a.id_practice " \
              "WHERE p.id_colla = %s " \
              "group by p.id;" % colla_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        practice_list = []
        for (id, date, description, address, id_colla, people) in result:
            practice = Practice(id=id, description=description, date=date, address=address, id_colla=id_colla, people=people)
            practice_list.append(practice)

        return practice_list


    def get_attendants(self, practice_id):
        sql = "select distinct u.name from users u join attendants a on u.id = a.id_user where a.id_practice = %s" % practice_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        attendants = []
        for name in result:
            attendants.append(str(name))

        return attendants


