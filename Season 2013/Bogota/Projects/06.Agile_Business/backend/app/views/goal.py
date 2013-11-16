import webapp2

class GoalPage(webapp2.RequestHandler):
    def get(self):
        self.redirect('media/index.html')