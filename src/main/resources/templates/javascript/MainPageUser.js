document.addEventListener('DOMContentLoaded', function () {
    fetch('/user', {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    })
        .then(response => response.json())
        .then(user => {
            document.getElementById('user-id').innerText = user.id;
            document.getElementById('user-username').innerText = user.username;
            document.getElementById('user-lastName').innerText = user.lastName;
            document.getElementById('user-age').innerText = user.age;
            document.getElementById('user-email').innerText = user.email;
            document.getElementById('user-roles').innerText = user.roles.map(role => role.replace('ROLE_', '')).join(', ');

            // Здесь будут вставляться данные из JavaScript
            document.getElementById('navbar-user-email').innerText = user.email;
            document.getElementById('navbar-user-roles').innerText = user.roles.map(role => role.replace('ROLE_', '')).join(', ');
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
});
