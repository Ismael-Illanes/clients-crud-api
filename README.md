# 🛠️ Client CRUD API - Spring Boot

## 📌 Overview
This is a simple **CRUD API** for managing clients, built with **Spring Boot** and using **Spring Data JPA** for database interaction. It provides RESTful endpoints to **Create, Read, Update, and Delete** client records.

## 🚀 Features
- ✅ Create new clients
- 🔍 Retrieve clients by ID
- 📜 List all clients
- 📝 Update client details
- ❌ Delete clients
- 📅 Automatically manage timestamps (created_at & updated_at)
- 💼 **Wallet Management**: Each client has a wallet to track expenses, recharges, and total expenses.

## 🏗️ Technologies Used
- 🌱 **Spring Boot** - Java framework for building applications
- 🛢️ **Spring Data JPA** - ORM for database interaction
- 🐘 **PostgreSQL / NeonDB** - Database for storing client information
- 🌐 **Spring Web** - REST API support
- 🛡️ **Spring Security (optional)** - Authentication and security
- 🛠️ **Lombok** - Reduces boilerplate code

## 📖 Endpoints
| Method | Endpoint | Description |
|--------|-------------|-------------|
| 🆕 **POST** | `/api/clients/insert` | Create a new client |
| 📖 **GET** | `/api/clients/find` | Retrieve a client by ID |
| 📃 **GET** | `/api/clients/findAll` | List all clients |
| ✏️ **PUT** | `/api/clients/update` | Update a client |
| ❌ **DELETE** | `/api/clients/delete` | Delete a client |
| 💳 **GET** | `/api/clients/balance` | Get client balance |
| 💵 **POST** | `/api/clients/recharge` | Recharge client balance |
| 💸 **POST** | `/api/clients/spend` | Record client expense |

## ⚙️ Installation & Usage
### 🔧 Prerequisites
- **Java 17+**
- **Maven**
- **PostgreSQL (or any SQL DB configured in `application.properties`)**

### 🚀 Running the Project
```sh
# Clone the repository
git clone https://github.com/Ismael-Illanes/clientscrud.git
cd client-crud-api

# Build and run the application
mvn spring-boot\:run

```

### 🔌 API Testing
Use **Postman**, **cURL**, or any REST client to interact with the API.
```
INSERT
{
    "name": "TestGuy",
    "email": "test@gmail.com",
    "phone": "0000000",
    "address": "Dummy Street"
}
```

## 🏆 Contribution
Feel free to fork the repo, submit issues, or contribute by creating pull requests!

## 📜 License
This project is licensed under the **MIT License**.

---
🔗 **Developed by: Ismael-Illanes | 🌍 https://github.com/Ismael-Illanes | ✉️ ismael@villasana.dev

