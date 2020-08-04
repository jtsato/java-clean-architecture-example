set -x

http DELETE :8080/books/2 Accept-Language:pt_BR

http DELETE :8080/authors/2 Accept-Language:pt_BR
