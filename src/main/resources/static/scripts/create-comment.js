let artId = $("#create-comment-request").data("article")

function createNewComment(json, from) {
    if (from.id !== "create-comment-request") {
        console.log("REPLY INSERT TO" + from.value)
        child = "<div class='child-comments-wrapper'>" + json.output + "</div>"
        $("#comment-edit-wrapper-" + from.value).replaceWith(child)

        new_footer = "<div id='comment-footer-wrapper-" + from.value + "' class='comment-footer-wrapper'>" +
            "          <button class='reply-button' id='" + from.value + "' name='parentComment'" +
            "                    value='" + from.value + "'>Ответить</button></div>" +
            "        <div id='comment-edit-wrapper-" + from.value + "' class='comment-edit-wrapper'></div>"
        $("#comment-footer-wrapper-" + from.value).replaceWith(new_footer)
    } else {
        console.log("NEW COMMENT INSERT")
        comments = $("#article-comments-section")
        comment = $(json.output)
        comment.appendTo(comments);
    }

    $(".reply-button").click(insertCreateCommentField)
}

function sendNewCommentRequest(id, from) {
    console.log(from)
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

    let url = getContext() + "/ajax/articles/" + artId + "/comment"
    console.log('sending new comment request to - ' + url)

    function getDataFromInputs() {
        if (id !== "") id = "-" + id
        return {
            body: $("#commentBody" + id).val(),
            parentId: $("#parentId" + id).val(),
            articleId: $("#articleId" + id).val()
        };
    }

    jsonData = getDataFromInputs()

    $.ajax({
        url: url,
        method: "POST",
        dataType: 'json',
        data: jsonData,
        cache: false,
        success: function (json) {
            if (json.status === "201 CREATED" || json.status === "201") {
                console.log("SUCCESS")
                createNewComment(json, from)
            } else {
                alert("Sorry, there was a problem, but request was accepted!");
                console.log(json)
            }
        },
        error: function (jqXHR, errorThrown) {
            if (jqXHR.status === 404) {
                alert("Sorry, there's no requested data");
            } else if (jqXHR.status === 403)
                alert("Sorry, authorization required.")
            else
                alert("Sorry, some problems. Cannot process request.")
        }
    });
}

function insertCreateCommentField() {
    var self = this
    var id = self.id
    form = "<div id=\"comment-edit-wrapper-" + id + "\">\n" +
        "        <form>\n" +
        "            <textarea id=\"commentBody-" + id + "\" class=\"comment-body\" name=\"commentBody\" placeholder=\"\"\n" +
        "                      required></textarea>\n" +
        "            <input id=\"articleId-" + id + "\" name='articleId' value='" + artId + "' type=\"hidden\" required>\n" +
        "            <input id=\"parentId-" + id + "\" name='parentId' value='" + id + "' type=\"hidden\" required>\n" +
        "        </form>\n" +
        "        <button id=\"create-comment-request-" + id + "\" class=\"btn btn-info\" type=\"submit\"\n" +
        "                data-article=\"" + artId + "\" value='" + id + "'>OK</button>\n" +
        "    </div>"
    self.remove()
    $("#comment-edit-wrapper-" + id).replaceWith(form)

    $("#create-comment-request-" + id).click(function () {
        sendNewCommentRequest(id, this)
    })
}

$(document).ready(function () {
    $("#create-comment-request").click(function () {
        sendNewCommentRequest("", this)
    });
    $(".reply-button").click(insertCreateCommentField)
});

