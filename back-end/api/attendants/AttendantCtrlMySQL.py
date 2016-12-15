from api.attendants.AttendantCtrl import AttendantCtrl
from api.attendants.Attendant import Attendant


class AttendantCtrlMySQL(AttendantCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def get_attendants(self):
        sql = "SELECT * FROM attendants"
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        attendants = []
        for (id_user, id_colla, date) in result:
            attendant = Attendant(id_user = id_user, id_colla=id_colla, date=date)
            attendants.append(attendant)

        return attendants

