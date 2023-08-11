document.addEventListener('DOMContentLoaded', function () {
    const addUserForm = document.getElementById('addUserForm');
    const usernameInput = document.getElementById('username');
    const lastNameInput = document.getElementById('lastName');
    const ageInput = document.getElementById('age');
    const emailInput = document.getElementById('email');
    const passwordInput = document.getElementById('password');
    const rolesInput = document.getElementById('roles');

    addUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const newUser = {
            username: usernameInput.value,
            lastName: lastNameInput.value,
            age: ageInput.value,
            email: emailInput.value,
            password: passwordInput.value,
            roles: Array.from(rolesInput.selectedOptions, option => option.value) // Convert selected options to an array
        };

        try {
            const response = await fetch('/api/admin/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')
                },
                body: JSON.stringify(newUser)
            })




            const data = await response.json();
            if (data.success) {
                alert('User added successfully');
                window.location.href = '/admin';
            } else {
                alert('Error adding user');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error adding user');
        }
    });
});
