from google.appengine.ext import endpoints
from protorpc import remote
from agilebusiness_app.models import GoalModel


@endpoints.api(name='goal', version='v1', description='API de Obejtivos')
class GoalApi(remote.Service):

    @GoalModel.method(request_fields=('title',),
                      response_fields=('completed','title','id'),
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

    @GoalModel.method(request_fields=('id',),
                      response_fields=('completed','title','id'),
                      path='goal/{id}',
                      name='toggle')
    def GoalToggle(self, goal):
        goal.completed = not goal.completed
        goal.put()
        return goal

    @GoalModel.query_method(query_fields=('limit', 'order', 'pageToken'),
                            collection_fields=('completed','title','id'),
                            path='goals',
                            name='goals.list')
    def GoalsList(self, query):
        return query