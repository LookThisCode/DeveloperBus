import logging
from google.appengine.ext import ndb
from endpoints_proto_datastore.ndb.model import EndpointsModel, EndpointsAliasProperty
from app.models.user import UserModel

class GoalModel(EndpointsModel):
	title = ndb.StringProperty()
	goalDescription = ndb.StringProperty()
	state = ndb.IntegerProperty()
	user = ndb.KeyProperty(UserModel)
	completed = ndb.BooleanProperty()