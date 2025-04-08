document.addEventListener("DOMContentLoaded", () => {
    const user = JSON.parse(localStorage.getItem("user"));
    const empId = user?.employeeId;
    if (!empId) {
        alert("Employee not logged in!");
        return;
    }

    fetch(`http://localhost:8080/api/employee-project/projects/${empId}`)
        .then(response => response.json())
        .then(projects => {
            const tableBody = document.getElementById("assigned-projects-body");
            tableBody.innerHTML = "";

            if (projects.length === 0) {
                tableBody.innerHTML = "<tr><td colspan='4'>No projects assigned.</td></tr>";
                return;
            }

            projects.forEach(project => {
                const row = `
                    <tr>
                        <td>${project.projectId}</td>
                        <td>${project.projectName}</td>
                        <td>${project.status || 'Ongoing'}</td>
                    </tr>
                `;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => {
            console.error("Error fetching assigned projects:", error);
        });
});
document.addEventListener("DOMContentLoaded", ()=>{
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "login.html"; // Redirect to login page
    });
})