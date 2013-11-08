#!/usr/bin/env python
# -*- coding: utf-8 -*-
# /* python | Hot it | Modelos | 071113 */
import datetime
from google.appengine.ext import db


# información del usuario
class Usuario(db.Model):
	id = db.IntegerProperty()
	user = db.StringProperty()

	def __unicode__(self, text):
		return (u"%s" % tag)


# Información de los tags que se han buscado
class Tags(db.Model):
	tag = db.StringProperty()
	fecha = db.DateTimeProperty()
	geo = db.GeoPtProperty()
	user = Usuario()

	def __unicode__(self, text):
		return (u"%s" % tag)

	def tojson(self):
		return_ = '{'
		return_ += '"id_user":"'+str(self.id_user)+'","tag":"'+ self.tag +'",'
		return_ += '"lat":"'+str(self.geo.lat)+'","lon":"'+str(self.geo.lon)+'",'
		return_ += '"fecha": "'+str(self.fecha)+'"'
		return_ += '}'
		return return_

