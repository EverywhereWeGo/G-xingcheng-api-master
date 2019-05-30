#!/bin/sh
MAIN_CLASS=com.bfd.api.app.EurekaApplication
BASE_DIR=$(cd $(dirname $0)/..; pwd)
MYLIB=$BASE_DIR/lib
CLASSPATH=$BASE_DIR/conf:$MYLIB/*
echo $CLASSPATH
export LOG4J_HOME=$BASE_DIR
echo " the program is starting..."
java -Xms512m -Xmx2024m -cp $CLASSPATH $MAIN_CLASS > $BASE_DIR/log/main.log 2>&1 &