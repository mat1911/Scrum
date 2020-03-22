$(document).ready(function () {
    $('#retireStoryModal').on('show.bs.modal', function (e) {
        var url = $(e.relatedTarget).data('href');
        $("#retireStoryForm").attr("action", "http://localhost:8080" + url)
    });
});

$(document).ready(function () {
    $('#editSprintModal').on('show.bs.modal', function (e) {
        var sprintId = $(e.relatedTarget).data('storyid');
        var sprint = {
            'title': '',
            'description': '',
            'beginDate': '',
            'endDate': ''
        };

        for (const currentSprint of sprints){
            if(currentSprint.id === sprintId){
                sprint = currentSprint;
            }
        }

        $('#formSprintTitle').val(sprint.title);
        $('#formSprintDescription').val(sprint.description);
        $('#dataSprintBeginForm').val(sprint.beginDate);
        $('#dataSprintEndForm').val(sprint.endDate);

        if(sprintId == null){
            $('#sprintForm').attr('action', "createSprint/");
        }else {
            $('#sprintForm').attr('action', "updateSprint/" + sprintId);
        }
    });
});

