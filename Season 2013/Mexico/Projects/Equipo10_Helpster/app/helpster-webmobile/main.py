import os
import cgi
import urllib

from google.appengine.api import users

import jinja2
import webapp2

JINJA_ENVIRONMENT = jinja2.Environment(
    loader=jinja2.FileSystemLoader(os.path.dirname(__file__)),
    extensions=['jinja2.ext.autoescape'],
    autoescape=True)

class MainPage(webapp2.RequestHandler):

	def get(self):
		user = users.get_current_user()
		if user:
			template_values = {
				'usuario': user.nickname(),
			}
			template = JINJA_ENVIRONMENT.get_template('index.html')
			self.response.write(template.render(template_values))			
		else:
			self.redirect(users.create_login_url(self.request.uri))

class LoginPage(webapp2.RequestHandler):

	def get(self):
		user = users.get_current_user()
		if user:
			template_values = {
				'usuario': user.nickname(),
			}
			template = JINJA_ENVIRONMENT.get_template('login.html')
			self.response.write(template.render(template_values))			
		else:
			self.redirect(users.create_login_url(self.request.uri))			

application = webapp2.WSGIApplication([
	('/', MainPage),
	('/login', LoginPage),
	], debug=True)
