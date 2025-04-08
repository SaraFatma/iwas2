document.addEventListener("DOMContentLoaded", function () {
    const user = JSON.parse(localStorage.getItem("user"));

    if (user) {
        // Update welcome message
        document.getElementById("employee-name").textContent = user.emp_name;

        // Update employee details
        document.getElementById("emp-name").textContent = user.emp_name;
        document.getElementById("emp-email").textContent = user.emp_email;
        document.getElementById("emp-department").textContent = user.emp_department;
    } else {
        alert("User not logged in. Redirecting to login page...");
        window.location.href = "../login.html";
    }

   
});

document.addEventListener("DOMContentLoaded", ()=>{
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "/pages/login.html"; // Redirect to login page
    });
})


document.addEventListener("DOMContentLoaded", function () {
    const user = JSON.parse(localStorage.getItem("user"));
const employeeId = user?.employeeId;
    if (!employeeId) {
        console.error("Employee ID not found in localStorage.");
        return;
    }

    // Fetch basic info on load
    fetchProfileData(employeeId);
    fetchLeaveRequests(employeeId);
    fetchAssignedProjects(employeeId);

    // Handle card clicks
    document.getElementById("profile-card").addEventListener("click", function () {
        fetchProfileData(employeeId);
        fetchAssignedProjects(employeeId);
    });

    document.getElementById("leave-approval-card").addEventListener("click", function () {
        fetchLeaveRequests(employeeId);
    });

    // Logout handler
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.clear();
        window.location.href = "../login.html";
    });
});

// Fetch profile and skills
function fetchProfileData(employeeId) {
    fetch(`http://localhost:8080/api/employees/${employeeId}`)
        .then(res => res.json())
        .then(data => {
            // document.getElementById("employee-name").textContent = data.empName;
            // document.getElementById("emp-name").textContent = data.empName;
            // document.getElementById("emp-email").textContent = data.empEmail;
            // document.getElementById("emp-department").textContent = data.empDepartment;
            document.getElementById("skills").textContent = data.skills.map(s => s.skillName).join(", ");
        })
        .catch(err => console.error("Error fetching profile:", err));
}

// Fetch assigned projects
function fetchAssignedProjects(employeeId) {
    fetch(`http://localhost:8080/api/employee-project/projects/${employeeId}`)
        .then(response => response.json())
        .then(projects => {
            const projectNames = projects.map(p => p.projectName).join(", ");
            document.getElementById("current-project").textContent = projectNames || "No projects assigned.";
        })
        .catch(error => console.error("Error fetching assigned projects:", error));
}

// Fetch leave requests and count pending
function fetchLeaveRequests(employeeId) {
    fetch(`http://localhost:8080/api/leave/${employeeId}`)
        .then(response => response.json())
        .then(data => {
            const pendingCount = data.filter(leave => leave.status === "PENDING").length;
            document.getElementById("leave-status").textContent = `Pending Leaves: ${pendingCount}`;
        })
        .catch(error => console.error("Error fetching leave requests:", error));
}
