# Functional Requirements

### Admin functionality
* As an admin, I want to be able to create a new book.
* As an admin, I want to be able to update existing book details.
* As an admin, I want to be able to delete a book.
* As an admin, I want to be able to create a new author.
* As an admin, I want to be able to update author information.
* As an admin, I want to be able to delete an author.
* As an admin, I want to be able to create new categories for better book organization.
* As an admin, I want to be able to delete a category when it’s no longer needed.
* As an admin, I want to be able to update category details.
* As an admin, I want to be able to delete users.

### User functionality
* As a user, I want to be able to add a book to my personal library.
* As a user, I want to be able to change the status of a book (e.g., "want to read," "currently reading," "finished").
* As a user, I want to be able to remove a book from my personal library.
* As a user, I want to be able to view a list of all books.
* As a user, I want to be able to view a list of all authors.
* As a user, I want to be able to view a list of all categories.
* As a user, I want to be able to view a list of all books by specific category or author.
* As a user, I want to be able to search for books by title.
* As a user, I want to be able to leave a review on a book.
* As a user, I want to be able to delete my review.
* As a user, I want to be able to view details of a specific book.
* As a user, I want to be able to view a list of all books inside my personal library.
* As a user, I want to be able to view a list of all categories inside my personal library.
* As a user, I want to be able to view a list of all authors inside my personal library.
* As a user, I want to be able to view a list of all books by specific category or author inside my personal library.
* As a user, I want to be able to search for books by title inside my personal library.

# System Behaviours

* Add a Book
* Update an Existing Book
* Delete a Book
* Add an Author
* Update Author Information
* Delete an Author
* Add a Category
* Update a Category
* Delete a Category
* Delete a User
* Add a Book to Personal Library
* Change Book Status (e.g., "want to read," "currently reading," "finished")
* Remove a Book from Personal Library
* View List of All Books
* View List of All Books in Personal Library
* View List of All Categories in Personal Library
* View List of All Authors in Personal Library
* View List of All Authors
* View List of All Categories
* View List of Books by Specific Category
* View List of Books by Specific Author
* Search for Books by Title
* Leave a Review on a Book
* Delete a Review
* View Details of a Specific Book
* See Reviews for a Specific Book

# Database

[Database](db.jpeg)

# REST API Endpoints

| **REST API Endpoint**                    | **Request Method** | **Description**                               | Query Parameters         |
|------------------------------------------|--------------------|-----------------------------------------------|--------------------------|
| `/api/books`                             | POST               | Add a new book                                |                          |
| `/api/authors`                           | POST               | Add a new author                              |                          |
| `/api/categories`                        | POST               | Add a new category                            |                          |
| `/api/library/books`                     | POST               | Add a book to the user's personal library     |                          |
| `/api/books/{bookId}/reviews`            | POST               | Leave a review on a book                      |                          |
| `/api/books/{bookId}`                    | PUT                | Update an existing book details               |                          |
| `/api/authors/{authorId}`                | PUT                | Update author information                     |                          |
| `/api/categories/{categoryId}`           | PUT                | Update category details                       |                          |
| `/api/library/books/{bookId}`            | PUT                | Change the status of a book                   | `status`                 |
| `/api/books/{bookId}`                    | DELETE             | Delete a book from the library                |                          |
| `/api/authors/{authorId}`                | DELETE             | Delete an author                              |                          |
| `/api/categories/{categoryId}`           | DELETE             | Delete a category                             |                          |
| `/api/users/{userId}`                    | DELETE             | Delete a user                                 |                          |
| `/api/books/{bookId}/reviews/{reviewId}` | DELETE             | Delete a review                               |                          |
| `/api/library/books/{bookId}`            | DELETE             | Remove a book from the user's library         |                          |
| `/api/books`                             | GET                | View a list of books                          | `categoryId`, `authorId` |
| `/api/authors`                           | GET                | View a list of all authors                    |                          |
| `/api/categories`                        | GET                | View a list of all categories                 |                          |
| `/api/library`                           | GET                | View personal library (list of books)         |                          |
| `/api/library/authors`                   | GET                | View a list of authors in personal library    |                          |
| `/api/library/categories`                | GET                | View a list of categories in personal library |                          |
| `/api/books/{bookId}`                    | GET                | View details of a specific book               |                          |
| `/api/books/{bookId}/reviews`            | GET                | See reviews for a specific book               | `bookId`                 |
