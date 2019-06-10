#!/bin/bash

PORT=8989
SERVICENAME=xingdadata.jar
SPRINGCONFIG=/home/qzc/xingda/config/application.yml

# -XX:+HeapDumpOnOutOfMemoryError : 在发生OutOfMemoryError时，生成dump文件
# -XX:HeapDumpPath=/var/jvm/logs/ : 生成的dump文件存放路径（/var/jvm/logs/ 需提前创建好）
nohup java -jar -Xms512m -Xmx2048m -XX:PermSize=1024m -XX:MaxNewSize=108m -XX:MaxPermSize=2048m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/var/jvm/logs/ ./$SERVICENAME -Dfile.encoding=UTF-8 --spring.config.location=$SPRINGCONFIG --server.port=$PORT >/dev/null 2>&1 &
