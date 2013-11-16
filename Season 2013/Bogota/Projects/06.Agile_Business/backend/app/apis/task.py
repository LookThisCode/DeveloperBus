from google.appengine.ext import endpoints
from protorpc import remote
from app.models.task import TaskModel

@endpoints.api(name='task', version='v1', description='API de Task')
class TaskApi(remote.Service):

    @TaskModel.method(request_fields=('title', ' email', 'name'),
                      response_fields=('title', 'state', 'id'),
                      path='task',
                      http_method='POST',
                      name='insert')
    def TaskInsert(self, task):
        task.completed = False
        task.put()
        return task

    @TaskModel.method(request_fields=('id',),
                      path='task/{id}',
                      http_method='DELETE',
                      name='delete')
    def TaskDelete(self, task):
        task.key.delete()
        return task

    @TaskModel.query_method(query_fields=('limit', 'order', 'pageToken'),
                            collection_fields=('email','name', 'id'),
                            path='task',
                            name='list')
    def TasksList(self, query):
        return query