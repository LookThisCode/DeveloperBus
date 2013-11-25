
import json
import webapp2
from model import Project,Quest,Task
from google.appengine.ext import ndb

class QuestCreateHandle(webapp2.RequestHandler):
    def post(self):
        quest = Quest(name=self.request.get('name'),
                      description=self.request.get('description'),
                      xp=int(self.request.get('xp')),
                      deadline=self.request.get('deadline'),
                      progress=int(self.request.get('progress')),
                      projectKey=ndb.Key(Project, int(self.request.get('projectId'))))
        quest.put()
        quest = quest.to_dct()
        self.response.write(quest['id'])



class QuestListHandle(webapp2.RedirectHandler):
    def get(self):
        quests = Quest.query().fetch()
        quests = [p.to_dct() for p in quests]
        self.response.write(json.dumps(quests))



class QuestDeleteHandle(webapp2.RequestHandler):
    def get(self):
        questId = int(self.request.get('id'))
        quest = ndb.Key(Quest, questId).get()
        quest.delete()