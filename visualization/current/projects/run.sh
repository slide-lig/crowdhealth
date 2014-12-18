#!/bin/sh
cd $(dirname $0)
java -jar target/dropwizard-sample-1.0-SNAPSHOT.jar server src/main/resources/sample.yml
