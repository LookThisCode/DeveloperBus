
import os
import sys

sys.path.insert(1, os.path.join(os.path.abspath('.'), 'lib'))

import webapp2

from google.appengine.ext import endpoints

from app.views.goal import GoalPage
from app.apis.goal import GoalApi

api = endpoints.api_server([GoalApi], restricted = False)
app = webapp2.WSGIApplication([('/', GoalPage)], debug = True)