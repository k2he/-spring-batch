#!/bin/sh
#
###################################
# Path Variables
#
###################################

cd `dirname $0`

PWD=`pwd`

SCRIPT_DIR=${PWD%/*}
echo $SCRIPT_DIR
JAR_FILE_NAME="spring-batch-0.0.1-SNAPSHOT.jar"

JASYPT_PASSWORD="devsecret"

LOG4J2_CONF="$SCRIPT_DIR/Order_Batch/config/log4j2.xml"
CONF_DIR="$SCRIPT_DIR/Order_Batch/config"

#set -A JAVA_OPTS " -Xms3584m" "-Xmx3584m" "-XX:MaxPermSize=x3584m"
#################################
# Launch Batch Job
#
#################################

mv inbound/input_`date +%Y%m%d`.xml inbound/input.xml
rm -f log/*

java -Dlogging.config=$LOG4J2_CONF -jar $JAR_FILE_NAME --app.config.home=$CONF_DIR --jasypt.encryptor.password=$JASYPT_PASSWORD
retToken=$?
echo "Completed Batch Job: $retToken"

if [ "$retToken" -eq 0 ]
then
echo "SUCCESS - Exiting with code 0"

mv inbound/input.xml archive/input_`date +%Y%m%d`.xml
gzip archive/input_`date +%Y%m%d`.xml

find archive/* -mtime +5 -exec rm {} \;

exit 0
else
echo "FAILURE - Exiting with code 1"

mv inbound/input.xml error/input_`date +%Y%m%d`.xml
gzip error/input_`date +%Y%m%d`.xml

find error/* -mtime +10 -exec rm {} \;

exit 1
fi