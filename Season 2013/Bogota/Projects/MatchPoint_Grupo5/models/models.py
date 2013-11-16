import json

from google.appengine.ext import db


class Service(db.Model):
	"""
	Servicios que prestan las pymes/personas (UserMP)
	
	"""
	name = db.StringProperty(default=None)
	description = db.StringProperty(default=None)

	def to_dict(self):
		r = dict([(p, unicode(getattr(self, p))) for p in self.properties()])
		r["id"] = str(self.key().id())
		return r

	def to_json(self):
		return json.dumps(self.to_dict())





class UserMP(db.Model):
	"""
	Usuarios del sistema. Pueden ser pymes, o clientes.

	"""
	email = db.EmailProperty(default=None)
	pyme_name = db.StringProperty(default=None)
	pyme_service = db.ReferenceProperty(Service, default=None)
	pyme_ubication = db.StringProperty(default=None)
	pyme_verified = db.BooleanProperty(default=False)

	def to_dict(self):
		r = dict([(p, unicode(getattr(self, p))) for p in self.properties()])
		r["id"] = str(self.key().id())
		if self.pyme_service != None:
			r["pyme_service"] = self.pyme_service.to_dict()
		return r

	def to_json(self):
		return json.dumps(self.to_dict())



class Need(db.Model):
	"""
	Necesidades que los usuarios como clientes publican en la plataforma

	"""
	user = db.ReferenceProperty(UserMP, collection_name="needs", default=None)
	service = db.ReferenceProperty(Service, default=None)

	description = db.StringProperty(default=None)
	delivery_time = db.StringProperty(default=None)
	budget = db.StringProperty(default=None)
	life = db.IntegerProperty(default=None)
	local_ubication = db.BooleanProperty(default=False)
	ubication = db.StringProperty(default=None)

	publish = db.DateTimeProperty(auto_now_add=True)

	def to_dict(self):
		r = dict([(p, unicode(getattr(self, p))) for p in self.properties()])
		r["id"] = str(self.key().id())
		r["user"] = self.user.to_dict()
		r["service"] = self.service.to_dict()
		return r

	def to_json(self):
		return json.dumps(self.to_dict())



class Offer(db.Model):
	"""
	Las ofertas que otras pymes responden a una necesidad de un cliente
	"""
	need = db.ReferenceProperty(Need, collection_name="offers")
	user = db.ReferenceProperty(UserMP, collection_name="offers")

	delivery_time = db.IntegerProperty(default=None)
	cost = db.IntegerProperty(default=None)
	comment = db.StringProperty(default=None)

	publish = db.DateTimeProperty(auto_now_add=True)

	def to_dict(self):
		r = dict([(p, unicode(getattr(self, p))) for p in self.properties()])
		r["id"] = str(self.key().id())
		r["user"] = self.user.to_dict()
		r["need"] = self.need.to_dict()
		return r

	def to_json(self):
		return json.dumps(self.to_dict())



