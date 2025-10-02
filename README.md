#  IssueTracker

A **Spring Boot based Issue Tracking System** that provides secure and scalable management of projects, issues, and users.  
Includes **JWT authentication with refresh tokens**, **role-based access control (RBAC)**, **pagination & filtering**, and **auditing features** for professional-grade backend applications.  


##  Features
- **Authentication & Authorization**
  - Login with email & password  
  - JWT Access + Refresh token flow  
  - HttpOnly Secure Cookies for refresh tokens  
  - CSRF protection on refresh/logout  
  - Role-based permissions (User, Admin)  

- **Issue Management**
  - Create, update, delete issues  
  - Pagination, sorting, and filtering with `Specification` + `Pageable`  

- **User & Role Management**
  - Manage users with roles  
  - Role-based access control on endpoints  

- **Auditing & Logging**
  - Entity auditing with timestamps  
  - Error & exception handling layer
 


##  Tech Stack
- **Java 17**  
- **Spring Boot 3**  
  - Spring Security 6 (JWT + FilterChain)  
  - Spring Data JPA (Hibernate)  
  - Validation (`jakarta.validation`)  
- **Database:** PostgreSQL (or H2 for testing)  
- **Build Tool:** Maven  
- **Containerization:** Docker & Docker Compose  
- **Mapping:** MapStruct (DTO ↔ Entity)  
- **Other:** Lombok, SLF4J logging


##  Security Features
- JWT Access (header) & Refresh (HttpOnly cookie)  
- Token rotation & revocation (jti)  
- JwtFilter + CookieHelper + SecurityFilterChain  
- CSRF on refresh/logout (`XSRF-TOKEN`)  
- Role-based access (User, Admin)  


