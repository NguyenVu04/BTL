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
        
        var bt_dd = parseFloat(document.getElementById("bt-dd-input").value) || 0;
        var bt_dd_note = document.getElementById("bt-dd-note-input").value;
        var bt_dd_percent = parseFloat(document.getElementById("bt-dd-percent-input").value) || 0;
        var bt_lon = parseFloat(document.getElementById("bt-lon-input").value) || 0;
        var bt_lon_note = document.getElementById("bt-lon-note-input").value;
        var bt_lon_percent = parseFloat(document.getElementById("bt-lon-percent-input").value) || 0;
        var kt_giua = parseFloat(document.getElementById("kt-giua-input").value) || 0;
        var kt_giua_note = document.getElementById("kt-giua-note-input").value;
        var kt_giua_percent = parseFloat(document.getElementById("kt-giua-percent-input").value) || 0;
        var kt_cuoi = parseFloat(document.getElementById("kt-cuoi-input").value) || 0;
        var kt_cuoi_note = document.getElementById("kt-cuoi-note-input").value;
        var kt_cuoi_percent = parseFloat(document.getElementById("kt-cuoi-percent-input").value) || 0;
        
        // Check if total weight exceeds 100
        var total_weight = bt_dd_percent + bt_lon_percent + kt_giua_percent + kt_cuoi_percent;
        if (total_weight > 100) {
            alert("Tổng trọng số không được vượt quá 100.");
            return;
        }
        
        // Calculate total
        var tong_ket = (bt_dd * bt_dd_percent / 100) + (bt_lon * bt_lon_percent / 100) + (kt_giua * kt_giua_percent / 100) + (kt_cuoi * kt_cuoi_percent / 100);

        if (bt_dd === 0 || bt_lon === 0 || kt_giua === 0 || kt_cuoi === 0) {
            tong_ket=0;
        }
        // Update table
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

        closeModal();
    }


// hàm nhận xét
// Lấy modal
var evaluateModal = document.getElementById("evaluatemodal");

function showComment() {
    evaluateModal.style.display = "block";
}

function closeEvaluateModal() {
    evaluateModal.style.display = "none";
}

function confirmEvaluation() {
    var comment = document.getElementById("commentInput").value;
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
