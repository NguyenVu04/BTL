var token;
document.addEventListener('DOMContentLoaded', () => {
    token = localStorage.getItem('Authorization');
    if (token === null || token === undefined) {
        document.getElementById('login').innerHTML = "Đăng nhập";
    } else {
        validate();
        setInterval(() => {
            validate();
        }, 3000000);
    }
});
function logout() {
    fetch('http://localhost:8080/logout', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Authorization': token
        }
    }).finally(
        () => {
            localStorage.removeItem('Authorization');
            window.location.href = '/login.html';
        }
    )
}

function validate() {
    fetch('http://localhost:8080/validate', {
        method: 'POST',
        mode: 'cors',
        headers: {
            'Authorization': token
        }
    }).then(
        res => {
            if (!res.ok) throw Error(res.statusText);
            return res.headers.get('Authorization');
        }
    ).then(
        data => {
            localStorage.setItem('Authorization', data);
            token = localStorage.getItem('Authorization');
            document.getElementById('change-password').innerHTML = "Đổi mật khẩu";
            let logoutElement = document.getElementById('logout');
            logoutElement.innerHTML = "Đăng xuất";
            logoutElement.addEventListener('click', logout);
        }
    ).catch(
        err => {
            localStorage.removeItem('Authorization');
            window.location.href = '/';
            () => console.clear()
        }
    )
}