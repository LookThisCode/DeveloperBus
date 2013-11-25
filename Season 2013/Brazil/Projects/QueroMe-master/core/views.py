# coding: utf-8
from django.http import HttpResponseRedirect
from django.shortcuts import render
from forms import FormUserRegistration, WishlistForm
from django.contrib.auth.decorators import login_required
from filetransfers.api import prepare_upload, serve_file
from django.core.urlresolvers import reverse
from django.contrib.auth import authenticate, login as authlogin
from models import Wishlist, Bid, Track
from django.shortcuts import get_object_or_404
from search.core import search


def register(request):
    controller = "users"
    method = "index"
    if request.method == 'POST':
        tipo_form = request.POST['tipo_form']
        if tipo_form == 'register':
            form = FormUserRegistration(request.POST)
            if form.is_valid():
                new_user = form.save()
                new_user.backend = 'django.contrib.auth.backends.ModelBackend'
                authlogin(request, new_user)
                return HttpResponseRedirect(reverse('core.views.listar_desejos'))
            else:
                return render(request, "system/users/index.html",
                    locals()
                )
        else:
            username = request.POST['username_login']
            password = request.POST['password_login']
            user = authenticate(username=username, password=password)
            if user is not None:
                if user.is_active:
                    user.backend = 'django.contrib.auth.backends.ModelBackend'
                    authlogin(request, user)
                    return HttpResponseRedirect(reverse('core.views.listar_desejos'))

    else:
        form = FormUserRegistration()
    return render(request, "system/users/index.html",
        locals()
    )

@login_required
def wish(request):
    controller = "wishes"
    method = "new"
    view_url = reverse('core.views.wish')
    if request.method == 'POST':
        form = WishlistForm(request.POST, request.FILES)
        if form.is_valid():
            new_wish = form.save()
            new_wish.user = request.user
            new_wish.save()
            return HttpResponseRedirect(reverse('core.views.listar_desejos'))
        return HttpResponseRedirect(view_url)

    upload_url, upload_data = prepare_upload(request, view_url)
    form = WishlistForm()
    uploads = Wishlist.objects.all()

    return render(request, "system/wishes/new.html",
        locals(),
    )

def system_home(request):
    controller = "system_home"
    method = "see"
    return render(request, "system/index.html",
        locals()
    )

def website_home(request):
    if request.user.is_authenticated():
        return HttpResponseRedirect(reverse('core.views.listar_desejos'))

    controller = "website_home"
    method = "see"
    return render(request, "website/index.html",
        locals()
    )

@login_required
def listar_desejos(request):
    controller = "wishes"
    method = "index"
    return render(request, "system/wishes/index.html",
        locals()
    )

@login_required
def show(request, wish_id):
    controller = "wishes"
    method = "show"
    w = get_object_or_404(Wishlist,id=wish_id, user = request.user)
    results = search(Bid, w.product)
    return render(request, "system/wishes/show.html",
        locals()
    )

def download_handler(request, pk):
    upload = get_object_or_404(Wishlist, pk=pk)
    return serve_file(request, upload.file, save_as=True)

def track_bid(request, pk):
    bid = get_object_or_404(Bid, pk=pk)
    t = Track()
    t.bid = bid
    if request.user:
        t.user = request.user
    t.save()
    return HttpResponseRedirect(bid.link)

@login_required
def remove_wish(request, wish_id):
    w = get_object_or_404(Wishlist,id=wish_id, user = request.user)
    w.delete()
    return HttpResponseRedirect(reverse('core.views.listar_desejos'))