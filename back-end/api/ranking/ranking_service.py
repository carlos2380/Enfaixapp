import urllib
from bs4 import BeautifulSoup


def get_ranking():
    html = BeautifulSoup(
        urllib.urlopen("http://www.webcasteller.cat/ranquings-i-estadistiques/ranquing-webcasteller/").read(),
        "html.parser")
    rows = html.find('table').find('tbody').find_all('tr')
    ranking = []
    first = True
    for row in rows:
        if first:
            first = False
        else:
            number = row.find('th').get_text()
            cells = row.find_all('td')
            nom_colla = cells[1].get_text()
            mapper = {
                'nom': nom_colla.strip(),
                'punctuation': cells[2].get_text()
            }
            ranking.append({number: mapper})

    return ranking
