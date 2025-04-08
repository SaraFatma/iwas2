// // API base URL
// const apiBaseUrl = "http://localhost:8080/api/leave";

// // Fetch all pending leave requests for the admin
// async function fetchPendingLeaveRequests() {
//     try {
//         const response = await fetch(`${apiBaseUrl}/pending`);
//         if (response.ok) {
//             const data = await response.json();
//             displayPendingLeaveRequests(data);
//         } else {
//             alert("Failed to fetch pending leave requests!");
//         }
//     } catch (error) {
//         console.error("Error fetching pending leave requests:", error);
//         alert("An error occurred while fetching pending leave requests.");
//     }
// }

// // Function to approve or reject a leave request
// async function approveOrRejectLeave(leaveId, status) {
//     try {
//         const response = await fetch(`${apiBaseUrl}/${leaveId}/approve?status=${status}`, {
//             method: 'PUT',
//         });

//         if (response.ok) {
//             const data = await response.json();
//             alert(`Leave request ${status}d successfully!`);
//             // Re-fetch pending leave requests after approval/rejection
//             fetchPendingLeaveRequests();
//         } else {
//             alert(`Failed to ${status} leave request!`);
//         }
//     } catch (error) {
//         console.error(`Error approving/rejecting leave request:`, error);
//         alert("An error occurred while approving/rejecting the leave request.");
//     }
// }

// // Function to display pending leave requests (Admin)
// function displayPendingLeaveRequests(leaveRequests) {
//     const leaveList = document.querySelector('.leave-list');
//     leaveList.innerHTML = ''; // Clear existing content

//     leaveRequests.forEach(request => {
//         const leaveItem = document.createElement('div');
//         leaveItem.classList.add('leave-item');

//         leaveItem.innerHTML = `
//             <div class="leave-info">
//                 <h3>Employee Name: ${request.employee.empName}</h3>
//                 <p><strong>Leave Type:</strong> ${request.reason}</p>
//                 <p><strong>Start Date:</strong> ${request.startDate}</p>
//                 <p><strong>End Date:</strong> ${request.endDate}</p>
//                 <p><strong>Status:</strong> ${request.status}</p>
//             </div>
//             <div class="leave-actions">
//                 <button class="approve-btn" onclick="approveOrRejectLeave(${request.leaveId}, 'approve')">Approve</button>
//                 <button class="reject-btn" onclick="approveOrRejectLeave(${request.leaveId}, 'reject')">Reject</button>
//             </div>
//         `;

//         leaveList.appendChild(leaveItem);
//     });
// }

// // Initialize by fetching pending leave requests when the page loads
// fetchPendingLeaveRequests();

// API base URL
const apiBaseUrl = "http://localhost:8080/api/leave";

// Fetch all pending leave requests for the admin
async function fetchPendingLeaveRequests() {
    try {
        const response = await fetch(`${apiBaseUrl}/pending`);
        if (response.ok) {
            const data = await response.json();
            console.log("Fetched Leave Requests:", data); // Debugging log
            displayPendingLeaveRequests(data);
        } else {
            alert("Failed to fetch pending leave requests!");
        }
    } catch (error) {
        console.error("Error fetching pending leave requests:", error);
        alert("An error occurred while fetching pending leave requests.");
    }
}

// Function to approve or reject a leave request
async function approveOrRejectLeave(leaveId, status) {
    // const leaveId =  request.leaveId;
    console.log("leave id is ",leaveId);
    console.log("leave status is ", status);
    if (!leaveId) {
        console.error("Invalid leaveId:", leaveId);
        alert("Error: Invalid leave request ID.");
        return;
    }

    try {
        const response = await fetch(`${apiBaseUrl}/${leaveId}/approve?status=${status}`, {
            method: 'PUT',
        });

        if (response.ok) {
            alert(`Leave request ${status}d successfully!`);
            fetchPendingLeaveRequests(); // Refresh list after approval/rejection
        } else {
            alert(`Failed to ${status} leave request!`);
        }
    } catch (error) {
        console.error(`Error approving/rejecting leave request:`, error);
        alert("An error occurred while approving/rejecting the leave request.");
    }
}

// Function to display pending leave requests (Admin)
function displayPendingLeaveRequests(leaveRequests) {
    const leaveList = document.querySelector('.leave-list');
    leaveList.innerHTML = ''; // Clear existing content
  
    leaveRequests.forEach(request => {
        console.log("Processing Leave Request:", request); // Debugging log

        if (!request.leaveId) {
            console.error("Missing leaveId for request:", request);
            return; // Skip rendering this leave request
        }

        // Create leave item container
        const leaveItem = document.createElement('div');
        leaveItem.classList.add('leave-item');

        // Create leave info section
        leaveItem.innerHTML = `
            <div class="leave-info">
                <h3>Employee Name: ${request.employee.empName}</h3>
                <p><strong>Leave Type:</strong> ${request.reason}</p>
                <p><strong>Start Date:</strong> ${request.startDate}</p>
                <p><strong>End Date:</strong> ${request.endDate}</p>
                <p><strong>Status:</strong> ${request.status}</p>
            </div>
        `;

        // Create action buttons
        const actionsDiv = document.createElement('div');
        actionsDiv.classList.add('leave-actions');

        const approveBtn = document.createElement('button');
        approveBtn.textContent = 'Approve';
        approveBtn.classList.add('approve-btn');
        approveBtn.addEventListener('click', () => approveOrRejectLeave(request.leaveId, 'APPROVED'));

        const rejectBtn = document.createElement('button');
        rejectBtn.textContent = 'Reject';
        rejectBtn.classList.add('reject-btn');
        rejectBtn.addEventListener('click', () => approveOrRejectLeave(request.leaveId, 'REJECTED'));

        actionsDiv.appendChild(approveBtn);
        actionsDiv.appendChild(rejectBtn);
        leaveItem.appendChild(actionsDiv);

        // Append to leave list
        leaveList.appendChild(leaveItem);
    });
}

// Initialize by fetching pending leave requests when the page loads
fetchPendingLeaveRequests();
document.addEventListener("DOMContentLoaded", ()=>{
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "login.html"; // Redirect to login page
    });
})