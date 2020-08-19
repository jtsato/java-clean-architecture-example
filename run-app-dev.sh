#!/bin/sh
cd $(dirname $0)

rm -rf ~/.m2/repository/io/github/jtsato

find . -name 'target' | xargs -P10 -I{} rm -rf {} 

mvn -e install -DskipTests
    
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi

export $(cat .env | xargs)

mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev" -f ./configuration

exit
