# TaskFlow - Task Management System

<div align="center">
  <img src="./taskflow-project-hahn-software-frontend/public/logo2.png" alt="TaskFlow Logo" width="120" height="120" style="border-radius: 50%;">

  <h3>A Modern Full-Stack Task Management Solution</h3>
  **link of the demo**:
  (https://drive.google.com/file1Us9smQTnV4ih6unTLfTHY-aiVCgOXGcF/view?usp=drive_link)


  [![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
  [![Vue.js](https://img.shields.io/badge/Vue.js-3-green.svg)](https://vuejs.org/)
  [![TypeScript](https://img.shields.io/badge/TypeScript-5.7.2-blue.svg)](https://www.typescriptlang.org/)
  [![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
  [![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
</div>

---

## 📖 About TaskFlow

**TaskFlow** is a comprehensive task management application designed to help teams collaborate effectively on projects. Built with modern technologies, it provides an intuitive interface for managing projects, tracking tasks, and coordinating team efforts.

### The Solution We're Presenting

TaskFlow addresses the common challenges faced by development teams in managing their work:

- **Project Organization**: Create and manage multiple projects with clear ownership and team structure
- **Task Tracking**: Break down projects into manageable tasks with priorities, due dates, and status tracking
- **Team Collaboration**: Add team members with role-based permissions for secure access control
- **Progress Monitoring**: Real-time progress visualization to track project completion
- **Task Assignment**: Granular permission system for assigning work to team members
- **Session Security**: Automatic timeout and secure JWT-based authentication

### Why TaskFlow?

✅ **Enterprise-Grade Security**: JWT authentication, RBAC, and session management
✅ **Modern Architecture**: RESTful API with clean separation of concerns
✅ **Developer-Friendly**: Docker support for easy deployment and development
✅ **Production-Ready**: Comprehensive error handling, validation, and logging
✅ **Scalable Design**: Stateless architecture that scales horizontally

---

## 📋 Table of Contents

- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Quick Start with Docker](#-quick-start-with-docker-recommended)
- [Local Development Setup](#-local-development-setup)
- [Database Setup](#-database-setup)
- [Configuration Guide](#-configuration-guide)
- [Running the Application](#-running-the-application)
- [Features](#-features)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Security](#-security-features)
- [Troubleshooting](#-troubleshooting)

---

## 🛠️ Technology Stack

### Backend

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 17 LTS | Programming language |
| **Spring Boot** | 4.0.0 | Application framework |
| **Spring Data JPA** | 4.0.0 | ORM and database access |
| **Spring Security** | 6.x | Authentication & authorization |
| **MySQL** | 8.0 | Relational database |
| **JWT (jjwt)** | 0.12.3 | Token-based authentication |
| **Maven** | 3.8+ | Build automation |
| **Lombok** | Latest | Boilerplate code reduction |
| **SpringDoc OpenAPI** | 3.0.0 | API documentation |

### Frontend

| Technology | Version | Purpose |
|------------|---------|---------|
| **Vue.js** | 3.x | Progressive JavaScript framework |
| **TypeScript** | 5.7.2 | Type-safe JavaScript |
| **Vite** | 6.0.7 | Build tool and dev server |
| **Pinia** | 2.3.1 | State management |
| **Vue Router** | 4.5.0 | Client-side routing |
| **Axios** | 1.7.9 | HTTP client |
| **Tailwind CSS** | 3.4.17 | Utility-first CSS framework |
| **Lucide Vue** | 0.468.0 | Icon library |

### DevOps & Database

| Technology | Version | Purpose |
|------------|---------|---------|
| **Docker** | 20.10+ | Containerization |
| **Docker Compose** | 2.0+ | Multi-container orchestration |
| **Nginx** | Alpine | Web server for frontend |
| **MySQL** | 8.0 | Database server |

---

## 📦 Prerequisites

### Option 1: Docker (Recommended - Easiest Setup)

**Requirements:**
- [Docker Desktop](https://www.docker.com/products/docker-desktop) (Windows/Mac) or Docker Engine (Linux)
- Docker Compose 2.0+
- Minimum 4GB RAM allocated to Docker
- 5GB free disk space

**That's it!** Docker handles Java, Node.js, MySQL, and all dependencies automatically.

### Option 2: Local Development (Manual Setup)

**Required Software:**

| Software | Version | Download Link |
|----------|---------|---------------|
| Java JDK | 17 or higher | https://adoptium.net/ |
| Node.js | 18.x or 20.x LTS | https://nodejs.org/ |
| MySQL | 8.0+ | https://dev.mysql.com/downloads/ |
| Maven | 3.8+ | https://maven.apache.org/ (optional, wrapper included) |
| Git | Latest | https://git-scm.com/ |

**Verify Installation:**
```bash
java -version    # Should show Java 17+
node --version   # Should show v18.x or v20.x
npm --version    # Should show 9.x or 10.x
mysql --version  # Should show 8.0+
mvn --version    # Should show 3.8+ (optional)
```

---

## 🚀 Quick Start with Docker (Recommended)

This is the **easiest and fastest** way to run TaskFlow!

### Step 1: Clone the Repository

```bash
git clone https://github.com/Salmaa-bou/taskflow_project_hahn_software.git
cd taskflow_project_hahn_software
```

### Step 2: Start All Services

```bash
# Build and start MySQL, Backend, and Frontend
docker-compose up --build

# Or run in background (detached mode)
docker-compose up --build -d
```

**What happens:**
1. ✅ MySQL database starts (port 3307)
2. ✅ Backend builds and starts (port 8080)
3. ✅ Frontend builds and starts (port 80)
4. ✅ Database tables are created automatically

**First build takes 5-10 minutes** (downloads dependencies). Subsequent starts are much faster (~30 seconds).

### Step 3: Monitor Startup Progress

```bash
# Watch all logs
docker-compose logs -f

# Watch specific service
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f mysql
```

**Wait for these messages:**
```
mysql      | ready for connections. Version: '8.0.x'
backend    | Started TaskflowApplication in X.XXX seconds
frontend   | TaskFlow frontend ready on port 80
```

### Step 4: Access the Application

Open your browser:

| Service | URL | Description |
|---------|-----|-------------|
| **Frontend** | http://localhost | Main application |
| **Backend API** | http://localhost:8080 | REST API |
| **API Docs** | http://localhost:8080/swagger-ui/index.html | Interactive API documentation |
| **MySQL** | localhost:3307 | Database (use MySQL Workbench/CLI) |

### Step 5: Create Your First Account

1. Navigate to: http://localhost
2. Click **"Sign up"** button
3. Fill the registration form:
   ```
   Full Name: Your Name
   Email: your@email.com
   Password: YourPassword123 (minimum 6 characters)
   Skills: Java, Vue.js, Docker (optional)
   Role: USER
   ```
4. Click **"Create account"**
5. Login with your credentials
6. Start creating projects and tasks!

### Docker Management Commands

```bash
# View all running containers
docker-compose ps

# Stop all services
docker-compose down

# Stop and remove all data (including database)
docker-compose down -v

# Restart a specific service
docker-compose restart backend

# View logs
docker-compose logs -f backend

# Rebuild specific service without affecting others
docker-compose up --build --no-deps frontend

# Execute commands inside container
docker-compose exec backend sh
docker-compose exec mysql mysql -uroot -proot
```

---

## 💻 Local Development Setup

### Database Setup

#### 1. Install MySQL 8.0

**Windows:**
- Download from: https://dev.mysql.com/downloads/installer/
- Run installer and choose "Developer Default"
- Set root password during installation
- Ensure MySQL service starts automatically

**macOS (Homebrew):**
```bash
brew install mysql@8.0
brew services start mysql@8.0
mysql_secure_installation
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install mysql-server
sudo systemctl start mysql
sudo mysql_secure_installation
```

#### 2. Create Database

```bash
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE taskflow_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Create dedicated user (optional but recommended)
CREATE USER 'taskflow_user'@'localhost' IDENTIFIED BY 'taskflow_pass';
GRANT ALL PRIVILEGES ON taskflow_db.* TO 'taskflow_user'@'localhost';
FLUSH PRIVILEGES;

# Verify database creation
SHOW DATABASES;

# Exit MySQL
EXIT;
```

#### 3. Verify Database Connection

```bash
mysql -u taskflow_user -p taskflow_db
# Enter password: taskflow_pass
# Should connect successfully
```

---

## ⚙️ Configuration Guide

### Backend Configuration

#### Create `application.properties`

Since `application.properties` contains sensitive data and is not in the repository, you need to create it:

```bash
cd taskflow-project-hahn-software-backend/src/main/resources/
touch application.properties
```

#### Configure `application.properties`

Edit the file with the following configuration:

```properties
# ============================================
# SERVER CONFIGURATION
# ============================================
server.port=8080
spring.application.name=TaskFlow

# ============================================
# DATABASE CONFIGURATION
# ============================================
spring.datasource.url=jdbc:mysql://localhost:3306/taskflow_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ============================================
# JPA / HIBERNATE CONFIGURATION
# ============================================
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.jdbc.batch_size=20
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true

# ============================================
# JWT CONFIGURATION
# ============================================
app.jwt.secret=jwYhsVtkHAIZhhPPEMDxaUXyiH+mLOE2/JmIwbXt7zA=
app.jwt.expiration=1800000

# ============================================
# CORS CONFIGURATION
# ============================================
app.cors.allowed-origins=http://localhost:5173,http://localhost:3000,http://localhost
app.cors.allowed-methods=GET,POST,PUT,DELETE,PATCH,OPTIONS
app.cors.allowed-headers=*
app.cors.allow-credentials=true

# ============================================
# LOGGING CONFIGURATION
# ============================================
logging.level.root=INFO
logging.level.ma.ensah.taskflowprojecthahnsoftwarebackend=DEBUG
logging.level.org.springframework.security=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

### 🔐 How to Generate JWT Secret

The JWT secret must be a Base64-encoded string with at least 256 bits (32 bytes) for HS256 algorithm.

#### Method 1: Using OpenSSL (Linux/Mac/Git Bash)

```bash
openssl rand -base64 32
```

Output example:
```
jwYhsVtkHAIZhhPPEMDxaUXyiH+mLOE2/JmIwbXt7zA=
```

#### Method 2: Using Node.js

```bash
node -e "console.log(require('crypto').randomBytes(32).toString('base64'))"
```

#### Method 3: Using Java

```java
import java.security.SecureRandom;
import java.util.Base64;

public class GenerateSecret {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        String secret = Base64.getEncoder().encodeToString(bytes);
        System.out.println("JWT Secret: " + secret);
    }
}
```

#### Method 4: Online Generator

Visit: https://generate-secret.vercel.app/32

**⚠️ Security Note:** Always generate a NEW secret for production! Never use the example secret in production environments.

### Frontend Configuration

The frontend automatically connects to `http://localhost:8080` for the backend API.

If you need to change the backend URL, edit:

**File:** `taskflow-project-hahn-software-frontend/src/api/axios.ts`

```typescript
const api = axios.create({
  baseURL: 'http://localhost:8080/api', // Change this if backend runs on different URL
  headers: {
    'Content-Type': 'application/json',
  },
})
```

---

## 🏃 Running the Application

### Running Backend Locally

```bash
# Navigate to backend directory
cd taskflow-project-hahn-software-backend

# Option 1: Using Maven wrapper (recommended)
./mvnw clean spring-boot:run

# Option 2: Using Maven (if installed globally)
mvn clean spring-boot:run

# Option 3: Build JAR and run
./mvnw clean package
java -jar target/taskflow-project-hahn-software-backend-0.0.1-SNAPSHOT.jar

# Option 4: Run with specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

**Verify Backend is Running:**
```bash
# Health check
curl http://localhost:8080/actuator/health

# Expected response:
{"status":"UP"}
```

**Access API Documentation:**
```
http://localhost:8080/swagger-ui/index.html
```

### Running Frontend Locally

```bash
# Navigate to frontend directory
cd taskflow-project-hahn-software-frontend

# Install dependencies (first time only)
npm install

# Run development server
npm run dev

# The app will be available at:
# http://localhost:5173
```

**Build for Production:**
```bash
# Create optimized production build
npm run build

# Output will be in 'dist/' folder

# Preview production build locally
npm run preview
```

**Type Checking:**
```bash
# Run TypeScript type checking
npm run type-check

# or during development
npm run dev -- --mode development
```

---

## ✨ Features

### Core Functionality

- ✅ **User Authentication**
  - JWT-based authentication with HS256 algorithm
  - 30-minute session timeout with automatic logout
  - Secure password hashing with BCrypt (strength 12)
  - Activity tracking for session management

- ✅ **Project Management**
  - Create, read, update, and delete projects
  - Real-time progress tracking and visualization
  - Project ownership and team management
  - Progress calculation based on task completion

- ✅ **Task Management**
  - Full CRUD operations on tasks
  - Task priorities (LOW, MEDIUM, HIGH, URGENT)
  - Task status workflow (TODO → IN_PROGRESS → IN_REVIEW → DONE)
  - Due date tracking and overdue indicators
  - Task descriptions and metadata

- ✅ **Team Collaboration**
  - Add team members via email invitation
  - Role-based project access (OWNER, ADMIN, MEMBER, VIEWER)
  - Task assignment with granular permissions
  - Member management (add, remove, update roles)

- ✅ **Task Assignment System**
  - Assign multiple users to a single task
  - Three permission levels per task:
    - **READ_ONLY**: View task details only
    - **CAN_UPDATE_STATUS**: Update status and mark complete
    - **CAN_EDIT**: Full edit permissions
  - Audit trail (who assigned, when assigned)
  - Bulk assignment operations

### Advanced Features

- 🔐 **Multi-Level RBAC**
  - System-level roles (USER, ADMIN)
  - Project-level roles (OWNER, ADMIN, MEMBER, VIEWER)
  - Task-level permissions (READ_ONLY, CAN_UPDATE_STATUS, CAN_EDIT)
  - Cascading permission checks

- 🔔 **Real-time Notifications**
  - Toast notifications for all user actions
  - Success, error, warning, and info messages
  - Auto-dismiss with configurable duration
  - Beautiful animated transitions

- ⏱️ **Session Management**
  - Activity tracking on mouse, keyboard, scroll, touch
  - Automatic token refresh on API requests
  - Session timeout warnings
  - Graceful logout with redirect

- 🎨 **Modern UI/UX**
  - Clean, elegant design with Tailwind CSS
  - User avatars with auto-generated initials
  - Gradient color schemes
  - Smooth animations and transitions
  - Responsive design (mobile, tablet, desktop)
  - Dark mode ready (can be enabled)

- 🚀 **Developer Experience**
  - Docker Compose for one-command deployment
  - Hot reload in development
  - TypeScript for type safety
  - Comprehensive error handling
  - API documentation with Swagger/OpenAPI
  - Structured logging

---

## 📚 API Documentation

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/login` | Login and get JWT token | No |
| GET | `/api/auth/me` | Get current user info | Yes |

### Project Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/projects` | Create new project | Yes |
| GET | `/api/projects` | Get user's projects | Yes |
| GET | `/api/projects/{id}` | Get project details | Yes |
| PUT | `/api/projects/{id}` | Update project | Yes (Owner/Admin) |
| DELETE | `/api/projects/{id}` | Delete project | Yes (Owner) |
| GET | `/api/projects/{id}/progress` | Get project progress | Yes |

### Task Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/projects/{projectId}/tasks` | Create task | Yes |
| GET | `/api/projects/{projectId}/tasks` | Get project tasks | Yes |
| GET | `/api/tasks/{id}` | Get task details | Yes |
| PUT | `/api/tasks/{id}` | Update task | Yes |
| DELETE | `/api/tasks/{id}` | Delete task | Yes |
| PATCH | `/api/tasks/{id}/status` | Update status | Yes |
| PATCH | `/api/tasks/{id}/complete` | Mark as complete | Yes |

### Member Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/projects/{projectId}/members` | Add member | Yes (Owner/Admin) |
| GET | `/api/projects/{projectId}/members` | List members | Yes |
| PUT | `/api/projects/{projectId}/members/{userId}` | Update role | Yes (Owner/Admin) |
| DELETE | `/api/projects/{projectId}/members/{userId}` | Remove member | Yes (Owner/Admin) |

### Task Assignment Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/tasks/{taskId}/assign` | Assign users | Yes (Owner/Admin) |
| GET | `/api/tasks/{taskId}/assignments` | Get assignees | Yes |
| DELETE | `/api/tasks/{taskId}/unassign/{userId}` | Unassign user | Yes (Owner/Admin) |
| PUT | `/api/tasks/{taskId}/permissions/{userId}` | Update permission | Yes (Owner/Admin) |

### Interactive API Documentation

**Access Swagger UI**: http://localhost:8080/swagger-ui/index.html

Features:
- Try out API endpoints directly from browser
- View request/response schemas
- Authentication flow testing
- Example requests and responses

---

## 📁 Project Structure

```
taskflow_project_hahn_software/
│
├── taskflow-project-hahn-software-backend/          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/ma/ensah/taskflowprojecthahnsoftwarebackend/
│   │   │   │   ├── config/                          # Configuration classes
│   │   │   │   │   ├── CorsConfig.java              # CORS configuration
│   │   │   │   │   ├── JwtConfig.java               # JWT configuration
│   │   │   │   │   ├── OpenApiConfig.java           # Swagger/OpenAPI config
│   │   │   │   │   └── SecurityConfig.java          # Spring Security config
│   │   │   │   │
│   │   │   │   ├── controller/                      # REST Controllers
│   │   │   │   │   ├── AuthController.java          # Authentication endpoints
│   │   │   │   │   ├── ProjectController.java       # Project CRUD
│   │   │   │   │   ├── ProjectMemberController.java # Member management
│   │   │   │   │   ├── TaskController.java          # Task CRUD
│   │   │   │   │   └── TaskAssignmentController.java # Task assignments
│   │   │   │   │
│   │   │   │   ├── domain/
│   │   │   │   │   ├── entity/                      # JPA Entities
│   │   │   │   │   │   ├── User.java                # User entity
│   │   │   │   │   │   ├── Project.java             # Project entity
│   │   │   │   │   │   ├── Task.java                # Task entity
│   │   │   │   │   │   ├── ProjectMember.java       # Project membership
│   │   │   │   │   │   └── TaskAssignment.java      # Task assignments
│   │   │   │   │   │
│   │   │   │   │   └── enums/                       # Enumerations
│   │   │   │   │       ├── UserRole.java            # USER, ADMIN
│   │   │   │   │       ├── ProjectRole.java         # OWNER, ADMIN, MEMBER, VIEWER
│   │   │   │   │       ├── TaskStatus.java          # TODO, IN_PROGRESS, IN_REVIEW, DONE
│   │   │   │   │       ├── TaskPriority.java        # LOW, MEDIUM, HIGH, URGENT
│   │   │   │   │       └── TaskPermission.java      # READ_ONLY, CAN_UPDATE_STATUS, CAN_EDIT
│   │   │   │   │
│   │   │   │   ├── dto/                             # Data Transfer Objects
│   │   │   │   │   ├── request/                     # Request DTOs
│   │   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   │   ├── RegisterRequest.java
│   │   │   │   │   │   ├── ProjectRequest.java
│   │   │   │   │   │   ├── TaskRequest.java
│   │   │   │   │   │   └── AssignTaskRequest.java
│   │   │   │   │   │
│   │   │   │   │   └── response/                    # Response DTOs
│   │   │   │   │       ├── AuthResponse.java
│   │   │   │   │       ├── UserResponse.java
│   │   │   │   │       ├── ProjectResponse.java
│   │   │   │   │       ├── TaskResponse.java
│   │   │   │   │       └── MemberResponse.java
│   │   │   │   │
│   │   │   │   ├── exception/                       # Exception Handling
│   │   │   │   │   ├── GlobalExceptionHandler.java  # Global exception handler
│   │   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   │   ├── ForbiddenException.java
│   │   │   │   │   └── ValidationException.java
│   │   │   │   │
│   │   │   │   ├── repository/                      # Spring Data JPA Repositories
│   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   ├── ProjectRepository.java
│   │   │   │   │   ├── TaskRepository.java
│   │   │   │   │   ├── ProjectMemberRepository.java
│   │   │   │   │   └── TaskAssignmentRepository.java
│   │   │   │   │
│   │   │   │   ├── security/                        # Security Components
│   │   │   │   │   ├── JwtAuthenticationFilter.java # JWT filter
│   │   │   │   │   ├── JwtTokenProvider.java        # JWT utility
│   │   │   │   │   └── UserDetailsServiceImpl.java  # UserDetails implementation
│   │   │   │   │
│   │   │   │   └── service/                         # Business Logic
│   │   │   │       ├── IAuthService.java            # Auth service interface
│   │   │   │       ├── IProjectService.java
│   │   │   │       ├── ITaskService.java
│   │   │   │       ├── ITaskAssignmentService.java
│   │   │   │       └── impl/                        # Service implementations
│   │   │   │           ├── AuthServiceImpl.java
│   │   │   │           ├── ProjectServiceImpl.java
│   │   │   │           ├── TaskServiceImpl.java
│   │   │   │           ├── ProjectMemberServiceImpl.java
│   │   │   │           └── TaskAssignmentServiceImpl.java
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.properties           # App configuration (create this!)
│   │   │       ├── static/                          # Static resources
│   │   │       └── templates/                       # Email templates
│   │   │
│   │   └── test/                                    # Unit & Integration tests
│   │
│   ├── Dockerfile                                   # Backend Docker image
│   ├── pom.xml                                      # Maven dependencies
│   └── mvnw                                         # Maven wrapper
│
├── taskflow-project-hahn-software-frontend/         # Vue.js Frontend
│   ├── public/
│   │   └── logo2.png                                # Application logo
│   │
│   ├── src/
│   │   ├── api/                                     # API Client
│   │   │   ├── axios.ts                             # Axios configuration
│   │   │   ├── auth.ts                              # Auth API calls
│   │   │   ├── projects.ts                          # Project API calls
│   │   │   ├── tasks.ts                             # Task API calls
│   │   │   └── members.ts                           # Member API calls
│   │   │
│   │   ├── components/                              # Reusable Components
│   │   │   └── ToastNotification.vue                # Toast notification component
│   │   │
│   │   ├── router/                                  # Vue Router
│   │   │   └── index.ts                             # Route definitions
│   │   │
│   │   ├── stores/                                  # Pinia State Management
│   │   │   ├── auth.ts                              # Auth store (user, token, session)
│   │   │   ├── projects.ts                          # Projects store
│   │   │   ├── tasks.ts                             # Tasks store
│   │   │   ├── members.ts                           # Members store
│   │   │   └── notifications.ts                     # Notifications store
│   │   │
│   │   ├── types/                                   # TypeScript Interfaces
│   │   │   └── index.ts                             # Type definitions
│   │   │
│   │   ├── views/                                   # Page Components
│   │   │   ├── LoginView.vue                        # Login page
│   │   │   ├── RegisterView.vue                     # Registration page
│   │   │   ├── ProjectsView.vue                     # Projects list page
│   │   │   └── ProjectDetailView.vue                # Project detail & tasks page
│   │   │
│   │   ├── App.vue                                  # Root component
│   │   ├── main.ts                                  # Application entry point
│   │   └── style.css                                # Global styles
│   │
│   ├── Dockerfile                                   # Frontend Docker image
│   ├── nginx.conf                                   # Nginx configuration
│   ├── package.json                                 # NPM dependencies
│   ├── tailwind.config.js                           # Tailwind CSS config
│   ├── tsconfig.json                                # TypeScript config
│   ├── vite.config.ts                               # Vite build config
│   └── index.html                                   # HTML template
│
├── .gitignore                                       # Git ignore rules
├── Docker-compose.yml                               # Docker Compose configuration
└── README.md                                        # This file
```

---

## 🔐 Security Features

### Authentication & Authorization

- **JWT-based Authentication**
  - HS256 signing algorithm
  - 30-minute token expiration (configurable)
  - Secure token storage in localStorage
  - Automatic token refresh on API requests

- **Password Security**
  - BCrypt hashing with strength 12
  - Minimum password length: 6 characters
  - Password validation on both frontend and backend

- **Session Management**
  - Activity tracking (mouse, keyboard, scroll, touch events)
  - Automatic logout after 30 minutes of inactivity
  - Last activity timestamp stored in localStorage
  - Session timeout warnings

### Role-Based Access Control (RBAC)

#### System Roles
- **ADMIN**: System administrator with full access
- **USER**: Regular user (default role)

#### Project Roles
- **OWNER**: Full control (delete project, manage all members)
- **ADMIN**: Can manage project, tasks, and members
- **MEMBER**: Can create and manage assigned tasks
- **VIEWER**: Read-only access

#### Task Permissions
- **READ_ONLY**: View task details
- **CAN_UPDATE_STATUS**: Update status and mark complete
- **CAN_EDIT**: Full edit permissions (title, description, dates, etc.)

### API Security

- **Protected Endpoints**: All endpoints except `/api/auth/**` require JWT
- **CORS Configuration**: Configurable allowed origins
- **Input Validation**: Bean Validation on all DTOs
- **SQL Injection Protection**: Parameterized queries via JPA
- **XSS Protection**: Content Security Policy headers

---

## 🔧 Troubleshooting

### Common Issues

#### Port Already in Use

**Problem**: `Port 80/8080/3307 is already in use`

**Solution**:
```bash
# Windows - Find process using port
netstat -ano | findstr :80
netstat -ano | findstr :8080

# Kill process by PID
taskkill /PID <PID> /F

# Linux/Mac - Find and kill process
lsof -ti:80 | xargs kill -9
lsof -ti:8080 | xargs kill -9

# Alternative: Change ports in docker-compose.yml
```

#### Database Connection Failed

**Problem**: `Could not connect to MySQL database`

**Solution**:
```bash
# Check MySQL is running
docker-compose ps
# or
sudo systemctl status mysql

# Check credentials in application.properties
# Verify database exists
mysql -u root -p -e "SHOW DATABASES;"

# Check MySQL logs
docker-compose logs mysql
```

#### JWT Token Invalid

**Problem**: `401 Unauthorized` errors

**Solution**:
```bash
# Clear browser localStorage
# In browser console:
localStorage.clear()

# Verify JWT secret matches in application.properties
# Regenerate JWT secret if needed:
openssl rand -base64 32

# Restart backend after changing secret
```

#### Frontend Build Fails

**Problem**: TypeScript errors or build failures

**Solution**:
```bash
# Clear node_modules and reinstall
rm -rf node_modules package-lock.json
npm install

# Clear Vite cache
rm -rf node_modules/.vite

# Update dependencies
npm update

# Check Node.js version
node --version  # Should be 18.x or 20.x
```

#### CORS Errors

**Problem**: `Access-Control-Allow-Origin` errors in browser console

**Solution**:
```properties
# Update application.properties
app.cors.allowed-origins=http://localhost:5173,http://localhost

# Restart backend
./mvnw spring-boot:run
```

---

## 📊 Environment Variables

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `MYSQL_ROOT_PASSWORD` | MySQL root password | root | Yes (Docker) |
| `MYSQL_DATABASE` | Database name | taskflow_db | Yes |
| `MYSQL_USER` | MySQL user | taskflow_user | Yes (Docker) |
| `MYSQL_PASSWORD` | MySQL password | taskflow_pass | Yes (Docker) |
| `MYSQL_DOCKER_PORT` | MySQL host port | 3307 | No |
| `APP_JWT_SECRET` | JWT signing key (Base64, 32+ bytes) | (generated) | Yes |
| `APP_JWT_EXPIRATION` | Token expiration in milliseconds | 1800000 (30 min) | Yes |
| `APP_CORS_ALLOWED_ORIGINS` | Allowed CORS origins | http://localhost:5173 | Yes |
| `APP_CORS_ALLOWED_METHODS` | Allowed HTTP methods | GET,POST,PUT,DELETE,PATCH,OPTIONS | Yes |

---

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## 📄 License

This project is created for educational purposes as part of the **Hahn Software Internship Program** technical assessment.

---

## 🙏 Acknowledgments

- **Hahn Software** for the internship opportunity
- **Spring Boot** team for the excellent framework
- **Vue.js** team for the reactive framework
- **Tailwind CSS** for the utility-first CSS framework
- **Docker** for simplifying deployment
- All open-source contributors

---

## 📧 Contact & Support

**Developed by**: Salma Boukhari
**GitHub**: https://github.com/Salmaa-bou/taskflow_project_hahn_software
**For questions**: Open an issue in the repository

---

<div align="center">
  <p><strong>Built with ❤️ for Hahn Software Internship Program</strong></p>
  <p>
    <a href="https://github.com/Salmaa-bou/taskflow_project_hahn_software">⭐ Star this repo</a> •
    <a href="https://github.com/Salmaa-bou/taskflow_project_hahn_software/issues">🐛 Report Bug</a> •
    <a href="https://github.com/Salmaa-bou/taskflow_project_hahn_software/issues">💡 Request Feature</a>
  </p>
</div>

---

**Last Updated**: December 2025
