var content = document.querySelector('.display-info');
var list_gv=document.querySelectorAll('.display-list__item');

function changeContent_func(p,classname){
    var classnameChange=document.querySelector(classname).innerHTML;
    content.innerHTML=classnameChange;
    for(let i=0;i<list_gv.length;i++)
    {
        if(list_gv[i].classList.contains('active'))
        {
            list_gv[i].classList.toggle('active')
        }
        else{}
    }
    p.classList.add('active');
}