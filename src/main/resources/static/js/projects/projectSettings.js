
$(document).ready(function () {
    $('#confirm-delete').on('show.bs.modal', function (e) {
        var url = $(e.relatedTarget).data('href');
        $("#deleteUserForm").attr("action", "http://localhost:8080" + url)
    });
});