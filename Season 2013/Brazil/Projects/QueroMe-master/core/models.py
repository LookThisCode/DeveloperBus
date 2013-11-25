# coding: utf-8
from django.db import models
from django.contrib.auth.models import User
from random import sample
from string import ascii_letters, join
from django.core.urlresolvers import reverse
from django.core.mail import send_mail
from djangotoolbox.fields import BlobField
from search.core import search

class UserProfile(models.Model):
    user = models.ForeignKey(User)
    verifying_code = models.CharField(max_length=40,unique=True,blank=True,null=True)
    def create_verifying_code(self):
        #Unique Verifying code
        flag = True
        while flag:
            verifying_code = join(sample(ascii_letters,40),"")
            users = User.objects.filter(verifying_code = verifying_code)
            if not users:
                self.verifying_code = verifying_code
                self.save()
                flag = False
    def enviar_email_ativacao(self):
        link = reverse('ativacao-cadastro',
            kwargs={
                'id':self.id,
                'verifying_code':self.verifying_code,
                }
        )
        mensagem_txt = u"""Bem vindo(a) Usuário %s %s,

                        Parabéns! Você acaba de se tornar nosso usuário !
                        É muito importante sua opinião para melhoria desta plataforma.
                        Seu cadastro foi efetuado com sucesso e você está a um passo de poder receber excelentes ofertas e preços
                        Por favor, ative sua conta. Para isso, clique no link abaixo para ativar o seu cadastro:

                        %s

                        Obrigado, e aproveite!
                        Equipe quero.me """
        mensagem_txt = mensagem_txt % (self.user.first_name,self.user.last_name,link)
        send_mail('[Quero.me]  Obrigado por se cadastrar',mensagem_txt,'contato@quero.me',[self.user.email])
    def save(self, *args, **kwargs):
        if not self.id and not self.user.is_active:
            #Se é a primeira vez é preciso gerar o código de verificação e enviar o e-mail de ativação
            super(UserProfile,self).save(*args,**kwargs)
            self.create_verifying_code()
            self.enviar_email_ativacao()
        super(UserProfile,self).save(*args,**kwargs)

class Categorie(models.Model):
    category = models.CharField(max_length=200)
    def qtd(self):
        return self.wishlist_set.count()
    def __unicode__(self):
        return unicode(self.category)

class Wishlist(models.Model):
    user = models.ForeignKey(User, blank=True, null=True)
    category = models.ForeignKey(Categorie, blank=True, null=True,verbose_name='Categoria')
    product = models.CharField(max_length=200,verbose_name='Produto')
    description = models.TextField(blank=True, null=True, verbose_name=u'Descrição')
    image = BlobField(verbose_name='Imagem',blank=True, null = True)
    file = models.FileField(upload_to='uploads/%Y/%m/%d/%H/%M/%S/',blank=True, null = True)

    def count_bids(self):
        if self.product:
            results = search(Bid, self.product)
            return results.count()

    def __unicode__(self):
        return unicode(self.product)

    @property
    def filename(self):
        return self.file.name.rsplit('/', 1)[-1]

class Company(models.Model):
    name = models.CharField(max_length=200)

    def __unicode__(self):
        return unicode(self.name)

class Bid(models.Model):
    company = models.ForeignKey(Company)
    product = models.CharField(max_length=200,verbose_name='Produto')
    price = models.DecimalField(max_digits=20,decimal_places=2)
    link = models.CharField(max_length=200,verbose_name='link da compra')

    def clicks(self):
        return self.track_set.count()

    def __unicode__(self):
        return unicode(self.product)

class Track(models.Model):
    user = models.ForeignKey(User, blank=True, null=True)
    bid = models.ForeignKey(Bid)

    def __unicode__(self):
        return unicode(self.bid)