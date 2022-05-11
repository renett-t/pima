document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("nameInput").onblur = checkNameInput;
    document.getElementById("emailInput").onblur = checkEmailInput;
    document.getElementById("loginInput").onblur = checkLoginInput;
    document.getElementById("passwordInput").onblur = checkPasswordInput;
    if (document.getElementById("repeatedPasswordInput") != null) {
        document.getElementById("repeatedPasswordInput").onblur = checkRepeatedPasswordInput;
    }

    for (const elem of document.getElementsByClassName("input-field")) {
        elem.onfocus = removeAlert;
    }

    function removeAlert() {
        if (this.classList.contains('invalid-data')) {
            this.classList.remove('invalid-data');
            document.getElementById("error-" + this.id).innerHTML = "";
        }
    }

    function checkNameInput() {
        if (this.value.length < 2) {
            this.classList.add('invalid-data');
            document.getElementById("error-" + this.id).innerHTML = 'Имя не должно быть пустым и должно содержать минимум 2 символа.'
        }
    }

    function checkEmailInput() {
        regexp = /[-.\w]+@([\w-]+\.)+[\w-]+/iu;
        if (this.value.match(regexp) == null) {
            this.classList.add('invalid-data');
            document.getElementById("error-" + this.id).innerHTML = 'Введите правильный email.'
        }
    }

    function checkLoginInput() {
        if (this.value.length < 5) {
            this.classList.add('invalid-data');
            document.getElementById("error-" + this.id).innerHTML = 'Логин не должен быть пустым и должен содержать минимум 5 символов.'
        }
    }

    function checkPasswordInput() {
        if (this.value.length < 5) {
            this.classList.add('invalid-data');
            document.getElementById("error-" + this.id).innerHTML = 'Пароль должен содержать как минимум 5 символов.'
        }
    }

    function checkRepeatedPasswordInput() {
        pass = document.getElementById("passwordInput").value;
        repeatedPass = document.getElementById("repeatedPasswordInput").value;

        if ((pass.length !== repeatedPass.length) || (pass !== repeatedPass)) {
            this.classList.add('invalid-data');
            document.getElementById("error-" + this.id).innerHTML = 'Пароли не совпадают.'
        }
    }
});
