from api.colles.CollaCtrl import CollaCtrl
from api.colles.Colla import Colla


class WallCtrlMySQL(CollaCtrl):
    def __init__(self, databaseConnection):
        self.cnx = databaseConnection

    def getAll(self):

        return "Not implemented"