$(function () {

    $(".draggable").ready().draggable({
        containment: "#droppable",
        revert: "invalid",
        stack: ".draggable",
        drag: function (event, ui) {
            ui.helper.css({
                transform: "rotate(6deg)"
            });
        }
    });


});