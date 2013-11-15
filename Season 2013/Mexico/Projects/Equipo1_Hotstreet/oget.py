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
		tag = u"%s" % self.request.get('tag')
		t = Twitter()
		return_ = '{"objetos": []}'
		if t.conect():
			return_ = t.get_tag(tag)
		self.response.out.write(return_)



app = webapp2.WSGIApplication([
	('/getinfo', MainHandler)
], debug=True)
