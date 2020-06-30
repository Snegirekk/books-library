# Books library app

### Deployment
* Clone this repo to the linux machine with preinstalled `docker` and `docker-compose`
* Go to the `books-library` directory
* Run `docker-compose up -d` command

### Endpoints
Base url is `http://127.0.0.1:8808/api/v1`

##### Create book
Request `POST /book`

Body example:
```
{
    "name": "Some name",
    "author": "Great Author"
}
```

Response:

Success code: `201`

Body example:
```
{
    "id": "7f0a9790-e7d4-41b8-bed9-1442f8e07c23",
    "name": "Some name",
    "author": "Great Author",
    "available": true,
    "last_two_reviews": null
}
```

##### Delete book
Request `DELETE /book/{bookId}`

Response:

Success code: `200`

##### Take book
Request `PUT /book/take/{bookId}`

Response:

Success code: `200`

##### Return book
Request `PUT /book/return/{bookId}`

Response:

Success code: `200`

##### List books
Request `GET /book`

Available query params:
* page (unsigned int)
* itemsPerPage (unsigned int)

Response:

Success code: `200`

Body example:
```
{
    "page": 1,
    "items_per_page": 10,
    "total_pages": 1,
    "items": [
        {
            "id": "5bd177a0-8dd3-4801-8cf3-dce9b0e85d4c",
            "name": "Some name",
            "author": "Great Author"
        },
        {
            "id": "7f0a9790-e7d4-41b8-bed9-1442f8e07c23",
            "name": "onegin",
            "name": "Some another name",
            "author": "Not Great Author"
        }
    ]
}
```

##### Retrieve book
Request `GET /book/{bookId}`

Response:

Success code: `200`

Body example:
```
{
    "id": "5bd177a0-8dd3-4801-8cf3-dce9b0e85d4c"
    "name": "Some name",
    "author": "Great Author",
    "available": true,
    "last_two_reviews": [
        {
            "id": "b17d12d3-56a8-4c6a-82da-cbc9a27bd341",
            "book_id": "5bd177a0-8dd3-4801-8cf3-dce9b0e85d4c",
            "text": "Amaizing book!"
        }
    ]
}
```

##### Create review
Request `POST /book/{bookId}/review`

Body example:
```
{
    "text": "Amaizing book!"
}
```

Response:

Success code: `201`

Body example:
```
{
    "id": "b17d12d3-56a8-4c6a-82da-cbc9a27bd341",
    "text": "Amaizing book!",
    "created_at": "2018-08-24T07:33:28.179"
}
```

##### List book reviews
Request `GET /book/{bookId}/review`

Available query params:
* page (unsigned int)
* itemsPerPage (unsigned int)

Response:

Success code: `200`

Body example:
```
{
    "page": 1,
    "items_per_page": 10,
    "total_pages": 1,
    "items": [
        {
            "id": "b17d12d3-56a8-4c6a-82da-cbc9a27bd341",
            "book_id": "5bd177a0-8dd3-4801-8cf3-dce9b0e85d4c",
            "text": "Amaizing book!"
        }
    ]
}
```

##### Retrieve review
Request `GET /review/{reviewId}`

Response:

Success code: `200`

Body example:
```
{
    "id": "b17d12d3-56a8-4c6a-82da-cbc9a27bd341",
    "text": "Amaizing book!",
    "created_at": "2018-08-24T07:33:28.179"
}
```

### TODO

* Tests
* Refactoring
* Bugfixes