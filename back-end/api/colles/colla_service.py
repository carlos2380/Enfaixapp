import base64
import os

from flask import json
from api.db.DB import DB


def get_all_info_colla(colla_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    from db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colla = colla_ctrl.get_full(colla_id)
    encoded_img = None
    if colla:
        if colla.img is not None:
            try:
                img_path = os.path.expanduser(colla.img)
                with open(img_path, "rb") as fh:
                    encoded_img = base64.b64encode(fh.read())
            except IOError:
                pass
            finally:
                colla.img = encoded_img
    return colla


def get_some_info_colla(colla_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    from db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colla = colla_ctrl.get(colla_id)
    encoded_img = None
    if colla:
        if colla.img is not None:
            try:
                img_path = os.path.expanduser(colla.img)
                with open(img_path, "rb") as fh:
                    encoded_img = base64.b64encode(fh.read())
            except IOError:
                pass
            finally:
                colla.img = encoded_img
    return colla


def get_universitaries():
    db_configuration = json.loads(open("api/db/db.json").read())
    from db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colles = colla_ctrl.get_universitaries()

    for colla in colles:
        encoded_img = None
        if colla.img is not None:
            try:
                img_path = os.path.expanduser(colla.img)
                with open(img_path, "rb") as fh:
                    encoded_img = base64.b64encode(fh.read())
            except IOError:
                pass
            finally:
                colla.img = encoded_img

    return colles


def get_convencionals():
    db_configuration = json.loads(open("api/db/db.json").read())
    from db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colles = colla_ctrl.get_convencionals()

    for colla in colles:
        encoded_img = None
        if colla.img is not None:
            try:
                img_path = os.path.expanduser(colla.img)
                with open(img_path, "rb") as fh:
                    encoded_img = base64.b64encode(fh.read())
            except IOError:
                pass
            finally:
                colla.img = encoded_img

    return colles


def get_all():
    db_configuration = json.loads(open("api/db/db.json").read())
    from db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colles = colla_ctrl.get_all()

    for colla in colles:
        encoded_img = None
        if colla.img is not None:
            try:
                img_path = os.path.expanduser(colla.img)
                with open(img_path, "rb") as fh:
                    encoded_img = base64.b64encode(fh.read())
            except IOError:
                pass
            finally:
                colla.img = encoded_img

    return colles
