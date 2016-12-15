from api.practices.PracticeCtrl import PracticeCtrl
from api.practices.Practice import Practice


class PracticeCtrlMySQL(PracticeCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def get_practices(self):
        sql = "SELECT * FROM practice"
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchall()
        practice = []
        for (date, description, address, id_colla) in result:
            practice2 = Practice(description=description, date=date, address=address, id_colla=id_colla)
            practice.append(practice2)

        return practice

