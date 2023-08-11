document.addEventListener('DOMContentLoaded', function () {
    fetchAndFillUserData();
});

function fetchAndFillUserData() {
    fetch('/api/admin')
        .then(response => response.json())
        .then(users => {
            // Очищаем таблицу перед заполнением
            const tableBody = document.getElementById('usersTableBody');
            tableBody.innerHTML = '';

            users.forEach(user => {
                const row = createTableRow(user);
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
// Функция для создания строки таблицы
function createTableRow(user) {
    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.lastName}</td>
        <td>${user.age}</td>
        <td>${user.email}</td>
        <td>${user.roles.map(role => role.replace('ROLE_', '')).join(', ')}</td>
        <td>
            <button class="btn btn-primary" onclick="editUser(${user.id})">Edit</button>
            <button class="btn btn-danger" onclick="deleteUser(${user.id})">Delete</button>
        </td>
    `;
    return row;
}

// Функция для редактирования
function editUser(userId) {
    fetch(`/api/users/${userId}`)
        .then(response => response.json())
        .then(user => {
            fillEditForm(user);
            $('#editUserModal').modal('show');
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Функция для удаления
function deleteUser(userId) {
    fetch(`/api/users/${userId}`, {
        method: 'DELETE'
    })
        .then(() => {
            fetchUsers();

            const tableBody = document.getElementById('usersTableBody');
            tableBody.innerHTML = ''; // Очистить таблицу
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Функция для сохранения отредактированного пользователя
function saveEditedUser() {
    const userId = document.getElementById('edit-user-id').value;
    const user = {
        id: userId,
        username: document.getElementById('edit-user-username').value,
        lastName: document.getElementById('edit-user-lastName').value,
        age: document.getElementById('edit-user-age').value,
        email: document.getElementById('edit-user-email').value
    };

    fetch(`/api/users/${userId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    })
        .then(() => {
            $('#editUserModal').modal('hide');
            fetchUsers();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Функция для обработки отправки формы для создания нового пользователя
function createUser() {
    const newUser = {
        username: document.getElementById('new-user-username').value,
        lastName: document.getElementById('new-user-lastName').value,
        age: document.getElementById('new-user-age').value,
        email: document.getElementById('new-user-email').value,
        password: document.getElementById('new-user-password').value,
        roles: ['USER'] // Assign default role
    };

    fetch('/api/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    })
        .then(() => {
            $('#newUserModal').modal('hide');
            fetchUsers();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

// Функция для заполнения данных при загрузке страницы
function fillUserData() {
    fetchUsers();
}

// Обработчик события загрузки DOM
document.addEventListener('DOMContentLoaded', fillUserData);
