from django.conf.urls.defaults import *
from django.contrib import admin
import dbindexer

handler500 = 'djangotoolbox.errorviews.server_error'

# django admin
admin.autodiscover()

# search for dbindexes.py in all INSTALLED_APPS and load them
dbindexer.autodiscover()

urlpatterns = patterns('',
    url('^_ah/warmup$', 'djangoappengine.views.warmup'),
#    url('^$', 'django.views.generic.simple.direct_to_template', {'template': 'home.html'}, name='home'),
    url('^admin/', include(admin.site.urls)),

    url('^login/$', 'django.contrib.auth.views.login', name='login'),
    url('^logout/$', 'django.contrib.auth.views.logout',{'next_page': '/',}, name='logout'),

    url('^register/$', 'core.views.register', name='register'),
    url('^wishes/new/$', 'core.views.wish', name='wish'),
    url('^wishes/$', 'core.views.listar_desejos', name='listar'),
    url(r'^wishes/show/(?P<wish_id>\d+)/$','core.views.show',name='show'),
    url(r'^wishes/delete/(?P<wish_id>\d+)/$','core.views.remove_wish',name='remove_wish'),
    url('^users/$', 'core.views.register', name='register_index'),
    url('^track/(?P<pk>\d+)/$', 'core.views.track_bid', name='track_bid'),

    url('^system/$', 'core.views.system_home', name='system_home'),
    url('^$', 'core.views.website_home', name='website_home'),

    url(r'^download/(?P<pk>.+)$', 'core.views.download_handler',name='dowload_img'),

#    #Pra fazer o OpenID
#    url(r'^google/login/$', 'django_openid_auth.views.login_begin', name='openid-login'),
#    url(r'^google/login-complete/$', 'django_openid_auth.views.login_complete', name='openid-complete'),

    url(r'^openid/', include('django_openid_auth.urls')),
)
