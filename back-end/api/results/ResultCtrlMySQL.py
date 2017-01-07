from api.results.ResultCtrl import ResultCtrl
from api.results.Performance import Performance
from api.results.Result import Result
from api.results.Round import Round


class ResultCtrlMySQL(ResultCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def insert_performance(self, performance):
        sql = "INSERT INTO performances (title, date) VALUES (%s, %s)"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (performance.title.encode('utf-8'), performance.date))
        self.cnx.commit()

        last_id = cursor.lastrowid
        performance.id = last_id

        return performance

    def insert_result(self, performance, colla, round):
        sql = "INSERT INTO results(id_performance, id_colla, round, try, castell, result) VALUES (%s, %s, %s, %s, %s, %s)"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (performance.id, colla.id, round.num, round.tries, round.castell, round.result))
        self.cnx.commit()

        return

    def get_performances(self, count=5, offset=0):
        sql = "SELECT * FROM performances ORDER BY date DESC LIMIT %s, %s"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (offset, count))

        tuples = cursor.fetchall()
        performances = []
        for (id, title, date) in tuples:
            performance = Performance(id=id, title=title, date=date)
            performances.append(performance)

        return performances

    def get_results(self, performance_id):
        sql = "SELECT c.name, r.round, r.try, r.castell, r.result FROM results r INNER JOIN colles c ON r.id_colla = c.id " \
              "WHERE id_performance = '%s' GROUP BY name, round, try, castell, result ORDER BY name, round, try" % performance_id
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        tuples = cursor.fetchall()
        colla = None
        results = []
        result = None
        for (colla_name, ronda, intents, castell, resultat) in tuples:
            if colla_name != colla:
                colla = colla_name
                result = Result(colla_name)
                results.append(result)
            round = Round(num=ronda, tries=intents, castell=castell, result=resultat)
            result.rounds.append(round)
        return results

    def get_latest_date(self):
        sql = "SELECT MIN(date) FROM performances"
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        last_date = cursor.fetchone()[0]
        return last_date

