#ops
import json
from google.appengine.ext import ndb

class Task(ndb.Model):
    name = ndb.StringProperty()
    done = ndb.StringProperty()
    xp = ndb.IntegerProperty()
    questKey = ndb.KeyProperty()
    userKey = ndb.KeyProperty()
    progress = ndb.IntegerProperty()

    def to_dct(self):
        dct = self.to_dict()
        del dct['questKey']
        dct['id'] = self.key.id()
        dct['done'] = 1 if self.done == "true" else 0

        return dct

class Quest(ndb.Model):
    progress = ndb.IntegerProperty()
    name = ndb.StringProperty()
    description = ndb.StringProperty()
    xp = ndb.IntegerProperty()
    deadline = ndb.StringProperty()
    projectKey = ndb.KeyProperty()

    def get_tasks(self):
        return Task.query(Task.questKey==self.key).fetch()

    def delete(self):
        for task in self.get_tasks():
            task.key.delete()
        self.key.delete()

    #temAttachment
    def to_dct(self):
        dct = self.to_dict()
        dct['id'] = self.key.id()
        dct['projectKey'] = self.projectKey.id()

        tasks = self.get_tasks()
        dct['tasks'] = [q.to_dct() for q in tasks]

        return dct

class Project(ndb.Model):
    name = ndb.StringProperty()
    description = ndb.StringProperty()
    xp = ndb.IntegerProperty()
    progress = ndb.IntegerProperty()

    def get_quests(self):
        return Quest.query(Quest.projectKey==self.key).fetch()

    def delete(self):
        for quest in self.get_quests():
            quest.delete()
        self.key.delete()
    #temUser

    #temComments

    def to_dct(self):
        dct = self.to_dict()
        dct['id'] = self.key.id()

        quests = self.get_quests()
        dct['quests'] = [q.to_dct() for q in quests]

        return dct



class User(ndb.Model):
    name = ndb.StringProperty()
    email = ndb.StringProperty()
    picturePath = ndb.StringProperty()



class Attachment(ndb.Model):
    title = ndb.StringProperty()
    name = ndb.StringProperty()
    when = ndb.StringProperty()
    type = ndb.StringProperty()



class Comment(ndb.Model):
    content = ndb.StringProperty()
    when = ndb.StringProperty()

