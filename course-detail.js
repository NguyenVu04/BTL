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
