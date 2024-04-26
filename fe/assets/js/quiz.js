// Hàm để chuyển đổi dữ liệu từ input thành text và lưu vào local storage
function convertToText() {
    var questions = [];

    // Lấy tất cả các phần tử có class là "qs-option" và "qs-question"
    var optionLabels = document.querySelectorAll('.qs-option');
    var questionLabels = document.querySelectorAll('.qs-question');

    // Chuyển đổi các câu trả lời từ input thành text và lưu vào mảng questions
    optionLabels.forEach(function(label, index) {
        var input = label.querySelector('.qs-option__input');
        var inputValue = input.value;
        questions[index] = questions[index] || {};
        questions[index].chooseA = inputValue;
    });

    // Chuyển đổi các câu hỏi từ input thành text và lưu vào mảng questions
    questionLabels.forEach(function(label, index) {
        var input = label.querySelector('.qs-question__input');
        var inputValue = input.value;
        questions[index] = questions[index] || {};
        questions[index].question = inputValue;
    });

    // Lưu mảng questions vào local storage
    localStorage.setItem('questions', JSON.stringify(questions));

    // Ẩn nút "Tạo bài kiểm tra"
    document.querySelector('.test-submit').style.display = 'none';

    // Hiển thị lại nút "Quay lại quiz"
    document.querySelector('.back-to-quiz').style.display = 'block';
}

// Hàm để khôi phục dữ liệu từ local storage và hiển thị lại input
function backToQuiz() {
    // Lấy mảng questions từ local storage
    var questions = JSON.parse(localStorage.getItem('questions'));

    // Lấy tất cả các phần tử có class là "qs-option" và "qs-question"
    var optionLabels = document.querySelectorAll('.qs-option');
    var questionLabels = document.querySelectorAll('.qs-question');

    // Gán lại giá trị từ mảng questions cho input và hiển thị input
    optionLabels.forEach(function(label, index) {
        var input = label.querySelector('.qs-option__input');
        input.value = questions[index].chooseA;
        input.style.display = 'block';
    });

    questionLabels.forEach(function(label, index) {
        var input = label.querySelector('.qs-question__input');
        input.value = questions[index].question;
        input.style.display = 'block';
    });

    // Hiển thị lại nút "Tạo bài kiểm tra"
    document.querySelector('.test-submit').style.display = 'block';

    // Ẩn nút "Quay lại quiz"
    document.querySelector('.back-to-quiz').style.display = 'none';
}
