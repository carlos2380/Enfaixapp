import abc
from api.db import DB


class ResultCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def insert_performance(self, performance):
        pass

    @abc.abstractmethod
    def insert_result(self, performance, colla, round):
        pass

    @abc.abstractmethod
    def get_performances(self, count, offset):
        pass

    @abc.abstractmethod
    def get_results(self, performance_id):
        pass

    @abc.abstractmethod
    def get_latest_date(self):
        pass
