document.addEventListener("DOMContentLoaded", async function () {
    await fetchProjects();
    document.getElementById("logout-btn").addEventListener("click", function () {
        console.log("Logout button clicked");
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "../pages/login.html"; // Redirect to login page
    });
});

async function fetchProjects() {
    try {
        const response = await fetch("http://localhost:8080/api/employee-project/allProjectEmp");
        const projects = await response.json();
        
        projects.sort((a, b) => a.projectId - b.projectId);

        const tableBody = document.getElementById("projectTableBody");
        tableBody.innerHTML = ""; // Clear previous data

        projects.forEach(async (project) => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${project.projectId}</td>  <!-- ✅ Correct field -->
                <td>${project.projectName}</td>  <!-- ✅ Correct field -->
                <td>${project.employees.map(emp => emp.empName).join(", ") || "None"}</td>  <!-- ✅ Correct field -->
                <td>
                    <select id="employeeSelect-${project.projectId}">
                        <option value="">Select Employee</option>
                    </select>
                    <button class="assign-btn" onclick="assignEmployee(${project.projectId})">Assign</button>
                </td>
            `;

            tableBody.appendChild(row);

            // Fetch and populate employees for the dropdown
            await populateEmployeeDropdown(`employeeSelect-${project.projectId}`);
        });
    } catch (error) {
        console.error("Error fetching projects:", error);
    }
}


async function populateEmployeeDropdown(selectId) {
    try {
        const response = await fetch("http://localhost:8080/api/employees");
        const employees = await response.json();

        const dropdown = document.getElementById(selectId);
        dropdown.innerHTML = '<option value="">Select Employee</option>'; // Reset dropdown

        employees.forEach(emp => {
            let option = document.createElement("option");
            option.value = emp.empId;
            option.textContent = emp.empName;
            dropdown.appendChild(option);
        });

    } catch (error) {
        console.error("Error fetching employees:", error);
    }
}

async function assignEmployee(projectId) {
    const select = document.getElementById(`employeeSelect-${projectId}`);
    const empId = select.value;

    if (!empId) {
        alert("Please select an employee!");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/employee-project/assign?empId=${empId}&projectId=${projectId}`, {
            method: "POST"
        });

        if (response.ok) {
            alert("Employee assigned successfully!");
            fetchProjects(); // Refresh table
        } else {
            alert("Failed to assign employee. They might already be assigned.");
        }
    } catch (error) {
        console.error("Error assigning employee:", error);
    }
}
