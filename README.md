# 🛡️ GhostWriter Forensics
**The Premium AI Detection & Code Analysis Engine**

GhostWriter Forensics is a sophisticated forensic tool designed to distinguish between human-written and AI-generated source code. By combining static analysis, cyclomatic complexity metrics, and Halstead volume, it provides a high-fidelity verdict on code authenticity.

---

## 🚀 Tech Stack
* **Frontend**: React.js with Tailwind CSS & Lucide Icons
* **Backend**: Spring Boot (Java) REST API
* **Database**: H2 In-Memory Database (SQL) for persistent scan history
* **Analysis Engine**: Python 3 utilizing Radon and Lizard metrics

---

## 🛠️ Installation & Setup

### 1. Analysis Engine (Python)
Ensure your system has Python 3.9+ and the required forensic libraries installed:
```bash
pip3 install radon lizard

Step 1:
Backend (Spring Boot)
Launch the Java engine to handle API requests and initialize the SQL database:
Open your terminal and navigate to the backend folder.
Run the following command:
Bash ./mvnw clean spring-boot:run
The API will be live at http://localhost:8080. Access the H2 Database Console at http://localhost:8080/h2-console

Step 2:
Frontend (React)
Start the Luxury Gold dashboard:
Open a second terminal and navigate to ghostwriter-frontend.
Run the following command:
Bash npm start
The dashboard will open at http://localhost:3000


Forensic Methodology
GhostWriter evaluates code based on three primary pillars:

Logic Complexity: Uses Radon to analyze the cyclomatic complexity of functions. High complexity often indicates human-authored logic.

Static Analysis: Uses Lizard to calculate NLOC (Non-Comment Lines of Code) and token density.

Data Persistence: Every scan is recorded in a local H2 database, tracking filenames, timestamps, verdicts, and AI probability scores.