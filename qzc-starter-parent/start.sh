#!/bin/bash

PORT=8989
SERVICENAME=xingdadata.jar
SPRINGCONFIG=/home/qzc/xingda/config/application.yml

nohup java -jar -Xms512m -Xmx2048m -XX:PermSize=1024m -XX:MaxNewSize=108m -XX:MaxPermSize=2048m ./$SERVICENAME -Dfile.encoding=UTF-8 --spring.config.location=$SPRINGCONFIG --server.port=$PORT >/dev/null 2>&1 &
