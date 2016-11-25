from api.colles.CollaCtrlMySQL import CollaCtrlMySQL
from api.users.UserCtrlMySQL import UserCtrlMySQL
from api.belongs.BelongCtrlMySQL import BelongCtrlMySQL
from api.follows.FollowCtrlMySQL import FollowCtrlMySQL
from token.TCtrlMySQL import TokenCtrlMySQL


def get_user_ctrl(data_source_connection):
    return UserCtrlMySQL(data_source_connection)


def get_colla_ctrl(data_source_connection):
    return CollaCtrlMySQL(data_source_connection)


def get_belonging_ctrl(data_source_connection):
    return BelongCtrlMySQL(data_source_connection)


def get_following_ctrl(data_source_connection):
    return FollowCtrlMySQL(data_source_connection)


def get_token_ctrl(data_source_connection):
    return TokenCtrlMySQL(data_source_connection)
