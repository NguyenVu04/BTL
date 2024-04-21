/*DONE*/
var content2 = document.querySelector('.login');
let count = 0;
function switchToLogin(classname){
    var classnameChange=document.querySelector(classname).innerHTML;
    content2.innerHTML=classnameChange;
    if (classname == '.backToOption') {
        document.getElementById('student-submit').removeEventListener('click', studentLogin);
        document.getElementById('teacher-submit').removeEventListener('click', teacherLogin);
    } else if (classname == '.goToStudent') {
        document.getElementById('student-submit').addEventListener('click', studentLogin);
    } else if (classname == '.goToTeacher') {
        document.getElementById('teacher-submit').addEventListener('click', teacherLogin);
    } else {
        window.location.href = 'http://127.0.0.1:3000/';
    }
}

function studentLogin() {
    document.getElementById('student-submit').removeEventListener('click', studentLogin);
    let studentEmail = document.getElementById('student-email').value.toLowerCase().trim();
    let studentPassword = document.getElementById('student-password').value;
    if (studentEmail == '' || studentPassword == '') {
        document.getElementById('alert-message').innerHTML = 'Vui lòng nhập đầy đủ thông tin';
        document.getElementById('student-submit').addEventListener('click', studentLogin);
        return;
    }
    let form = new FormData();
    form.append('email', studentEmail);
    form.append('password', studentPassword);
    form.append('role', 'STUDENT');
    fetch('http://localhost:8080/login', {
        method: 'POST',
        mode: 'cors',
        body: form
    }).then(
        res => {
            if (!res.ok) throw Error(res.statusText);
            return res.headers.get('Authorization');
        }
    ).then(
        data => {
            localStorage.setItem('Authorization', data);
            window.location.href = 'http://127.0.0.1:3000/';
        }
    ).catch (
        err => {
            count++;
            if (count < 3) {
                document.getElementById('student-submit').addEventListener('click', studentLogin);
                document.getElementById('alert-message').innerHTML = 'Tài khoản hoặc mật khẩu không chính xác';
            } else {
                document.getElementById('alert-message').innerHTML = 'Vui lòng thử lại sau 10 phút';
                setTimeout(() => {
                    document.getElementById('student-submit').addEventListener('click', studentLogin);
                    count = 0;
                }, 600000);
            }
        }
    ).finally (
        () => console.clear()
    )
}

function teacherLogin() {
    document.getElementById('teacher-submit').removeEventListener('click', teacherLogin);
    let teacherEmail = document.getElementById('teacher-email').value.toLowerCase().trim();
    let teacherPassword = document.getElementById('teacher-password').value;
    if (teacherEmail === '' || teacherPassword === '') {
        document.getElementById('alert-message').innerHTML = 'Vui lòng nhập đầy đủ thông tin';
        document.getElementById('teacher-submit').addEventListener('click', teacherLogin);
        return;
    }
    let form = new FormData();
    form.append('email', teacherEmail);
    form.append('password', teacherPassword);
    form.append('role', 'TEACHER');
    fetch('http://localhost:8080/login', {
        method: 'POST',
        mode: 'cors',
        body: form
    }).then(
        res => {
            if (!res.ok) throw Error(res.statusText);
            return res.headers.get('Authorization');
        }
    ).then(
        data => {
            localStorage.setItem('Authorization', data);
            window.location.href = 'http://127.0.0.1:3000/';
        }
    ).catch(
        err => {
            count++;
            if (count < 3) {
                document.getElementById('teacher-submit').addEventListener('click', teacherLogin);
                document.getElementById('alert-message').innerHTML = 'Tài khoản hoặc mật khẩu không chính xác';
            } else {
                document.getElementById('alert-message').innerHTML = 'Vui lòng thử lại sau 10 phút';
                setTimeout(() => {
                    document.getElementById('teacher-submit').addEventListener('click', teacherLogin);
                    count = 0;
                }, 600000);
            }
        }
    ).finally(
        //() => console.clear()
    )
}