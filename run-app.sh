#!/bin/sh
cd $(dirname $0)

rm -rf ~/.m2/repository/io/github/jtsato

find . -name 'target' | xargs -P10 -I{} rm -rf {} 

mvn -e install -DskipTests
    
ret=$?
if [ $ret -ne 0 ]; then
exit $ret
fi

mvn spring-boot:run -f ./configuration

exit
