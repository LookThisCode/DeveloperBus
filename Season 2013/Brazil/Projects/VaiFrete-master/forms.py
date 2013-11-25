import djangoforms
from django import forms
from models import *

Vehicles = (
         ('Moto','Moto'),
         ('Bicicleta','Bicicleta'),
         ('Helicoptero','Helicoptero'),
         ('Van','Van'),
        )

UserTypes = (
		('Entregador','Entregador'),
		('Usuario','Usuario'),
	)

PackageTypes = (
    ('Envelope [menos de 1kg]', 'Envelope [menos de 1kg]'),
    ('Pacote [ate 10 kg]', 'Pacote [ate 10 kg]'),
    ('Acima de 10 kg', 'Acima de 10 kg'),
)

class AppUserForm(djangoforms.ModelForm):
  name = forms.CharField(label='Nome',widget=forms.TextInput(attrs={'size':'50','maxlength':'50'} ))
  real_user = forms.EmailField(label='E-mail',widget=forms.TextInput(attrs={'size':'50','maxlength':'50'} ))
  phone = forms.CharField(label='Telefone',widget=forms.TextInput(attrs={'size':'50','maxlength':'50'} ))
  #vehicle = forms.TypedChoiceField(choices=Vehicles, initial='Moto')
  #user_type = forms.TypedChoiceField(choices=UserTypes, initial='Usuario')
  class Meta:
    model = AppUser
    exclude = ['last_status', 'last_position', 'user_email', 'last_lng', 'last_lat', 'avg_price', 'social_id', 'vehicle', 'user_type']

class DeliverFeeForm(djangoforms.ModelForm):
	source_address = forms.CharField(label='Origem',widget=forms.TextInput(attrs={'size':'50','maxlength':'50'} ))
	destination_address = forms.CharField(label='Destino',widget=forms.TextInput(attrs={'size':'50','maxlength':'50'} ))
	item = forms.TypedChoiceField(choices=PackageTypes, initial='Envelope [menos de 1kg]')
	class Meta:
		model = DeliverFee
		exclude = ['dest_lng','dest_lat', 'source_lat', 'source_lng','request_user', 'item', 'request_date_time', 'closed', 'state', 'request_datetime']