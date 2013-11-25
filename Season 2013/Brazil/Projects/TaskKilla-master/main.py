import webapp2
from google.appengine.api import oauth
from configuracao_handle import CreateEntitiesHandler
from model import User
from project_handles import ProjectCreateHandle, ProjectDeleteHandle, ProjectListHandler
from quest_handles import QuestCreateHandle, QuestListHandle, QuestDeleteHandle
from task_handles import TaskCreateHandle, TaskListHandle, TaskDeleteHandle
from user_handles import UserListHandler, getUser




class MainHandler(webapp2.RequestHandler):
    def get(self):
        #self.response.write(getUser())
        return webapp2.redirect('/landing_page/index.html')



class SignInHandler(webapp2.RequestHandler):
    def get(self):
        return webapp2.redirect('/app/signin.html')


app = webapp2.WSGIApplication([
        ('/', MainHandler),
        ('/init', CreateEntitiesHandler),
        ('/project/list', ProjectListHandler),
        ('/project/create', ProjectCreateHandle),
        ('/project/delete', ProjectDeleteHandle),
        ('/quest/create', QuestCreateHandle),
        ('/quest/list', QuestListHandle),
        ('/quest/delete', QuestDeleteHandle),
        ('/task/create', TaskCreateHandle),
        ('/task/list', TaskListHandle),
        ('/task/delete', TaskDeleteHandle),
        ('/user/list', UserListHandler),

    ], debug=True)

