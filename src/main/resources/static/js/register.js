document.getElementById("registerForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const empName = document.getElementById("empName").value.trim();
    const empEmail = document.getElementById("empEmail").value.trim();
    const empDepartment = document.getElementById("empDepartment").value.trim();
    const password = document.getElementById("empPassword").value.trim(); // Fixed ID

    if (!empName || !empEmail || !empDepartment || !password) {
        alert("All fields are required!");
        return;
    }

    const employeeData = {
        empName,
        empEmail,
        empDepartment,
        password,  
        empDesignation: "EMPLOYEE", // Default role
        empAvailability: true
    };

    try {
        const response = await fetch("http://localhost:8080/api/employees", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(employeeData)
        });

        if (response.ok) {
            alert("Registration successful! Please log in.");
            window.location.href = "login.html"; // Redirect to login page
        } else {
            alert("Registration failed. Email may already exist.");
        }
    } catch (error) {
        console.error("Error:", error);
        alert("An error occurred during registration.");
    }
});
