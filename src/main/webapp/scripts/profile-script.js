document.addEventListener("DOMContentLoaded", function() {

    document.getElementById("delete-icon-request").onclick = async function () {
        let isConfirmed = confirm(" Вы действительно хотите удалить свой аккаунт? Это действие нельзя отменить. ");
        if (isConfirmed) {
            url = cntx + "/deleteProfile";
            let response = await fetch(url);

            if (response.ok) {
                alert("Аккаунт был удалён! Даём возможность полюбоваться им в последний раз :3")
            } else {
                alert("Ошибка при отправлении запроса на удаление аккаунта: " + response.status + ". Попробуйте ещё раз. ");
            }
        }
    }
});
