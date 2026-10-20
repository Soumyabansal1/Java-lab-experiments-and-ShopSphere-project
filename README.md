# Java Lab Experiments and ShopSphere Project

Lab experiments and the final project for the **Advanced Java** course, B.Tech Computer Science, Rajasthan Technical University (RTU), Kota.

**Author:** Soumya - B.Tech CSE (3rd year), Global Institute of Technology, Jaipur

The repository covers all six syllabus units: Swing and MVC, JDBC and sockets, RMI, J2EE web applications, Servlets and JSP, plus a complete Java project, **ShopSphere**.

---

## Repository Structure

```
Java-lab-experiments-and-ShopSphere-project/
├── Calculator/                    Swing calculator using MVC
├── student(JDBC)/                 Student registration form with MySQL (JDBC)
├── Experiment-5 server client/    TCP client and server
├── Experiment -3 (RMI )/          RMI calculator
├── servett project 2/             Servlets, sessions, filter, listener
├── Experiment -7 (JSP)/           JSP with servlet (MVC Model 2)
├── shopsphere_project/            ShopSphere e-commerce project
└── README.md
```

---

## Syllabus Coverage

| Unit | RTU Syllabus Topics | Folder | Where It Is Shown |
|------|---------------------|--------|-------------------|
| 1 | Swing, MVC architecture, basic Swing components | `Calculator`, `student(JDBC)` | MVC calculator with `JTextField`, `JButton`, `ActionListener`. Registration form with `JTextField`, `JRadioButton` + `ButtonGroup`, `JCheckBox`, `JButton` |
| 2 | Java database programming, `java.sql`, JDBC driver, `java.net`, client and server programs | `student(JDBC)`, `Experiment-5 server client` | `PreparedStatement` INSERT into MySQL. TCP `ServerSocket` and `Socket` on port 5000 |
| 3 | RMI architecture, RMI registry, distributed applications, naming, serialization | `Experiment -3 (RMI )` | Remote interface, `UnicastRemoteObject`, `Naming.rebind()` and `Naming.lookup()`. Serialization is also used in ShopSphere (`.ser` files) |
| 4 | J2EE architecture, HTTP, web application, web container | `servett project 2`, `Experiment -7 (JSP)` | Web applications packaged as WAR files and run on Apache Tomcat 10 |
| 5 | Servlets, life cycle, config and context, request and response, sessions, events, filters | `servett project 2` | `HelloServlet`, `LoginServlet`, `HomeServlet`, `HttpSession` login, `ServletContext` attributes, `AuthFilter`, `AppListener` |
| 6 | JSP architecture, JSP elements, Expression Language | `Experiment -7 (JSP)` | `student.jsp` form, `HelloServlet` controller, `result.jsp` using EL (`${name}`) |
| Project | Complete Java project | `shopsphere_project` | ShopSphere e-commerce system |

---

## Requirements

| Tool | Version / Note |
|------|----------------|
| JDK | 21 (the Maven projects are set to Java 21) |
| Maven | 3.x (for `servett project 2` and `Experiment -7 (JSP)`) |
| MySQL | 8.x (for `student(JDBC)`; optional for ShopSphere) |
| MySQL Connector/J | included in `student(JDBC)/Regisration  form/lib` |
| Apache Tomcat | 10.1 or newer (Jakarta namespace). Not needed for `servett project 2`, which embeds Tomcat |
| Editor | VS Code with Java Extension Pack, or IntelliJ IDEA |

---

## Experiments

### Calculator - Swing Calculator using MVC
A two-number calculator with Add, Subtract, Multiply and Divide.

| Part | File | Role |
|------|------|------|
| Model | `Models.java` | The arithmetic. Divide throws `ArithmeticException` for division by zero |
| View | `Views.java` | The `JFrame` window with text fields and buttons |
| Controller | `Controller.java` | `ActionListener` that reads the inputs, calls the model, and shows the result or an error ("Invalid Input") |
| Entry point | `Cmain.java` | Creates the view, model and controller |

```bash
cd Calculator
javac *.java
java Cmain
```

### student(JDBC) - Student Registration Form with JDBC
A Swing form (name, roll number, gender, branch, terms checkbox) that validates the input and saves it to MySQL using a `PreparedStatement`. The project is in the `Regisration  form` folder.

1. Create the database and table (example):
   ```sql
   CREATE DATABASE java;
   USE java;
   CREATE TABLE student (
       roll_number INT PRIMARY KEY,
       name        VARCHAR(100),
       gender      VARCHAR(10),
       branch      VARCHAR(50)
   );
   ```
2. Set your own MySQL username and password in `src/DBConnectionStudent.java`.
3. Compile and run from the `src` folder (Windows):
   ```bash
   javac -cp ".;..\lib\mysql-connector-j-26.7.0.jar" *.java
   java  -cp ".;..\lib\mysql-connector-j-26.7.0.jar" StudentRegistration
   ```
   On Linux or macOS use `:` instead of `;` in the classpath.

`App.java` in the same folder is a small connection test.

### Experiment-5 server client - TCP Client and Server
The server listens on port 5000, accepts one client, reads one message and replies. Start the server first, then the client in a second terminal.

```bash
cd "Experiment-5 server client"
javac Server.java Client.java
java Server
java Client
```

Output: the server prints `Client says: Hello Server`, and the client prints `Server says: Hello Client`.

### Experiment -3 (RMI ) - Remote Method Invocation
A calculator whose methods run on a server and are called remotely by a client.

| File | Role |
|------|------|
| `Calculator.java` | Remote interface (extends `Remote`) with `add()` and `subtract()` |
| `CalculatorImpl.java` | Implementation (extends `UnicastRemoteObject`) |
| `Server.java` | Creates the object and registers it with `Naming.rebind("rmi://localhost/Calculator", obj)` |
| `Client.java` | Gets the remote object with `Naming.lookup()` and calls it |

The server does not start the registry itself, so start `rmiregistry` first, **from this folder**.

```bash
cd "Experiment -3 (RMI )"
javac *.java
rmiregistry &        # Windows: start rmiregistry
java Server
java Client          # in another terminal
```

Output: `Addition = 30` and `Subtraction = 10`.

### servett project 2 - Servlets, Sessions, Filter and Listener
A Maven WAR project (Jakarta Servlet 6, embedded Tomcat 10.1) with a login-protected dashboard.

| URL | Class | What It Does |
|-----|-------|--------------|
| `/` (`index.html`) | - | Login page |
| `/login`, `/logout` | `LoginServlet` | Creates or ends the `HttpSession` |
| `/home` | `HomeServlet` | Dashboard showing the user, login time and active session count |
| `/home` | `AuthFilter` | Filter that redirects to the login page if there is no session |
| `/hello` | `HelloServlet` | `doGet()` and `doPost()` with request details in the response |
| - | `AppListener` | `ServletContextListener` and `HttpSessionListener` that count active sessions |

**Run (embedded Tomcat):**
```bash
cd "servett project 2"
mvn compile exec:java
```
Open `http://localhost:8080/`. Demo login: `admin` / `admin123` (any username with a password of 4 or more characters also works).

**Run on a standalone Tomcat:** `mvn clean package`, copy `target/servlet-demo.war` into Tomcat's `webapps` folder, then open `http://localhost:8080/servlet-demo/`.

### Experiment -7 (JSP) - JSP with Servlet (MVC Model 2)
A student registration page built with JSP and a servlet.

```
student.jsp  --POST /hello-->  HelloServlet  --forward-->  result.jsp
 (form)                        (controller)                 (view, EL)
```

`HelloServlet` reads `name`, `email`, `course` and `age`, stores them as request attributes and forwards to `result.jsp`, which shows them with Expression Language (`${name}`, `${email}` and so on).

**Run (real JSP on Tomcat):**
```bash
cd "Experiment -7 (JSP)"
mvn clean package
```
Copy `target/servlet.war` into Tomcat 10.1+ `webapps`, start Tomcat and open `http://localhost:8080/servlet/`.

`src/main/java/com/servlet/App.java` is a quick preview that uses Java's built-in HTTP server to imitate the same flow. It does not run real JSP, so use Tomcat for the actual JSP version.

---

## Final Project - ShopSphere

Folder: `shopsphere_project`

A Java e-commerce management system with a console menu and a browser storefront.

**Highlights**
- **OOP design:** abstract `Product` with `PhysicalProduct` and `DigitalProduct`; abstract `User` with `Customer` and `Admin`; `Order`, `OrderItem`, `Coupon`, `Review`, `Address`.
- **Interfaces:** `Discountable`, `Payable`, `Searchable`, `InventoryOperations`, `Storable`.
- **Custom exceptions:** `InsufficientStockException`, `InvalidCouponException`, `ProductNotFoundException`, `AuthenticationException`, `OrderNotFoundException`, `PaymentFailedException`, `DatabaseException`.
- **Storage:** object serialization (`.ser`), CSV files, and a MySQL schema with JDBC DAO classes (`database/shopsphere.sql`: users, addresses, categories, products, coupons, orders, order_items, reviews).
- **Console app:** browse, search, filter and sort products; customer login and register; cart; checkout (card, UPI / net banking, cash on delivery); order history; admin portal.
- **Web app:** pages for home, products, cart, checkout and admin, plus a JSON endpoint (`/api/products`), served by Java's built-in HTTP server. No Tomcat needed.
- **Tests:** `ShopSphereTest` automated test suite.

**Run (Windows):** from the `shopsphere_project` folder
```bash
run_web.bat      # web storefront at http://localhost:8080
run_app.bat      # console application
run_tests.bat    # automated tests
```

**Run (any OS):**
```bash
cd shopsphere_project
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out com.shopsphere.app.ShopSphereWebApplication    # web
java -cp out com.shopsphere.app.ShopSphereApplication       # console
```
MySQL is only needed for the JDBC part. Run `database/shopsphere.sql` first and put your own MySQL password in `src/com/shopsphere/dao/DBConnection.java`. Demo logins are listed in the README inside the folder.

---

## Key Terms

| Term | Meaning |
|------|---------|
| MVC | Model (data and logic), View (screen), Controller (reacts to the user) |
| JDBC | Java's way to talk to a database |
| Socket | One end of a two-way connection between two programs |
| RMI | Calling a method of an object that lives in another JVM |
| Servlet | A Java class that runs on a server and answers web requests |
| JSP | A web page that mixes HTML with Java, turned into a servlet by the server |
| Filter | Code that checks requests before they reach a servlet (here, a login check) |
| WAR | A packaged web application that Tomcat can run |
