from django.conf.urls import patterns, include, url

from django.contrib import admin
from routes.views import HomeView

admin.autodiscover()

urlpatterns = patterns('',
    url('^$', HomeView.as_view(), name='home'),
    url(r'^admin/', include(admin.site.urls)),
    url(r'^accounts/', include('accounts.urls', namespace='accounts')),
    url('', include('social.apps.django_app.urls', namespace='social')),
)
