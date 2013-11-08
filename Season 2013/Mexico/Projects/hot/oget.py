#!/usr/bin/env python
# -*- coding: utf-8 -*-
# /* python | Hot it | Modelos | 071113 */
import os
import webapp2
from twitter_hot import Twitter

from google.appengine.api import memcache
from google.appengine.ext import db

from models import *

class MainHandler(webapp2.RequestHandler):
	def get(self):
		self.response.out.write('done')

	def post(self):
		tag = self.request.get('tag')
		t = Twitter()
		if t.conect():
			val = True
			self.response.out.write(t.get_tag(tag))
		#return_ = '{"objetos":['
		#objetos = db.GqlQuery('select * from Tags where tag  = :tag ',tag=tag)
		#aux = ''
		#for objeto in objetos:
		#	return_ += aux + objeto.tojson()
		#	aux = ','
		#return_ += ']}'
		#self.response.out.write(return_)
	

app = webapp2.WSGIApplication([
	('/getinfo', MainHandler)
], debug=True)
