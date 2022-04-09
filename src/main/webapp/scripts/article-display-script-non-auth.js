document.addEventListener("DOMContentLoaded", function() {
    let buttons = document.getElementsByClassName("reply-button");
    let button, i;
    for (i = 0; i < buttons.length; i++) {
        button = buttons[i];
        button.onclick = alertUser;
    }
    document.getElementById("like-icon-request").onclick = alertUser;

    function alertUser() {
        alert("Для выполнения этого действия нужно быть авторизированным.");
    }
});
