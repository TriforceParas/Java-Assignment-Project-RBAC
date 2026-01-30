# RBAC Demo Project

A Role-Based Access Control (RBAC) system built with **Java JSP/Servlets** and **MySQL**. This project demonstrates a secure, modular, and dynamic permission system without using complex frameworks like Spring Security.

## 🚀 Features

-   **Role-Based Access Control**: Dynamic permission management (Read/Write) for different screens/pages.
-   **User Management**: Create, edit, and delete employees.
-   **Role Management**: Create custom roles and assign granular permissions via a UI matrix.
-   **No-JS Architecture**: Core functionality works with pure server-side rendering (JSP), using Bootstrap only for UI components.
-   **Secure Authentication**: BCrypt password hashing and session management.
-   **Database**: MySQL with HikariCP connection pooling.

## 🛠️ Technology Stack

-   **Language**: Java 11+
-   **Web Tech**: JSP, Servlets, JSTL
-   **Database**: MySQL
-   **Build Tool**: Maven
-   **Container**: Apache Tomcat 9
-   **Frontend**: HTML5, CSS3, Bootstrap 5

## 📋 Prerequisites

-   Java Development Kit (JDK) 11 or higher
-   Apache Maven
-   Apache Tomcat 9
-   MySQL Server

## ⚙️ Setup & Installation

1.  **Clone the repository**
    ```bash
    git clone https://github.com/TriforceParas/Java-Assignment-Project-RBAC.git
    cd rbac-demo
    ```

2.  **Configure Database**
    -   Create a database named `rbac_demo` in MySQL.
    -   Edit `src/main/resources/db.properties` to match your MySQL credentials:
        ```properties
        db.url=jdbc:mysql://localhost:3306/rbac_demo?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&createDatabaseIfNotExist=true
        db.username=root
        db.password=your_password
        ```

3.  **Build the Project**
    ```bash
    mvn clean package
    ```

4.  **Deploy to Tomcat**
    -   Copy the generated WAR file to your Tomcat `webapps` folder:
        ```bash
        cp target/rbac-demo.war ~/tomcat9/webapps/
        ```
    -   Start or restart Tomcat.

5.  **Access the Application**
    -   Open your browser and navigate to: `http://localhost:8080/rbac-demo`

