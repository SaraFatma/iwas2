// document.addEventListener("DOMContentLoaded", function () {
//     const tableBody = document.getElementById("projectEmployeeTable");
//     const apiUrl = "http://localhost:8080/api/employee-project/allProjectEmp";

//     // Fetch and display project-employee data
//     function fetchProjectEmployeeData() {
//         fetch(apiUrl)
//             .then(response => response.json())
//             .then(data => {
//                 tableBody.innerHTML = ""; // Clear table before adding new rows
//                 data.forEach(project => {
//                     project.employees.forEach(employee => {
//                         const row = document.createElement("tr");

//                         row.innerHTML = `
//                             <td>${project.projectName}</td>
//                             <td>${employee.empName}</td>
//                             <td><button class="delete-btn" onclick="deleteEmployee('${project.projectName}', '${employee}')">Remove</button></td>
//                         `;

//                         tableBody.appendChild(row);
//                     });
//                 });
//             })
//             .catch(error => console.error("Error fetching project-employee data:", error));
//     }

//     // Function to remove employee from a project
//     function deleteEmployee(projectName, employeeName) {
//         const deleteUrl = `http://localhost:8080/api/employee-project/remove`;
        
//         fetch(deleteUrl, {
//             method: "DELETE",
//             headers: {
//                 "Content-Type": "application/json"
//             },
//             body: JSON.stringify({ projectName, employeeName })
//         })
//         .then(response => {
//             if (response.ok) {
//                 alert(`Removed ${employeeName} from ${projectName}`);
//                 fetchProjectEmployeeData(); // Refresh table after deletion
//             } else {
//                 alert("Failed to remove employee");
//             }
//         })
//         .catch(error => console.error("Error deleting employee:", error));
//     }

//     // Load data on page load
//     fetchProjectEmployeeData();
// }); 

// document.addEventListener("DOMContentLoaded", function () {
//     const tableBody = document.getElementById("projectEmployeeTable");
//     const apiUrl = "http://localhost:8080/api/employee-project/allProjectEmp";

//     // Fetch and display project-employee data
//     function fetchProjectEmployeeData() {
//         fetch(apiUrl)
//             .then(response => response.json())
//             .then(data => {
//                 tableBody.innerHTML = ""; // Clear table before adding new rows
//                 data.forEach(project => {
//                     const projectRow = document.createElement("tr");
//                     projectRow.innerHTML = `<td colspan="3"><strong>${project.projectName}</strong></td>`;
//                     tableBody.appendChild(projectRow);

//                     project.employees.forEach(employee => {
//                         const row = document.createElement("tr");

//                         row.innerHTML = `
//                             <td>${project.projectName}</td>
//                             <td>${employee.empName}</td>
//                             <td>
//                                 <button class="delete-btn" onclick="deleteEmployee(${employee.empId}, ${project.projectId}, '${employee.empName}', '${project.projectName}')">Remove</button>
//                             </td>
//                         `;

//                         tableBody.appendChild(row);
//                     });
//                 });
//             })
//             .catch(error => console.error("Error fetching project-employee data:", error));
//     }

//     // Function to remove employee from a project
//     function deleteEmployee(empId, projectId, empName, projectName) {
//         const deleteUrl = "http://localhost:8080/api/employee-project/remove";

//         fetch(deleteUrl, {
//             method: "DELETE",
//             headers: {
//                 "Content-Type": "application/json"
//             },
//             body: JSON.stringify({ empId, projectId })
//         })
//         .then(response => {
//             if (response.ok) {
//                 alert(`Removed ${empName} from ${projectName}`);
//                 fetchProjectEmployeeData(); // Refresh table after deletion
//             } else {
//                 alert("Failed to remove employee");
//             }
//         })
//         .catch(error => console.error("Error deleting employee:", error));
//     }

//     // Load data on page load
//     fetchProjectEmployeeData();
// });

document.addEventListener("DOMContentLoaded", function () {
    const tableBody = document.getElementById("projectEmployeeTable");

    function fetchProjectEmployeeData() {
        fetch("http://localhost:8080/api/employee-project/allProjectEmp")
            .then(response => response.json())
            .then(data => {
                tableBody.innerHTML = ""; 

                data.forEach(project => {
                    const projectRow = document.createElement("tr");
                    projectRow.innerHTML = `<td colspan="3"><strong>${project.projectName}</strong></td>`;
                    tableBody.appendChild(projectRow);

                    project.employees.forEach(employee => {
                        const row = document.createElement("tr");

                        row.innerHTML = `
                            <td>${project.projectName}</td>
                            <td>${employee.empName}</td>
                            <td><button class="delete-btn" data-empid="${employee.empId}" data-projid="${project.projectId}">Remove</button></td>
                        `;

                        tableBody.appendChild(row);
                    });
                });

                // Attach event listeners to all delete buttons
                document.querySelectorAll(".delete-btn").forEach(button => {
                    button.addEventListener("click", function () {
                        const empId = this.getAttribute("data-empid");
                        const projectId = this.getAttribute("data-projid");
                        console.log("Clicked delete for Employee ID:", empId, "Project ID:", projectId); // Debugging log
                        deleteEmployee(empId, projectId);
                    });
                });
            })
            .catch(error => console.error("Error fetching project-employee data:", error));
        }
    function deleteEmployee(empId, projectId) {
        fetch("http://localhost:8080/api/employee-project/remove", {
            method: "DELETE",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ empId, projectId })
        })
        .then(response => {
            if (response.ok) {
                alert("Employee removed successfully");
                fetchProjectEmployeeData(); 
            } else {
                alert("Failed to remove employee");
            }
        })
        .catch(error => console.error("Error deleting employee:", error));
    }

    fetchProjectEmployeeData();
    document.getElementById("logout-btn").addEventListener("click", function () {
        console.log("Logout button clicked");
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "../pages/login.html"; // Redirect to login page
    });
});

