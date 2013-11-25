from google.appengine.ext.webapp import util
from protorpc.wsgi import service
import logging
import deliveryservice

logging.debug('Handling service...')
# Map the RPC service and path (/DeliverService)
delivery_service = service.service_mapping(deliveryservice.DeliveryService, '/DeliveryService.*')

def main():
  util.run_wsgi_app(delivery_service)

if __name__ == '__main__':
  main()