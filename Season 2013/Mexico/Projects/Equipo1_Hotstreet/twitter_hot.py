#!/usr/bin/env python
# -*- coding: utf-8 -*-
# /* python | Hot it | twitter | 071113 */


import json, logging, os, random ## core
import httplib2, tweepy ## libs

from google.appengine.ext import db
from models import *
from datetime import datetime

class Twitter():
	# Clase principal de twitter
	api = None
	def conect(self):
		logger = logging.getLogger('twitter_hot')
		logger.setLevel(logging.DEBUG)
		### Modificar el archivo tw_credentials.json para poder acceder al api de twitter
		### https://dev.twitter.com/
		creds = json.loads(open('tw_credentials.json').read())
		#
		# Twitter OAuth credenciales
		#
		tw_consumer_key = creds['tw_consumer_key']
		tw_consumer_secret = creds['tw_consumer_secret']
		tw_access_token = creds['tw_access_token']
		tw_access_token_secret = creds['tw_access_token_secret']
		
		try:
			# Auth Twitter
			auth = tweepy.OAuthHandler(tw_consumer_key, tw_consumer_secret)
			auth.set_access_token(tw_access_token, tw_access_token_secret)
			self.api = tweepy.API(auth_handler=auth, api_root='/1.1')
			
			logger.debug(">> twitter/tweepy ")
			return True
		except Exception:
			self.api = None
			logger.debug(">> No se conecto")
			return False
	
	def get_tag(self, tag):
		###results = self.api.search(q=tag, geocode="19.307255233641808,-99.140625,30km", result_type="mixed"
		init, id_init, id_final = 0, 0, 0
		objetos = db.GqlQuery('select * from Tags where tag = :tag ', tag=tag)
		txt = ''
		if objetos.count() <= 0:
			results = self.api.search(q=tag, result_type="mixed")
			for result in results:
				if hasattr(result, 'location'):
					otag = Tags()
					otag.tag = tag
					otag.fecha = ''
					otag.geo = db.GeoPt(result.location.lon, result.location.lat)
					otag.id_twitter = result.id
					otag.put()
			ultimo = Ultimas()
			ultimo.tag = tag
			ultimo.id_inicio = id_final
			ultimo.id_final = id_init
			ultimo.fecha = datetime.today()
			ultimo.put()
			objetos = db.GqlQuery('select * from Tags where tag = :tag ', tag=tag)
		json, aux = '{"objetos": [', ''
		for o in objetos:
			json += aux + o.tojson()
			aux = ','
		json += ']}'
		return json



