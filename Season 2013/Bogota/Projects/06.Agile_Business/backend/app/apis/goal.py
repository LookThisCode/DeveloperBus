from google.appengine.ext import endpoints
from protorpc import remote
from app.models.goal import GoalModel

@endpoints.api(name='goal', version='v1', description = 'API de Obejtivos')
class GoalApi(remote.Service):

    @GoalModel.method(request_fields=('title', ' goalDescription', 'state', 'user', 'completed'),
                      response_fields=('title', 'state', 'id'),
                      path='goal',
                      http_method='POST',
                      name='insert')
    def GoalInsert(self, goal):
        goal.completed = False
        goal.put()
        return goal

    @GoalModel.method(request_fields=('id',),
                      path='goal/{id}',
                      http_method='DELETE',
                      name='delete')
    def GoalDelete(self, goal):
        goal.key.delete()
        return goal

    @GoalModel.method(request_fields=('id', 'state'),
                      path='goal/{id}',
                      http_method='PUT',
                      name='update.state')
    def GoalDelete(self, goal):
        goal.state = state
        return goal

    @GoalModel.query_method(query_fields=('limit', 'order', 'pageToken'),
                            collection_fields=('title','state', 'completed', 'id'),
                            path='goal',
                            name='list')
    def GoalsList(self, query):
        return query