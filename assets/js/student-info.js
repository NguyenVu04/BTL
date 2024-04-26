
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
// các biến cho thông tin sinh viên để sửa dữ liệu
function showEditModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "block";
    // Điền các trường thông tin từ trang chính vào form modal
    //console.log(document.getElementById("studentID"));
    document.getElementById("editStudentID").value = document.getElementById("studentID").innerText;
    document.getElementById("editCitizenID").value = document.getElementById("citizenID").innerText;
    document.getElementById("editFullName").value = document.getElementById("fullName").innerText;
    document.getElementById("editPhoneNumber").value = document.getElementById("phoneNumber").innerText;
    document.getElementById("editDOB").value = document.getElementById("dob").innerText;
    document.getElementById("editEmail").value = document.getElementById("email").innerText;
    document.getElementById("editGender").value = document.getElementById("gender").innerText;
    document.getElementById("editPermanentAddress").value = document.getElementById("permanentAddress").innerText;
    document.getElementById("editBirthPlace").value = document.getElementById("birthPlace").innerText;
    document.getElementById("editMajor").value = document.getElementById("major").innerText;
}

function closeModal() {
    var modal = document.getElementById("myModal");
    modal.style.display = "none";
}

function saveChanges() {
    var modal = document.getElementById("myModal");
    var studentID = document.getElementById("editStudentID").value;
    var citizenID = document.getElementById("editCitizenID").value;
    var fullName = document.getElementById("editFullName").value;
    var phoneNumber = document.getElementById("editPhoneNumber").value;
    var dob = document.getElementById("editDOB").value;
    var email = document.getElementById("editEmail").value;
    var gender = document.getElementById("editGender").value;
    var permanentAddress = document.getElementById("editPermanentAddress").value;
    var birthPlace = document.getElementById("editBirthPlace").value;
    var major = document.getElementById("editMajor").value;
    
    // Hiển thị dữ liệu đã chỉnh sửa trên trang chính
    document.getElementById("studentID").innerText = studentID;
    document.getElementById("citizenID").innerText = citizenID;
    document.getElementById("fullName").innerText = fullName;
    document.getElementById("phoneNumber").innerText = phoneNumber;
    document.getElementById("dob").innerText = dob;
    document.getElementById("email").innerText = email;
    document.getElementById("gender").innerText = gender;
    document.getElementById("permanentAddress").innerText = permanentAddress;
    document.getElementById("birthPlace").innerText = birthPlace;
    document.getElementById("major").innerText = major;
    
    modal.style.display = "none"; // Đóng modal sau khi lưu
}