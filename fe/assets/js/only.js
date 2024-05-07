let teacher_only = document.getElementsByClassName("teacher-only");
if (localStorage.getItem('Role') === 'STUDENT') {
    for (let i = 0; i < teacher_only.length; i++) {
        teacher_only[i].style.display = 'none';
    }
}

let student_only = document.getElementsByClassName("student-only");
if (localStorage.getItem('Role') === 'TEACHER') {
    console.log(localStorage.getItem('Role'));
    for (let i = 0; i < student_only.length; i++) {
        student_only[i].style.display = 'none';
    }
}
console.log(student_only);