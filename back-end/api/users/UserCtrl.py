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
    def get_by_email(self, email):
        pass

    @abc.abstractmethod
    def insert(self, user):
        pass

    @abc.abstractmethod
    def exists_by_email(self, email):
        pass

    @abc.abstractmethod
    def check_password(self, expected_email, expected_password):
        pass

    @abc.abstractmethod
    def add_token(self, user_id, token):
        pass

    @abc.abstractmethod
    def update(self, user):
        pass
