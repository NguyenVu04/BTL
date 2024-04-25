/*
TÌM KIẾM KHÓA HỌC
*/
    document.addEventListener("DOMContentLoaded", getCourse().then(res => {
        document.addEventListener('DOMContentLoaded', addinner(res));
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


/*
MODAL ĐĂNG KÍ KHÓA HỌC
*/
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




/*
HIỂN THỊ KHÓA HỌC MỚI
*/
function generateRandomClass() {
    const randomClassNumber = Math.floor(Math.random() * 20) + 1; // Sinh số nguyên ngẫu nhiên từ 1 đến 20
    return "L" + ("0" + randomClassNumber).slice(-2); // Chuyển số thành chuỗi và thêm "0" vào trước số nếu số chỉ có một chữ số, sau đó lấy 2 chữ số cuối cùng
}

document.getElementById("submit-btn").addEventListener("click", function() {
    // Lấy thông tin về tên môn học và mã môn học từ modal
    const courseNameInput = document.getElementById("modal-course-name").value;
    
    const courseCodeInput = document.getElementById("modal-course-id").value;

    // Kiểm tra xem liệu cả hai trường thông tin đã được nhập hay chưa
    if (courseNameInput.trim() !== "" && courseCodeInput.trim() !== "") {
        // Tạo một đối tượng chứa thông tin của khóa học mới
        const newCourse = {
            name: courseNameInput,
            code: courseCodeInput,
            // Ở đây bạn có thể thêm các thông tin khác nếu cần
        };

        // Gọi hàm để hiển thị khóa học mới trên giao diện
        displayNewCourse(newCourse);

        // Đóng modal sau khi đã đăng kí môn học mới thành công
        hideCourseRegister();
    } else {
        // Nếu thông tin không hợp lệ, có thể hiển thị một thông báo hoặc thực hiện các xử lý khác tùy ý
        alert("Vui lòng nhập đầy đủ thông tin cho môn học mới!");
    }
});



function displayNewCourse(course) {
    // Tạo một phần tử div mới để chứa thông tin của khóa học
    const courseElement = document.createElement("div");
    courseElement.classList.add("col", "l-4", "m-6", "c-12"); // Thêm các lớp cho phần tử div mới

    // Tạo HTML cho thông tin của khóa học và gắn vào phần tử div mới tạo


    // Thêm phần tử khóa học mới vào giao diện
    const gridWide = document.querySelector(".content__courses .grid.wide");
    const rows = gridWide.querySelectorAll(".row");
    let targetRow;
    for (let i = 0; i < rows.length; i++) {
        const cols = rows[i].querySelectorAll(".col");
        if (cols.length < 3) {
            targetRow = rows[i];
            break;
        }
    }
    if (!targetRow) {
        targetRow = document.createElement("div");
        targetRow.classList.add("row");
        gridWide.appendChild(targetRow);
    }
    targetRow.appendChild(courseElement);

    // Gán sự kiện xóa môn học cho nút "Xóa khóa học" của khóa học mới
    const deleteBtn = courseElement.querySelector('.delete-course');
    deleteBtn.addEventListener('click', function() {
        const courseContainer = deleteBtn.closest('.course-each');
        courseContainer.remove();
    });
}

function displayNewCourse(course) {
    // Tạo một phần tử div mới để chứa thông tin của khóa học
    const courseElement = document.createElement("div");
    courseElement.classList.add("col", "l-4", "m-6", "c-12"); // Thêm các lớp cho phần tử div mới

    // Tạo HTML cho thông tin của khóa học và gắn vào phần tử div mới tạo
        courseElement.innerHTML = `
        <div class="course-each">
            <div class="course-img">
                <img src="./assets/img/courses-img/3.jpg" alt="" width="100%">
            </div>
            <div class="course-desc">
                <div class="course-name">
                    <a href="">${course.name}</a>
                </div>
                <div class="course-info">
                    <p>
                        <i class="fa-regular fa-folder-open"></i> 
                        Mã môn học: <strong>${course.code}</strong>
                    </p>
                    <p>
                        <i class="fa-regular fa-calendar-days"></i> 
                        Lớp: <strong>${generateRandomClass()}</strong> <!-- Thay đổi ở đây -->
                    </p>
                    <p>
                        <i class="fa-regular fa-chart-bar"></i> 
                        Số tín chỉ: <strong>4</strong>
                    </p>
                    <p>
                        <i class="fa-regular fa-user"></i> 
                        Giảng viên: <strong>Lê Đình Thuận</strong>
                    </p>
                </div>
            </div>
            <div class="course-actions">
                <div class="course-enter-btn">
                    <a class="btn-text" href="./course-detail.html">Chi tiết khóa học</a>
                </div>
                <div class="course-delete-btn" style="display: none;">
                    <button class="btn-text delete-course">Xóa khóa học này</button>
                </div>
            </div>
        </div>
    `;

    // Thêm phần tử khóa học mới vào giao diện
    const gridWide = document.querySelector(".content__courses .grid.wide");
    const rows = gridWide.querySelectorAll(".row");
    let targetRow;
    for (let i = 0; i < rows.length; i++) {
        const cols = rows[i].querySelectorAll(".col");
        if (cols.length < 3) {
            targetRow = rows[i];
            break;
        }
    }
    if (!targetRow) {
        targetRow = document.createElement("div");
        targetRow.classList.add("row");
        gridWide.appendChild(targetRow);
    }
    targetRow.appendChild(courseElement);

    // Gán sự kiện xóa môn học cho nút "Xóa khóa học" của khóa học mới
    const deleteBtn = courseElement.querySelector('.delete-course');
    deleteBtn.addEventListener('click', function() {
        const courseContainer = deleteBtn.closest('.course-each');
        courseContainer.remove();

        // Kiểm tra xem hàng có trống không sau khi xóa
        const row = courseContainer.parentElement;
        if (row.childElementCount === 0) {
            row.remove(); // Nếu hàng trống, loại bỏ nó
        }
    });
}



/* 
XÓA MÔN HỌC
*/
document.addEventListener("DOMContentLoaded", function() {
    // Lấy danh sách tất cả các nút "Chi tiết khóa học"
    const detailButtons = document.querySelectorAll('.course-enter-btn a');

    // Lặp qua từng nút "Chi tiết khóa học" và thêm sự kiện click
    detailButtons.forEach(function(button) {
        button.addEventListener('click', function(event) {
            // Ngăn chặn hành vi mặc định của nút "Chi tiết khóa học" (chuyển hướng trang)
            event.preventDefault();

            // Kiểm tra xem có ở chế độ xóa môn học không
            const isInDeleteMode = document.body.classList.contains('delete-mode');

            if (!isInDeleteMode) {
                // Nếu không ở chế độ xóa môn học, chuyển đến trang chi tiết khóa học
                window.location.href = button.getAttribute('href');
            }
        });
    });

    // Xử lý sự kiện khi bấm nút "Xóa môn học"
    const deleteCourseBtns = document.querySelectorAll('.delete-course');
    deleteCourseBtns.forEach(function(deleteBtn) {
        deleteBtn.addEventListener('click', function() {
            // Xóa phần tử của khóa học
            const courseContainer = deleteBtn.closest('.course-each');
            courseContainer.remove();

            // Kiểm tra xem hàng có trống không sau khi xóa
            const row = courseContainer.parentElement;
            if (row.childElementCount === 0) {
                row.remove(); // Nếu hàng trống, loại bỏ nó
            }
        });
    });

    // Xử lý sự kiện khi bấm nút "Xóa môn học" để chuyển sang chế độ xóa
    const deleteCourseModeBtn = document.querySelector('.delete-mode-btn');
    deleteCourseModeBtn.addEventListener('click', function() {
        // Hiển thị nút "Thoát" khỏi chế độ xóa môn học
        const exitDeleteModeBtn = document.querySelector('.exit-delete-mode-btn');
        exitDeleteModeBtn.style.display = 'block';
        // Thêm lớp "delete-mode" vào phần tử <body>
        document.body.classList.add('delete-mode');
        // Ẩn nút "Chi tiết khóa học" khi ở chế độ xóa môn học
        const detailButtons = document.querySelectorAll('.course-enter-btn');
        detailButtons.forEach(function(enterBtnContainer) {
            enterBtnContainer.style.display = 'none';
        });
        // Hiển thị nút "Xóa khóa học"
        const deleteCourseButtons = document.querySelectorAll('.course-delete-btn');
        deleteCourseButtons.forEach(function(deleteBtnContainer) {
            deleteBtnContainer.style.display = 'block';
        });
    });

    // Xử lý sự kiện khi bấm nút "Thoát" khỏi chế độ xóa môn học
    const exitDeleteModeBtn = document.querySelector('.exit-delete-mode-btn');
    exitDeleteModeBtn.addEventListener('click', function() {
        // Ẩn nút "Thoát" khỏi chế độ xóa môn học
        exitDeleteModeBtn.style.display = 'none';
        // Loại bỏ lớp "delete-mode" khỏi phần tử <body>
        document.body.classList.remove('delete-mode');
        // Hiển thị lại nút "Chi tiết khóa học" khi thoát khỏi chế độ xóa môn học
        const detailButtons = document.querySelectorAll('.course-enter-btn');
        detailButtons.forEach(function(enterBtnContainer) {
            enterBtnContainer.style.display = 'block';
        });
        // Ẩn nút "Xóa khóa học"
        const deleteCourseButtons = document.querySelectorAll('.course-delete-btn');
        deleteCourseButtons.forEach(function(deleteBtnContainer) {
            deleteBtnContainer.style.display = 'none';
        });
    });
});

        return res;
    }));
    






function addinner(data) {
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
                                            Mã môn học: <strong>{idCourse}</strong>
                                        </p>
                                        <p>
                                            <i class="fa-regular fa-calendar-days"></i> 
                                            Lớp: <strong>L07</strong>
                                        </p>
                                        <p>
                                            <i class="fa-regular fa-chart-bar"></i> 
                                            Số tín chỉ: <strong>{price}</strong>
                                        </p>
                                        <p>
                                            <i class="fa-regular fa-user"></i> 
                                            Giảng viên: <strong>{teacherName}</strong>
                                        </p>
                                    </div>
                                </div>
                                <div class="course-actions">
                                    <div class="course-enter-btn">
                                        <a class="btn-text" href="./course-detail.html">Chi tiết khóa học</a>
                                    </div>
                                    <div class="course-delete-btn" style="display: none;">
                                        <button class="btn-text delete-course">Xóa khóa học này</button>
                                    </div>
                                </div>
                            </div>
                        </div>
`
    let stringhtml = '';
    for (let i = 0 ; i < data.length ; i++) {
        let temp = innerTable;
        console.log(data[i]);
        let listTeacher = data[i].listTeacher;
        let teacherName = '';
        for (let j = 0 ; j < listTeacher.length ; j++) {
            teacherName += listTeacher[j];
        }
        console.log(teacherName);
        
        let nameCourse = data[i].name;
        let idCourse = data[i].id;
        let price = data[i].price;
        temp= temp.replace('{teacherName}',teacherName );
        temp= temp.replace('{nameCourse}', nameCourse);
        temp= temp.replace('{idCourse}', idCourse);
        temp= temp.replace('{price}',price )
        stringhtml += temp;
    }

    tableCourse.innerHTML = stringhtml;
}
const user = {
    id: '1213',
    name: 'LeDinhThuan',
    price: '1213'
};
// document.addEventListener('DOMContentLoaded', fetchCourse());
// document.addEventListener('DOMContentLoaded', getCourse());
async function getCourse() {
    let url = 'http://localhost:8080/course/all';
    let returnVal = await 
    fetch(url ,{
        mode: 'cors',
        method: 'GET'
    })
    .then(res => {
        if (!res.ok){
            throw Error(res.statusText);
        }
        return res.json();}
    );
    // console.log(returnVal);
    for (let i = 0 ; i < returnVal.length; i++) {
        const val = returnVal[i];
        console.log(val.id, val.name, val.price);
    }
    // document.addEventListener('DOMContentLoaded', addinner(returnVal));
    return returnVal;

}
function addCourse() {
    // let url = 'http://localhost:8080/course/id?';
    let url = 'http://localhost:8080/course/add/course';
    let form = new FormData();
    form.append('id', 'CO2011');
    form.append('name', 'CTRR1');
    form.append('price', '125224');
    
    fetch(url ,{
        mode: 'cors',
        method: 'POST',
        body: form
    })
    .then(res => 
        {
            // console.log(123);
        if (!res.ok) {
            throw Error(res.statusText);
            // return;
        }
            console.log(res);
            return res.json();
        
        }
    )
    .then(data => {
        console.log(data);
        // return res.json();
    })
    .catch(error => {
        console.log(error);
    })
    ;
    
}



