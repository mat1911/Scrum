$(document).ready(function () {
    $('#confirm-recover').on('show.bs.modal', function (e) {
        var url = $(e.relatedTarget).data('href');
        $("#recoverProjectForm").attr("action", "http://localhost:8080" + url)
    });
});