/*$(document).ready(function () {
    $('#confirm-delete').on('show.bs.modal', function (e) {
        $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
    });
});*/

$(document).ready(function () {
    $('#confirm-delete').on('show.bs.modal', function (e) {
        var url = $(e.relatedTarget).data('href');
        $("#deleteUserForm").attr("action", "http://localhost:8080" + url)
    });
});