import json
import webapp2
from model import Project,Quest,Task
from google.appengine.ext import ndb
from user_handles import getUser

class ProjectCreateHandle(webapp2.RequestHandler):
    def post(self):
        project = Project(
            name=self.request.get('name'),
            description=self.request.get('description'),
            xp=int(self.request.get('xp'))
        )
        project.put()
        project = project.to_dct()
        self.response.write(project['id'])



class ProjectDeleteHandle(webapp2.RequestHandler):
    def get(self):
        id=int(self.request.get('id'))
        project=ndb.Key(Project, id).get()
        project.delete()



class ProjectListHandler(webapp2.RequestHandler):
    def get(self):
        query = Project.query().order(Project.progress)
        projetos = query.fetch()
        projetos = [p.to_dct() for p in projetos]
        self.response.write(json.dumps(projetos))