set -x

http GET :8081/enumerators domain==Gender key==MALE Accept-Language:pt_BR 'Authorization:'"$token"'' 
http GET :8081/enumerators domain==Gender key==FEMALE Accept-Language:pt_BR 'Authorization:'"$token"'' 

http GET :8081/authors/1 Accept-Language:pt_BR 'Authorization:'"$token"'' 
http GET :8081/authors Accept-Language:pt_BR 'Authorization:'"$token"'' 
http POST :8081/authors/findByIds ids:='[1,2]' Accept-Language:pt_BR 'Authorization:'"$token"'' 

http GET :8081/books/1 Accept-Language:pt_BR 'Authorization:'"$token"'' 
http GET :8081/books Accept-Language:pt_BR 'Authorization:'"$token"'' 
http POST :8081/books/findByIds ids:='[1,2]' Accept-Language:pt_BR 'Authorization:'"$token"'' 

http GET :8081/authors name=='Eleanora' gender==FEMALE startBirthdate==1977-09-04 endBirthdate==1979-09-04 \
page=0 size=1 sort=name,asc Accept-Language:pt_BR 'Authorization:'"$token"'' 

http GET :8081/books title=='a' startCreationDate==2020-01-01T00:00:00.000Z endCreationDate==2020-12-31T00:00:00.000Z \
author.name=='Eleanora' author.gender==FEMALE author.startBirthdate==1977-09-04 author.endBirthdate==1979-09-04 page==0 size==10 sort==title sort==creationDate,desc Accept-Language:pt_BR 'Authorization:'"$token"''