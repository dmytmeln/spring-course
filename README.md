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

- As a reader, I want to be able to see my personal information

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

- Searching for Book

- Searching for Book Copy

- Searching for Author

- Searching for Category

- Searching for Book (by title, author name, and/or category name)

- Searching for Author (by name)

- Searching for Category (by name)

- Show personal Information

- Updating Personal Information

- Viewing Current Book Loans

- Reserving a Book

- Borrowing Books



# Database

[Database](db.jpeg)





# REST API endpoints


| **REST API Endpoint** | **Request Method** | **Description** | **Request Body** | **Request Params / Path variables** | **Response** |
|------------------------------------|--------------------|--------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/api/librarian/books` | POST | Add a new book to the library | `{ "title": "string", "category_id": "int", "writing_year": "int", "language": "string", "authors": [int] }` | None | `201 Created` - `{ "book_id": "int", "title": "string", "category_id": "int", "writing_year": "int", "language": "string", "authors": [int] }` |
| `/api/librarian/books/{bookId}/copies` | POST | Add a new book's copy to the library | `{ "publisher": "string", "publication_date": "int", "ISBN": "string", "pages": "int", "language": "string" }` | `book_id`: int| `201 Created` - `{ "publisher": "string", "publication_date": "int", "ISBN": "string", "pages": "int", "language": "string", "isBorrowed": "boolean" }` |
| `/api/librarian/authors` | POST | Add a new author to the library | `{ "full_name": "string", "descr": "text", "birth_year": "int", "death_year": "int", "country": "string" }` | None | `201 Created` - `{ "author_id": "int", "full_name": "string", "descr": "text", "birth_year": "int", "death_year": "int", "country": "string" }` |
| `/api/librarian/categories` | POST | Add a new category to the library | `{ "name": "string", "descr": "text" }` | None | `201 Created` - `{ "category_id": "int", "name": "string", "descr": "text" }` |
| `/api/librarian/books/{book_id}` | PUT | Update the details of an existing book | `{ "title": "string", "category_id": "int", "writing_year": "int", "language": "string", "authors": [int] }` | `book_id`: int | `200 OK` - `{ "book_id": "int", "title": "string", "category_id": "int", "writing_year": "int", "language": "string", "authors": [int] }` |
| `/api/librarian/books/{bookId}/copies/{copy_id}` | PUT| Update the details of an existing book copy | `{ "publisher": "string", "publication_date": "int", "ISBN": "string", "pages": "int", "language": "string" }` | `book_id`: int, `copy_id`: int| `200 OK` - `{ "publisher": "string", "publication_date": "int", "ISBN": "string", "pages": "int", "language": "string", "isBorrowed": "boolean" }` |
| `/api/librarian/authors/{author_id}` | PUT | Update the details of an existing author | `{ "full_name": "string", "descr": "text", "birth_year": "int", "death_year": "int", "country": "string" }` | `author_id`: int | `200 OK` - `{ "author_id": "int", "full_name": "string", "descr": "text", "birth_year": "int", "death_year": "int", "country": "string" }` |
| `/api/librarian/categories/{category_id}` | PUT | Update the details of an existing category | `{ "name": "string", "descr": "text" }` | `category_id`: int | `200 OK` - `{ "category_id": "int", "name": "string", "descr": "text" }` |
| `/api/librarian/categories/{category_id}` | DELETE | Delete an existing category | None | `category_id`: int | `204 No Content` |
| `/api/librarian/books/{book_id}` | DELETE | Delete an existing book | None | `book_id`: int | `204 No Content` |
| `/api/librarian/books/{bookId}/copies/{copy_id}` | DELETE| Delete an existing book copy | None | `book_id`: int, `copy_id`: int| `204 No Content` |
| `/api/librarian/authors/{author_id}` | DELETE | Delete an existing author | None | `author_id`: int | `204 No Content` |
| `/api/books` | GET | Search for books by title and/or category name and/or author name, otherwise get all books | None | `title`: string, `author_name`: string, `category_name`: string | `200 OK` - `[ { "book_id": "int", "title": "string", "category_id": "int", "writing_year": "int", "language": "string", "authors": [int] } ]` |
| `/api/books/{book_id}/copy` | GET | Get all book copies | None| `book_id`: int | `200 OK` - `[ { "publisher": "string", "publication_date": "int", "ISBN": "string", "pages": "int", "language": "string", "isBorrowed": "boolean" } ]` |
| `/api/authors` | GET | Search for authors by name or get all authors | None | `full_name`: string | `200 OK` - `[ { "author_id": "int", "full_name": "string", "descr": "text", "birth_year": "int", "death_year": "int", "country": "string" } ]` |
| `/api/categories` | GET | Search for categories by name or get all categories | None | `name`: string | `200 OK` - `[ { "category_id": "int", "name": "string", "descr": "text" } ]` |
| `/api/books/{book_id}` | GET | Get book | None | `book_id`: int | `200 OK` - `{ "book_id": "int", "title": "string", "category_id": "int", "writing_year": "int", "language": "string", "authors": [int] }` |
| `/api/books/{book_id}/copy/{copy_id}` | GET | Get book copy | None| `book_id`: int, `copy_id`: int | `200 OK` - `{ "publisher": "string", "publication_date": "int", "ISBN": "string", "pages": "int", "language": "string", "isBorrowed": "boolean" }` |
| `/api/authors/{author_id}` | GET | Get author | None | `author_id`: int| `200 OK` - `{ "author_id": "int", "full_name": "string", "descr": "text", "birth_year": "int", "death_year": "int", "country": "string" }` |
| `/api/categories/{category_id}` | GET | Get category | None | `category_id`: int| `200 OK` - `{ "category_id": "int", "name": "string", "descr": "text" }` |
| `/api/reader` | Get| Get personal information of a reader | None | None | `200 OK` - `{ "user_id": "int", "firstname": "string", "lastname": "string", "e-mail": "string", "ticket_ending_date": "date", "ticket_loan_limit": "int", "ticket_status": "boolean" }` |
| `/api/reader` | PUT | Update personal information of a reader | `{ "firstname": "string", "lastname": "string", "e-mail": "string", "password": "string" }` | None | `200 OK` - `{ "user_id": "int", "firstname": "string", "lastname": "string", "e-mail": "string", "ticket_ending_date": "date", "ticket_loan_limit": "int", "ticket_status": "boolean" }` |
| `/api/reader/loans` | GET | View current book loans of a reader | None | None | `200 OK` - `[ { "loan_id": "int", "book_copy_id": "int", "start_date": "date", "end_date": "date" } ]` |
| `/api/books/{book_id}/copies/{copy_id}/reserve` | POST | Reserve a book that is currently unavailable | None | `book_id`: int, `reader_ticket_id`: int | `200 OK` - `{ "reservation_id": "int", "book_id": "int", "reader_ticket_id": "int", "start_date": "date", "end_date": "date" }` |
| `/api/books/{book_id}/copies/{copy_id}/borrow` | POST | Borrow books from the library | `{ "reader_ticket_id": "int", "book_copy_ids": ["int"] }` | None | `200 OK` - `[ { "loan_id": "int", "book_copy_id": "int", "reader_ticket_id": "int", "start_date": "date", "end_date": "date", ]` |
| `/api/auth/register` | POST | Register a user | `{ "firstname": "string", "lastname": "string", "e-mail": "string", "password": "string" }` | None | `201 CREATED` -  `{ "user_id": "int", "firstname": "string", "lastname": "string", "e-mail": "string", "ticket_ending_date": "date", "ticket_loan_limit": "int", "ticket_status": "boolean" }` |
| `/api/auth/login` | POST | Login | `{ "e-mail": "string", "password": "string" }` | None | `200 OK` - `{ "user_id": "int", "firstname": "string", "lastname": "string", "e-mail": "string", "ticket_ending_date": "date", "ticket_loan_limit": "int", "ticket_status": "boolean" }` |
