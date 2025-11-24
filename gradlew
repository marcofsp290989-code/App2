#!/bin/sh
if [ -x "./gradle/wrapper/gradle-wrapper.jar" ]; then
  exec java -jar ./gradle/wrapper/gradle-wrapper.jar "$@"
else
  exec gradle "$@"
fi
