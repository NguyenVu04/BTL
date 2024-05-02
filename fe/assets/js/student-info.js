let idStudent = '2213954'

// let asd = document.getElementById('asd');
// console.log(asd.innerHTML);
document.addEventListener('DOMContentLoaded', function() {
    
    getInfo().then(data => {
        addinner(data);

    });
    getCourse().then(data => {
        for (let i = 0 ; i < data.length; i++) {
            const val = data[i];
            // console.log(val.id, val.name);
            getScore(val);
        }
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

async function getCourse() {
    let url = 'http://localhost:8080/course/student/id?';
    let returnVal = await
    fetch(url 
            + new URLSearchParams({
            idStudent: idStudent
            }) 
    ,{
        mode: 'cors',
        method: 'GET'
    })
    .then(res => {
        if (!res.ok){
            throw Error(res.statusText);
        }
        return res.json();}
    );
    
    for (let i = 0 ; i < returnVal.length; i++) {
        const val = returnVal[i].id;
        console.log(val);
    }
    // document.addEventListener('DOMContentLoaded', addinner(returnVal));
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
async function getScore(course) {
    let url = 'http://localhost:8080/course/student/score?';
    url = url + new URLSearchParams({
        idCourse: course.id,
        idStudent: idStudent
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
    // console.log(course.id, course.name, returnVal);
    add_result(course.id, course.name, returnVal);
    return returnVal;
}
function add_result(idCourse, nameCourse, data) {
    let result = document.getElementById('result-info');

    let adder = `<div class="info-body-content">
                    <div>
                        <p>{nameCourse}</p>
                    </div>
                    <div>
                        {other}
                    </div>
                    <div>{assignment}</div>
                    <div>{midterm}</div>
                    <div>{finalexam}</div>
                    <div>{total}</div>
                </div>`;
    adder = adder.replace('{nameCourse}', nameCourse);
    adder = adder.replace('{other}', data.other);
    adder = adder.replace('{assignment}', data.assignment);
    adder = adder.replace('{midterm}', data.midTerm);
    adder = adder.replace('{finalexam}', data.finalExam);

    let total = (data.other*10 + data.assignment*20 + data.midTerm*20 + data.finalExam*50)/100;
    adder = adder.replace('{total}', total.toString());
    result.innerHTML += adder;
}
function addinner(data) {

    let idStudentHTML = document.getElementById('idStudent');
    let idStudentHTML1 = document.getElementById('idStudent1');
    idStudentHTML.innerHTML = data.id;
    idStudentHTML1.innerHTML = data.id;
    
    let personalId = document.getElementById('personalId');
    let personalId1 = document.getElementById('personalId1');
    personalId.innerHTML = data.personalId;
    personalId1.innerHTML = data.personalId;

    let nameHTML = document.getElementById('name');
    let nameHTML1 = document.getElementById('name1');
    nameHTML.innerHTML = data.name;
    nameHTML1.innerHTML = data.name;

    let phoneNumberHTML = document.getElementById('phoneNumber');
    let phoneNumberHTML1 = document.getElementById('phoneNumber1');
    phoneNumberHTML.innerHTML = data.phoneNumber;
    phoneNumberHTML1.innerHTML = data.phoneNumber;

    let dobHTML = document.getElementById('dob');
    let dobHTML1 = document.getElementById('dob1');
    var d = new Date(data.dob.seconds*1000);
    dobHTML.innerHTML = d;
    dobHTML1.innerHTML = d;

    let emailHTML = document.getElementById('email');
    let emailHTML1 = document.getElementById('email1');
    emailHTML.innerHTML = data.email;
    emailHTML1.innerHTML = data.email;
    
    let genderHTML = document.getElementById('gender');
    let genderHTML1 = document.getElementById('gender1');
    genderHTML.innerHTML = data.gender;
    genderHTML1.innerHTML = data.gender;
    
    let addressHTML = document.getElementById('address');
    let addressHTML1 = document.getElementById('address1');
    addressHTML.innerHTML = data.address;
    addressHTML1.innerHTML = data.address;

    let country = document.getElementById('country');
    let country1 = document.getElementById('country1');
    country.innerHTML = data.country;
    country1.innerHTML = data.country;

    let majorHTML = document.getElementById('major');
    let majorHTML1 = document.getElementById('major1');
    majorHTML.innerHTML = data.major;
    majorHTML1.innerHTML = data.major;
}
