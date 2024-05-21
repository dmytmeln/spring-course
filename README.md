


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

- As a librarian, I want to be able to add a copy of a book to the library system

- As a reader, I want to be able to search for a certain book copy

- As a reader, I want to be able to search for book copies by the book title

- As a reader, I want to be able to get all books

- As a reader, I want to be able to search for books by title, author, and/or category

- As a reader, I want to be able to get all authors

- As a reader, I want to be able to search for authors by name

- As a reader, I want to be able to get all categories

- As a reader, I want to be able to search for categories by name

- As a reader, I want to be able to update my personal information (such as name, email, or passport number) in the system to keep my account details accurate and up to date



- As a reader, I want to be able to see my current book loans, including the due dates and any overdue books, to help me manage my borrowing schedule effectively



- As a reader, I want to be able to reserve a book that is currently unavailable so that I can ensure its availability for future borrowing



- As a reader, I want the ability to borrow books from the library using my reader ticket and selecting the desired books for loan



# System behaviors:



- Adding a New Book



- Adding a New Author



- Adding a New Category

 - Adding a New Book Copy

- Updating Book Details

- Updating Book Copy Details

- Updating Author Details

- Updating Category Details



- Deleting a Book

- Deleting a Book Copy

- Deleting an Author

- Deleting a Category

- Get all Books

- Get all Book copies

- Get all Authors

- Get all Categories

-   Searching for Book (by title, author name, and/or category name)

-   Searching for Book Copy

-   Searching for Book Copies (by Book  title)

- Searching for Author (by name)

- Searching for Category (by name)



- Updating Personal Information



- Viewing Current Book Loans



- Reserving a Book



- Borrowing Books

# Database

[Database](db.jpeg)



# REST API endpoints



| **REST API Endpoint** | **Request Method** | **Description** | **Request Body** | **Request Params** | **Response** |
|------------------------------------|--------------------|--------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/books` | POST | Add a new book to the library | `{ "title": "string", "category_id": "int", "writing_year": "smallint", "language": "string", "authors": [int] }` | None | `201 Created` - `{ "book_id": "int", "title": "string", "category_id": "int", "writing_year": "smallint", "language": "string", "authors": [int] }` |
| `/authors` | POST | Add a new author to the library | `{ "full_name": "string", "descr": "text", "birth_year": "smallint", "death_year": "smallint", "country": "string" }` | None | `201 Created` - `{ "author_id": "int", "full_name": "string", "descr": "text", "birth_year": "smallint", "death_year": "smallint", "country": "string" }` |
| `/categories` | POST | Add a new category to the library | `{ "name": "string", "descr": "text" }` | None | `201 Created` - `{ "category_id": "int", "name": "string", "descr": "text" }` |
| `/books/{book_id}` | PUT | Update the details of an existing book | `{ "title": "string", "category_id": "int", "writing_year": "smallint", "language": "string", "authors": [int] }` | `book_id`: int | `200 OK` - `{ "book_id": "int", "title": "string", "category_id": "int", "writing_year": "smallint", "language": "string", "authors": [int] }` |
| `/authors/{author_id}` | PUT | Update the details of an existing author | `{ "full_name": "string", "descr": "text", "birth_year": "smallint", "death_year": "smallint", "country": "string" }` | `author_id`: int | `200 OK` - `{ "author_id": "int", "full_name": "string", "descr": "text", "birth_year": "smallint", "death_year": "smallint", "country": "string" }` |
| `/categories/{category_id}` | PUT | Update the details of an existing category | `{ "name": "string", "descr": "text" }` | `category_id`: int | `200 OK` - `{ "category_id": "int", "name": "string", "descr": "text" }` |
| `/categories/{category_id}` | DELETE | Delete an existing category | None | `category_id`: int | `204 No Content` |
| `/books/{book_id}` | DELETE | Delete an existing book | None | `book_id`: int | `204 No Content` |
| `/authors/{author_id}` | DELETE | Delete an existing author | None | `author_id`: int | `204 No Content` |
| `/books` | GET | Search for books by title or get all books | None | `title`: string | `200 OK` - `[ { "book_id": "int", "title": "string", "category_id": "int", "writing_year": "smallint", "language": "string", "authors": [authors_id] } ]` |
| `/authors` | GET | Search for authors by name or get all authors | None | `full_name`: string | `200 OK` - `[ { "author_id": "int", "full_name": "string", "descr": "text", "birth_year": "smallint", "death_year": "smallint", "country": "string" } ]` |
| `/categories` | GET | Search for categories by name or get all categories | None | `name`: string | `200 OK` - `[ { "category_id": "int", "name": "string", "descr": "text" } ]` |
| `/collections` | GET | Browse the library's collection by category or author | None | `author_id`: int, `category_id`: int | `200 OK` - `[ { "book_id": "int", "title": "string", "category_id": "int", "writing_year": "smallint", "language": "string" } ]` |
| `/readers/{user_id}/info` | PUT | Update personal information of a reader | `{ "firstname": "string", "lastname": "string", "e-mail": "string", "password": "string" }` | `user_id`: int | `200 OK` - `{ "user_id": "int", "role_name": "string", "firstname": "string", "lastname": "string", "e-mail": "string", "password": "string" }` |
| `/readers/{user_id}/loans` | GET | View current book loans of a reader | None | `user_id`: int | `200 OK` - `[ { "loan_id": "int", "book_copy_id": "int", "start_date": "date", "end_date": "date", "returned_date": "date" } ]` |
| `/books/{book_id}/reserve` | POST | Reserve a book that is currently unavailable | None | `book_id`: int, `reader_ticket_id`: int | `200 OK` - `{ "reservation_id": "int", "book_id": "int", "reader_ticket_id": "int", "start_date": "date", "end_date": "date" }` |
| `/books/borrow` | POST | Borrow books from the library | `{ "reader_ticket_id": "int", "book_copy_ids": ["int"] }` | None | `200 OK` - `[ { "loan_id": "int", "book_copy_id": "int", "reader_ticket_id": "int", "start_date": "date", "end_date": "date", "returned_date": "date" } ]` |
