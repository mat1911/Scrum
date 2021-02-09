$(document).ready(function () {
    $('#retireStoryModal').on('show.bs.modal', function (e) {
        var url = $(e.relatedTarget).data('href');
        $("#retireStoryForm").attr("action", url)
    });
});

$(document).ready(function () {
    $('#editSprintModal').on('show.bs.modal', function (e) {
        var sprintid = $(e.relatedTarget).data('sprintid');
        var sprint = {
            'title': '',
            'description': '',
            'beginDate': '',
            'endDate': ''
        };

        for (const currentSprint of sprints){
            if(currentSprint.id === sprintid){
                sprint = currentSprint;
            }
        }

        $('#formSprintTitle').val(sprint.title);
        $('#formSprintDescription').val(sprint.description);
        $('#dataSprintBeginForm').val(sprint.beginDate);
        $('#dataSprintEndForm').val(sprint.endDate);

        if(sprintid == null){
            $('#sprintForm').attr('action', "createSprint/");
        }else {
            $('#sprintForm').attr('action', "updateSprint/" + sprintid);
        }
    });
});

$(document).ready(function () {
    $('#deleteSprintModal').on('show.bs.modal', function (e) {
        var url = $(e.relatedTarget).data('href');
        var sprintId = $(e.relatedTarget).data('sprintid');

        $("#deleteSprintForm").attr("action", url + sprintId)
    });
});

