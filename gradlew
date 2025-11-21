#!/bin/sh
# -----------------------------------------------------------------------------
# Gradle start up script for UN*X
# -----------------------------------------------------------------------------

# Attempt to set APP_HOME to the directory containing this script
APP_HOME="$(cd "$(dirname "$0")" && pwd)"

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS=""

# Resolve any symlinks
PRG="$0"
while [ -h "$PRG" ]; do
  ls=$(ls -ld "$PRG")
  link=$(expr "$ls" : '.*-> (.*)$')
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG="$(dirname "$PRG")/$link"
  fi
done

APP_HOME="$(cd "$(dirname "$PRG")" && pwd)"

# Define the Gradle home directory
GRADLE_HOME="$APP_HOME/gradle/wrapper"

# Run Gradle Wrapper using java command
exec java $DEFAULT_JVM_OPTS -cp "$GRADLE_HOME/gradle-wrapper.jar" org.gradle.wrapper.GradleWrapperMain "$@"
