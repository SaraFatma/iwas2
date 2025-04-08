 
 
document.addEventListener("DOMContentLoaded", function () {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user) {
        alert("User not logged in. Redirecting to login page...");
        window.location.href = "../login.html";
        return;
    }
    const empId = user.employeeId;  // Ensure correct property is used
    console.log("Employee ID:", empId);

    // Populate user details
    document.getElementById("emp-name").value = user.emp_name;
    document.getElementById("emp-email").value = user.emp_email;
    document.getElementById("emp-department").value = user.emp_department;
    document.getElementById("emp-designation").value = user.emp_role;
    document.getElementById("emp-availability").value = user.emp_availability;

    //fetchSkills(user.emp_id); // Load available skills & preselect user skills
    // fetchAllSkills(empId); 

    document.getElementById("edit-btn").addEventListener("click", function () {
        document.getElementById("emp-skills").disabled = false;
        document.getElementById("emp-availability").disabled = false;
        document.getElementById("edit-btn").style.display = "none";
        document.getElementById("save-btn").style.display = "inline-block";
    });

    document.getElementById("save-btn").addEventListener("click", function () {
        updateProfile(user.empId);
    });
});

  
document.addEventListener("DOMContentLoaded", function () {
    fetchEmployeeSkills();
 
});
const user1 = JSON.parse(localStorage.getItem("user"));
const empId = user1.employeeId;
function fetchEmployeeSkills() {
    // const empId = 1; // Replace this with dynamic employee ID retrieval
    const skillsList = document.getElementById("emp-current-skills");

    fetch(`http://127.0.0.1:8080/api/employee-skills/${empId}/names`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch skills");
            }
            return response.json();
        })
        .then(skills => {
            skillsList.innerHTML = ""; // Clear previous list

            if (skills.length === 0) {
                skillsList.innerHTML = "<li>No skills added</li>";
            } else {
                skills.forEach(skill => {
                    const li = document.createElement("li");
                    li.textContent = skill;
                    skillsList.appendChild(li);
                });
            }
        })
        .catch(error => console.error("Error fetching skills:", error));
}

/**
 * Updates the employee profile (skills & availability)
 */
async function updateProfile(empId) {
    const selectedSkills = Array.from(document.getElementById("emp-skills").selectedOptions)
                                .map(option => option.value);
    const availability = document.getElementById("emp-availability").value === "true";

    const updatedProfile = {
        availability: availability,
        skills: selectedSkills
    };

    try {
        const response = await fetch(`http://localhost:8080/api/employees/${empId}/update`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "loggedInUserId": empId
            },
            body: JSON.stringify(updatedProfile)
        });

        if (!response.ok) throw new Error("Failed to update profile.");

        alert("Profile updated successfully!");
        document.getElementById("emp-skills").disabled = true;
        document.getElementById("emp-availability").disabled = true;
        document.getElementById("edit-btn").style.display = "inline-block";
        document.getElementById("save-btn").style.display = "none";
        location.reload();
    } catch (error) {
        console.error("Error updating profile:", error);
    }
}
 

const user = JSON.parse(localStorage.getItem("user"));
const employeeId = user.employeeId;
console.log("Employee ID before API call:", employeeId);
////
// document.addEventListener("DOMContentLoaded", function () {
     
//     fetchAllSkills(user.employeeId);

//     document.getElementById("edit-btn").addEventListener("click", function () {
//         document.getElementById("emp-skills").disabled = false;
//         document.getElementById("emp-availability").disabled = false;
//         document.getElementById("edit-btn").style.display = "none";
//         document.getElementById("save-btn").style.display = "inline-block";
//     });

//     document.getElementById("save-btn").addEventListener("click", function () {
//         updateProfile(user.employeeId);
//     });
// });

// Fetch all available skills and preselect user skills
function fetchAllSkills(employeeId) {
    fetch("http://localhost:8080/api/skills")
        .then(response => response.json())
        .then(allSkills => {
            const skillDropdown = document.getElementById("emp-skills");
            skillDropdown.innerHTML = "";

            allSkills.forEach(skill => {
                const option = document.createElement("option");
                option.value = skill.skillName;
                option.textContent = skill.skillName;
                skillDropdown.appendChild(option);
            });

            fetchEmployeeSkills(employeeId);
        })
        .catch(error => console.error("Error fetching skills:", error));
}
document.addEventListener("DOMContentLoaded", function () {
     
    fetchAllSkills(user.employeeId);

    document.getElementById("edit-btn").addEventListener("click", function () {
        document.getElementById("emp-skills").disabled = false;
        document.getElementById("emp-availability").disabled = false;
        document.getElementById("edit-btn").style.display = "none";
        document.getElementById("save-btn").style.display = "inline-block";
    });

    document.getElementById("save-btn").addEventListener("click", function () {
        updateProfile(user.employeeId);
    });


});
document.addEventListener("DOMContentLoaded", ()=>{
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "/pages/login.html"; // Redirect to login page
    });
})
