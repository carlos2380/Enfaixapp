import abc
from api.db.DB import DB


class CollaCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def insert(self, colla):
        pass

    @abc.abstractmethod
    def get_all(self):
        pass

    @abc.abstractmethod
    def get_universitaries(self):
        pass

    @abc.abstractmethod
    def get_convencionals(self):
        pass

    @abc.abstractmethod
    def get(self, colla_id):
        pass

    @abc.abstractmethod
    def get_full(self, colla_id):
        pass
