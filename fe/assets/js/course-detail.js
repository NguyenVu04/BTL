console.log(localStorage.detail_course);
getCourse();
document.addEventListener("DOMContentLoaded", function() {
    var button = document.getElementById("content__toggle");
    var sections = document.querySelectorAll(".content__section");

    // Thêm sự kiện click cho nút toggle
    button.addEventListener("click", function() {
        var isAllCollapsed = button.textContent === "Thu gọn toàn bộ";
        sections.forEach(function(section) {
            if (isAllCollapsed) {
                collapseSection(section);
            } else {
                expandSection(section);
            }
        });
        // Cập nhật nút toggle
        button.textContent = isAllCollapsed ? "Mở rộng tất cả" : "Thu gọn toàn bộ";
    });

    // Hàm mở rộng section
    function expandSection(section) {
        section.classList.remove("collapsed");
        var overviewImg = section.querySelector(".overview-img img");
        if (overviewImg) {
            overviewImg.style.display = "block"; // Hiển thị hình ảnh
        }
    }

    // Hàm thu gọn section
    function collapseSection(section) {
        section.classList.add("collapsed");
        var overviewImg = section.querySelector(".overview-img img");
        if (overviewImg) {
            overviewImg.style.display = "none"; // Ẩn hình ảnh
        }
    }

    // Thêm sự kiện click cho mỗi heading
    sections.forEach(function(section) {
        var heading = section.querySelector(".section__heading");
        var content = section.querySelector(".section__list"); // Lấy phần nội dung bên dưới mỗi section
        var files = section.querySelectorAll(".file-container"); // Lấy danh sách các file trong section
        heading.addEventListener("click", function() {
            if (section.classList.contains("collapsed")) {
                expandSection(section);
            } else {
                collapseSection(section);
            }
        });
    });
});


document.addEventListener("DOMContentLoaded", function() {
    // Function to handle file upload
    function handleFileUpload(input, fileList) {
        input.addEventListener("change", function(event) {
            var files = event.target.files; // Lấy danh sách file từ input

            // Hiển thị từng file và tạo một div để chứa file, icon và link mở file
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                var fileContainer = document.createElement("div");
                fileContainer.className = "file-container"; // Thêm class cho div chứa file

                var fileIcon = document.createElement("i");
                fileIcon.className = "fa-solid fa-file-arrow-down file-icon"; // Thêm class cho icon

                var fileName = document.createElement("span");
                fileName.textContent = " " + file.name; // Thêm khoảng trắng trước tên file

                // Tạo link để mở file
                var fileLink = document.createElement("a");
                fileLink.href = URL.createObjectURL(file); // Tạo URL cho file
                fileLink.target = "_blank"; // Mở file trong tab mới

                fileLink.appendChild(fileIcon); // Thêm icon vào link
                fileLink.appendChild(fileName); // Thêm tên file vào link

                fileContainer.appendChild(fileLink); // Thêm link vào div chứa file

                // Tạo nút xóa file
                var deleteButton = document.createElement("button");
                deleteButton.textContent = "Xóa";
                deleteButton.className = "delete-button";
                deleteButton.addEventListener("click", function() {
                    fileList.removeChild(fileContainer); // Xóa div chứa file khi nút xóa được click
                    if (fileList.childNodes.length === 0) {
                        fileList.innerHTML = ''; // Nếu không còn file nào, xóa hết nội dung của danh sách
                    }
                });

                fileContainer.appendChild(deleteButton); // Thêm nút xóa vào div chứa file

                fileList.appendChild(fileContainer); // Thêm div chứa file vào danh sách
            }
        });
    }

    var slideFileInput = document.querySelector(".slideFileUpload");
    var referenceFileInput = document.querySelector(".referenceFileUpload");
    var slideFileList = document.getElementById("slideFileList");
    var referenceFileList = document.getElementById("referenceFileList");

    // Xử lý upload file cho cả phần Slide và Tài liệu tham khảo
    handleFileUpload(slideFileInput, slideFileList);
    handleFileUpload(referenceFileInput, referenceFileList);
});



function addinner(data){
    // let page = document.getElementsByClassName("contents");
    let page = document.getElementById("content");

    let html = `<div class="grid wide">
    <div class="row">
        <div class="content__title col l-12">
            {nameCourse}
        </div>
    </div>
</div>

<div class="grid wide">
    <div class="row">
        <div class="course-on content__map col l-o-2 l-2">
            <a href="./course-detail.html">Khoá học</a>
        </div>
        <div class="score-on content__map col l-o-1 l-2">
            <a href="./course-score.html">Điểm số - Tiến độ</a>
        </div>
        <div class="classlist-on content__map col l-o-1 l-2">
            <a href="./list_stu.html">Danh sách lớp</a>
        </div>  
    </div>
</div>

<div class="grid wide">
    <div class="row">
        <div class="col l-o-10">
            <div id="content__toggle">Thu gọn toàn bộ</div>
        </div>
    </div>
</div>



<div id="content__section-list">
    <div class="grid wide">
        <div class="row">
            <div class="col l-12">
                <div class="content__section">
                    <h2 class="section__heading">
                        <i class="fa-solid fa-angle-down"></i>
                        Tổng quan về khóa học
                    </h2>
                    <div class="grid wide">
                        <div class="row">
                            <div class="col l-8">
                                <div class="section__list">
                                    <h3 class="section__list-heading">Course Outcomes</h3>
                                    <ul class="overview-wrapper">
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> The fundamental concepts in modern Operating Systems (OSs):</li>
                                        <ul>
                                            <li>(1) Processes and Threads: scheduling, synchronization;</li>
                                            <li>(2) Memory management, main and virtual memory;</li>
                                            <li>(3) I/O management;</li>
                                            <li>(4) Persistent data storage and File systems, journaling;</li>
                                            <li>(5) Security and protection.</li>
                                        </ul>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Multi-programming and synchronization in such programming models.</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> System performance, applying the lessons of algorithms and data structures to the complex operations of an operating system.</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Practicing and performing simulation experiments (using programming language C or Java)</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Presenting materials on group projects</li>
                                    </ul>
                                
                                    <h3 class="section__list-heading">Course Outline</h3>
                                    <ul class="overview-wrapper">
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Introduction to Operating systems</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Processes/Threads management</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> CPU scheduling</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Synchronization</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Memory management</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> I/O management</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Storage management</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> File systems</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Security and protection</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Advanced topics</li>
                                        <li><i class="fa-solid fa-check" style="color: #0c89dc; padding-right: 4px"></i> Summary</li>
                                    </ul>
                                </div>
                                
                            </div>
                            <div class="col l-4 overview-img">
                                <img src="./assets/img/course-detail/about.svg" alt="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="grid wide">
        <div class="row">
            <div class="col l-12">
                <div class="content__section">
                    <h2 class="section__heading">
                        <i class="fa-solid fa-angle-down"></i>
                        Thông báo chung
                    </h2>
                    <div class="grid wide">
                        <div class="row">
                            <div class="col l-8">
                                <div class="section__list noti-list">
                                    <p>Chào các bạn học lớp Hệ điều hành L07 và L08,

                                        Các bạn có lịch học bù môn HĐH ngày thứ 5 (04/04/24), tiết 5-6 (L07), tiết 8-9 (L08).
                                        Các bạn cố gắng tham gia đầy đủ.</p>
                                    <p>Chào các bạn lớp Hệ điều hành,
                                        Các bạn được nghỉ học ngày mai thứ 4 (17/01/2024) vì tôi tham gia Hội đồng Đồ án chuyên ngành. Lịch học bù sẽ thông báo lại sau.</p>
                                </div>
                            </div>
                            <div class="col l-4 overview-img">
                                <img src="./assets/img/course-detail/noti.svg" alt="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="grid wide">
        <div class="row">
            <div class="col l-12">
                <div class="content__section">
                    <h2 class="section__heading">
                        <i class="fa-solid fa-angle-down"></i>
                        Slide bài giảng
                    </h2>
                    <div class="section__list" id="slideFileList">
                        <!-- Nút "Chọn file" ở đầu danh sách -->
                        <input type="file" class="fileUpload slideFileUpload" accept=".pdf, .doc, .docx">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="grid wide">
        <div class="row">
            <div class="col l-12">
                <div class="content__section">
                    <h2 class="section__heading">
                        <i class="fa-solid fa-angle-down"></i>
                        Tài liệu tham khảo  
                    </h2>
                    <div class="section__list" id="referenceFileList">
                        <!-- Nút "Chọn file" ở đầu danh sách -->
                        <input type="file" class="fileUpload referenceFileUpload" accept=".pdf, .doc, .docx">
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="grid wide">
        <div class="row">
            <div class="col l-12">
                <div class="content__section">
                    <h2 class="section__heading">
                        <i class="fa-solid fa-angle-down"></i>
                        Quiz kiểm tra</h2>
                    <div class="section__list">
                        Hiện chưa có bài kiểm tra nào.
                    </div>
                    <!-- Nội dung của mục Tài liệu -->
                </div>
            </div>
        </div>
    </div>
</div>           `;

    let nameCourse = data.name;
    console.log(nameCourse);
    html = html.replace('{nameCourse}', nameCourse);
    page.innerHTML= html;
}


async function getCourse() {
    let url = 'http://localhost:8080/course/id?';
    let returnVal = await fetch(url + new URLSearchParams({
        idCourse: localStorage.detail_course
    }), {
        method:"GET",
        mode:"cors"
    }).then(
        data => {
            if(!data.ok) {
                throw Error(data.statusText);
            }
            return data.json();
        }
    ).then(res => {
        console.log(res);
        return res;
    });
    addinner(returnVal);
    return returnVal;
}