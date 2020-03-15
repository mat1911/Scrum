function createDetailsButtonListeners() {
    const details = document.getElementsByName("details");

    let detail;
    for (var i = 0; i < details.length; i++) {
        detail = details[i];
        detail.addEventListener("click", function () {
            document.getElementById("select").innerText=this.getAttribute("id");
        })
    }
}
function load() {
    createDetailsButtonListeners();
    //Delete button listener:
    const deleteButton = document.getElementById("deleteStoryButton");
    deleteButton.addEventListener("click", function() {
        let number = document.getElementById("select").innerText;
        $.ajax({
            url: "http://localhost:8080/stories/" + number,
            type: 'DELETE'
        });
        location.reload();
    })
}

