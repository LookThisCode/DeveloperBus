#!/bin/sh

sudo apt-get update
sudo apt-get install python-setuptools -y
sudo apt-get install libpq-dev -y
sudo apt-get install python-dev -y
sudo apt-get install gettext -y
sudo easy_install pip
sudo pip install -r /home/vagrant/django_site/requirements.txt

sudo apt-get install postgresql -y