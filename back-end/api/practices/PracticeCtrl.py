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

    @abc.abstractmethod
    def delete_practice(self, id_practice):
        pass

    @abc.abstractmethod
    def get_practice_by_id(self, id_practice):
        pass

    @abc.abstractmethod
    def delete_attendants(self, id_practice):
        pass

    @abc.abstractmethod
    def insert(self, practice):
        pass

    @abc.abstractmethod
    def insert_attendant(self, attendant):
        pass


