#!/usr/bin/env python
# -*- coding: utf-8 -*-
# /* python | Hot it | 071113 */
import os
import webapp2
from google.appengine.ext.webapp import template

class MainHandler(webapp2.RequestHandler):
	def get(self):
		args_ = {}
		url = os.path.join(os.path.dirname(__file__), 'index.html')
		self.response.out.write(template.render(url, args_))

app = webapp2.WSGIApplication([
	('/', MainHandler)
], debug=True)
