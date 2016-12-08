import abc
from api.db.DB import DB


class EventCtrl:
    __metaclass__ = abc.ABCMeta

    def __init__(self, dbconfig):
        self.db = DB.getDatabase(dbconfig.user, dbconfig.password, dbconfig.database_name)

    @abc.abstractmethod
    def get_all(self):
        pass

    @abc.abstractmethod
    def get_events_follows(self, user_id):
        pass

    @abc.abstractmethod
    def get_event_belongs(self, user_id):
        pass

    @abc.abstractmethod
    def get(self):
        pass

    @abc.abstractmethod
    def insert(self, event):
        pass

    @abc.abstractmethod
    def update(self, event):
        pass
