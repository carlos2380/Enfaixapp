class Performance:
    def __init__(self, title, date, id=None):
        self.id = id
        self.title = title
        self.date = date
        self.results = []

    def __dict__(self):
        return {
            'id': self.id,
            'title': self.title,
            'date': self.date,
            'results': [result.__dict__() for result in self.results]
        }
