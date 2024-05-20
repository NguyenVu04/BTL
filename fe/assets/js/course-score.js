
function addinner() {
    // let page = document.getElementsByClassName("contents");
    let page = document.getElementById("nameCourse");

    let nameCourse_html = `
                    {nameCourse}
               `;

    let nameCourse = localStorage.nameCourse;
    nameCourse_html = nameCourse_html.replace('{nameCourse}', nameCourse);
    page.innerHTML = nameCourse_html;

    let name = document.getElementById("nameStu");

    console.log(name);
    let nameStu_html = `
    <i class="fa-regular fa-user" style="padding-right: 4px"></i>
    Sinh viên: {nameStu}
               `;

    let nameStu = localStorage.nameStu;
    nameStu_html = nameStu_html.replace('{nameStu}', nameStu);
    name.innerHTML = nameStu_html;


}

addinner();