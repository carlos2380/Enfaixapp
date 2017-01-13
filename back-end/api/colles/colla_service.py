import base64
import os

from flask import json
from api.db.DB import DB


def get_all_info_colla(colla_id):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_colla_ctrl
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
    from api.db.CtrlFactory import get_colla_ctrl
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
    from api.db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colles = colla_ctrl.get_universitaries()

    # for colla in colles:
    #     encoded_img = None
    #     if colla.img is not None:
    #         try:
    #             img_path = os.path.expanduser(colla.img)
    #             with open(img_path, "rb") as fh:
    #                 encoded_img = base64.b64encode(fh.read())
    #         except IOError:
    #             pass
    #         finally:
    #             colla.img = encoded_img

    return colles


def get_convencionals():
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colles = colla_ctrl.get_convencionals()

    # for colla in colles:
    #     encoded_img = None
    #     if colla.img is not None:
    #         try:
    #             img_path = os.path.expanduser(colla.img)
    #             with open(img_path, "rb") as fh:
    #                 encoded_img = base64.b64encode(fh.read())
    #         except IOError:
    #             pass
    #         finally:
    #             colla.img = encoded_img

    return colles


def get_all():
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colles = colla_ctrl.get_all()

    # for colla in colles:
    #     encoded_img = None
    #     if colla.img is not None:
    #         try:
    #             img_path = os.path.expanduser(colla.img)
    #             with open(img_path, "rb") as fh:
    #                 encoded_img = base64.b64encode(fh.read())
    #         except IOError:
    #             pass
    #         finally:
    #             colla.img = encoded_img

    return colles


def update(colla, body):
    name = body['name']
    uni = body['uni']
    description = body['description']
    phoneNumber = body['phoneNumber']
    email = body['email']
    web = body['web']
    address = body['address']
    color = body['color']
    img = body['img']

    image_name = None
    if img is not None:
        if colla.img is not None and os.path.exists(colla.img):
            os.remove(colla.img)
        image_name = os.path.expanduser("~/images/colles") + "/" + colla.name.lower() + ".png"
        with open(image_name, "wb") as fh:
            fh.write(img.decode('base64'))

    colla.name = name
    colla.uni = uni
    colla.description = description
    colla.phoneNumber = phoneNumber
    colla.email = email
    colla.web = web
    colla.address = address
    colla.color = color
    colla.img = image_name

    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_colla_ctrl
    colla_ctrl = get_colla_ctrl(DB(db_configuration).get_database_connection())
    colla_ctrl.update(colla)
    colla.img = img
    return colla
