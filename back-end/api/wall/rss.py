import feedparser
import json
import codecs
import datetime


def rssInfo(url, month=datetime.datetime.today().month, year=datetime.datetime.today().year, number_of_entries=5)
    d = feedparser.parse(url)
    json1 = {}
    id = 0
    count = 0
    for post in d.entries:
        entry = {}
        date = post.published_parsed
        if date.tm_year == year and date.tm_mon == month:
            if (count <= number_of_entries):
                entry['title'] = post.title.encode('utf-8')
                entry['link'] = post.link
                entry['date'] = post.published
                entry['description'] = post.description
                json1[id] = entry
                count = count + 1
            id = id + 1

    return json1
    # print id
    # with open('data.txt', 'w') as f:
    # json.dump(json1, f)