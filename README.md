CharityApp

This project aims to provide a comprehensive and user-friendly platform for individuals to donate their unused but good-quality items to those in need. 
The goal is to simplify the process of donating and ensure that the donated items reach the intended recipients in a trustworthy manner. 
The advanced version of the project expands on the basic features and introduces additional functionality.


Project Features

Administrator Profile:

    Authentication and authorization mechanisms to secure the administrator's access
    Dashboard providing statistical insights and data visualization on donations and user activities
    Grant management system to handle donations and funding from various sources
    Integration with external services for automating administrative tasks, such as email notifications

Trusted Institutions:

    Detailed profiles for trusted institutions, including their mission, location, and contact information
    Verification process to ensure the credibility and authenticity of trusted institutions
    Integration with mapping services to display trusted institutions on a map for easier discovery

User Profile:

    Authentication and authorization mechanisms to secure user accounts
    User dashboard displaying personalized information, such as donated items, their status, and donation history

Donation Workflow:

    Streamlined donation process, allowing users to easily upload item details, including descriptions

Management:

    Inventory management system for administrators and trusted institutions to keep track of available items and their status
    
    
Installation

Clone the repository:

    git clone https://github.com/KraxPL/CharityApp.git

Create a MySQL database named VetClinic:

    CREATE DATABASE charity_donation CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

Configure the database connection in application.yaml:

    spring.datasource.url=jdbc:mysql://localhost:3306/charity_donation?serverTimezone=UTC
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword

Build and run the application:

    mvn spring-boot:run

Open http://localhost:8080 in your web browser.

To log in to the CharityApp web application, you can go to the following address: http://localhost:8080/login.

To log in as an admin, use the following credentials:

    Email: admin@vetclinic.pl
    Password: password

To log in as a regular user, use the following credentials:

    Email: user@vetclinic.pl
    Password: password


Technologies Used:

    Java
    Spring Boot
    Spring MVC
    Spring Data
    Spring Security
    MySQL
    Hibernate
    Maven
    Lombok
    Thymeleaf
    MapStruct
    JavaScript
