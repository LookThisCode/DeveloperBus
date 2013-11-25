
import json
import webapp2
from model import Project,Quest,Task
from google.appengine.ext import ndb

class TaskListHandle(webapp2.RedirectHandler):
    def get(self):
        tasks = Task.query().fetch()
        tasks = [t.to_dct() for t in tasks]
        self.response.write(json.dumps(tasks))

class TaskCreateHandle(webapp2.RequestHandler):
    def post(self):
        task = Task(name=self.request.get('name'),
                    done=self.request.get('done'),
                    progress=int(self.request.get('progress')),
                    questKey=ndb.Key(Quest, int(self.request.get('questId'))))

        task.put()
        task = task.to_dct()
        self.response.write(task['id'])


class TaskDeleteHandle(webapp2.RequestHandler):
    def get(self):
        taskId = int(self.request.get('id'))
        task = ndb.Key(Task, taskId).get()
        task.key.delete()