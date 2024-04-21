

// này là phần cho avatar


// Lấy phần tử cửa sổ thay đổi ảnh đại diện và nút đóng
var modal = document.getElementById("changeAvatarModal");
var closeBtn = document.getElementsByClassName("close")[0];

// Lấy phần tử ảnh đại diện và thêm sự kiện click
var avatar = document.getElementById("avatar");
avatar.addEventListener("click", function() {
  modal.style.display = "block";
});

// Sự kiện đóng cửa sổ
closeBtn.onclick = function() {
  modal.style.display = "none";
}

// Đóng cửa sổ khi click bên ngoài cửa sổ
window.onclick = function(event) {
  if (event.target == modal) {
    modal.style.display = "none";
  }
}

// Hiển thị trước ảnh khi người dùng chọn ảnh từ máy tính
document.getElementById("newAvatar").addEventListener("change", function() {
  var reader = new FileReader();
  reader.onload = function(e) {
    document.getElementById("preview").src = e.target.result;
  };
  reader.readAsDataURL(this.files[0]);
});

// Hàm lưu ảnh đại diện mới
function saveNewAvatar() {
  var newAvatarFile = document.getElementById("newAvatar").files[0];
  
  // Kiểm tra xem người dùng đã chọn ảnh hay chưa
  if (newAvatarFile) {
    var reader = new FileReader();
    reader.onload = function(e) {
      var newAvatarSrc = e.target.result; // Đường dẫn của ảnh mới
      var avatarImg = document.querySelector("#avatar img");
      avatarImg.src = newAvatarSrc; // Cập nhật đường dẫn của ảnh trong class "avatar"
    };
    reader.readAsDataURL(newAvatarFile);
  } else {
    // Hiển thị thông báo nếu người dùng không chọn ảnh
    alert("Vui lòng chọn một ảnh để thay đổi!");
  }
  
  modal.style.display = "none"; // Đóng cửa sổ sau khi lưu
}



// này là phần cho upload file văn bằng


// Lấy phần tử cửa sổ modal và biểu tượng
var uploadModal = document.getElementById("uploadModal");
var certiIcon = document.querySelector(".fa-pen-to-square");

// Mở cửa sổ modal khi click vào biểu tượng
function openModal() {
  uploadModal.style.display = "block";
}

// Đóng cửa sổ modal khi click vào nút đóng
function closeModal() {
  uploadModal.style.display = "none";
}

// Hiển thị trước ảnh khi người dùng chọn ảnh từ máy tính
document.getElementById("certificateFile").addEventListener("change", function() {
  var reader = new FileReader();
  reader.onload = function(e) {
    var previewFrame = document.getElementById("previewFrame");
    previewFrame.style.backgroundImage = "url('" + e.target.result + "')";
  };
  reader.readAsDataURL(this.files[0]);
});

// Tải lên văn bằng
// Tải lên văn bằng
function openModal() {
  document.getElementById('uploadModal').style.display = 'block';
}

function closeModal() {
  document.getElementById('uploadModal').style.display = 'none';
}

function uploadCertificate() {
  // Xử lý việc tải lên file và hiển thị preview
  var input = document.getElementById('certificateFile');
  var file = input.files[0];
  var fileType = file.type;

  if (fileType.includes('image')) {
      // Xử lý nếu tệp là hình ảnh
      var reader = new FileReader();
      reader.onload = function(e) {
          var img = document.createElement('img');
          img.src = e.target.result;
          img.style.maxWidth = '100%'; // Điều chỉnh kích cỡ phù hợp

          // Xóa các phần tử <p> không cần thiết trong class "vanbang"
          var vanbang = document.querySelector('.vanbang');
          vanbang.innerHTML = '';

          // Thêm ảnh vào khung vanbang
          vanbang.appendChild(img);
      }
      reader.readAsDataURL(file);
  } else if (fileType === 'application/pdf') {
      // Xử lý nếu tệp là PDF
      var reader = new FileReader();
      reader.onload = function(e) {
          var pdfUrl = e.target.result;

          // Tạo một thẻ iframe để hiển thị PDF
          var iframe = document.createElement('iframe');
          iframe.src = pdfUrl;
          iframe.style.width = '100%';
          iframe.style.height = '100%';
          iframe.style.border = 'none';

          // Xóa các phần tử <p> không cần thiết trong class "vanbang"
          var vanbang = document.querySelector('.vanbang');
          vanbang.innerHTML = '';

          // Thêm iframe vào khung vanbang
          vanbang.appendChild(iframe);

          // Hiển thị liên kết tải về
          var downloadLink = document.getElementById('downloadLink');
          downloadLink.style.display = 'block';
          var downloadButton = document.getElementById('downloadButton');
          downloadButton.href = pdfUrl;
          downloadButton.download = 'certificate.pdf';
      }
      reader.readAsDataURL(file);
  } else {
      alert('Định dạng tệp không được hỗ trợ.');
  }

  // Đóng cửa sổ modal sau khi tải lên
  closeModal();
}




// phần sửa thông tin 

// Open edit modal
function openEditForm() {
  // Display edit modal
  var modal = document.getElementById('editModal');
  modal.style.display = 'block';
}

// Close edit modal
function closeEditModal() {
  var modal = document.getElementById('editModal');
  modal.style.display = 'none';
}

// Save edited information
function saveInfo() {
  var form = document.getElementById('infoForm');
  var fullname = form.elements['fullname'].value;
  var birthdate = form.elements['birthdate'].value;
  var khoa = form.elements['khoa'].value;
  var dienthoai = form.elements['dienthoai'].value;
  var email = form.elements['email'].value;
  var hocvi = form.elements['hocvi'].value;
  var nuoc = form.elements['nuoc'].value;
  var nganh = form.elements['nganh'].value;
  var cn = form.elements['cn'].value;
  var chucdanh = form.elements['chucdanh'].value;
  var linhvuc = form.elements['linhvuc'].value;
  var huong = form.elements['huong'].value;
  var lats = form.elements['lats'].value;
  var lv = form.elements['lv'].value;
  var daymh = form.elements['daymh'].value;
  var ctkh = form.elements['ctkh'].value;

  var personalInfo = document.getElementById('personalInfo');

  personalInfo.innerHTML = '<li><strong>Họ và tên:</strong> ' + fullname + '</li>' +
                            '<li><strong>Ngày tháng năm sinh:</strong> ' + birthdate + '</li>' +
                            '<li><strong>Khoa:</strong> ' + khoa + '</li>' +
                            '<li><strong>Điện thoại liên hệ:</strong> ' + dienthoai + '</li>' +
                            '<li><strong>Email:</strong> ' + email + '</li>' +
                            '<li><strong>Học vị:</strong> ' + hocvi + '</li>' +
                            '<li><strong>Nước tốt nghiệp:</strong> ' + nuoc + '</li>' +
                            '<li><strong>Ngành:</strong> ' + nganh + '</li>' +
                            '<li><strong>Chuyên ngành:</strong> ' + cn + '</li>' +
                            '<li><strong>Chức danh khoa học:</strong> ' + chucdanh + '</li>' +
                            '<li><strong>Lĩnh vực chuyên môn hiện tại:</strong> ' + linhvuc + '</li>' +
                            '<li><strong>Các hướng nghiên cứu chính:</strong> ' + huong + '</li>' +
                            '<li><strong>Số LATS đã hướng dẫn thành công tại trường Đại học Bách khoa Tp.HCM (từ năm 2004):</strong> ' + lats + '</li>' +
                            '<li><strong>Số LVThS đã hướng dẫn thành công tại trường Đại học Bách khoa Tp.HCM (từ năm 2004):</strong> ' + lv + '</li>' +
                            '<li><strong>Giảng dạy các môn học đại học:</strong> ' + daymh + '</li>' +
                            '<li><strong>Các công trình khoa học đã công bố:</strong> ' + ctkh + '</li>';
  // Add other personal information fields here

  closeEditModal();
}




