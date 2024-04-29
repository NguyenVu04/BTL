let idStudent = '2213955'

// let asd = document.getElementById('asd');
// console.log(asd.innerHTML);
document.addEventListener('DOMContentLoaded', function() {
    
    getInfo().then(data => {
        console.log(123);
        addinner(data);
    });
});
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


async function updateInfo() {
    let url = 'http://localhost:8080/student/adjustion/id?' + new URLSearchParams({
        id: idStudent,
        name: nameStudent,
        year: year,
        month: month,
        date: date,
        gender: gender,
        country: country,
        personalId: personalId,
        phoneNumber: phoneNumber,
        email: email,
        address: address,
        major : major
    });

    let returnVal = await fetch(url, {
        method: 'PUT',
        mode: 'cors'
    }).then(respone => {
        if (!respone.ok) throw Error(respone.statusText);
        return respone.json();
    }).then(data => {
        return data.json();
    });

    return returnVal;
}


async function getInfo() {
    let url = 'http://localhost:8080/student/id?' + new URLSearchParams({
        id: idStudent
    });

    let returnVal = await fetch(url, {
        method: 'GET',
        mode: 'cors'
    }).then(respone => {
        if (!respone.ok) throw Error(respone.statusText);
        return respone.json();
    }).then(data => {
        return data;
    });

    return returnVal;
}
function addinner(data) {

    let idStudentHTML = document.getElementById('idStudent');
    idStudentHTML.innerHTML = data.id;
    
    let personalId = document.getElementById('personalId');
    personalId.innerHTML = data.personalId;

    let nameHTML = document.getElementById('name');
    nameHTML.innerHTML = data.name;

    let phoneNumberHTML = document.getElementById('phoneNumber');
    phoneNumberHTML.innerHTML = data.phoneNumber;

    let dobHTML = document.getElementById('dob');
    var d = new Date(data.dob.seconds*1000);
    dobHTML.innerHTML = d;

    let emailHTML = document.getElementById('email');
    emailHTML.innerHTML = data.email;
    
    let genderHTML = document.getElementById('gender');
    genderHTML.innerHTML = data.gender;
    
    let addressHTML = document.getElementById('address');
    addressHTML.innerHTML = data.address;

    let country = document.getElementById('country');
    country.innerHTML = data.country;

    let majorHTML = document.getElementById('major');
    majorHTML.innerHTML = data.major;
}
