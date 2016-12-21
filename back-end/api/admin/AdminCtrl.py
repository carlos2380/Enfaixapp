import abc

from api.db.DB import DB


class AdminCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def insert(self, user_id, colla_id):
        pass

    @abc.abstractmethod
    def delete(self, user_id, colla_id):
        pass

    def is_admin(self, user_id):
        pass
