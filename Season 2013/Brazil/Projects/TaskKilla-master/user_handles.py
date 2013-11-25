import webapp2
import sys
from google.appengine.api import oauth
from model import User



def testAPI(argv):
  # Authenticate and construct service.
  service, flags = sample_tools.init(
      argv, 'plus', 'v1', __doc__, __file__,
      scope='https://www.googleapis.com/auth/plus.me')

  try:
    person = service.people().get(userId='me').execute()

    print 'Got your ID: %s' % person['displayName']
    print
    print '%-040s -> %s' % ('[Activitity ID]', '[Content]')

    # Don't execute the request until we reach the paging loop below.
    request = service.activities().list(
        userId=person['id'], collection='public')

    # Loop over every activity and print the ID and a short snippet of content.
    while request is not None:
      activities_doc = request.execute()
      for item in activities_doc.get('items', []):
        print '%-040s -> %s' % (item['id'], item['object']['content'][:30])

      request = service.activities().list_next(request, activities_doc)

  except client.AccessTokenRefreshError:
    print ('The credentials have been revoked or expired, please re-run'
      'the application to re-authorize.')



def getUser():
    userGoogle = ""
    try:
        userGoogle = oauth.get_current_user()


        return userGoogle.nickname()
    except oauth.OAuthRequestError, e:
        print "User not logged"



class UserListHandler(webapp2.RequestHandler):
    def get(self):
        users = User.query().fetch()
        users = [p.to_dct() for p in users]
        self.response.write(json.dumps(users))