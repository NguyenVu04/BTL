var token;
let teacher_only = document.getElementsByClassName("teacher-only");
    if (localStorage.getItem('Role') === 'STUDENT') {
        for (let i = 0; i < teacher_only.length; i++) {
            teacher_only[i].style.display = 'none';
        }
    }
    let student_only = document.getElementsByClassName("student-only");
    if (localStorage.getItem('Role') === 'TEACHER') {
        for (let i = 0; i < student_only.length; i++) {
            student_only[i].style.display = 'none';
        }
    }
document.addEventListener('DOMContentLoaded', () => {
    token = localStorage.getItem('Authorization');
    if (token === null) {
        if (window.location.pathname === '/')
            document.getElementById('login').innerHTML = "Đăng nhập";
        else
          window.location.href = '/';
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