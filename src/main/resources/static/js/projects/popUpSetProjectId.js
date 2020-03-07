console.log("projectId");
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
