document.addEventListener("DOMContentLoaded", function() {
    const searchInput = document.querySelector(".search__input");
    const courseNames = document.querySelectorAll(".course-name");

    searchInput.addEventListener("input", function() {
        const searchTerm = this.value.toLowerCase();

        courseNames.forEach(function(courseName) {
            const name = courseName.textContent.toLowerCase();
            const course = courseName.closest(".course-each");

            if (name.includes(searchTerm)) {
                course.style.display = "block";
            } else {
                course.style.display = "none";
            }
        });
    });
});

document.addEventListener("DOMContentLoaded", addinner);

const registBtn = document.querySelector('.regist-course')
const modal = document.querySelector('.js-modal')
const modalContainer = document.querySelector('.js-modal-container')
const modalClose = document.querySelector('.js-modal-close')

//Ham hien thi modal mua ve (them class open vao modal)
function showCourseRegister() {
    modal.classList.add('open')
}

registBtn.addEventListener('click', showCourseRegister)

//Ham an modal mua ve (go bo class open cua modal)
function hideCourseRegister() {
    modal.classList.remove('open')
}

//nghe hanh vi click vao button close cua user, nghe dc thi goi ham an
modalClose.addEventListener('click', hideCourseRegister)

//bam vao ben ngoai cung tat giao dien modal 
modal.addEventListener('click', hideCourseRegister)

// khi bam vao form modal thi van se close do modal-container la con cua modal (tinh chat noi bot)
//-> ngan chan t/c noi bot tu modal-container ra ben ngoai
//event la bien tra ve
modalContainer.addEventListener('click', function (event) {
    event.stopPropagation() //ngung noi bot tu modalcontainer qua bien tra ve event, su kien click van dien ra nhung ko noi bot ra ben ngoai
})  


function addinner() {
    let tableCourse = document.getElementById("table-course");
    let innerTable = `<div class="col l-4 m-6 c-12">
    <div class="course-each">
        <div class="course-img">
            <img src="./assets/img/courses-img/1.jpg" alt="" width="100%" >   
        </div>
        <div class="course-desc">
            <div class="course-name">
                <a href="">{nameCourse}</a>
            </div>
            <div class="course-info">
                <p>
                    <i class="fa-regular fa-folder-open"></i> 
                    Mã môn học: <strong>C02039</strong>
                </p>
                <p>
                    <i class="fa-regular fa-calendar-days"></i> 
                    Lớp: <strong>L07</strong>
                </p>
                <p>
                    <i class="fa-regular fa-chart-bar"></i> 
                    Số tín chỉ: <strong>3</strong>
                </p>
                <p>
                    <i class="fa-regular fa-user"></i> 
                    Giảng viên: <strong>Lê Đình Thuận</strong>
                </p>
            </div>
        </div>
        <div class="course-enter-btn">
            <a class="btn-text" href="./course-detail.html">Chi tiết khóa học</a>
        </div>
    </div>
</div>
`
    let stringhtml = '';
    
    
    for (let i = 0 ; i < 4 ; i++) {
        let nameCourse = "LTNC" + i;

        stringhtml += innerTable.replace('{nameCourse}', nameCourse);
    }

    tableCourse.innerHTML = stringhtml;
}

