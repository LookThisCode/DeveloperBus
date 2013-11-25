# coding: utf-8
from django import forms
from django.contrib.auth.models import User
from models import Wishlist

class FormUserRegistration(forms.ModelForm):
    """
    User Registration
    """

    def __init__(self, *args, **kwargs):
        super(FormUserRegistration, self).__init__(*args, **kwargs)
        if self.errors:
            for f_name in self.fields:
                if f_name in self.errors:
                    classes = self.fields[f_name].widget.attrs.get('class', '')
                    classes += ' error'
                    self.fields[f_name].widget.attrs['class'] = classes

    class Meta:
        model = User
        #fields= ('first_name','last_name','email','email_confirmation',
        #'password','password_confirmation','telefone_1','telefone_2')
        fields= ('first_name','last_name','email','password','password_confirmation')

    first_name = forms.CharField(
        label='Nome',
        widget=forms.TextInput(
            attrs={
                'placeholder':'Digite seu nome',
                'type':'text',
                'required':'true'
            }
        )
    )

    last_name = forms.CharField(
        label='Sobrenome',
        widget=forms.TextInput(
            attrs={
                'placeholder':'Sobrenome',
                'type':'text',
                'required':'true'}
        )
    )

    email = forms.EmailField(
        label='E-mail',
        widget=forms.TextInput(
            attrs={
                'placeholder':'seunome@empresa.com.br',
                'type':'email',
                'required':'true'
            }
        )
    )

#    email_confirmation = forms.EmailField(
#        label='Confirme o seu e-mail',
#        widget=forms.TextInput(
#            attrs={
#                'placeholder':'seunome@empresa.com.br',
#                'type':'email',
#                'required':'true'
#            }
#        )
#    )

    password = forms.CharField(
        label='Senha',
        min_length=4,
        widget=forms.PasswordInput(
            attrs={
                'placeholder':'',
                'type':'password',
                'required':'true'
            }
        )
    )
    password_confirmation = forms.CharField(
        label='Repita a sua senha',
        widget=forms.PasswordInput(
            attrs={
                'placeholder':'',
                'type':'password',
                'required':'true'}
        )
    )

#    telefone_1 = forms.CharField(
#        label='Telefone para contato',
#        required=True,
#        widget=forms.TextInput(
#            attrs={
#                'placeholder':'(DDD)5555-5555',
#                'type':'text',
#                'required':'true'}
#        )
#    )
#
#    telefone_2 = forms.CharField(
#        label='Outro telefone para contato',
#        required=False,
#        widget=forms.TextInput(
#            attrs={'placeholder':'(DDD)95555-5555'}
#        )
#    )

    def clean_password_confirmation(self):
        if self.cleaned_data.has_key('password'):
            if self.cleaned_data['password_confirmation'] != self.cleaned_data['password']:
                raise forms.ValidationError(u'A senha e sua confirmação devem coincidir')
        return self.cleaned_data['password_confirmation']

    def clean_email(self):
        if User.objects.filter(username=self.cleaned_data['email']):
            raise forms.ValidationError(u'Email já cadastrado.')

        return self.cleaned_data['email']

#    def clean_email_confirmation(self):
#        if self.cleaned_data.has_key('email'):
#            if self.cleaned_data['email'] != self.cleaned_data['email_confirmation']:
#                raise forms.ValidationError(u'A confirmação do e-mail deve coincidir com o e-mail informado')
#        return self.cleaned_data['email_confirmation']

    def save(self,commit=True):
        #Salvando na tabela de Usuários
        new_user = User()
        new_user.username = self.cleaned_data.get('email')
        new_user.email = self.cleaned_data.get('email')
        new_user.set_password(self.cleaned_data.get('password'))
        new_user.first_name = self.cleaned_data.get('first_name')
        new_user.last_name = self.cleaned_data.get('last_name')
        new_user.is_active = True
        new_user.save()
        return new_user

class WishlistForm(forms.ModelForm):
    class Meta:
        model = Wishlist
        fields= ('category','product','description', 'file')

#    def save(self, commit=True):
#        from google.appengine.ext import blobstore
#        upload_url = blobstore.create_upload_url('/upload')
#        files = {'file': self.cleaned_data.get('image')}
#        r = requests.post(upload_url, files = files)
#        print r
#        super(WishlistForm,self).save()