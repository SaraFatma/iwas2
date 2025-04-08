document.getElementById("loginForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form reload

    const empEmail = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8080/api/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ empEmail, password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Login failed. Please check your credentials.");
        }
        return response.json();
    })
    .then(data => {
        console.log("Login successful:", data);

        // Store login details in localStorage instead of sessionStorage
        localStorage.setItem("user", JSON.stringify(data));

        // Redirect based on role
        if (data.emp_role === "ADMIN") {
            window.location.href = "admin-dashboard.html";
        } else {
            window.location.href = "employee-dashboard.html";
        }
    })
    .catch(error => {
        console.error("Error:", error);
        alert("Invalid credentials. Please try again.");
    });
});
