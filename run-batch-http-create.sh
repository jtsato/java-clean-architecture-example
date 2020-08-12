set -x

http POST :8080/authors name='John Smith' gender=MALE birthdate=1980-04-23 Accept-Language:pt_BR
http PUT :8080/authors/1 name='Emma Williams' gender=FEMALE birthdate=1982-09-04 Accept-Language:pt_BR
http POST :8080/authors name='John Smith' gender=MALE birthdate=1980-04-23 Accept-Language:pt_BR

http POST :8080/books authorId=2 title='Get Your Hands Dirty on Clean Architecture V1.' price=11.11 available=true Accept-Language:pt_BR
http PUT :8080/books/1 authorId=1 title='Get Your Hands Dirty on Clean Architecture V2.' price=22.22 available=false Accept-Language:pt_BR
http POST :8080/books authorId=2 title='Get Your Hands Dirty on Clean Architecture V1.' price=33.33 available=true Accept-Language:pt_BR
