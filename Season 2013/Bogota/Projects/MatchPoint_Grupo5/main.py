#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
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
import endpoints
import webapp2
import os
import urllib
import jinja2

from google.appengine.ext import db
from protorpc import messages
from protorpc import message_types
from protorpc import remote
from models.models import *
from handlers.handlers import *

JINJA_ENVIRONMENT = jinja2.Environment(
    loader=jinja2.FileSystemLoader(os.path.dirname(__file__)),
    extensions=['jinja2.ext.autoescape'],
    autoescape=True)

#package = 'mp'



# class Greeting(messages.Message):
#    """Greeting that stores a message."""
#    message = messages.StringField(1)


#class GreetingCollection(messages.Message):
#    """Collection of Greetings."""
#    items = messages.MessageField(Greeting, 1, repeated=True)


#STORED_GREETINGS = GreetingCollection(items=[
#    Greeting(message='hello world!'),
#    Greeting(message='goodbye world!'),
#])


#@endpoints.api(name='matchpoint', version='v1')
#class MatchPointApi(remote.Service):
#    """MatchPoint API v1."""
#    @endpoints.method(message_types.VoidMessage, GreetingCollection,
#                      path='hellogreeting', http_method='GET',
#                      name='greetings.listGreeting')
#    def greetings_list(self, unused_request):
#        return STORED_GREETINGS

#    ID_RESOURCE = endpoints.ResourceContainer(
#            message_types.VoidMessage,
#            id=messages.IntegerField(1, variant=messages.Variant.INT32))

#    @endpoints.method(ID_RESOURCE, Greeting,
#                      path='hellogreeting/{id}', http_method='GET',
#                      name='greetings.getGreeting')
#    def greeting_get(self, request):
#        try:
#            return STORED_GREETINGS.items[request.id]
#        except (IndexError, TypeError):
#            raise endpoints.NotFoundException('Greeting %s not found.' %
#                                              (request.id,))


app = webapp2.WSGIApplication([
    #GET Necesidades publicadas
    #POST Nueva necesidad
    webapp2.Route(r'/api/need' , NeedHandler),
    #Una necesidad
    webapp2.Route(r'/api/need/<id:\d+>' , NeedHandler),
    #Ofertas de una necesidad
    webapp2.Route(r'/api/need/<id:\d+>/<data:offers>' , NeedHandler),
    #POST Nueva oferta
    webapp2.Route(r'/api/offer' , OfferHandler),
    #Una oferta
    webapp2.Route(r'/api/offer/<id:\d+>' , OfferHandler),
    #Un usuario
    webapp2.Route(r'/api/user/<id:\d+>' , UserMPHandler),
    #Necesidades que ha publicado un usuario
    webapp2.Route(r'/api/user/<id:\d+>/<data:needs>' , UserMPHandler),
    #Ofertas que ha publicado un usuario
    webapp2.Route(r'/api/user/<id:\d+>/<data:offers>' , UserMPHandler),
    #Login - Register
    webapp2.Route(r'/api/user/login' , UserMPLoginHandler),
    #Todos los servicios
    webapp2.Route(r'/api/service' , ServiceHandler),
    #Un servicio
    webapp2.Route(r'/api/service/<id:\d+>' , ServiceHandler),
], debug=True)


class MainHandler(webapp2.RequestHandler):
    def get(self):
      template_values = {
      }
      template = JINJA_ENVIRONMENT.get_template('templates/index.html')
      self.response.write(template.render(template_values))


app_html = webapp2.WSGIApplication([
    ('/', MainHandler)
], debug=True)
