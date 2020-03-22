$(document).ready(function () {
    $('#deleteStoryModal').on('show.bs.modal', function (e) {
        var url = $(e.relatedTarget).data('href');
        $("#deleteStoryForm").attr("action", "http://localhost:8080" + url)
    });
});