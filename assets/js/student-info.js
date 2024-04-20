
var content1 = document.querySelector('.detail-info');
var list_i=document.querySelectorAll('.option_detail h4 i');
var list_option=document.querySelectorAll('.option_detail');

function changeStudentInfo(p,classname){
    var classnameChange=document.querySelector(classname).innerHTML;
    content1.innerHTML=classnameChange;
    for(let i=0;i<3;i++)
    {
        if(list_option[i].classList.contains('active'))
        {
            list_option[i].classList.toggle('active')
        }
        else{}
        if(list_i[i].classList.contains('main-color'))
        {
            list_i[i].classList.toggle('main-color')
        }
        else{}
    }
    var item=p.querySelector('h4 i');
    item.classList.add('main-color');
    p.classList.add('active');
}

