@echo off

echo "---------------------start mvn install---------------------"
cd ./qzc-starter-parent
echo "start mvn install qzc-starter-parent"
call mvn clean install -Dmaven.test.skip=true

cd ../qzc-base-service
echo "start mvn install qzc-base-service"
call mvn clean install -Dmaven.test.skip=true

echo "---------------------end mvn install---------------------"
pause