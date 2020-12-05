#!/bin/bash

cd nis
java -Xms4800M -Xmx4800M -cp ".:./*:../libs/*" org.nem.deploy.CommonStarter
cd -
