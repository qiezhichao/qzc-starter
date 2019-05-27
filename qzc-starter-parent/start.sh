#!/bin/bash

nohup java -jar -Xms512m -Xmx2048m -XX:PermSize=1024m -XX:MaxNewSize=108m -XX:MaxPermSize=2048m ./xingdadata.jar -Dfile.encoding=UTF-8 --spring.config.location=/home/qzc/xingda/config/application.yml --server.port=8999 >/dev/null 2>&1 &
