
# 📚 Library API

This is a Spring Boot application for managing a library system. It provides RESTful APIs for managing books, authors, and other library resources.

## 🚀 Features

- **Book Management:** Add, update, delete, and retrieve book information.
- **Author Management:** Add, update, delete, and retrieve author information.
- **Search Functionality:** Search for books by title, author, or genre.
- **User Authentication:** Secure endpoints with authentication and authorization.
- **Email Notifications:** Send email notifications for various events.

## 🛠️ Technologies Used

- **Java:** Main programming language.
- **Spring Boot:** Framework for building the application.
- **Spring Data JPA:** For database interactions.
- **Spring Security:** For securing the application.
- **Spring Mail:** For sending emails.
- **H2 Database:** In-memory database for testing.
- **MySQL:** Main database for the application.
- **Maven:** Build and dependency management tool.

## 📂 Project Structure

- **Java Version:** 23
- **Spring Boot Version:** 3.4.2
- **Main Directory Path:** [library-api/src/main/java/com/example/library_api](https://github.com/21Ravan12/Library-api-spring-boot/tree/main/src/main/java/com/example/library_api)

## 📦 Dependencies

The project uses the following dependencies:
- `spring-boot-starter-data-jpa`: For database-related functionality.
- `spring-boot-starter-web`: For building web applications (REST APIs, etc.).
- `spring-boot-devtools`: For runtime improvements like auto-reloading and better debugging.
- `h2`: In-memory database for testing and development purposes.
- `spring-boot-starter-test`: For unit testing and integration testing support.
- `mysql-connector-java`: For connecting to a MySQL database.
- `spring-boot-starter-security`: For adding authentication and authorization to the application.
- `spring-boot-starter-mail`: For sending emails from the application.

## ⚙️ Configuration

The application uses the following configuration properties:

```ini
spring.application.name=library-api
spring.datasource.url=jdbc:mysql://localhost:3306/library
spring.datasource.username=<DB_USERNAME>
spring.datasource.password=<DB_PASSWORD>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
spring.jpa.hibernate.ddl-auto=update
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<EMAIL_USERNAME>
spring.mail.password=<EMAIL_PASSWORD>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Replace `<DB_USERNAME>`, `<DB_PASSWORD>`, `<EMAIL_USERNAME>`, and `<EMAIL_PASSWORD>` with your database and email credentials.

## 🗄️ Database

The application uses MySQL as the main database. Ensure you have MySQL installed and running on your local machine or server. The database connection properties should be configured in the `application.properties` file as shown above.

- **Database URL:** `jdbc:mysql://localhost:3306/library`
- **Username:** Your MySQL username
- **Password:** Your MySQL password

## 🏗️ Building and Running the Application

You can build and run the application using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

## 📑 API Endpoints

### 🔒 User Endpoints

- **POST /api/users/register:** User registration
- **POST /api/users/verify:** User verification
- **POST /api/users/login:** User login
- **POST /api/users/changeCode:** Send password change code
- **POST /api/users/verifyForChangeCode:** Verify password change code
- **POST /api/users/changeCodeEnd:** Complete password change
- **PUT /api/users/giveTierAndRetract:** Give or retract user tier
- **PUT /api/users/update:** Update user details
- **GET /api/users/search:** Search users
- **DELETE /api/users/delete:** Delete user

### 📚 Book Endpoints

- **POST /api/books/add:** Add a book
- **GET /api/books/getAll:** Get all books
- **GET /api/books/search:** Search books
- **DELETE /api/books/delete:** Delete a book
- **PUT /api/books/update:** Update book details

### 📅 Reservation Endpoints

- **POST /api/reservations/reserve:** Reserve a book
- **GET /api/reservations/search:** Search reservations
- **DELETE /api/reservations/delete:** Delete reservation
- **PUT /api/reservations/update:** Update reservation

### 📖 Loan Endpoints

- **POST /api/loans/borrow:** Borrow a book
- **POST /api/loans/return:** Return a book
- **GET /api/loans/search:** Search loaned books
- **PUT /api/loans/update:** Update loan details

### 💬 Comment Endpoints

- **POST /api/comments/add:** Add a comment
- **DELETE /api/comments/delete:** Delete a comment
- **GET /api/comments/search:** Search comments
- **PUT /api/comments/update:** Update comment

### 🗂️ Category Endpoints

- **POST /api/categories/add:** Add category
- **PUT /api/categories/update:** Update category
- **GET /api/categories/search:** Search categories
- **DELETE /api/categories/delete:** Delete category

## 📜 License

🫠
