#!/usr/bin/env python
# -*- coding: utf-8 -*-
# /* python | Hot it | Modelos | 071113 */
import datetime
from google.appengine.ext import db

def gql_json_parser(query_obj):
	result = []
	for entry in query_obj:
		result.append(dict([(p, unescape(unicode(getattr(entry, p)))) for p in entry.properties()]))
	return result

# historial de busquedas del tag
class Ultimas(db.Model):
	tag = db.StringProperty()
	id_inicio = db.IntegerProperty()
	id_final = db.IntegerProperty()
	fecha = db.DateTimeProperty()
	
	def __unicode__(self):
		return (u"%s %s - $s" % self.tag)
	
	def get_inicio(self):
		return self.id_inicio - 3000000
	def get_final(self):
		return self.id_final

# información del usuario
class Usuario(db.Model):
	id = db.IntegerProperty()
	user = db.StringProperty()

	def __unicode__(self):
		return (u"%s" % self.user)


# Información de los tags que se han buscado
class Tags(db.Model):
	tag = db.StringProperty()
	fecha = db.StringProperty()
	geo = db.GeoPtProperty()
	id_twitter = db.IntegerProperty()

	def __unicode__(self):
		return (u"%s" % self.tag)

	def tojson(self):
		r_ = '{';
		r_ += '"tag": "' + self.tag + '", "fecha": "'+str(self.fecha)+'", "lon": "' + str(self.geo.lon) + '", "lat": "'+ str(self.geo.lat) + '"'
		r_ += '}'
		return r_
