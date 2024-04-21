var token;
document.addEventListener('DOMContentLoaded', () => {
    token = localStorage.getItem('Authorization');
    if (token === null || token === undefined) return;
    document.getElementById('change-password').innerHTML = "Đổi mật khẩu";
    let logoutElement = document.getElementById('logout');
    logoutElement.innerHTML = "Đăng xuất";
    logoutElement.addEventListener('click', logout);
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
            window.location.href = 'http://127.0.0.1:3000/';
        }
    )
}