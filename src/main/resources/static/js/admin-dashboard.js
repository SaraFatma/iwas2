 
document.addEventListener("DOMContentLoaded", function () {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user) {
        window.location.href = "login.html"; // Redirect if not logged in
    } else {
        // Display user details
        document.getElementById("admin-name").textContent = user.emp_name;
        document.getElementById("admin-email").textContent = user.emp_email;
        document.getElementById("admin-department").textContent = user.emp_department;
    }

    // Logout Functionality
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "login.html"; // Redirect to login page
    });
});

// API base URL
const apiBaseUrl = "http://localhost:8080/api";

// Function to fetch and update dashboard metrics
async function loadDashboardMetrics() {
    try {
        // Fetch total projects
        const projectResponse = await fetch(`${apiBaseUrl}/projects/count`);
        const totalProjects = await projectResponse.json();
        document.getElementById("total-projects").textContent = totalProjects;

        // Fetch total employees
        const employeeResponse = await fetch(`${apiBaseUrl}/employees/count`);
        const totalEmployees = await employeeResponse.json();
        document.getElementById("total-employees").textContent = totalEmployees;

        // Fetch pending leave requests
        const leaveResponse = await fetch(`${apiBaseUrl}/leave/pending/count`);
        const pendingLeaves = await leaveResponse.json();
        document.getElementById("pending-leaves").textContent = pendingLeaves;
        
    } catch (error) {
        console.error("Error fetching dashboard metrics:", error);
    }
}

// Load dashboard metrics when the page loads
window.onload = loadDashboardMetrics;
