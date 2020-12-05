#!/bin/bash
cd /usr/local/NIS-Explorer-V2/
mvn -Dmaven.test.skip -U clean package
cd target
java -Xms4800M -Xmx4800M -jar explorer_es.jar
