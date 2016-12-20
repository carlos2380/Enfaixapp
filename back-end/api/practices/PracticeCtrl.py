import abc
from api.db.DB import DB


class PracticeCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def get_practices(self, colla_id):
        pass

    @abc.abstractmethod
    def get_attendants(self, practice_id):
        pass

