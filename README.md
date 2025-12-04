# Online Food Store – Spring Boot

A web application developed with Java and Spring Boot for managing an online food store. It includes product listing, administration, shopping cart, multi-language support, and user geolocation.

## Features

### Product Listing
- Display all available products.
- Search and filter by categories.
- Product detail pages.

### Shopping Cart
- Add, modify, and remove products from the cart.
- Automatic calculation of totals.
- Temporary cart persistence.

### Administration
- CRUD for products and categories.
- User and role management.
- Admin dashboard for authorized users.

### Multi-Language Support
- Internationalization (i18n) using Spring's `MessageSource`.
- Language selection via parameter, cookie, or configuration files.
- Includes message files like `messages_en.properties` and `messages_es.properties`.

### Geolocation
- Approximate user location detection via IP or browser API.
- Content adjustments based on location (e.g., language or currency).
- Optional integration with external geolocation services.

## Technologies Used
- Java 17 or higher
- Spring Boot
  - Spring Web
  - Spring MVC
  - Spring Security 
  - Internationalization (i18n)
- Maven
- Database (H2 for development)
- Frontend: Thymeleaf 
- HTML, CSS, JavaScript

## Requirements
- Java 17+
- Maven 3.8+
- Database configured in `application.properties









mvn clean install

