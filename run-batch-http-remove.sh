set -x

http DELETE :8081/books/2 Accept-Language:pt_BR

http DELETE :8081/authors/2 Accept-Language:pt_BR
