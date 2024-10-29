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
* As a user, I want to be able to leave a rating and review on a book.
* As a user, I want to be able to delete my review or rating.
* As a user, I want to be able to view details of a specific book.
* As a user, I want to be able to see reviews for a specific book.

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

| **REST API Endpoint**              | **Request Method** | **Description**                           | Request Parameters or Path Variables                      |
|------------------------------------|--------------------|-------------------------------------------|-----------------------------------------------------------|
| `/api/books`                       | POST               | Add a new book                            |                                                           |
| `/api/authors`                     | POST               | Add a new author                          |                                                           |
| `/api/categories`                  | POST               | Add a new category                        |                                                           |
| `/api/library`                     | POST               | Add a book to the user's personal library |                                                           |
| `/api/reviews/books/{bookId}`      | POST               | Leave a review on a book                  | `bookId`                                                  |
| `/api/books/{bookId}`              | PUT                | Update an existing book details           | `bookId`                                                  |
| `/api/authors/{authorId}`          | PUT                | Update author information                 | `authorId`                                                |
| `/api/categories/{categoryId}`     | PUT                | Update category details                   | `categoryId`                                              |
| `/api/library/books/{bookId}`      | PUT                | Change the status of a book               | `bookId`, `status` (request parameter)                    |
| `/api/books/{bookId}`              | DELETE             | Delete a book from the library            | `bookId`                                                  |
| `/api/authors/{authorId}`          | DELETE             | Delete an author                          | `authorId`                                                |
| `/api/categories/{categoryId}`     | DELETE             | Delete a category                         | `categoryId`                                              |
| `/api/users/{userId}`              | DELETE             | Delete a user                             | `userId`                                                  |
| `/api/library/{bookId}`            | DELETE             | Remove a book from the user's library     | `bookId`                                                  |
| `/api/reviews/{reviewId}`          | DELETE             | Delete a review                           | `reviewId`                                                |
| `/api/books`                       | GET                | View a list of all books                  | `title` (request parameter)                               |
| `/api/authors`                     | GET                | View a list of all authors                |                                                           |
| `/api/categories`                  | GET                | View a list of all categories             |                                                           |
| `/api/books/category/{categoryId}` | GET                | View all books by specific category       | `categoryId`                                              |
| `/api/books/author/{authorId}`     | GET                | View all books by specific author         | `authorId`                                                |
| `/api/books/{bookId}`              | GET                | View details of a specific book           | `bookId`                                                  |
| `/api/reviews/books/{bookId}`      | GET                | See reviews for a specific book           | `bookId`                                                  |
