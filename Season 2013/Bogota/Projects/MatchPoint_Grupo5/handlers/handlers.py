import webapp2
import json

from google.appengine.ext import db
from models.models import *

class NeedHandler(webapp2.RequestHandler):
    def get(self, id=None, data=None):
        res = {}

        if id != None:
            if data == "offers":
                need = Need.get_by_id(int(id))
                res = json.dumps({
                "need": id,
                "offers": [offer.to_dict() for offer in need.offers]
                })
            else:
                need = Need.get_by_id(int(id))
                res = need.to_json()
        else:
            needs = Need.all()
            res = json.dumps({
            'items': [need.to_dict() for need in needs]
            })

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(res)


    def post(self):
        need = Need()
        need.user = UserMP.get_by_id(6578378068983808)
        need.description = self.request.POST.get('description', None)
        need.delivery_time = self.request.POST.get('delivery_time', None)
        need.budget = self.request.POST.get('budget', None)
        need.life = int(self.request.POST.get('life', 2))
        need.local_ubication = (self.request.POST.get('local_ubication') == 'true')
        need.ubication = self.request.POST.get('ubication', None)
        need.service = Service.get_by_id(int(self.request.POST.get('service')))
        need.put()

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(json.dumps({'saved':True}))


class OfferHandler(webapp2.RequestHandler):
    def get(self, id):
        offer = Offer.get_by_id(int(id))
        res = offer.to_json()

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(res)


    def post(self):
        offer = Offer()
        offer.user = UserMP.get_by_id(6614661952700416)
        offer.need = Need.get_by_id(int(self.request.POST.get('need')))
        offer.delivery_time = int(self.request.POST.get('delivery_time', 0))
        offer.cost = int(self.request.POST.get('cost', 0))
        offer.comment = self.request.POST.get('comment', None)
        offer.put()

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
            res = json.dumps({
            'items': [service.to_dict() for service in services]
            })

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
            res = json.dumps({
            'user': id,
            'needs': [need.to_dict() for need in user.needs]
            })
        elif data == 'offers':
            user = UserMP.get_by_id(int(id))
            res = json.dumps({
            'user': id,
            'offers': [offer.to_dict() for offer in user.offers]
            })
        else:
            res = UserMP.get_by_id(int(id)).to_json()

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(res)



class UserMPLoginHandler(webapp2.RequestHandler):
    def post(self):
        email = self.request.POST.get("email", "")
        res = {}
        if email != "":
            res["error"] = 0
            u = UserMP.gql('WHERE email = :1', 'crisanto112@gmail.com').get()
            if u:
                res = {
                "action": "exist",
                "user": u.to_dict()
                }
            else:
                u = UserMP()
                u.email = email
                u.put()

                res = {
                "action": "new",
                "user": u.to_dict()
                }
        else:
            res = {"error": "no email"}

        self.response.headers.add_header('content-type', 'application/json', charset='utf-8')
        self.response.out.write(json.dumps(res))