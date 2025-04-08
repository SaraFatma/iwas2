# iwas2
# Intelligent Workforce Allocation System (IWAS)

The **Intelligent Workforce Allocation System (IWAS)** is a web-based platform that helps organizations efficiently manage workforce allocation based on employee skills, availability, and leave status. The system supports both **admin** and **employee** roles with tailored dashboards and functionalities.

## 🔧 Tech Stack

- **Backend:** Java 17, PostgreSQL  
- **Frontend:** HTML, CSS, JavaScript  
- **Tools:** Maven, JDBC  
- **Authentication:** Custom (no Spring Security)

## 🌐 Features

### 👩‍💼 Employee Features
- ✅ Login  
- ✅ Dashboard with project updates  
- ✅ Profile management (skills & availability)  
- ✅ Apply for leave and track leave status  
- ✅ View past project assignments  
- 🚧 Receive notifications (coming soon)

### 🛠️ Admin Features
- ✅ Login  
- ✅ Employee CRUD (Add/Update/Delete/View)  
- ✅ Project CRUD (Admins only)  
- ✅ Leave request approval/rejection  
- ✅ Manual employee assignment to projects  
- ✅ Dashboard with reports and analytics  
- 🚧 Skill-based matching for project allocation (coming soon)

## 📁 Project Structure

IWAS/ │ ├── backend/ (Java + PostgreSQL) │ ├── src/com/example/iwas/ │ │ ├── model/ │ │ ├── dao/ │ │ ├── controller/ │ │ ├── utils/ │ ├── frontend/ │ ├── login.html │ ├── employee_dashboard.html │ ├── employee_profile.html │ ├── leave_request.html │ ├── past_assignments.html │ ├── employee_management.html │ ├── project_management.html │ ├── css/ │ ├── js/ │ │ ├── script.js (shared logic) │ │ ├── employee.js │ │ ├── admin.js

📌 Upcoming Enhancements
🔄 Skill-based employee suggestion for projects

📩 Real-time employee notifications

📊 Enhanced dashboard reports

🔐 Optional Spring Security integration

🤝 Contributing
Pull requests are welcome! For major changes, please open an issue first to discuss what you’d like to change.

🧑‍💻 Author
Sara Fatma – LinkedIn
AI Student | Fintech Enthusiast | Developer

📜 License
This project is open source and free to use under the MIT License.
