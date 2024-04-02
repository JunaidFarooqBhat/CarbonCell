# Project Name
# Note: Uses H2 DATABASE(INMEMORY)
## Description

The Project Name is a Spring Boot web application designed to provide essential user authentication and data retrieval functionalities. It offers APIs for user registration, login, logout, and retrieving entries. Additionally, it provides an API to fetch Ethereum wallet balances using wallet addresses. The project uses H2, an in-memory database, to store user data.

# To access this project Use swagger UI
  ![Swagger UI](https://drive.google.com/file/d/1OjEyNYK-arLTKP6sR6vDFzakQMr34qXV/view?usp=sharing)
## Features

- **User Registration**: 
  - Method: POST
  - Endpoint: `/api/register`
  - Parameters:
    - `name` (string, required): Full name of the user.
    - `username` (string, required): Username chosen by the user.
    - `password` (string, required): Password chosen by the user.

- **User Login**:
  - Method: POST
  - Endpoint: `/api/login`
  - Parameters:
    - `username` (string, required): Username of the user.
    - `password` (string, required): Password of the user.

- **User Logout**:
  - Method: POST
  - Endpoint: `/api/logout`
  - Description: Logs out the authenticated user.

- **Get Entries**:
  - Method: GET
  - Endpoint: `/api/entries`
  - Description: Retrieves entries (availabe apis).

- **Get Ethereum Wallet Balance**:
  - Method: GET
  - Endpoint: `/api/ethereumbalance`
  - Parameters:
    - `wallet_address` (string, required): Ethereum wallet address.



## Technologies Used

- Spring Boot
- Java
- H2 Database (In-memory database)
- Public apis API ( for getting the free available apis)
- Ethereum API (for fetching wallet balances)

## Installation

1. Clone the repository.
2. Build the project using Maven or your preferred build tool.
3. Run the application.



