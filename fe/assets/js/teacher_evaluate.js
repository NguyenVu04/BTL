    // init values to get from database
    var BT_DD = 0;
    var BT_DD_NOTE = "NONE";
    var BT_DD_PERCENT = 0;
    var BT_LON_PERCENT = 0;
    var BT_LON =0;
    var BT_LON_NOTE =0;
    var KT_GIUA = 0;
    var KT_GIUA_NOTE = "NONE";
    var KT_GIUA_PERCENT =  0;
    var KT_CUOI = 3;
    var KT_CUOI_NOTE = "NONE";
    var KT_CUOI_PERCENT = 50;

    localStorage.setItem(BT_DD, 10);



// hàm đổi điểm số
    
    // Function to open modal
     function openModal() {
        var modal = document.getElementById("myModal");
        modal.style.display = "block";
    }

    // Function to close modal
    function closeModal() {
        var modal = document.getElementById("myModal");
        modal.style.display = "none";
    }

    // Function to update scores
    function updateScores(event) {
        event.preventDefault();
        
        // // Tính tổng trọng số
        // var totalWeight = parseFloat(document.getElementById("bt-dd-percent-input").value) +
        // parseFloat(document.getElementById("bt-lon-percent-input").value) +
        // parseFloat(document.getElementById("kt-giua-percent-input").value) +
        // parseFloat(document.getElementById("kt-cuoi-percent-input").value);

        // // Kiểm tra tổng trọng số
        // if (totalWeight > 100) {
        //     alert("Tổng trọng số không được vượt quá 100!");
        //     return; // Không thực hiện cập nhật nếu tổng trọng số vượt quá 100
        // }

        // Lưu giá trị vào Local Storage
        localStorage.setItem('bt_dd', document.getElementById("bt-dd-input").value);
        localStorage.setItem('bt_dd_note', document.getElementById("bt-dd-note-input").value);
        localStorage.setItem('bt_dd_percent', document.getElementById("bt-dd-percent-input").value);
        localStorage.setItem('bt_lon', document.getElementById("bt-lon-input").value);
        localStorage.setItem('bt_lon_note', document.getElementById("bt-lon-note-input").value);
        localStorage.setItem('bt_lon_percent', document.getElementById("bt-lon-percent-input").value);
        localStorage.setItem('kt_giua', document.getElementById("kt-giua-input").value);
        localStorage.setItem('kt_giua_note', document.getElementById("kt-giua-note-input").value);
        localStorage.setItem('kt_giua_percent', document.getElementById("kt-giua-percent-input").value);
        localStorage.setItem('kt_cuoi', document.getElementById("kt-cuoi-input").value);
        localStorage.setItem('kt_cuoi_note', document.getElementById("kt-cuoi-note-input").value);
        localStorage.setItem('kt_cuoi_percent', document.getElementById("kt-cuoi-percent-input").value);

        // Gọi hàm updateTable để cập nhật bảng
        updateTable();

        closeModal();
    }

    // Hàm cập nhật bảng với dữ liệu từ Local Storage
    function updateTable() {
        var bt_dd = parseFloat(localStorage.getItem('bt_dd')) || BT_DD;
        var bt_dd_note = localStorage.getItem('bt_dd_note') || BT_DD_NOTE;
        var bt_dd_percent = parseFloat(localStorage.getItem('bt_dd_percent')) || BT_DD_PERCENT;
        var bt_lon = parseFloat(localStorage.getItem('bt_lon')) || BT_LON;
        var bt_lon_note = localStorage.getItem('bt_lon_note') || BT_LON_NOTE;
        var bt_lon_percent = parseFloat(localStorage.getItem('bt_lon_percent')) || BT_LON_PERCENT;
        var kt_giua = parseFloat(localStorage.getItem('kt_giua')) || KT_GIUA;
        var kt_giua_note = localStorage.getItem('kt_giua_note') || KT_GIUA_NOTE;
        var kt_giua_percent = parseFloat(localStorage.getItem('kt_giua_percent')) || KT_GIUA_PERCENT;
        var kt_cuoi = parseFloat(localStorage.getItem('kt_cuoi')) || KT_CUOI;
        var kt_cuoi_note = localStorage.getItem('kt_cuoi_note') || KT_CUOI_NOTE;
        var kt_cuoi_percent = parseFloat(localStorage.getItem('kt_cuoi_percent')) || KT_CUOI_PERCENT;

        // Tính tổng trọng số
        var total_weight = bt_dd_percent + bt_lon_percent + kt_giua_percent + kt_cuoi_percent;
        // Kiểm tra nếu tổng trọng số vượt quá 100
        if (total_weight > 100) {
            // Hiển thị cảnh báo
            alert("Tổng trọng số không được vượt quá 100.");

            // Hủy bỏ việc lưu dữ liệu và cập nhật bảng
            return;
        }

        // Tính tổng kết và cập nhật bảng
        var tong_ket = (bt_dd * bt_dd_percent / 100) + (bt_lon * bt_lon_percent / 100) + (kt_giua * kt_giua_percent / 100) + (kt_cuoi * kt_cuoi_percent / 100);
        if (bt_dd === 0 || bt_lon === 0 || kt_giua === 0 || kt_cuoi === 0) {
            tong_ket = 0;
        }

        document.getElementById("bt-dd").textContent = bt_dd;
        document.getElementById("bt-dd-note").textContent = bt_dd_note;
        document.getElementById("bt-lon").textContent = bt_lon;
        document.getElementById("bt-lon-note").textContent = bt_lon_note;
        document.getElementById("kt-giua").textContent = kt_giua;
        document.getElementById("kt-giua-note").textContent = kt_giua_note;
        document.getElementById("kt-cuoi").textContent = kt_cuoi;
        document.getElementById("kt-cuoi-note").textContent = kt_cuoi_note;
        document.getElementById("tong-ket").textContent = tong_ket.toFixed(2);
        document.getElementById("tong-ket-note").textContent = document.getElementById("tong-ket-note-input").value;
    }

    // Khi trang được load, kiểm tra xem có dữ liệu trong Local Storage không và cập nhật bảng
    document.addEventListener("DOMContentLoaded", function() {
        updateTable();
    });




    // hàm nhận xét
    // Lấy modal
    var evaluateModal = document.getElementById("evaluatemodal");
    var commentInput = document.getElementById("commentInput");

    // Kiểm tra xem có dữ liệu đã lưu trong localStorage không
    var savedComment = localStorage.getItem("comment");
    if (savedComment) {
        // Nếu có dữ liệu đã lưu, xóa đi hai thẻ <p> trống trong phần nhận xét
        var emptyComments = document.querySelectorAll(".nhanxet p");
        emptyComments.forEach(function(pElement) {
            pElement.remove();
        });
    
        // Gán giá trị của savedComment vào phần hiển thị trên màn hình
        var newCommentElements = document.getElementsByClassName("nhanxet");
        for (var i = 0; i < newCommentElements.length; i++) {
            var commentElement = document.createElement("span");
            commentElement.textContent = savedComment;
            newCommentElements[i].appendChild(commentElement);
        }
    }

    function showComment() {
        evaluateModal.style.display = "block";
    }

    function closeEvaluateModal() {
        evaluateModal.style.display = "none";
    }

    function confirmEvaluation() {
        var comment = commentInput.value;
        // Lưu comment vào localStorage
        localStorage.setItem("comment", comment);

        var emptyComments = document.querySelectorAll(".nhanxet p");
        emptyComments.forEach(function(pElement) {
            pElement.remove();
        });

        var commentElements = document.querySelectorAll(".nhanxet span");
        for (var i = 0; i < commentElements.length; i++) {
            commentElements[i].remove();
        }
        // Hiển thị nhận xét ở dưới mục nhận xét
        var newCommentElements = document.getElementsByClassName("nhanxet");
        for (var i = 0; i < newCommentElements.length; i++) {
            var commentElement = document.createElement("span");
            commentElement.textContent = comment;
            newCommentElements[i].appendChild(commentElement);
        }
        closeEvaluateModal();
    }

    window.onclick = function(event) {
        if (event.target == evaluateModal) {
            evaluateModal.style.display = "none";
        }
    }







    var CURRENTNAME = "VO QUANG DAI VIET";
    var CURRENTID = "2213954";
    // Đổi tên sinh viên 
    // Restore form data when the page is loaded
    window.addEventListener('DOMContentLoaded', function() {
        // Lấy thông tin từ local storage
        var currentName = localStorage.getItem('studentName') || CURRENTNAME;
        var currentID = localStorage.getItem('studentID') || CURRENTID;

        // Hiển thị thông tin từ local storage trên màn hình chính
        document.querySelector('.name-id').innerText = currentName + ' - ' + currentID;
    });

    // Function to open edit modal
    function updateName() {
        var modal = document.getElementById("changeName");
        modal.style.display = "block";

        // Lấy tên và mã số sinh viên hiện tại từ local storage (nếu có)
        var currentName = localStorage.getItem('studentName') || "";
        var currentID = localStorage.getItem('studentID') || "";

        // Đặt giá trị hiện tại của tên sinh viên và mã số sinh viên vào input
        document.getElementById('editStudentName').value = currentName;
        document.getElementById('editStudentID').value = currentID;
    }

    // Lắng nghe sự kiện submit form
    document.getElementById('editFormName').addEventListener('submit', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của form

        // Lấy giá trị từ input
        var newName = document.getElementById('editStudentName').value;
        var newID = document.getElementById('editStudentID').value;

        // Lưu giá trị của tên sinh viên và mã số sinh viên vào local storage
        localStorage.setItem('studentName', newName);
        localStorage.setItem('studentID', newID);

        // Hiển thị tên sinh viên và mã số sinh viên mới trên màn hình chính
        document.querySelector('.name-id').innerText = newName + ' - ' + newID;

        // Ẩn modal sau khi submit
        closeModalName();
    });

    // Function to close edit modal
    function closeModalName() {
        var modal = document.getElementById("changeName");
        modal.style.display = "none";
    }



    // đổi gmail của sinh viên
    // Restore form data when the page is loaded
    window.addEventListener('DOMContentLoaded', function() {
        // Lấy thông tin từ local storage
        var currentEmail = localStorage.getItem('studentEmail') || "";

        // Hiển thị thông tin từ local storage trên màn hình chính
        document.querySelector('.mail').innerText = currentEmail;
    });
    // Function to open email edit modal
    function updateEmail() {
        var modal = document.getElementById("changeEmail");
        modal.style.display = "block";

        // Lấy email hiện tại từ local storage (nếu có)
        var currentEmail = localStorage.getItem('studentEmail') || "";

        // Đặt giá trị hiện tại của gmail vào input
        document.getElementById('editStudentEmail').value = currentEmail;
    }

    // Lắng nghe sự kiện submit form
    document.getElementById('editFormEmail').addEventListener('submit', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của form

        // Lấy giá trị từ input
        var newEmail = document.getElementById('editStudentEmail').value;

        // Lưu giá trị của tên sinh viên và mã số sinh viên vào local storage
        localStorage.setItem('studentEmail', newEmail);

        // Hiển thị tên sinh viên và mã số sinh viên mới trên màn hình chính
        document.querySelector('.mail').innerText = newEmail;

        // Ẩn modal sau khi submit
        closeModalEmail();
    });

    // Function to close edit modal
    function closeModalEmail() {
        var modal = document.getElementById("changeEmail");
        modal.style.display = "none";
    }


