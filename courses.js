document.addEventListener("DOMContentLoaded", function() {
    const searchInput = document.querySelector(".search__input");
    const courseNames = document.querySelectorAll(".course-name");

    searchInput.addEventListener("input", function() {
        const searchTerm = this.value.toLowerCase();

        courseNames.forEach(function(courseName) {
            const name = courseName.textContent.toLowerCase();
            const course = courseName.closest(".course-each");

            if (name.includes(searchTerm)) {
                course.style.display = "block";
            } else {
                course.style.display = "none";
            }
        });
    });
});



