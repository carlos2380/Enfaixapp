import abc

from api.db.DB import DB


class FollowCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def insert(self, user_id, colla_id):
        pass

    @abc.abstractmethod
    def exists(self, user_id, coll_id):
        pass

    @abc.abstractmethod
    def get_name_followed_colles_by_user(self, id_user):
        pass

    @abc.abstractmethod
    def get_id_followed_colles_by_user(self, id_user):
        pass

    @abc.abstractmethod
    def insert_following_batch(self, following_list, user_id):
        pass

    @abc.abstractmethod
    def delete(self, user_id, colla_id):
        pass