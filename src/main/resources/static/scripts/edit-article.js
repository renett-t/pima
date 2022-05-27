function enableCheckBoxes() {
    console.log("ABOUT TO ENABLE CHECKBOXES")
    var tags = $("#tags-wrapper").data("tags")
    if (tags != null && tags.length > 0) {
        for (let i = 0; i < tags.length; i++) {
            console.log("Enabled " + tags[i].id)
            $("#tag" + tags[i].id).checked = true
        }
    }
}

function enableCheckBoxesListeners() {
    var specialCbsId = "tag-1";
    let cbs = $("input[name=tags]");

    function check(checked) {
        cbs.each(function () {
            if (this.id !== specialCbsId) {
                this.checked = checked;
            }
        });
    }

    function disableOtherCheckBoxes() {
        if (this.checked)
            check(false);
    }

    function disableCheckbox() {
        $("#tag-1").checked = false;
    }

    function checkBoxesListeners() {
        cbs.each(function () {
            if (this.id !== specialCbsId) {
                this.change(function () {
                    if (this.checked) {
                        console.log("disabling tag-1")
                        disableCheckbox()
                    }
                })
            }
        })
    }

    $("#tag-1").click(disableOtherCheckBoxes);
    checkBoxesListeners()
}

$(document).ready(function () {
    enableCheckBoxes();
    enableCheckBoxesListeners();
});
