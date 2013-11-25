from django.contrib import admin
from models import Categorie, Wishlist, Company, Bid

class CategorieAdmin(admin.ModelAdmin):
    model = Categorie
    list_display = ['category','qtd']

class WishlistAdmin(admin.ModelAdmin):
    model = Wishlist

class BidAdmin(admin.ModelAdmin):
    model = Bid
    list_display = ['company','product','price','link','clicks']

admin.site.register(Categorie, CategorieAdmin)
admin.site.register(Wishlist, WishlistAdmin)
admin.site.register(Company)
admin.site.register(Bid,BidAdmin)