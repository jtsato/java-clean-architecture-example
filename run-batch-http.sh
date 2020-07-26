set -x

http POST :8080/authors name='John Smith' gender=MALE birthday=1980-04-23 Accept-Language:pt_BR
http PUT :8080/authors/1 name='Emma Williams' gender=FEMALE birthday=1982-09-04 Accept-Language:pt_BR
http POST :8080/authors name='John Smith' gender=MALE birthday=1980-04-23 Accept-Language:pt_BR

http POST :8080/books title='Get Your Hands Dirty on Clean Architecture V1.' price=11.11 authorId=2 Accept-Language:pt_BR
http PUT :8080/books/1 title='Get Your Hands Dirty on Clean Architecture V2.' price=22.22 authorId=1 Accept-Language:pt_BR
http POST :8080/books title='Get Your Hands Dirty on Clean Architecture V1.' price=33.33 authorId=2 Accept-Language:pt_BR

http GET :8080/authors/1 Accept-Language:pt_BR
http GET :8080/books/1 Accept-Language:pt_BR

http GET :8080/authors Accept-Language:pt_BR
http GET :8080/books Accept-Language:pt_BR

http GET :8080/authors id==1 name=='Emma Williams' gender==FEMALE startBirthday==1981-09-04 endBirthday==1983-09-04 \
page=0 size=1 sort=name,asc Accept-Language:pt_BR

http GET :8080/books title=='Get Your Hands Dirty' startCreationDate==2020-01-01T00:00:00.000Z endCreationDate==2020-12-31T00:00:00.000Z \
author.id==1 author.name=='Emma Williams' author.gender==FEMALE author.startBirthday==1981-09-04 author.endBirthday==1983-09-04 page==0 size==10 sort==title sort==creationDate,desc Accept-Language:pt_BR

# http DELETE :8080/books/2 Accept-Language:pt_BR

# http DELETE :8080/authors/2 Accept-Language:pt_BR
