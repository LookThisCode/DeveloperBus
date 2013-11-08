import cgi
import os

from google.appengine.ext.webapp import template
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app

		
class MainPage(webapp.RequestHandler):
    def get(self):

        template_values = {
            'titulo': 'Dezka Reid',
            }
        path = os.path.join(os.path.dirname(__file__), 'error.html')
        self.response.out.write(template.render(path,template_values))

application = webapp.WSGIApplication(
                                     [('/.*', MainPage)],
                                     debug=True)

def main():
    run_wsgi_app(application)

if __name__ == "__main__":
    main()