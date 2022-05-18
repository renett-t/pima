document.addEventListener("DOMContentLoaded", function() {
    let buttons = document.getElementsByClassName("reply-button");
    let button, i;
    for (i = 0; i < buttons.length; i++) {
        button = buttons[i];
        button.onclick = insertCommentEditField;
    }

    function insertCommentEditField() {
        prevId = this.id;
        articleId = this.dataset.article
        document.getElementById("comment-edit-wrapper-" + prevId).innerHTML = '<div class="comment-edit-wrapper"><textarea class="comment-body" id="comment-body-' + prevId + '" form="comment-form" name="body" placeholder="Введите текст комментария"></textarea><br><button class="btn" id="reply-button-' + prevId + '" data-article="' + articleId + '" data-parent="' + prevId + '">Отправить комментарий</button></div>';
        document.getElementById("reply-button-" + prevId).onclick = sendCommentRequest;
    }
    //
    // <form action="<@spring.url'/articles/${articleId}/comments/new'/>" method="POST">
    //         <textarea className="comment-body" name="commentBody" placeholder="Введите текст комментария"
    //                   required></textarea>
    //     <input name="articleId" value="${articleId}" type="hidden">
    //         <input name="parentId" value="-1" type="hidden">
    //             <br>
    //                 <button className="btn" type="submit">
    //                     <
    //                     @spring.message 'page.comment.send'/>
    //                 </button>
    // </form>

    function sendCommentRequest() {
        body = document.getElementById("comment-body-" + this.dataset.parent).value;
        if (body === "") {
            alert("Тело комментария пустое :0");
            return;
        }
        httpRequest = new XMLHttpRequest();
        function alertCommentSent() {
            try {
                if (httpRequest.readyState === 4) {
                    if (httpRequest.status === 200) {
                        alert('Комментарий был отправлен! Обнови страничку для отображения изменений ^^');
                    } else {
                        alert('Что-то пошло не так... Попробуйте ещё раз.')
                    }

                }
            } catch( e ) {
                console.log('Произошло исключение: ' + e.description);
            }

        }
        httpRequest.onreadystatechange = alertCommentSent;
        url = cntx + "/newComment?id=" + this.dataset.article;
        httpRequest.open('POST', url, true);
        httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        httpRequest.send("articleId=" + this.dataset.article + "&commentBody=" + body + "&parent=" + this.dataset.parent);
    }

    document.getElementById("like-icon-request").onclick = async function () {
        likeIcon = document.getElementById("like-icon-request");
        id = document.getElementById("article-id").value
        url = cntx + "/like?id=" + id;
        let response = await fetch(url);

        if (response.ok) {
            if (likeIcon.classList.contains("liked")) {
                likeIcon.src = likeIconSrc;
                newCount = parseInt(document.getElementById("article-likes-count").innerHTML) - 1;
                document.getElementById("article-likes-count").innerHTML = newCount;
                likeIcon.classList.remove("liked");
            } else {
                likeIcon.src = activeLikeIconSrc;
                newCount = parseInt(document.getElementById("article-likes-count").innerHTML) + 1;
                document.getElementById("article-likes-count").innerHTML = newCount;
                likeIcon.classList.add("liked");
            }
        } else {
            alert("Ошибка при отправлении запроса на сохранение статьи:" + response.status);
        }
    }

    if (document.getElementById("delete-icon-request") != null) {
        document.getElementById("delete-icon-request").onclick = async function () {
            let isConfirmed = confirm(" Вы уверены, что хотите удалить статью? Это действие невозможно отменить.");
            if (isConfirmed) {
                id = this.dataset.id;
                url = cntx + "/deleteArticle?id=" + id;
                let response = await fetch(url);

                if (response.ok) {
                    alert("Статья была удалена! Даём возможность полюбоваться ею в последний раз :3")
                } else {
                    alert("Ошибка при отправлении запроса на удаление статьи: " + response.status);
                }

            }
        }
    }
});
