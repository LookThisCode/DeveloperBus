from google.appengine.ext import endpoints
from protorpc import remote
from app.models.project import ProjectModel

@endpoints.api(name='project', version='v1', description='API de Project')
class ProjectApi(remote.Service):

    @ProjectModel.method(request_fields=('name'),
                      response_fields=('name', 'id'),
                      path='project',
                      http_method='POST',
                      name='insert')
    def ProjectInsert(self, project):
        project.completed = False
        project.put()
        return project

    @ProjectModel.method(request_fields=('id',),
                      path='project/{id}',
                      http_method='DELETE',
                      name='delete')
    def ProjectDelete(self, project):
        project.key.delete()
        return project

    @ProjectModel.query_method(query_fields=('limit', 'order', 'pageToken'),
                            collection_fields=('name', 'id'),
                            path='project',
                            name='list')
    def ProjectsList(self, query):
        return query