# 📝 Blog App Backend (Spring Boot)

This is the backend API for a Blog Application, built with **Spring Boot**, **PostgreSQL**, **JWT Authentication**, and deployed on **Railway**. It supports user login, post and category management, and tag features with secure endpoints.

> 🔗 Deployed at: [https://blog-app-backend.up.railway.app](https://blog-app-backend.up.railway.app)

---

## 🚀 Features

- User login with **JWT authentication**
- Create, update, delete blog posts (secured)
- Public access to all published posts, categories, and tags
- Drafts and post management (authenticated)
- Admin-style **category and tag management**
- Integrated with **PostgreSQL** using Spring Data JPA
- Stateless security with **JWT filter**

---

## 📦 Tech Stack

- Java 21, Spring Boot 3
- Spring Security, JWT
- Spring Data JPA + PostgreSQL
- Maven
- Docker & Railway (for deployment)

---
---

## 📌 To-Do

- Add multiple user registration & role-based access.


## 📂 API Endpoints

### ✅ Public

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/auth/login` | Login and get JWT token |
| `GET`  | `/api/v1/posts` | Get all published posts |
| `GET`  | `/api/v1/posts/{id}` | Get post by ID |
| `GET`  | `/api/v1/categories` | List all categories |
| `GET`  | `/api/v1/tags` | List all tags |

---

### 🔒 Authenticated (Need JWT)

Use the **token from `/login`** in the headers:

#### 📁 Posts

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/posts` | Create a new post |
| `PUT`  | `/api/v1/posts/{id}` | Update post |
| `DELETE` | `/api/v1/posts/{id}` | Delete post |
| `GET`  | `/api/v1/posts/drafts` | Get all draft posts |

#### 🗂️ Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/categories` | Add new category |
| `DELETE` | `/api/v1/categories/{id}` | Delete a category |

#### 🏷️ Tags

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/v1/tags` | Add new tag |
| `DELETE` | `/api/v1/tags/{id}` | Delete a tag |

---

## 📌 Example Request Body

### 🔹 Create Category

```json
{
  "name": "Technology"
}
