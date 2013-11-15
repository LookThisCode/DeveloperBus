import webapp2
import json

from google.appengine.ext import db
from models.models import *

class NeedHandler(webapp2.RequestHandler):
    def get(self, id=None):
        res = {}

        if ( id != None ):
            need = Need.get_by_id(int(id))
            res = need.to_json()
        else:
            needs = Need.all()
            res = {
            'items': [need.to_json() for need in needs]
            }

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(res)


    def post(self):
        need = Need()
        need.user = UserMP.get_by_id(6614661952700416)
        need.service = Service.get_by_id(int(self.request.POST.get('service')))
        need.delivery_time = self.request.POST.get('delivery_time', None)
        need.budget = self.request.POST.get('budget', None)
        need.life = int(self.request.POST.get('life', 5))
        need.local_ubication = (self.request.POST.get('local_ubication') == 'true')
        need.ubication = self.request.POST.get('ubication', None)
        need.put()

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(json.dumps({'saved':True}))


class ServiceHandler(webapp2.RequestHandler):
    def get(self, id=None):
        res = {}

        if ( id != None ):
            service = Service.get_by_id(int(id))
            res = service.to_json()
        else:
            services = Service.all()
            res = {
            'items': [service.to_json() for service in services]
            }

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(res)

    def post(self):
        service = Service()
        service.name = self.request.POST.get('name', None)
        service.description = self.request.POST.get('description', None)
        service.put()

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(service.to_json())


class UserMPHandler(webapp2.RequestHandler):
    def get(self, id, data=None):
        res = {}
        if data == 'needs':
            user = UserMP.get_by_id(int(id))
            res = {
            'user': id,
            'needs': [need.to_json() for need in user.needs]
            }
            
        else:
            res = UserMP.get_by_id(int(id)).to_json()

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(res)