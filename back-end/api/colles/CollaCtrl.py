import abc


class CollaCtrl:
    __metaclass__ = abc.ABCMeta

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
