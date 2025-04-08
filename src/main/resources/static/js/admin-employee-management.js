document.addEventListener("DOMContentLoaded", () => {
    loadEmployees();
    loadSkills();
});

// Fetch Employees & Populate Table
async function loadEmployees() {
    try {
        const response = await fetch("http://localhost:8080/api/employees");
        if (!response.ok) throw new Error("Failed to fetch employees.");
        
        const data = await response.json();
        const employeeList = document.getElementById("employee-list");
        employeeList.innerHTML = "";

        data.forEach(employee => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${employee.empId}</td>
                <td>${employee.empName}</td>
                <td>${employee.empEmail}</td>
                <td>${employee.empDesignation}</td>
                <td>${employee.empDepartment}</td>
                <td>${employee.empAvailability ? "Available" : "Unavailable"}</td>
                <td><button class="skill-btn" data-id="${employee.empId}" data-name="${employee.empName}">Manage</button></td>
                <td>
                    <button class="update-btn" data-id="${employee.empId}">✏️update</button>
                    <button class="delete-btn" data-id="${employee.empId}">❌delete</button>
                </td>
            `;
            employeeList.appendChild(row);
        });

    } catch (error) {
        console.error("Error loading employees:", error);
    }
}
async function loadSkills() {
    try {
        const response = await fetch("http://localhost:8080/api/skills");
        if (!response.ok) throw new Error("Failed to fetch skills.");

        const data = await response.json();
        const skillSelect = document.getElementById("skillSelect");
        skillSelect.innerHTML = "";

        data.forEach(skill => {
            if (skill.skillName.trim() !== "") {  // Ignore empty skill names
                let option = document.createElement("option");
                option.value = skill.skillId; // Ensure skillId is used as the value
                option.textContent = skill.skillName; // Display skill name correctly
                skillSelect.appendChild(option);
            }
        });

    } catch (error) {
        console.error("Error loading skills:", error);
    }
}


// Open Skill Modal
function openSkillModal(empId, empName) {
    document.getElementById("skillEmpName").textContent = empName;
    document.getElementById("skillModal").dataset.empId = empId;
    document.getElementById("skillModal").style.display = "block";
    loadEmployeeSkills(empId);
}

// Close Skill Modal
function closeSkillModal() {
    document.getElementById("skillModal").style.display = "none";
}

// Fetch & Display Assigned Skills
async function loadEmployeeSkills(empId) {
    try {
        const response = await fetch(`http://localhost:8080/api/employee-skills/${empId}/names`);
        if (!response.ok) throw new Error("Failed to fetch employee skills.");

        const data = await response.json();
        const assignedSkills = document.getElementById("assignedSkills");
        assignedSkills.innerHTML = data.map(skill =>
            `<li>${skill} <button class="remove-skill-btn" data-emp-id="${empId}" data-skill-name="${skill}">Remove</button></li>`
        ).join("");

    } catch (error) {
        console.error("Error loading employee skills:", error);
    }
}


async function addSkillToEmployee() {
    const empId = document.getElementById("skillModal").dataset.empId;
    const skillDropdown = document.getElementById("skillSelect");  // ✅ Define skillDropdown properly

    if (!skillDropdown) {
        console.error("❌ Error: Skill dropdown not found.");
        return;
    }

    const skillName = skillDropdown.options[skillDropdown.selectedIndex].text; // ✅ Get skill name properly
    console.log("🛠 Selected skill:", skillName);  // Debugging output

    try {
        const response = await fetch(`http://localhost:8080/api/employee-skills/${empId}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ skillName })  // ✅ Sending correct skill name
        });

        if (!response.ok) throw new Error("Failed to add skill.");
        console.log("✅ Skill added successfully!");
        loadEmployeeSkills(empId);
    } catch (error) {
        console.error("❌ Error adding skill:", error);
    }
}

async function removeSkill(empId, skillName) {
    console.log(`Attempting to remove skill: ${skillName} from employee: ${empId}`);
    try {
        await fetch(`http://localhost:8080/api/employee-skills/${empId}?skillName=${encodeURIComponent(skillName)}`, { 
            method: "DELETE" 
        });
        console.log("Skill removed successfully! Reloading skills...");
        loadEmployeeSkills(empId);
    } catch (error) {
        console.error("Error removing skill:", error);
    }
}


// Open Update Employee Modal
function openUpdateModal(empId) {
    document.getElementById("updateEmpId").value = empId;
    document.getElementById("updateEmployeeModal").style.display = "block";
}

// Close Update Employee Modal
function closeUpdateModal() {
    document.getElementById("updateEmployeeModal").style.display = "none";
}
 
// Function to get logged-in user’s ID & role (assuming stored in localStorage)
function getLoggedInUserDetails() {
    return {
        userId: localStorage.getItem("loggedInUserId"), // Logged-in user's ID
         
       role: localStorage.getItem("userRole") // "admin" or "employee"
        
    };
}
  
 
    

// Update Employee
// async function updateEmployee() {
//     const empId = document.getElementById("updateEmpId").value;
//    // const { userId, role } = getLoggedInUserDetails(); // Get user ID & role
//     console.log(localStorage.getItem("loggedInUser")); 
    
//    const user = JSON.parse(localStorage.getItem("user"));
//    const userId = user?.employeeId;
//    const role = user?.emp_role;
//     console.log(role);
//     if (!userId || !role) {
//         alert("User is not logged in!");
//         return;
//     }

//     // If employee is trying to update someone else's profile, block it
//     if (role !== "ADMIN" ) {
//         alert("You can only update your own profile!");
//         return;
//     }

//     const updatedData = {
//         empName: document.getElementById("updateEmpName").value,
//         empEmail: document.getElementById("updateEmpEmail").value,
//         empDesignation: document.getElementById("updateEmpDesignation").value,
//         empDepartment: document.getElementById("updateEmpDepartment").value,
//         empAvailability: document.getElementById("updateEmpAvailability").value === "true",
//         password: document.getElementById("updateEmpPassword").value
//     };

//     try {
//         const response = await fetch(`http://localhost:8080/api/employees/${empId}`, {
//             method: "PUT",
//             headers: { 
//                 "Content-Type": "application/json",
//                 "loggedInUserId": userId, // ✅ Dynamically setting logged-in user ID
//                 "loggedInUserRole": role // ✅ Sending role in headers
//             },
//             body: JSON.stringify(updatedData)
//         });

//         if (!response.ok) throw new Error("Failed to update employee.");
//         alert("Employee updated successfully!");
//         closeUpdateModal();
//         await loadEmployees();

//     } catch (error) {
//         console.error("Error updating employee:", error);
//         alert("Update failed! Please try again.");

//     }
// }

async function updateEmployee() {
    const empId = document.getElementById("updateEmpId").value;

    const user = JSON.parse(localStorage.getItem("user"));
    const userId = user?.employeeId;
    const role = user?.emp_role;

    if (!userId || !role) {
        alert("User is not logged in!");
        return;
    }

    if (role !== "ADMIN" && userId != empId) {  
        alert("You can only update your own profile!");  
        return;  
    }

    try {
        // 🔍 Step 1: Fetch the existing employee details
        const response = await fetch(`http://localhost:8080/api/employees/${empId}`);
        if (!response.ok) throw new Error("Failed to fetch employee data.");
        const existingData = await response.json();
        console.log(existingData);
        // 🔄 Step 2: Merge previous data with updated values
        const updatedData = {
            empName: document.getElementById("updateEmpName").value || existingData.empName,
            empEmail: document.getElementById("updateEmpEmail").value || existingData.empEmail,
            empDesignation: document.getElementById("updateEmpDesignation").value || existingData.empDesignation,
            empDepartment: document.getElementById("updateEmpDepartment").value || existingData.empDepartment,
            empAvailability: document.getElementById("updateEmpAvailability").checked ?? existingData.empAvailability,
            password: document.getElementById("updateEmpPassword").value || existingData.password
        };

        // 🔄 Step 3: Send the merged data
        const updateResponse = await fetch(`http://localhost:8080/api/employees/${empId}`, {
            method: "PUT",
            headers: { 
                "Content-Type": "application/json",
                "loggedInUserId": userId, 
                "loggedInUserRole": role 
            },
            body: JSON.stringify(updatedData)
        });

        if (!updateResponse.ok) throw new Error("Failed to update employee.");
        
        alert("Employee updated successfully!");
        closeUpdateModal();
        await loadEmployees(); 

    } catch (error) {
        console.error("Error updating employee:", error);
        alert("Update failed! Please try again.");
    }
}




// Delete Employee
async function deleteEmployee(empId) {
    if (!confirm("Are you sure you want to delete this employee?")) return;

    try {
        const response = await fetch(`http://localhost:8080/api/employees/${empId}`, { method: "DELETE" });
        if (!response.ok) throw new Error("Failed to delete employee.");

        alert("Employee deleted successfully!");
        loadEmployees();

    } catch (error) {
        console.error("Error deleting employee:", error);
    }
}

// Add Employee
async function addEmployee() {
    const newEmployee = {
        empName: document.getElementById("empName").value,
        empEmail: document.getElementById("empEmail").value,
        empDesignation: document.getElementById("empDesignation").value,
        empDepartment: document.getElementById("empDepartment").value,
        password: document.getElementById("empPassword").value,
        empAvailability: document.getElementById("empAvailability").value=== "true"
    };
    console.log("🛠 Sending Employee Data:", newEmployee); 

    try {
        const response = await fetch("http://localhost:8080/api/employees", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newEmployee)
        });
        
    const result = await response.json(); 
        if (!response.ok) throw new Error("Failed to add employee.");
        alert("Employee added successfully!");
        loadEmployees();

    } catch (error) {
        console.error("Error adding employee:", error);
    }
}

// Event Delegation for Buttons
document.getElementById("employee-list").addEventListener("click", (event) => {
    const target = event.target;
    const empId = target.dataset.id;

    if (target.classList.contains("skill-btn")) {
        openSkillModal(empId, target.dataset.name);
    } else if (target.classList.contains("update-btn")) {
        openUpdateModal(empId);
    } else if (target.classList.contains("delete-btn")) {
        deleteEmployee(empId);
    }else if(target.classList.contains("add-employee-btn")){
        addEmployee();
    }
});

// Event Delegation for Removing Skills
document.getElementById("assignedSkills").addEventListener("click", (event) => {
    if (event.target.classList.contains("remove-skill-btn")) {
        const empId = event.target.dataset.empId;
        const skillName = event.target.dataset.skillName;
        removeSkill(empId, skillName);
    }
});

// Toggle Sidebar
document.addEventListener("DOMContentLoaded", () => {
    const menuToggle = document.createElement("button");
    menuToggle.className = "menu-toggle";
    menuToggle.innerHTML = "☰";
    document.body.insertBefore(menuToggle, document.body.firstChild);

    const sidebar = document.querySelector(".sidebar");
    menuToggle.addEventListener("click", () => {
        sidebar.classList.toggle("open");
    });
});

document.addEventListener("DOMContentLoaded", ()=>{
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "login.html"; // Redirect to login page
    });
})