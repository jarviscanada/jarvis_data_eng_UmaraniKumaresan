# Stock Quote Application
## Overview
The Stock Quote Application is designed to provide real-time stock information and portfolio management. Users can view details of any stock listed on exchanges by entering a valid stock symbol. The application fetches real-time stock data using the Alpha Vantage API and displays the current stock position, including the number of shares and total price.
The application also allows users to:
##### Buy Stocks:
Enter the stock symbol and the number of shares to purchase. The transaction is recorded in the portfolio and stored in the database.
##### Sell Stocks:
Remove stocks from the portfolio by deleting the corresponding entries from the database.
##### View Portfolio:
Display the entire portfolio and its details.

### Database Setup
There are two tables created for this application to store stock details of the user.
1. `quote`: This table contains stock information including the price, high, low for the day and other stock details.
2. `position`: This table contains current stock portfolio.

### Configuration File
##### Database Configuration
1. `db.url`: JDBC URL for PostgreSQL.
2. `db.username`: Database username.
3. `db.password`: Database password.
##### API Configuration
1. `api.url`: Endpoint for fetching stock data.
2. `api.key`: API key for Alpha Vantage.
3. `api.host`: API host name.
## Quick Start
### Docker
1. Download the Docker Image from [here](https://hub.docker.com/repository/docker/umarani100/stockquote/general).
2. Once the image has been downloaded, container can run instantly.
3. To create and run the container, run the following command<br>
   `docker run --rm -i --name stockquote umarani100/stockquote`<br>

## Implementation
### ER Diagram
![img.png](assets/01_ER_Diagram.png)

### Application Design
![img.png](assets/02_application_diagram.png)

### Design Patterns
MVC design pattern was adapted in this application.

#### DAO (Data Access Object)
The DAO design pattern was followed tp access the database from the application.
Inside the project, it is under `ca.jrvs.apps.stockquote.DAO` package.

#### Services
The Service contains the application business logic and calling DAO classes.
Inside the project, it is under `ca.jrvs.apps.stockquote.service` package.

#### DTO (Data Transfer Object)
The DTO layer comprises getter and setter methods and uses JSON properties to map to the respective Java fields.
Inside the project, it is under `ca.jrvs.apps.stockquote.DTO` package.

#### Controller
This package contains classes that provide functionalities for the command-line interface (using Scanner).
Inside the project, it is under `ca.jrvs.apps.stockquote.controller` package

## Testing
- Comprehensive testing was conducted for each module to ensure the application's robustness.
- Unit and integration tests were developed using JUnit 5 and Mockito, located in the test folder.

## Deployment
- The application was deployed using Docker and GitHub.
- Containerization was implemented to package all necessary dependencies into a container, ensuring efficient cross-platform usability.
- GitHub, combined with the GitFlow branching strategy, was utilized to maintain consistent and structured code delivery.

## Future Enhancements
The project can be further enhanced with the following improvements:
1. `Connection Pooling`: Connection pooling using HikariCP, which caches database connections to improve application performance.
2. `Spring Boot Integration`: Refactoring the project into a Spring Boot application.
3. `REST API Development`: Replacing the command-line interface with a Spring Boot REST Controller for better scalability and usability.
4. `Enhanced Features`: Adding functionalities such as user registration, viewing user profiles, and managing stock portfolios, enabling users to buy and sell stocks linked to their accounts.





