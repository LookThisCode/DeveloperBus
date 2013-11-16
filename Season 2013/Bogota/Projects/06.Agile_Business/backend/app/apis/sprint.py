from google.appengine.ext import endpoints
from protorpc import remote
from app.models.sprint import SprintModel

@endpoints.api(name='sprint', version='v1', description='API de Sprint')
class SprintApi(remote.Service):

    @SprintModel.method(request_fields=('startDate', 'endDate'),
                      response_fields=('startDate', 'endDate', 'id'),
                      path='sprint',
                      http_method='POST',
                      name='insert')
    def SprintInsert(self, sprint):
        sprint.completed = False
        sprint.put()
        return sprint

    @SprintModel.method(request_fields=('id',),
                      path='sprint/{id}',
                      http_method='DELETE',
                      name='delete')
    def SprintDelete(self, sprint):
        sprint.key.delete()
        return sprint

    @SprintModel.query_method(query_fields=('limit', 'order', 'pageToken'),
                            collection_fields=('startDate', 'endDate', 'id'),
                            path='sprint',
                            name='list')
    def SprintsList(self, query):
        return query