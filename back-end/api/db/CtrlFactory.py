from api.users.UserCtrlMySQL import UserCtrlMySQL


class CtrlFactory:
    def __init__(self):
        pass

    def __new__(cls):
        if not hasattr(cls, 'instance'):
            cls.instance = super(CtrlFactory, cls).__new__(cls)
        return cls.instance

    def getUserCtrl(self, datasourceConnection):
        return UserCtrlMySQL(datasourceConnection)
