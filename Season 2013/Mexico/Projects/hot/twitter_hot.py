#!/usr/bin/env python
# -*- coding: utf-8 -*-
# /* python | Hot it | twitter | 071113 */


import json, logging, os, datetime, random ## core
import httplib2, tweepy ## libs

class Twitter():
	# Clase principal de twitter
	api = None
	def conect(self):
		logger = logging.getLogger('twitter_hot')
		logger.setLevel(logging.DEBUG)
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
		results = self.api.search(q=tag, locale='es')
		txt = '['
		for result in results:
			geo = result.location if hasattr(result, 'location') else ''
			txt += '{"text": "'+result.text+'", "geo": "'+geo+'"}'
		return txt



