from google.appengine.ext import endpoints
from protorpc import remote
from app.models.user import UserModel

@endpoints.api(name='user', version='v1', description='API de User')
class UserApi(remote.Service):

    @UserModel.method(request_fields=('email', 'name'),
                      response_fields=('email', 'name', 'id'),
                      path='user',
                      http_method='POST',
                      name='insert')
    def UserInsert(self, user):
        user.completed = False
        user.put()
        return user

    @UserModel.method(request_fields=('id',),
                      path='user/{id}',
                      http_method='DELETE',
                      name='delete')
    def UserDelete(self, user):
        user.key.delete()
        return user

    @UserModel.query_method(query_fields=('limit', 'order', 'pageToken'),
                            collection_fields=('email','name', 'id'),
                            path='user',
                            name='list')
    def UsersList(self, query):
        return query