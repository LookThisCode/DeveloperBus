
from google.appengine.ext import db

class AppUser(db.Model):
  real_user = db.UserProperty()
  user_email = db.StringProperty()
  vehicle = db.StringProperty()
  user_type = db.StringProperty()
  last_status = db.StringProperty()
  last_position = db.StringProperty()
  last_lat = db.StringProperty()
  last_lng = db.StringProperty()
  avg_price = db.StringProperty()
  social_id = db.StringProperty()
  name = db.StringProperty()
  phone = db.StringProperty()
  photo = db.StringProperty()
  request_datetime = db.DateTimeProperty(auto_now=True)

class DeliverFee(db.Model):
	source_address = db.StringProperty()
	destination_address = db.StringProperty()
	request_user = db.UserProperty()
	item = db.StringProperty()
	request_date_time = db.DateTimeProperty()
	closed = db.BooleanProperty()
	source_lat = db.StringProperty()
	source_lng = db.StringProperty()
	dest_lat = db.StringProperty()
	dest_lng = db.StringProperty()
	state = db.StringProperty(default='Pendente')

class DeliverOffer(db.Model):
	deliver_fee = db.ReferenceProperty(DeliverFee)
	app_user = db.ReferenceProperty(AppUser)
	offer_info = db.TextProperty()
	state = db.StringProperty(default='Pendente')