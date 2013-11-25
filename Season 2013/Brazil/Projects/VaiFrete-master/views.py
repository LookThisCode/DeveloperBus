#!/usr/bin/env python
#
# Copyright 2008 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

import datetime
import logging
import urllib2

from google.appengine.ext import ndb
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.http import HttpResponseBadRequest
from django.shortcuts import render_to_response

from django import http
from django import shortcuts

from google.appengine.api import users
import json

from models import *
from forms import *

def respond(request, user, template, params=None):
  """Helper to render a response, passing standard stuff to the response.

  Args:
    request: The request object.
    user: The User object representing the current user; or None if nobody
      is logged in.
    template: The template name; '.html' is appended automatically.
    params: A dict giving the template parameters; modified in-place.

  Returns:
    Whatever render_to_response(template, params) returns.

  Raises:
    Whatever render_to_response(template, params) raises.
  """
  if params is None:
    params = {}
  if user:
    params['userLogged'] = user
    params['sign_out'] = users.CreateLogoutURL('/')
    params['is_admin'] = users.IsCurrentUserAdmin() #and
                          #'Dev' in os.getenv('SERVER_SOFTWARE'))
  else:
    params['sign_in'] = users.CreateLoginURL(request.path)
  
  if not template.endswith('.html'):
    template += '.html'
  
  return shortcuts.render_to_response(template, params)

def index(request):
  if users.GetCurrentUser() is None:
    return render_to_response('index.html')
  else:
    return HttpResponseRedirect('/app/welcome') 

def map(request):
  if users.GetCurrentUser() is not None:
    return render_to_response('map.html')
  else:
    return HttpResponseRedirect('/app/welcome')  

def login(request):
  return HttpResponseRedirect(users.create_login_url('/app/welcome'))  

def logout(request):
  return HttpResponseRedirect(users.create_logout_url('/'))

def call_geocoder(address):
  maps_url = 'http://maps.googleapis.com/maps/api/geocode/json?address=%s&sensor=false' % address.replace(' ', '+')
  logging.debug ('Calling maps: %s' % maps_url)
  response = urllib2.urlopen(maps_url)
  return response.read()

def createDeliverFee(request):
  user = users.GetCurrentUser()
  if request.method == 'POST':
    logging.debug('saving the deliver fee...')
    form = DeliverFeeForm(data=request.POST)
    errors = form.errors
    if not errors:
      try:
        deliver_fee = form.save(commit=False)

        #TODO: Add it assyncrhonous...
        #adding geo location
        source_location = json.loads(call_geocoder(deliver_fee.source_address))
        deliver_fee.source_lat = extractFromJson(source_location, 'lat')
        deliver_fee.source_lng = extractFromJson(source_location, 'lng')

        dest_location = json.loads(call_geocoder(deliver_fee.destination_address))
        deliver_fee.dest_lat = extractFromJson(dest_location, 'lat')
        deliver_fee.dest_lng = extractFromJson(dest_location, 'lng')

        deliver_fee.request_date_time = datetime.datetime.today()
        deliver_fee.request_user = user
        deliver_fee.closed = False
        deliver_fee.put()

        return http.HttpResponseRedirect('/app/welcome')        
      except ValueError, err:
        logging.error (err)
        errors['__all__'] = unicode(err)

    if errors:
      return respond(request, user, 'deliverfee', {'form' : form } )

  else:
    form = DeliverFeeForm()
    return respond(request, user, 'deliverfee', {'form' : form } )    

def extractFromJson(parsed_map, param):
  try:
    return str(parsed_map.get('results')[0].get('geometry').get('location').get(param))
  except TypeError, err:
    logging.error(err)
    return '0.0';

def editUser(request):
  """Create or edit a User.  GET shows a pre filled form, POST processes it."""
  app_user = get_app_user()
  user = users.GetCurrentUser()

  #saves the user
  if request.method == 'POST':
    
    logging.debug('saving the user...')
    #app_user = app_users[0] if len(app_users) > 0 else None
    form = AppUserForm(data=request.POST, instance=app_user)
    
    errors = form.errors
    if not errors:
      try:
        obj_save = form.save(commit=True)
        obj_save.user_email = user.email()
        obj_save.put()
    
        return http.HttpResponseRedirect('/app/welcome')        
      except ValueError, err:
        logging.error (err)
        errors['__all__'] = unicode(err)

    if errors:
      return respond(request, user, 'signup', {'form' : form } )    

  if app_user is None:
    app_user = AppUser()
    app_user.real_user = user
  
  form = AppUserForm(data=None, instance=app_user)
  logging.debug ('Return the user..')
  return respond(request, user, 'signup', {'form' : form } )

def listDeliveries(request):
  user = users.GetCurrentUser()

  q =  db.GqlQuery("SELECT * "
                          "FROM DeliverFee "
                          "WHERE request_user = :1 ",
                          user )

  deliveries = q.fetch(100)

  for deliver in deliveries:
    logging.debug('Deliver state is %s' % deliver.state)
    #we get a count of requests that have been made to this guy...
    if deliver.state == 'Pendente':
      innerQuery = create_offer_query(deliver)
      c = innerQuery.count()
      deliver.offers = c
      logging.debug('Number of offers: %r' % c)


  return respond(request,user, 'listdeliveries', { 'deliveries' : deliveries })

def listOffers(request):
  deliver_id = int(request.GET.get('deliverId', 0))
  deliver = DeliverFee.get(db.Key.from_path(DeliverFee.kind(), deliver_id))
  
  if deliver is None:
    return http.HttpResponseBadRequest('No Deliver exists with that key (%r)' %
                                       deliver_id)    
  

  offers = create_offer_query(deliver).fetch(100)

  return respond(request,users.GetCurrentUser(), 'listoffers', { 'offers' : offers })  

def deliverInfo(request):
  deliver_id = int(request.GET.get('deliverId', 0))
  deliver = DeliverFee.get(db.Key.from_path(DeliverFee.kind(), deliver_id))
  
  if deliver is None:
    return http.HttpResponseBadRequest('No Deliver exists with that key (%r)' %
                                       deliver_id)

  #ok, here we need to find the DeliveryOffer that is currently in progress, after the confirmation
  q = db.GqlQuery("SELECT * FROM DeliverOffer WHERE deliver_fee = :1 AND state = 'Aceito' ", deliver)
  offer = q.get()

  return respond(request, users.GetCurrentUser(), 'delivery', { 'delivery' : offer })

def deliverCoordinates(request):
  deliver_id = int(request.GET.get('deliverId', 0))
  deliver = DeliverFee.get(db.Key.from_path(DeliverFee.kind(), deliver_id))
  
  if deliver is None:
    return http.HttpResponseBadRequest('No Deliver exists with that key (%r)' %
                                       deliver_id)

  #ok, here we need to find the DeliveryOffer that is currently in progress, after the confirmation
  q = db.GqlQuery("SELECT * FROM DeliverOffer WHERE deliver_fee = :1 AND state = 'Aceito' ", deliver)
  offer = q.get()

  response_data = {}
  response_data['src_lat'] = float(deliver.source_lat)
  response_data['src_lgn'] = float(deliver.source_lng)
  response_data['des_lat'] = float(deliver.dest_lat)
  response_data['dest_lgn'] = float(deliver.dest_lng)
  response_data['drv_lat'] = float(offer.app_user.last_lat)
  response_data['drv_lgn'] = float(offer.app_user.last_lng)

  return HttpResponse(json.dumps(response_data), content_type="application/json")

def create_offer_query(deliver):
  return db.GqlQuery("SELECT * "
                                    " FROM DeliverOffer "
                                    " WHERE deliver_fee = :1 ",
                                    deliver )
def confirmOffer(request):
  if request.method != 'POST':
    return HttpResponseBadRequest('Only post is allowed')

  offer_id = int(request.POST.get('offerId', 0))
  offer = DeliverOffer.get(db.Key.from_path(DeliverOffer.kind(), offer_id))

  logging.debug('Confirming %r' % offer_id)

  if offer is None:
    return HttpResponseBadRequest('This offer does not exists: %r' % offer_id)

  #running operations in a transaction
  apply_confirm(offer.key())
  
  return HttpResponse("Success")

#@db.transactional(xg=True)
def apply_confirm(key):
  offer = db.get(key)
  offer.state = 'Aceito'
  
  #these are the other offers, so we want to set them as Rejected
  otherOffers = db.GqlQuery("SELECT * FROM DeliverOffer WHERE deliver_fee = :1", offer.deliver_fee).fetch(100)

  logging.debug("Other offers are: %s" % str(otherOffers) )

  for obj in otherOffers:
    if obj.key() is not key:
      obj.state = 'Rejeitado'
      obj.put()

  offer.deliver_fee.state='Em andamento'
  offer.deliver_fee.put()

  offer.put()

def welcome(request):
  app_user = get_app_user()
  user = users.GetCurrentUser()

  if app_user is None:
    return http.HttpResponseRedirect('/app/editUser')
  else:
    return respond(request, user, 'welcome')

def get_app_user():
  user = users.GetCurrentUser()
  if user is None:
    return http.HttpResponseForbidden('You must be signed in to add or edit a camera')

  q =  db.GqlQuery("SELECT * "
                          "FROM AppUser "
                          "WHERE real_user = :1 ",
                          user )

  return q.get()