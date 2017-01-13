class Round:
    def __init__(self, num, tries, castell, result):
        self.num = num
        self.tries = tries
        self.castell = castell
        self.result = result

    def __dict__(self):
        return {
            'num': self.num,
            'tries': self.tries,
            'castell': self.castell,
            'result': self.result
        }