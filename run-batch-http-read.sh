set -x

http GET :8081/enumerators domain==Gender key==MALE Accept-Language:pt_BR
http GET :8081/enumerators domain==Gender key==FEMALE Accept-Language:pt_BR

http GET :8081/authors/1 Accept-Language:pt_BR
http GET :8081/authors Accept-Language:pt_BR
http POST :8081/authors/findByIds ids:='[1,2]' Accept-Language:pt_BR

http GET :8081/books/1 Accept-Language:pt_BR
http GET :8081/books Accept-Language:pt_BR
http POST :8081/books/findByIds ids:='[1,2]' Accept-Language:pt_BR

http GET :8081/authors id==1 name=='Emma Williams' gender==FEMALE startBirthdate==1981-09-04 endBirthdate==1983-09-04 \
page=0 size=1 sort=name,asc Accept-Language:pt_BR

http GET :8081/books title=='Get Your Hands Dirty' startCreationDate==2020-01-01T00:00:00.000Z endCreationDate==2020-12-31T00:00:00.000Z \
author.id==1 author.name=='Emma Williams' author.gender==FEMALE author.startBirthdate==1981-09-04 author.endBirthdate==1983-09-04 page==0 size==10 sort==title sort==creationDate,desc Accept-Language:pt_BR