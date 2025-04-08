let currentProjectId = null; 

// Function to fetch projects from the server
function loadProjects() {
    fetch('http://localhost:8080/api/projects')
        .then(response => response.json())
        .then(data => displayProjects(data))
        .catch(error => console.error('Error fetching projects:', error));
}

// Function to display projects in the table
function displayProjects(projects) {
    const projectTableBody = document.querySelector('#projectTable tbody');
    projectTableBody.innerHTML = '';

    projects.forEach(project => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${project.projectId}</td>
            <td>${project.projectName}</td>
            <td>${project.projectDescription || 'N/A'}</td>
            <td>${formatDate(project.startDate)}</td>
            <td>${formatDate(project.endDate)}</td>
            <td>
                <button class="edit-btn" onclick="editProject(${project.projectId})">Edit</button>
                <button class="delete-btn" onclick="deleteProject(${project.projectId})">Delete</button>
            </td>
        `;
        projectTableBody.appendChild(row);
    });
}

// Function to format date
function formatDate(dateString) {
    return new Date(dateString).toLocaleDateString();
}

// Function to open the add project form
function openAddProjectForm() {
    document.getElementById('addProjectForm').style.display = 'block';
    loadSkillsForAddProject();
}

document.getElementById('addProjectBtn').addEventListener('click', openAddProjectForm);

// Function to load available skills for the Add Project form
function loadSkillsForAddProject() {
    fetch('http://localhost:8080/api/skills')
        .then(response => response.json())
        .then(skills => {
            const skillSelect = document.getElementById('skills');
            skillSelect.innerHTML = '';
            skills.forEach(skill => {
                const option = document.createElement('option');
                option.value = skill.skillId;
                option.textContent = skill.skillName;
                skillSelect.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching skills:', error));
}

// Function to close the add project form
function closeForm() {
    document.getElementById('addProjectForm').style.display = 'none';
}

// Function to save a new project
function saveProject() {
    const newProject = {
        projectName: document.getElementById('projectName').value,
        projectDescription: document.getElementById('projectDescription').value,
        startDate: document.getElementById('startDate').value,
        endDate: document.getElementById('endDate').value,
        skillIds: Array.from(document.getElementById('skills').selectedOptions).map(option => option.value)
    };

    fetch('http://localhost:8080/api/projects', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'loggedInUserId': 2 },
        body: JSON.stringify(newProject)
    })
    .then(response => response.json())
    .then(() => {
        alert('Project added successfully!');
        loadProjects();
        closeForm();
    })
    .catch(error => console.error('Error adding project:', error));
}



// Function to open the edit project modal
function editProject(projectId) {
    currentProjectId = projectId;  // Store the current project ID
    fetch(`http://localhost:8080/api/projects/${projectId}`)
        .then(response => response.json())
        .then(project => {
            // Populate the form with the current project details
            document.getElementById('editProjectName').value = project.projectName;
            document.getElementById('editProjectDescription').value = project.projectDescription || '';
            document.getElementById('editStartDate').value = formatDateInput(project.startDate);
            document.getElementById('editEndDate').value = formatDateInput(project.endDate);

            // Load skills
            loadSkills(project.skills); // Pass current skills to pre-select them

            // Display the modal
            document.getElementById('editProjectModal').style.display = 'block';
            closeForm();
        })
        .catch(error => {
            console.error('Error fetching project details:', error);
        });
}
 
// Function to load available skills and select those already assigned to the project
function loadSkills(selectedSkills) {
    fetch('http://localhost:8080/api/skills') // Assuming there's an endpoint to get all skills
        .then(response => response.json())
        .then(skills => {
            const skillSelect = document.getElementById('editSkills');
            skillSelect.innerHTML = ''; // Clear previous options

            skills.forEach(skill => {
                const option = document.createElement('option');
                option.value = skill.skillId;
                option.textContent = skill.skillName;
                // If the skill is already associated with the project, mark it as selected
                if (selectedSkills.some(selectedSkill => selectedSkill.skillId === skill.skillId)) {
                    option.selected = true;
                }
                skillSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching skills:', error);
        });
}

// Function to format date for the input
function formatDateInput(dateString) {
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];  // Convert to YYYY-MM-DD format
}

// Update project details (after editing)
document.getElementById('editProjectForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const updatedProject = {
        projectName: document.getElementById('editProjectName').value,
        projectDescription: document.getElementById('editProjectDescription').value,
        startDate: document.getElementById('editStartDate').value,
        endDate: document.getElementById('editEndDate').value,
        skillIds: Array.from(document.getElementById('editSkills').selectedOptions)
                        .map(option => option.value)
    };

    fetch(`http://localhost:8080/api/projects/${currentProjectId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'loggedInUserId': 2  // Replace with actual logged-in user ID
        },
        body: JSON.stringify(updatedProject)
    })
    .then(response => response.json())
    .then(updatedProject => {
        alert('Project updated successfully!');
        loadProjects();  // Reload projects
        closeEditModal();  // Close the modal
    })
    .catch(error => {
        console.error('Error updating project:', error);
    });
});

// Close the edit modal
function closeEditModal() {
    document.getElementById('editProjectModal').style.display = 'none';
}

// Delete project
function deleteProject(projectId) {
    if (confirm('Are you sure you want to delete this project?')) {
        fetch(`http://localhost:8080/api/projects/${projectId}`, {
            method: 'DELETE',
            headers: {
                'loggedInUserId': 2  // Replace with actual logged-in user ID
            }
        })
        .then(response => {
            if (response.ok) {
                loadProjects();  // Reload the projects after successful deletion
            } else {
                alert('Error deleting project');
            }
        })
        .catch(error => {
            console.error('Error deleting project:', error);
        });
    }
}
document.addEventListener("DOMContentLoaded", function() {
    loadProjects();
    loadSkillsForAddProject();
});

document.addEventListener("DOMContentLoaded", ()=>{
    document.getElementById("logout-btn").addEventListener("click", function () {
        localStorage.removeItem("user"); // Clear storage
        window.location.href = "login.html"; // Redirect to login page
    });
})