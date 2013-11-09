#!/usr/bin/env python
# -*- coding: utf-8 -*-
# /* python | Hot it | 071113 */
import os
import webapp2

from models import *
from google.appengine.ext.webapp import template
from datetime import datetime
from google.appengine.ext import db

class MainHandler(webapp2.RequestHandler):
	def get(self):
		args_ = {}
		url = os.path.join(os.path.dirname(__file__), 'page.html')
		self.response.out.write(template.render(url, args_))

class ConsultaHandler(webapp2.RequestHandler):
	def get(self):
		args_ = {}
		url = os.path.join(os.path.dirname(__file__), 'index.html')
		self.response.out.write(template.render(url, args_))

app = webapp2.WSGIApplication([
	('/', MainHandler),
	('/consulta', ConsultaHandler)
], debug=True)
