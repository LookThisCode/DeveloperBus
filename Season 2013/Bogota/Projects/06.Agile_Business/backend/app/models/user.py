import logging
from google.appengine.ext import ndb
from endpoints_proto_datastore.ndb.model import EndpointsModel, EndpointsAliasProperty

class UserModel(EndpointsModel):
	email = ndb.StringProperty()
	name = ndb.StringProperty()