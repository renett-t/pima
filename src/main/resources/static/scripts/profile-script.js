function sendDeleteProfileRequest() {
    var token_value = $("meta[name='_csrf']").attr("content");
    var header_title = $("meta[name='_csrf_header']").attr("content");

    $.ajaxSetup({
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header_title, token_value);
        }
    });

    function getContext() {
        return cntx
    }

    var isConfirmed = confirm(" Вы действительно хотите удалить свой аккаунт?");
    if (isConfirmed) {
        let req = getContext() + "/profile/delete"
        console.log('sending delete profile request to - ' + req)

        $.ajax({
            url: req,
            method: "DELETE",
            success: function (result) {
                console.log(result)
            },
            error: function (xhr, errorThrown) {
                if (xhr.status === 403)
                    alert("Sorry, authorization required.")
                else
                    alert("Sorry, some problems. Cannot process request.")
            },
            complete: function () {
                location.reload()
            }
        });
    }
}

$(document).ready(function () {
    $("#delete-icon-request").click(sendDeleteProfileRequest);
});
