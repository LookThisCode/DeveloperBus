import logging
from google.appengine.ext import ndb
from endpoints_proto_datastore.ndb.model import EndpointsModel, EndpointsAliasProperty
from app.models.goal import GoalModel

class TaskModel(EndpointsModel):
	goal = ndb.KeyProperty(GoalModel)
	title = ndb.StringProperty()
	description = ndb.StringProperty()
	state = ndb.IntegerProperty()
	taskType = ndb.IntegerProperty()
	user = ndb.KeyProperty(UserModel)
	completed = ndb.BooleanProperty()