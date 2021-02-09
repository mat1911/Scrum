/*set up project id for invitation*/
$(document).ready(function () {
    $(".inviteUserDialog").click(function () {
            var projectId = $(this)
                .parent()
                .parent()
                .attr('id');

            $(".modal-body #projectId").val(projectId);
        }
    )
});
/*updating selected project id*/
$(document).ready(function () {
    $(".selectButton").click(function () {

            var projectId = $(this)
                .parent()
                .parent()
                .attr('id');

            $.ajax({
                url: "http://localhost:8080/selectProject/" + projectId,
                type: 'POST'
            });
        }
    )
});
