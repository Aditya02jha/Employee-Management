# 🏢 Employee Management System

A full-stack **Employee Management System** built with **Spring Boot (Backend)** and **React (Frontend)**. This project allows users to **manage employees**, add addresses, assign managers, and filter employees by **country & department**.

---

## 🚀 Features

✅ **Manage Employees** – Add, Edit, Delete Employees  
✅ **Filter Employees** – Search by Country & Department  
✅ **Assign Managers** – Set up reporting hierarchy  
✅ **Manage Departments** – Assign employees to multiple departments  
✅ **Address Management** – Add multiple addresses per employee  
✅ **Modern UI** – Dark-themed, professional design  

---

## 🛠️ Tech Stack

### **Backend (Spring Boot)**
- Java 17
- Spring Boot
- Spring Data JPA
- MySQL Database
- Hibernate ORM
- Lombok
- Swagger (for API Docs)

### **Frontend (React)**
- React 18 + Vite
- TailwindCSS (Dark Mode)
- React Router
- Axios (API Calls)
- Context API (State Management)

---

## 💂️ Installation & Setup

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/YOUR_GITHUB_USERNAME/Employee-Management.git
cd Employee-Management
```

### **2️⃣ Backend (Spring Boot) Setup**
#### ✅ **Step 1: Configure Database**
Update `application.properties` in `backend/src/main/resources/`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

#### ✅ **Step 2: Build & Run Backend**
```sh
cd backend
mvn clean install
mvn spring-boot:run
```
Backend will start at: **`http://localhost:8080`** 🎡  

---

### **3️⃣ Frontend (React) Setup**
#### ✅ **Step 1: Install Dependencies**
```sh
cd frontend
npm install
```

#### ✅ **Step 2: Configure Environment Variables**
Create a `.env` file in the `frontend/` directory:
```sh
VITE_API_URL=http://localhost:8080
```

#### ✅ **Step 3: Run Frontend**
```sh
npm run dev
```
Frontend will start at: **`http://localhost:5173`** 🎡  

---

## 🔧 API Endpoints

| Method | Endpoint                     | Description                     |
|--------|------------------------------|---------------------------------|
| GET    | `/api/employee/employees`    | Get all employees              |
| GET    | `/api/employee/{id}`         | Get employee details by ID     |
| POST   | `/api/admin/addEmployee`     | Create a new employee          |
| PUT    | `/api/employee/{id}`         | Update an employee             |
| DELETE | `/api/employee/{id}`         | Delete an employee             |
| GET    | `/api/countries`             | Get list of countries          |
| GET    | `/api/departments`           | Get list of departments        |

---

## 🛠️ Contributing

1. **Fork the repo**
2. **Create a new branch** (`feature-xyz`)
3. **Commit changes**
4. **Push & Create Pull Request** ✅  

---

## 🐟 License

**MIT License** - Free to use & modify 🚀  

---

## 📈 Contact

📧 **Email:** aditya0000jha@gmail.com  
🔗 **GitHub:** [YourGitHub](https://github.com/YOUR_GITHUB_USERNAME)  

---

