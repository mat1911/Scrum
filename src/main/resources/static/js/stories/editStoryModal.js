$(document).ready(function () {
    $('#editStoryModal').on('show.bs.modal', function (e) {
        var storyId = $(e.relatedTarget).data('storyid');
        var story = {
            'title': '',
            'shortDescription': '',
            'description': '',
            'acceptanceCriteria': '',
            'storyPoints': '',
            'sprintId': ''
        };

        for (const currentStory of stories){
            if(currentStory.id === storyId){
                story = currentStory;
            }
        }

        $('#formTitle').val(story.title);
        $('#formShortDescription').val(story.shortDescription);
        $('#formDescription').val(story.description);
        $('#formAcceptanceCriteria').val(story.acceptanceCriteria);
        $('#formStoryPoints').val(story.storyPoints);
        $('#formSprintId').val(story.sprintId);

        if(storyId == null){
            $('#storyForm').attr('action', "createStory/");
        }else {
            $('#storyForm').attr('action', "updateStory/" + storyId);
        }
    });
});