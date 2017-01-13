import feedparser


def rss_info(url, number_of_entries=5):
    d = feedparser.parse(url)
    json_new = {}
    count = 0
    for post in d.entries:
        entry = {}
        if count <= number_of_entries:
            entry['title'] = post.title.encode('utf-8')
            entry['link'] = post.link
            entry['date'] = post.published
            description = post.summary.encode('utf-8')
            description = tractar_caracters(description)
            entry['description'] = description
            json_new[count] = entry
            count += 1

    return json_new


def busca_rss(url, colles, number_of_entries=5):
    d = feedparser.parse(url)
    json_new = {}
    count = 0
    for post in d.entries:
        for colla in colles:
            entry = {}
            post_encoded = post.description.encode('utf-8')
            if post_encoded.find(colla) != -1:
                if count <= number_of_entries:
                    entry['title'] = post.title.encode('utf-8')
                    entry['link'] = post.link
                    entry['date'] = post.published
                    description = post.summary.encode('utf-8')
                    description = tractar_caracters(description)
                    entry['description'] = description
                    json_new[count] = entry
                    count += 1

    return json_new


def tractar_caracters(text):
    new_text = text.replace(u'&nbsp;', ' ')
    new_text = new_text.replace(u'&ccedil;', 'c')
    return new_text
