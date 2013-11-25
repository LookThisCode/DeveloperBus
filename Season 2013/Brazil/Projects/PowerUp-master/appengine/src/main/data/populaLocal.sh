#!/bin/bash
export PATH=$PATH:/shared/dev/appengine-sdks/python/google_appengine

EMAIL="cit.cc.fwc2014@gmail.com"
PASSWORD="12345!@#$%"
APP_ID="localhost:8080"
KINDS="Configuration;ExerciseDescriptor"
ENVIRONMENT=""

IFS=';' read -a KIND_ARRAY <<< "${KINDS}"

for (( i=0; i<${#KIND_ARRAY[@]}; i++ ))
do
  CSV=${KIND_ARRAY[$i]}.csv

  if [ -f ${ENVIRONMENT}/${CSV} ]
  then
    CSV=${ENVIRONMENT}/${CSV}
  fi

  echo ${PASSWORD} | appcfg.py upload_data --config_file=bulkloader.yaml --url=http://${APP_ID}/_ah/remote_api --kind=${KIND_ARRAY[$i]} --filename=${CSV} --email=${EMAIL}
done
