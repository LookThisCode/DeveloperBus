from django_openid_auth.models import Association, UserOpenID

FIELD_INDEXES = {
    Association: {'indexed': ['server_url', 'assoc_type']},
    UserOpenID: {'indexed': ['claimed_id']},
    }

