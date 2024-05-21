
# Functional requirements:

- As a librarian, I want to be able to add a new book to the library system

- As a librarian, I want to be able to add a new author to the library system

- As a librarian, I want to be able to add a new category to the library system

- As a librarian, I want to be able to update the details of a book

- As a librarian, I want to be able to update the details of an author

- As a librarian, I want to be able to update the details of a category

- As a librarian, I want to be able to update the details of a book

- As a librarian, I want to be able to update the details of an author

- As a librarian, I want to be able to delete a category

- As a reader, I want to be able to search for books by title

- As a reader, I want to be able to search for authors by name

- As a reader, I want to be able to search for categories by name

- As a reader, I want to be able to browse the library's collection by category or author, to discover new books that match my interests

- As a reader, I want to be able to update my personal information (such as name, email, or passport number) in the system to keep my account details accurate and up to date

- As a reader, I want to be able to see my current book loans, including the due dates and any overdue books, to help me manage my borrowing schedule effectively

- As a reader, I want to be able to reserve a book that is currently unavailable so that I can ensure its availability for future borrowing

- As a reader, I want the ability to borrow books from the library using my reader ticket and selecting the desired books for loan

# System behaviors:

- Adding a New Book

- Adding a New Author

- Adding a New Category

- Updating Book Details

- Updating Author Details

- Updating Category Details

- Deleting a Book

- Deleting an Author

- Deleting a Category

- Searching for Book (by title)

- Searching for Author (by name)

- Searching for Category (by name)

- Browse the library's collection by category or author

- Updating Personal Information

- Viewing Current Book Loans

- Reserving a Book

- Borrowing Books

# REST API endpoints
### REST API Endpoints

| **REST API Endpoint**              | **Request Method** | **Description**                                        | **Request Body**                                                                                                                                         | **Request Params**     | **Response**                                                                                                                                                    |
|------------------------------------|--------------------|--------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/books`                           | POST               | Add a new book to the library                          | `{ "title": "string", "authorId": "int", "categoryId": "int", "isbn": "string", "publishedDate": "string" }`                                               | None                   | `201 Created` - `{ "id": "int", "title": "string", "authorId": "int", "categoryId": "int", "isbn": "string", "publishedDate": "string" }`                     |
| `/authors`                         | POST               | Add a new author to the library                        | `{ "name": "string", "biography": "string", "birthDate": "string" }`                                                                                       | None                   | `201 Created` - `{ "id": "int", "name": "string", "biography": "string", "birthDate": "string" }`                                                               |
| `/categories`                      | POST               | Add a new category to the library                      | `{ "name": "string", "description": "string" }`                                                                                                            | None                   | `201 Created` - `{ "id": "int", "name": "string", "description": "string" }`                                                                                    |
| `/books/{id}`                      | PUT                | Update the details of an existing book                 | `{ "title": "string", "authorId": "int", "categoryId": "int", "isbn": "string", "publishedDate": "string" }`                                               | `id`: int              | `200 OK` - `{ "id": "int", "title": "string", "authorId": "int", "categoryId": "int", "isbn": "string", "publishedDate": "string" }`                          |
| `/authors/{id}`                    | PUT                | Update the details of an existing author               | `{ "name": "string", "biography": "string", "birthDate": "string" }`                                                                                       | `id`: int              | `200 OK` - `{ "id": "int", "name": "string", "biography": "string", "birthDate": "string" }`                                                                    |
| `/categories/{id}`                 | PUT                | Update the details of an existing category             | `{ "name": "string", "description": "string" }`                                                                                                            | `id`: int              | `200 OK` - `{ "id": "int", "name": "string", "description": "string" }`                                                                                         |
| `/categories/{id}`                 | DELETE             | Delete an existing category                            | None                                                                                                                                                      | `id`: int              | `204 No Content`                                                                                                                                                 |
| `/books/{id}`                 | DELETE             | Delete an existing book| None                                                                                                                                                      | `id`: int              | `204 No Content`                                                                                                                                                 |
| `/authors/{id}`                 | DELETE             | Delete an existing author| None                                                                                                                                                      | `id`: int              | `204 No Content`                                                                                                                                                 |
| `/books`                           | GET                | Search for books by title                              | None                                                                                                                                                      | `title`: string        | `200 OK` - `[ { "id": "int", "title": "string", "authorId": "int", "categoryId": "int", "isbn": "string", "publishedDate": "string" } ]`                      |
| `/authors`                         | GET                | Search for authors by name                             | None                                                                                                                                                      | `name`: string         | `200 OK` - `[ { "id": "int", "name": "string", "biography": "string", "birthDate": "string" } ]`                                                                |
| `/categories`                      | GET                | Search for categories by name                          | None                                                                                                                                                      | `name`: string         | `200 OK` - `[ { "id": "int", "name": "string", "description": "string" } ]`                                                                                     |
| `/collections`                     | GET                | Browse the library's collection by category or author  | None                                                                                                                                                      | `authorId`: int, `categoryId`: int | `200 OK` - `[ { "id": "int", "title": "string", "authorId": "int", "categoryId": "int", "isbn": "string", "publishedDate": "string" } ]`                      |
| `/readers/{id}/info`               | PUT                | Update personal information of a reader                | `{ "name": "string", "email": "string", "passportNumber": "string" }`                                                                                      | `id`: int              | `200 OK` - `{ "id": "int", "name": "string", "email": "string", "passportNumber": "string" }`                                                                   |
| `/readers/{id}/loans`              | GET                | View current book loans of a reader                    | None                                                                                                                                                      | `id`: int              | `200 OK` - `[ { "bookId": "int", "dueDate": "string", "overdue": "boolean" } ]`                                                                                 |
| `/books/{id}/reserve`              | POST               | Reserve a book that is currently unavailable           | None                                                                                                                                                      | `id`: int, `readerId`: int | `200 OK` - `{ "reservationId": "int", "bookId": "int", "readerId": "int", "reservationDate": "string" }`                                                        |
| `/books/{id}/borrow`                    | POST               | Borrow books from the library                          | None                                                                                                             | `id`: int | `200 OK` - `[ { "loanId": "int", "bookId": "int", "readerId": "int", "loanDate": "string", "dueDate": "string" } ]`                                            |