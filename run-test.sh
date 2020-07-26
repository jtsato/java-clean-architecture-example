#!/bin/sh
cd $(dirname $0)

rm -rf ~/.m2/repository/io/github/jtsato

find . -name 'target' | xargs -P10 -I{} rm -rf {}  

mvn -e install verify org.pitest:pitest-maven:mutationCoverage

exit