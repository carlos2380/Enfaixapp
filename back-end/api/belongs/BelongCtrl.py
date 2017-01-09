import abc

from api.db.DB import DB


class BelongCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def insert(self, user, colla):
        pass

    @abc.abstractmethod
    def insert_belonging_batch(self, belonging_list, user_id):
        pass

    @abc.abstractmethod
    def get_name_belonging_colles_by_user(self, user_id):
        pass

    @abc.abstractmethod
    def get_id_belonging_colles_by_user(self, user_id):
        pass

    @abc.abstractmethod
    def get_belonging_colles_by_user(self, user_id):
        pass

    @abc.abstractmethod
    def delete(self, user_id, colla_id):
        pass

    @abc.abstractmethod
    def get_number_of_users_in_colla(self, colla_id):
        pass