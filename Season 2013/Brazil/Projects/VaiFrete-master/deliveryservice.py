import datetime
import time
import logging
import urllib2
from protorpc import remote
from protorpc import messages
from models import *
from google.appengine.ext import db
from protorpc import message_types
from google.appengine.api import users
import json

class Delivery(messages.Message):
    destination_address = messages.StringField(1, required=True)
    dest_lat = messages.StringField(2)
    dest_lng = messages.StringField(3)
    source_address = messages.StringField(4)
    source_lat = messages.StringField(5)
    source_lng =messages.StringField(6)
    request_user = messages.StringField(7)
    deliver_id = messages.IntegerField(8)

class Deliveries(messages.Message):
    deliveries = messages.MessageField(Delivery, 1, repeated=True)

class Offer(messages.Message):
    id = messages.IntegerField(1, required=True)
    state = messages.StringField(2)

class Offers(messages.Message):
    offers = messages.MessageField(Offer, 1, repeated=True)


class GetDeliveriesRequest(messages.Message):
    limit = messages.IntegerField(1, default=10)
    user = messages.StringField(2)

class GetDeliverOfferRequest(messages.Message):
    user_email = messages.StringField(1)
    deliver_key = messages.IntegerField(2)
    offer_info = messages.StringField(3)

class GetPositionUpdateRequest(messages.Message):
    user_email = messages.StringField(1)
    last_lat = messages.StringField(2)
    last_lng = messages.StringField(3)

class GetUpdateUserRequest(messages.Message):
    user_email = messages.StringField(1)
    avg_price = messages.StringField(2)
    vehicle = messages.StringField(3)
    social_id = messages.StringField(4)
    photo = messages.StringField(5)
    name = messages.StringField(6)
    phone = messages.StringField(7)

class DeliveryService(remote.Service):
    @remote.method(GetDeliveriesRequest, Deliveries)
    def get_deliveries(self, request):
        logging.debug('Received request ' + str(request))

        query = DeliverFee.all().order('-request_date_time')
  
        #if request.user:
            #query.filter('date <=', when)
  
        deliveries = []
        for delivery_model in query.fetch(request.limit):
            delivery = Delivery(destination_address=delivery_model.destination_address, dest_lat=delivery_model.dest_lat, dest_lng=delivery_model.dest_lng, source_address=delivery_model.source_address, source_lat=delivery_model.source_lat, source_lng=delivery_model.source_lng, request_user=delivery_model.request_user.email(), deliver_id=delivery_model.key().id())
            deliveries.append(delivery)
        
        return Deliveries(deliveries=deliveries)

    @remote.method(GetDeliverOfferRequest, message_types.VoidMessage)
    def put_offer(self, request):
        logging.debug('Received request ' + str(request))

        deliver = DeliverFee.get(db.Key.from_path(DeliverFee.kind(), request.deliver_key))
        app_user = get_app_user(request.user_email)       

        logging.debug('Deliver: %s, User %s' % (str(deliver), str(app_user)))

        deliver_offer = DeliverOffer(deliver_fee=deliver, app_user=app_user, offer_info=request.offer_info, state='Made')
        deliver_offer.put()

        return message_types.VoidMessage()

    @remote.method(GetPositionUpdateRequest, message_types.VoidMessage)
    def update_position(self, request):
        logging.debug('Received request ' + str(request))

        app_user = get_app_user(request.user_email)
        app_user.last_lng = request.last_lng
        app_user.last_lat = request.last_lat

        #getting the readable address
        app_user.last_position = get_readable_address(request.last_lat, request.last_lng)
        app_user.put()

        return message_types.VoidMessage()

    @remote.method(GetDeliveriesRequest, Offers)
    def get_my_offers(self, request):
        logging.debug('Received request ' + str(request))

        query = DeliverOffer.all()
  
        #if request.user:
        query.filter('user_email =', request.user)
  
        offers = []
        for offer_model in query.fetch(request.limit):
            offer = Offer(state=offer_model.state, id=offer_model.key().id())
            offers.append(offer)
        
        return Offers(offers=offers)

    @remote.method(GetUpdateUserRequest, message_types.VoidMessage)
    def update_user(self, request):
        app_user = get_app_user(request.user_email)
        
        if app_user is None:
            app_user = AppUser()
            app_user.user_email = request.user_email
            logging.debug('Creating user: %s' % request.user_email)
        else: 
            logging.debug('Updating user: %s' % request.user_email)
        app_user.vehicle = request.vehicle
        app_user.user_type = 'Entregador'
        app_user.last_status = 'Aguardando'
        app_user.avg_price = request.avg_price
        app_user.social_id = request.social_id
        app_user.photo = request.photo
        app_user.name = request.name
        app_user.phone = request.phone

        app_user.put()

        return message_types.VoidMessage()

def get_readable_address(lat,lng):
    maps_url = 'http://maps.googleapis.com/maps/api/geocode/json?latlng=%s,%s&sensor=false' % (lat,lng)
    logging.debug ('Calling maps: %s' % maps_url)
    response = urllib2.urlopen(maps_url).read()

    parsed = json.loads(response)

    return parsed.get('results')[0].get('formatted_address')

def get_app_user(email):
    q =  db.GqlQuery("SELECT * "
                          "FROM AppUser "
                          "WHERE user_email = :1 ",
                          email )

    return q.get()