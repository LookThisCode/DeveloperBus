
import os
import sys

sys.path.insert(1, os.path.join(os.path.abspath('.'), 'lib'))

import webapp2

from google.appengine.ext import endpoints

from agilebusiness_app.views import GoalPage, ProjectPage
from agilebusiness_app.apis import GoalApi

api = endpoints.api_server([GoalApi], restricted = False)
app = webapp2.WSGIApplication([('/objetivo', GoalPage), ("/proyecto", ProjectPage)], debug = True)