import abc

from api.db import DB


class CollaCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def get(self, id_colla):
        pass

    @abc.abstractmethod
    def getUniversitaries(self):
        pass

    @abc.abstractmethod
    def getConvencionals(self):
        pass
