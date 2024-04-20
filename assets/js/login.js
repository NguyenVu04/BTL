var content2 = document.querySelector('.login');
function switchToLogin(classname){
    console.log(123);
    var classnameChange=document.querySelector(classname).innerHTML;
    content2.innerHTML=classnameChange;
    console.log(classnameChange);
}