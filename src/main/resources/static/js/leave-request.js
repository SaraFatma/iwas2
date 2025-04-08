// document.addEventListener("DOMContentLoaded", function () {
//    // fetchLeaveRequests();
// });

const user = JSON.parse(localStorage.getItem("user"));
const employeeId = user.employeeId;

// function fetchLeaveRequests() {
//     // const employeeId = 1; // Replace with dynamically fetched logged-in employee ID

//     fetch(`http://localhost:8080/api/leave/employee/${employeeId}`)
//         .then(response => response.json())
//         .then(data => {
//             const tableBody = document.querySelector(".leave-table tbody");
//             tableBody.innerHTML = ""; // Clear existing rows

//             data.forEach(leave => {
//                 const row = document.createElement("tr");
//                 row.innerHTML = `
//                     <td>${leave.reason}</td>
//                     <td>${leave.startDate} - ${leave.endDate}</td>
//                     <td class="status">${leave.status}</td>
//                 `;
//                 tableBody.appendChild(row);
//             });
//         })
//         .catch(error => console.error("Error fetching leave requests:", error));
// }

document.getElementById("apply-leave").addEventListener("click", function () {
    // const employeeId = 1; // Replace with dynamically fetched logged-in employee ID
    const startDate = document.getElementById("leave-date").value;
    const endDate = document.getElementById("leave-end-date").value;
    const reason = document.getElementById("leave-reason").value;
    const leaveType = document.getElementById("leave-type").value;

    if (!startDate || !endDate || !reason) {
        alert("Please fill in all fields.");
        return;
    }
//  employee: { empId: employeeId },
    const leaveRequest = {
        empId:  employeeId ,
        startDate: startDate,
        endDate: endDate,
        reason: reason,
        status: "PENDING"
    };

    fetch("http://localhost:8080/api/leave/apply", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(leaveRequest)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Failed to apply for leave");
        }
        return response.json();
    })
    .then(data => {
        alert("Leave request submitted successfully!");
       // fetchLeaveRequests(); // Refresh leave requests list
    })
    .catch(error => console.error("Error:", error));
});


function fetchLeaveRequests() {
    

    fetch(`http://localhost:8080/api/leave/${employeeId}`)
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector(".leave-table tbody");
            tableBody.innerHTML = ""; // Clear existing rows

            data.forEach(leave => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${leave.reason}</td>
                    <td>${leave.startDate} - ${leave.endDate}</td>
                    <td class="status">${leave.status}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("Error fetching leave requests:", error));
}
document.addEventListener("DOMContentLoaded", function () {
   fetchLeaveRequests();
});

document.addEventListener("DOMContentLoaded", ()=>{
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "../pages/login.html"; // Redirect to login page
    });
})