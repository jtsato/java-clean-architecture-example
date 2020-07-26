echo POST /authors
curl --request POST \
  --header 'accept-language: pt_BR' \
  --header 'content-type: application/json' \
  --url 'http://localhost:8080/authors' \
  --data '{
	"name": "John Smith",
	"gender": "MALE",
	"birthday": "1980-04-23"
}' | json_pp

echo
echo PUT /authors
curl --request PUT \
  --header 'accept-language: pt_BR' \
  --header 'content-type: application/json' \
  --url 'http://localhost:8080/authors/1' \
  --data '{
	"name": "Emma Williams",
	"gender": "FEMALE",
	"birthday": "1982-09-04"
}' | json_pp

echo
echo POST /authors
curl --request POST \
  --header 'accept-language: pt_BR' \
  --header 'content-type: application/json' \
  --url 'http://localhost:8080/authors' \
  --data '{
	"name": "John Smith",
	"gender": "MALE",
	"birthday": "1980-04-23"
}' | json_pp

echo
echo POST /books
curl --request POST \
  --header 'accept-language: pt_BR' \
  --header 'content-type: application/json' \
  --url 'http://localhost:8080/books' \
  --data '{
	"title": "Get Your Hands Dirty on Clean Architecture V1.",
  "price:" 11.11,
	"authorId": 2
}' | json_pp

echo
echo PUT /books
curl --request PUT \
  --header 'accept-language: pt_BR' \
  --header 'content-type: application/json' \
  --url 'http://localhost:8080/books/1' \
  --data '{
	"title": "Get Your Hands Dirty on Clean Architecture V2.",
  "price:" 22.22,
	"authorId": 1
}' | json_pp

echo
echo POST /books
curl --request POST \
  --header 'accept-language: pt_BR' \
  --header 'content-type: application/json' \
  --url 'http://localhost:8080/books' \
  --data '{
	"title": "Get Your Hands Dirty on Clean Architecture V1.",
  "price:" 33.33,
	"authorId": 2
}' | json_pp

echo
echo GET /authors/1
curl --request GET \
  --header 'accept-language: pt_BR' \
  --url 'http://localhost:8080/authors/1' | json_pp

echo
echo GET /books/1
curl --request GET \
  --header 'accept-language: pt_BR' \
  --url 'http://localhost:8080/books/1' | json_pp

echo
echo GET /authors
curl --request GET \
  --header 'accept-language: pt_BR' \
  --url 'http://localhost:8080/authors?page=0&size=1&sort=name%2Casc' | json_pp

echo
echo GET /books
curl --request GET \
  --header 'accept-language: pt_BR' \
  --url 'http://localhost:8080/books?page=0&size=1' | json_pp

echo
echo DELETE /books
curl --request DELETE --header 'accept-language: pt_BR' --url 'http://localhost:8080/books/2'

echo
echo DELETE /authors
curl --request DELETE --header 'accept-language: pt_BR' --url 'http://localhost:8080/authors/2'
