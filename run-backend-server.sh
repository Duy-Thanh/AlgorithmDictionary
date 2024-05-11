#! /bin/bash

JAVA=java
DICTIONARY_DIR=/var/www/dict
BACKEND_FILE=$DICTIONARY_DIR/backend-1.0.jar
COMMAND=-jar

$JAVA $COMMAND $BACKEND_FILE &

