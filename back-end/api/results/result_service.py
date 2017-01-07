# encoding: utf-8
import _mysql_exceptions
import urllib
from bs4 import BeautifulSoup
from datetime import datetime, date
from time import strptime, mktime

from flask import json

from api.db.DB import DB
from api.results.Performance import Performance
from api.results.Round import Round


def get_results(count=5, offset=0):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_result_ctrl
    result_ctrl = get_result_ctrl(DB(db_configuration).get_database_connection())
    performances = result_ctrl.get_performances(count=int(count), offset=int(offset))
    for performance in performances:
        performance.results = result_ctrl.get_results(performance.id)

    return performances


def check_if_new(pub_date):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_result_ctrl
    result_ctrl = get_result_ctrl(DB(db_configuration).get_database_connection())
    latest_date_in_db = result_ctrl.get_latest_date()
    if latest_date_in_db:
        return datetime.fromtimestamp(mktime(pub_date)) > latest_date_in_db
    else:
        return True


def add_performance(item):
    db_configuration = json.loads(open("api/db/db.json").read())
    from api.db.CtrlFactory import get_result_ctrl
    result_ctrl = get_result_ctrl(DB(db_configuration).get_database_connection())
    pub_date = strptime(item.pubDate.string[:-6], '%a, %d %b %Y %H:%M:%S')
    performance = Performance(title=item.title.string, date=datetime.fromtimestamp(mktime(pub_date)))
    performance = result_ctrl.insert_performance(performance)

    inner_html = BeautifulSoup(item.description.string, 'html.parser')
    rows = inner_html.find('table').find_all('tr')
    colla_name = None
    colla = None
    first = True
    from api.db.CtrlFactory import get_colla_ctrl
    for row in rows:
        if first:
            first = False
        else:
            cells = row.find_all('td')
            name = cells[0].get_text()
            if colla_name != name and name.encode('utf-8') != '':
                colla_name = name
                colla = get_colla_ctrl(DB(db_configuration).get_database_connection()).get_by_name(colla_name)
            if colla:
                ronda = Round(num=cells[1].get_text(), tries=cells[2].get_text(),
                              castell=cells[3].get_text(), result=cells[4].get_text())
                try:
                    result_ctrl.insert_result(performance, colla, ronda)
                except _mysql_exceptions.IntegrityError:
                    pass
    return


def too_old(pub_date):
    if date.today().month - 3 < 1:
        date_6_months_ago = date.today().replace(year=date.today().year - 1, month=((date.today().month - 6) % 12))
    else:
        date_6_months_ago = date.today().replace(month=date.today().month-6)

    return datetime.fromtimestamp(mktime(pub_date)).date() < date_6_months_ago


def import_results(url):
    xml = BeautifulSoup(
        urllib.urlopen(url).read(), "xml")
    items = xml.find_all('item')
    for item in items:
        pub_date = strptime(item.pubDate.string[:-6], '%a, %d %b %Y %H:%M:%S')
        if check_if_new(pub_date) or not too_old(pub_date):
            add_performance(item)
        else:
            break
    return
