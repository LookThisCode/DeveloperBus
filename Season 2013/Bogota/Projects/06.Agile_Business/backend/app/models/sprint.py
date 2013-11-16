import logging
from google.appengine.ext import ndb
from endpoints_proto_datastore.ndb.model import EndpointsModel, EndpointsAliasProperty
from app.models.project import ProjectModel
from app.models.goal import ProjectGoal

class SprintModel(EndpointsModel):
	project = ndb.KeyProperty(ProjectModel)
	startDate = ndb.DateProperty()
	endDate = ndb.DateProperty()
	goalList = ndb.KeyProperty(ProjectGoal, repeat = true)