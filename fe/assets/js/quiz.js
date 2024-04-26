<<<<<<< HEAD:fe/assets/js/quiz.js
// Hàm để chuyển đổi dữ liệu từ input thành text và lưu vào local storage
function convertToText() {
    var questions = [];

=======
function convertToText() {
>>>>>>> remotes/origin/master:assets/js/quiz.js
    // Lấy tất cả các phần tử có class là "qs-option" và "qs-question"
    var optionLabels = document.querySelectorAll('.qs-option');
    var questionLabels = document.querySelectorAll('.qs-question');

<<<<<<< HEAD:fe/assets/js/quiz.js
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

=======
    // Chuyển đổi các câu trả lời từ input thành text
    optionLabels.forEach(function(label) {
        var input = label.querySelector('.qs-option__input');
        var inputValue = input.value;
        var newTextElement = document.createElement('span');
        newTextElement.textContent = inputValue;
        newTextElement.classList.add('qs-option__text');
        label.appendChild(newTextElement);
        input.style.display = 'none';
    });

    // Chuyển đổi các câu hỏi từ input thành text
    questionLabels.forEach(function(label) {
        var input = label.querySelector('.qs-question__input');
        var inputValue = input.value;
        var newTextElement = document.createElement('span');
        newTextElement.textContent = inputValue;
        newTextElement.classList.add('qs-question__text');
        label.appendChild(newTextElement);
        input.style.display = 'none';
    });

>>>>>>> remotes/origin/master:assets/js/quiz.js
    // Ẩn nút "Tạo bài kiểm tra"
    document.querySelector('.test-submit').style.display = 'none';

    // Hiển thị lại nút "Quay lại quiz"
    document.querySelector('.back-to-quiz').style.display = 'block';
}

<<<<<<< HEAD:fe/assets/js/quiz.js
// Hàm để khôi phục dữ liệu từ local storage và hiển thị lại input
function backToQuiz() {
    // Lấy mảng questions từ local storage
    var questions = JSON.parse(localStorage.getItem('questions'));

=======

function backToQuiz() {
>>>>>>> remotes/origin/master:assets/js/quiz.js
    // Lấy tất cả các phần tử có class là "qs-option" và "qs-question"
    var optionLabels = document.querySelectorAll('.qs-option');
    var questionLabels = document.querySelectorAll('.qs-question');

<<<<<<< HEAD:fe/assets/js/quiz.js
    // Gán lại giá trị từ mảng questions cho input và hiển thị input
    optionLabels.forEach(function(label, index) {
        var input = label.querySelector('.qs-option__input');
        input.value = questions[index].chooseA;
        input.style.display = 'block';
    });

    questionLabels.forEach(function(label, index) {
        var input = label.querySelector('.qs-question__input');
        input.value = questions[index].question;
=======
    // Xóa bỏ các phần tử span đã thêm từ text nếu chúng tồn tại
    optionLabels.forEach(function(label) {
        var textElement = label.querySelector('.qs-option__text');
        if (textElement) {
            textElement.remove();
        }
    });

    questionLabels.forEach(function(label) {
        var textElement = label.querySelector('.qs-question__text');
        if (textElement) {
            textElement.remove();
        }
    });

    // Chuyển đổi các câu trả lời từ text thành input
    optionLabels.forEach(function(label) {
        var input = label.querySelector('.qs-option__input');
        input.style.display = 'block';
    });

    // Chuyển đổi các câu hỏi từ text thành input
    questionLabels.forEach(function(label) {
        var input = label.querySelector('.qs-question__input');
>>>>>>> remotes/origin/master:assets/js/quiz.js
        input.style.display = 'block';
    });

    // Hiển thị lại nút "Tạo bài kiểm tra"
    document.querySelector('.test-submit').style.display = 'block';

    // Ẩn nút "Quay lại quiz"
    document.querySelector('.back-to-quiz').style.display = 'none';
}
<<<<<<< HEAD:fe/assets/js/quiz.js
=======



>>>>>>> remotes/origin/master:assets/js/quiz.js
