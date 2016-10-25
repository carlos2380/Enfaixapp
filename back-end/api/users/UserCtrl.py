import abc

from api.db import DB


class UserCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def get(self, id_user):
        pass

    @abc.abstractmethod
    def insert(self, user):
        pass
