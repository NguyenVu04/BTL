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





