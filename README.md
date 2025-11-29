# AirBnbCloneMonolith
# 🏠 Airbnb Clone – Monolithic Architecture (Spring Boot + Angular)

A full-stack **Airbnb Clone** built with **Angular (frontend)** and **Spring Boot (backend)** following a clean and scalable **monolithic architecture**.  
This project provides listing management, search, filtering, booking features, and a modern front-end UI that resembles Airbnb.

---
###Architecture Diagram 

<img width="640" height="581" alt="image" src="https://github.com/user-attachments/assets/91f96d59-7e23-42cf-b457-750392b5953c" />


## 🚀 Tech Stack

### **Frontend**
- Angular 12
- TypeScript
- RxJS & Observables
- Angular Services / Components Architecture

### **Backend**
- Spring Boot 3+
- Spring Data JPA
- PostgreSQL
- RESTful APIs
- Model–Repository–Service–Controller Architecture

### **Other Tools**
- Maven  
- Git & GitHub  
- Postman for API Testing  

---

# 🏗️ Architecture Overview

This project uses a **monolithic, layered architecture**.


### **Key Features in Architecture**
✔ Modular Angular components  
✔ Reusable services for API communication  
✔ `@RestController` endpoints  
✔ Service layer for real business logic  
✔ JPA repositories for DB operations  
✔ PostgreSQL as persistent storage  

---

# ✨ Features

### **Listings**
- Create a new listing  
- Fetch all listings  
- Search listings by address  
- Filter by availability / price  

### **Search**
- Realtime search using query params  
- Search by partial address (Case-insensitive)

### **Booking (Optional Module Being Added)**
- Book a listing  
- Manage reservations  
- Check availability  

### **Frontend**
- Airbnb-like UI  
- Proper separation of components  
- Search bar with dynamic filtering  
- Listing cards with images & details  

---

# 📂 Folder Structure

### **Backend (Spring Boot)**

### **Frontend (Angular)**


---

# 🛠️ Installation & Setup

Follow these steps to run it **locally** on your system.

---

# 🖥️ 1. Clone the Repository

```sh
git clone https://github.com/rithSubhro/AirBnbCloneMonolith.git
cd AirBnbCloneMonolith

cd backend
mvn clean install
spring.datasource.url=jdbc:postgresql://localhost:5432/airbnb
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
cd frontend
npm install
GET    /listings                → Get all listings
GET    /listings/search?query   → Search listings by address
POST   /listings                → Create listing

