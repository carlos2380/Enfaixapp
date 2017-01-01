from api.users.User import User
from api.users.UserCtrl import UserCtrl


class UserCtrlMySQL(UserCtrl):
    def __init__(self, database_connection):
        self.cnx = database_connection

    def get(self, id_user):
        sql = 'SELECT * FROM users WHERE id=%s' % id_user
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchone()
        user = None
        if result:
            int(result[0])
            user = User(user_id=int(result[0]), name=result[1], surname=result[2], email=result[3])

        return user

    def get_by_email(self, email):
        # This is because MySQL has problems with @, so email has to be surrounded by ''
        param = "'" + email + "'"
        sql = 'SELECT * FROM users WHERE email=%s' % param
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        result = cursor.fetchone()
        user = None
        if result is not None:
            user = User(user_id=result[0], name=result[1], surname=result[2], email=result[3])
        return user

    def insert(self, user):
        sql = "INSERT INTO users (name, surnames, email, password) " \
              "VALUES ('%s','%s','%s','%s')" % (user.name, user.surname, user.email, user.password)
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        self.cnx.commit()

        last_id = cursor.lastrowid
        user.id = last_id

        return user

    def exists_by_email(self, email):
        # This is because MySQL has problems with @, so email has to be surrounded by ''
        param = "'" + email + "'"
        sql = 'SELECT COUNT(*) FROM users WHERE email=%s' % param
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        count = cursor.fetchone()
        return count > 0

    def check_password(self, expected_email, expected_password):
        # This is because MySQL has problems with @, so email has to be surrounded by ''
        param = "'" + expected_email + "'"
        sql = 'SELECT password FROM users WHERE email=%s' % param
        cursor = self.cnx.cursor()
        cursor.execute(sql)

        db_password = cursor.fetchone()[0]
        return db_password == expected_password

    def add_token(self, user_id, token):
        sql = "INSERT INTO tokens (id_user, token) " \
              "VALUES ('%s','%s')" % (user_id, token)
        cursor = self.cnx.cursor()
        cursor.execute(sql)
        self.cnx.commit()
        return

    def update(self, user):
        sql = "UPDATE users SET name = %s, surnames = %s, password = %s WHERE id = %s"
        cursor = self.cnx.cursor()
        cursor.execute(sql, (user.name, user.surname, user.password, user.id))
        self.cnx.commit()

        return user
