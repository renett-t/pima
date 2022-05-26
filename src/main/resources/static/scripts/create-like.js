function sendRequest() {
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

    artId = $("#like-icon-request").data("article")
    let url = getContext() + "/ajax/articles/" + artId + "/like"
    console.log('sending like request to - ' + url)

    $.ajax({
        url: url,
        method: "POST",
        dataType: 'json',
        cache: false,
        success: function (json) {
            if (json.status === "201 CREATED" || json.status === "201") {
                $("#like-icon-request").attr('src', getContext() + json.source)
                var count = parseInt($("#article-likes-count").text())
                if (json.liked === false)
                    $("#article-likes-count").text(count - 1)
                else
                    $("#article-likes-count").text(count + 1)
            } else {
                alert("Sorry, there was a problem, but request was accepted!");
                console.log(json.message)
            }
        },
        error: function (xhr, errorThrown) {
            if (xhr.status === 404)
                alert("Sorry, there's no requested page.");
            else if (xhr.status === 403)
                alert("Sorry, authorization required.")
            else
                alert("Sorry, some problems. Cannot process request.")
        }
    });
}

$(document).ready(function () {
    $("#like-icon-request").click(sendRequest);
});

