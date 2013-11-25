import webapp2
from model import Project,Quest,Task
from google.appengine.ext import ndb

class CreateEntitiesHandler(webapp2.RequestHandler):
    def get(self):
        ndb.delete_multi(
            Project.query().fetch(keys_only=True)
        )
        ndb.delete_multi(
            Quest.query().fetch(keys_only=True)
        )
        ndb.delete_multi(
            Task.query().fetch(keys_only=True)
        )

        project = Project(name="project 01", progress=0,
                          description="descricao", xp=100)
        project.put()

        quest = Quest(name="quest 01", description="description",
                      xp=30, deadline="2013-11-21",
                      progress=0, projectKey=project.key)
        quest.put()

        task = Task(name="task 01", done="false",
                    xp=15, progress=50,
                    questKey=quest.key)
        task.put()


        task = Task(name="task 02", done="false",
                    xp=15, progress=50,
                    questKey=quest.key)
        task.put()



        quest = Quest(name="quest 02", description="description",
                      xp=30, deadline="2013-12-10",
                      progress=0, projectKey=project.key)
        quest.put()
        task = Task(name="task 01", done="false",
                    xp=15, progress=50,
                    questKey=quest.key)
        task.put()
        task = Task(name="task 02", done="false",
                    xp=15, progress=50,
                    questKey=quest.key)
        task.put()



        quest = Quest(name="quest 03", description="description",
                      xp=30, deadline="2014-01-10",
                      progress=0, projectKey=project.key)
        quest.put()
        task = Task(name="task 01", done="false",
                    xp=15, progress=50,
                    questKey=quest.key)
        task.put()
        task = Task(name="task 02", done="false",
                    xp=15, progress=50,
                    questKey=quest.key)
        task.put()



        self.response.write("ok")