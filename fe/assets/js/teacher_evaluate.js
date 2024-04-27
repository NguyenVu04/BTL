console.log(localStorage.idStudent);
console.log(localStorage.idCourse);

// Khi trang được load, kiểm tra xem có dữ liệu trong Local Storage không và cập nhật bảng
document.addEventListener("DOMContentLoaded", function() {
    getScore().then(data => {
        updateTable(data);
    } );


});

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
async function updateScores(event) {
    event.preventDefault();

    // Lưu giá trị vào Local Storage
    localStorage.setItem('bt_dd', document.getElementById("bt-dd-input").value);
    localStorage.setItem('bt_lon', document.getElementById("bt-lon-input").value );
    localStorage.setItem('kt_giua', document.getElementById("kt-giua-input").value);
    localStorage.setItem('kt_cuoi', document.getElementById("kt-cuoi-input").value);

    let url = 'http://localhost:8080/course/student/score/update?' 
    + new URLSearchParams({
        idStudent: localStorage.idStudent,
        idCourse: localStorage.idCourse,
        midTerm: localStorage.getItem('kt_giua'),
        finalExam: localStorage.getItem('kt_cuoi'),
        other: localStorage.getItem('bt_dd'),
        assignment: localStorage.getItem('bt_lon')
    }) ;
    
    let fetchs = await fetch(url, {
        method: 'PUT',
        mode: 'cors'
    }).then(result => {
        if (!result.ok) throw Error(result.statusText);
        return result.json();
    }).then(res => {
        return res;
    })
    
    closeModal();
    location.reload();
}

// Hàm cập nhật bảng với dữ liệu từ Local Storage
function updateTable(data) {
    console.log(data);
    console.log(111);
    var bt_dd = parseFloat(localStorage.getItem('bt_dd')) || data.other;
    var bt_dd_note = localStorage.getItem('bt_dd_note') || '';
    var bt_dd_percent = 10;
    var bt_lon = parseFloat(localStorage.getItem('bt_lon')) || data.assignment;
    var bt_lon_note = localStorage.getItem('bt_lon_note') || '';
    var bt_lon_percent = 20;
    var kt_giua = parseFloat(localStorage.getItem('kt_giua')) || data.midTerm;
    var kt_giua_note = localStorage.getItem('kt_giua_note') || '';
    var kt_giua_percent = 20;
    var kt_cuoi = parseFloat(localStorage.getItem('kt_cuoi')) || data.finalExam;
    var kt_cuoi_note = localStorage.getItem('kt_cuoi_note') || '';
    var kt_cuoi_percent = 50;


    // Tính tổng kết và cập nhật bảng
    var tong_ket = (bt_dd * bt_dd_percent / 100) + (bt_lon * bt_lon_percent / 100) + (kt_giua * kt_giua_percent / 100) + (kt_cuoi * kt_cuoi_percent / 100);
    if (bt_dd < 2 || bt_lon < 2 || kt_giua < 2 || kt_cuoi < 2) {
        tong_ket = 0;
    }

    document.getElementById("bt-dd").textContent = bt_dd;
    document.getElementById("bt-lon").textContent = bt_lon;
    document.getElementById("kt-giua").textContent = kt_giua;
    document.getElementById("kt-cuoi").textContent = kt_cuoi;
    document.getElementById("tong-ket").textContent = tong_ket.toFixed(2);
    
    let savedComment = data.message;
    localStorage.setItem("comment", savedComment);
        // Lấy thông tin sinh viên
        var currentName = data.name;
        var currentID = data.id;
        var currentEmail = data.email;

        document.querySelector('.name-id').innerText = currentName + ' - ' + currentID;
        document.querySelector('.mail').innerText = currentEmail;
}





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

async function confirmEvaluation() {
    var comment = commentInput.value;

    let url = 'http://localhost:8080/course/student/score/update?' 
    + new URLSearchParams({
        idStudent: localStorage.idStudent,
        idCourse: localStorage.idCourse,
        midTerm: localStorage.getItem('kt_giua'),
        finalExam: localStorage.getItem('kt_cuoi'),
        other: localStorage.getItem('bt_dd'),
        assignment: localStorage.getItem('bt_lon'),
        message: comment
    }) ;
    
    let fetchs = await fetch(url, {
        method: 'PUT',
        mode: 'cors'
    }).then(result => {
        if (!result.ok) throw Error(result.statusText);
        return result.json();
    }).then(res => {
        return res;
    })

    // Lưu comment vào localStorage
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



var headingNode = document.getElementById('headingID');

var headingNodes = document.getElementsByClassName('headingClass');
var headingNodes = document.getElementsByTagName('p');

var headingNode = document.querySelector('.box .heading-2:first-child');
var headingNode = document.querySelector('.box .heading-2:nth-child(2)');
var headingNodes = document.querySelectorAll('.box .heading-2');

var headingNodes = document.querySelector

async function getScore() {
    let url = 'http://localhost:8080/course/student/score?';
    url = url + new URLSearchParams({
        idCourse: localStorage.idCourse,
        idStudent: localStorage.idStudent
    });
    let returnVal = await fetch(url, {
        method: 'GET',
        mode: 'cors'
    }).then(data => {
        if (!data.ok){
            throw Error(data.statusText);
        }

        return data.json();
    });

    return returnVal;
}