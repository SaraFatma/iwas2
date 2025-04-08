
document.addEventListener('DOMContentLoaded', function () {
    // Function to fetch project allocation suggestions from the API
    async function fetchSuggestions() {
        try {
            const response = await fetch('http://localhost:8080/api/employee-project/suggest-all', {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer <your-token-here>' // Replace with actual token
                }
            });

            if (!response.ok) {
                throw new Error('Failed to fetch suggestions');
            }

            const data = await response.json();
            renderSuggestions(data);
        } catch (error) {
            console.error('Error fetching suggestions:', error);
        }
    }

    // Function to render suggestions dynamically on the page
    function renderSuggestions(data) {
        const projectsList = document.querySelector('.projects-list');
        projectsList.innerHTML = ''; // Clear existing content

        // Loop through each project and its suggested employees
        data.forEach(project => {
            const projectItem = document.createElement('div');
            projectItem.classList.add('project-item');

            const projectTitle = document.createElement('h3');
            projectTitle.textContent = `Project: ${project.project.projectName}`;

            // Render project skills
            const projectSkills = document.createElement('p');
            if (project.project.skills.length > 0) {
                const skillsNames = project.project.skills.map(skill => skill.skillName).join(', ');
                projectSkills.innerHTML = `<strong>Required Skills:</strong> ${skillsNames}`;
            } else {
                projectSkills.innerHTML = `<strong>Required Skills:</strong> None specified`;
            }

            const employeesList = document.createElement('div');
            employeesList.classList.add('employee-suggestions');

            // Loop through suggested employees for the current project
            project.suggestedEmployees.forEach(employee => {
                const employeeItem = document.createElement('div');
                employeeItem.classList.add('employee-item');

                // Employee name and skills
                const employeeDetails = document.createElement('p');
                const employeeSkills = employee.skills.map(skill => skill.skillName).join(', ');
                employeeDetails.innerHTML = `<strong>${employee.empName}</strong> - Skills: ${employeeSkills}`;

                // Create accept and reject buttons with the correct empId and projectId
                const acceptButton = document.createElement('button');
                acceptButton.classList.add('accept-btn');
                acceptButton.textContent = 'Accept';
                acceptButton.dataset.empId = employee.empId;  // Add empId as data attribute
                acceptButton.dataset.projectId = project.project.projectId;  // Add projectId as data attribute

                const rejectButton = document.createElement('button');
                rejectButton.classList.add('reject-btn');
                rejectButton.textContent = 'Reject';
                rejectButton.dataset.empId = employee.empId;  // Add empId as data attribute
                rejectButton.dataset.projectId = project.project.projectId;  // Add projectId as data attribute

                employeeItem.appendChild(employeeDetails);
                employeeItem.appendChild(acceptButton);
                employeeItem.appendChild(rejectButton);

                employeesList.appendChild(employeeItem);
            });

            projectItem.appendChild(projectTitle);
            projectItem.appendChild(projectSkills);
            projectItem.appendChild(employeesList);

            projectsList.appendChild(projectItem);
        });

        // Attach event listeners for accept/reject buttons
        attachButtonEventListeners();
    }


     
        // Function to handle the accept and reject button click events
        function attachButtonEventListeners() {
            const acceptButtons = document.querySelectorAll('.accept-btn');
            const rejectButtons = document.querySelectorAll('.reject-btn');
    
            acceptButtons.forEach(button => {
                button.addEventListener('click', async function () {
                    const empId = button.dataset.empId;
                    const projectId = button.dataset.projectId;
    
                    try {
                        const response = await fetch(`http://localhost:8080/api/employee-project/confirm?empId=${empId}&projectId=${projectId}&isConfirmed=true`, {
                            method: 'POST',
                            headers: {
                                'Authorization': 'Bearer <your-token-here>' // Replace with actual token
                            }
                        });
    
                        const result = await response.text();
                        alert(result);  // Show success message for allocation
                    } catch (error) {
                        console.error('Error allocating employee:', error);
                    }
                });
            });
    
            

            rejectButtons.forEach(button => {
                button.addEventListener('click', function () {
                    const empId = button.dataset.empId;
                    const projectId = button.dataset.projectId;
            
                    // Remove the employee suggestion from the UI
                    const employeeItem = button.closest('.employee-item');
                    if (employeeItem) {
                        employeeItem.remove();
                    }
            
                    alert(`Employee with ID ${empId} has been rejected for project ${projectId}.`);
                });
            });
            
        }
    
    // Fetch the suggestions when the page loads
    fetchSuggestions();

    // Logout Button Event
    document.getElementById("logout-btn").addEventListener("click", function () {
        console.log("Logout button clicked");
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "../pages/login.html"; // Redirect to login page
    });
});
