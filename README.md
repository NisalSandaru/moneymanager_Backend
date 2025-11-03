# 💰 MoneyMate – Personal Money Manager (Backend)

**Developed by [Nisal Sandaru](mailto:nisalsandaru1@gmail.com)**  
Frontend Repo: [MoneyMate Frontend](https://github.com/NisalSandaru/moneymanager_Frontend)  
Live Frontend: [https://devnisalmoneymate.netlify.app/](https://devnisalmoneymate.netlify.app/)

---

## 🌟 Overview

The **MoneyMate Backend** is a robust **Spring Boot REST API** designed to manage user finances, including **income**, **expenses**, **categories**, **reports**, and **profiles**.  
It also supports **email notifications**, **Excel exports**, **JWT-based authentication**, and **cloud media management**.

---

## ⚙️ Tech Stack

- ☕ **Spring Boot** – Backend framework  
- 🧩 **Spring Data JPA** – ORM and database management  
- 🐘 **PostgreSQL (Neon.tech)** – Cloud database  
- 🐬 **MySQL** – Local testing database  
- 💌 **Brevo (Sendinblue)** – Email service for activation & reports  
- ☁️ **Cloudinary** – Image hosting (profile pictures)  
- 🧾 **Apache POI** – Excel file generation  
- 🐳 **Docker** – Containerized backend deployment  
- 🚆 **Railway** – Backend hosting platform  

---

## 🧠 Key Features

✅ User Registration & Login with JWT Authentication  
✅ Email Activation for New Accounts (via Brevo)  
✅ Manage Income and Expense Records  
✅ Category Management with Type Filtering  
✅ Monthly Dashboard Data Summary  
✅ Generate and Download Excel Reports (Apache POI)  
✅ Email Monthly Reports (Income & Expense)  
✅ Filter Transactions by Date, Keyword, or Type  
✅ Profile Management with Cloudinary Image Uploads  
✅ Health Check API for Deployment Monitoring  

---

## 🧩 API Endpoints Overview

### 🧍‍♂️ Profile & Authentication
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/register` | Register a new profile |
| `GET` | `/activate?token={token}` | Activate profile via email token |
| `POST` | `/login` | User login and get JWT token |
| `GET` | `/profile` | Get current user's public profile |
| `GET` | `/test` | Test API availability |

---

### 💰 Income Management
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/incomes` | Add new income entry |
| `GET` | `/incomes` | Get current month’s incomes |
| `DELETE` | `/incomes/{id}` | Delete income by ID |

---

### 💸 Expense Management
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/expenses` | Add new expense entry |
| `GET` | `/expenses` | Get current month’s expenses |
| `DELETE` | `/expenses/{id}` | Delete expense by ID |

---

### 🏷 Category Management
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/categories` | Create a new category |
| `GET` | `/categories` | Get all categories for current user |
| `GET` | `/categories/{type}` | Get categories by type (income/expense) |
| `PUT` | `/categories/{categoryId}` | Update existing category |

---

### 📊 Dashboard
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `GET` | `/dashboard` | Get dashboard data summary (totals, trends, etc.) |

---

### 🔍 Filter Transactions
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/filter` | Filter income or expense by date range, keyword, and sort order |

---

### 📈 Excel Reports
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `GET` | `/excel/download/income` | Download current month’s income report (Excel) |
| `GET` | `/excel/download/expense` | Download current month’s expense report (Excel) |

---

### 📧 Email Reports
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `POST` | `/email/income-excel` | Email monthly income report |
| `POST` | `/email/expense-excel` | Email monthly expense report |

---

### 🏥 Health Check
| Method | Endpoint | Description |
|---------|-----------|-------------|
| `GET` | `/status` | Check if backend is running |
| `GET` | `/health` | Health check endpoint (for deployment) |

---

## 🗄️ Database Schema (Simplified)

| Table | Description |
|--------|-------------|
| `profile` | Stores user details and authentication data |
| `income` | Income transactions |
| `expense` | Expense transactions |
| `category` | Categories (income/expense types) |
| `email_token` | Activation and verification tokens |

**Database:**  
- Local: MySQL  
- Production: PostgreSQL on [Neon.tech](https://neon.tech)

---

## 🧩 Services Overview

| Service | Purpose |
|----------|----------|
| **ProfileService** | Handles registration, login, activation, and profile retrieval |
| **IncomeService** | Manages all income CRUD operations |
| **ExpenseService** | Handles expense CRUD and filtering |
| **CategoryService** | Manages income/expense categories |
| **DashboardService** | Aggregates income and expense data for visual charts |
| **ExcelService** | Generates Excel reports via Apache POI |
| **EmailService** | Sends activation and report emails through Brevo |
| **CloudinaryService** | Handles image uploads and URLs |

---

## 🧾 Environment Variables












🚆 Deployment Overview
Component	Platform	Description
Backend	Railway	Spring Boot API Hosting
Database	Neon.tech	Cloud PostgreSQL Database
Frontend	Netlify	React + Vite Deployment
Images	Cloudinary	Profile Picture Hosting
Emails	Brevo	Email Notification Service
🧑‍💻 Developer

👨‍💻 Developed by: Nisal Sandaru

📧 Email: nisalsandaru1@gmail.com

🌐 Frontend Live: https://devnisalmoneymate.netlify.app/

💾 Frontend Repository: MoneyMate Frontend

🪪 License

This project is open source and available under the MIT License.
