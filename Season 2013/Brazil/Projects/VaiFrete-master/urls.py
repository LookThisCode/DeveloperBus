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

from django.conf.urls import *

urlpatterns = patterns('',
    (r'^$', 'views.index'),
    (r'^login$', 'views.login'),
    (r'^app/editUser$', 'views.editUser'),
    (r'^app/createDeliverFee$', 'views.createDeliverFee'),
    (r'^app/welcome$', 'views.welcome'),
    (r'^app/listDeliveries$', 'views.listDeliveries'),
    (r'^app/listOffers', 'views.listOffers'),
    (r'^app/confirmOffer', 'views.confirmOffer'),
    (r'^app/deliverCoordinates', 'views.deliverCoordinates'),
    (r'^app/deliverInfo', 'views.deliverInfo'),
    (r'^app/map', 'views.map'),
    (r'^logout$', 'views.logout')
)