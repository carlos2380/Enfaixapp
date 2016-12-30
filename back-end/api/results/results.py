from flask import json

import feedparser
from bs4 import BeautifulSoup

def rss_results(url, number_of_entries=5):
    d = feedparser.parse(url)
    count = 0
    result = ''
    for post in d.entries:
        if count <= number_of_entries:
            html_data = post.summary.encode('utf-8')
            table_data = [[cell.text for cell in row("td")]
                          for row in BeautifulSoup(html_data, "html.parser")("tr")]
            prova = json.dumps(table_data)
            result = result + prova
            count = count + 1
    return result


def tractar_caracters(text):
    new_text = text.replace(u'&nbsp;', ' ')
    new_text = new_text.replace(u'&ccedil;', 'c')
    return new_text
