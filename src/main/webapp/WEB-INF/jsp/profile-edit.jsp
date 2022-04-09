<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:mainLayout title="Профиль - Редактирование"><%--@elvariable id="user" type="ru.kpfu.itis.renett.models.User"--%>
    <form method="POST" class="account-form">
        <div class="row row-cols-2">
            <div class="col">
                <label for="nameInput" class="form-label">Ваше имя</label><br>
                <input name="firstName" class="input-field form-control" id="nameInput" type="text" value="${user.firstName}">
                <div class="error-field" id="error-nameInput"></div><br>
            </div>
            <div class="col">
                <label for="name2Input" class="form-label">Ваша фамилия</label><br>
                <input name="secondName" class="form-control" id="name2Input" type="text" value="${user.secondName}">
            </div>
            <div class="col">
                <label for="emailInput" class="form-label">E-mail</label><br>
                <input name="email" class="input-field form-control" id="emailInput" type="text" value="${user.email}">
                <div class="error-field" id="error-emailInput"></div><br>
            </div>
            <div class="col">
                <label for="loginInput" class="form-label">Логин</label><br>
                <input name="login" class="input-field form-control" id="loginInput" type="text" value="${user.login}">
                <div class="error-field" id="error-loginInput"></div><br>
            </div>
            <div class="col">
                <label for="passwordInput" class="form-label">Новый пароль</label><br>
                <input name="password" class="input-field form-control" id="passwordInput" type="password">
                <div class="error-field" id="error-passwordInput"></div><br>
            </div>
            <br>
            <div class="col">
                <label for="oldPasswordInput" class="form-label">Подтвердите изменение данных вводом прежнего пароля</label><br>
                <input name="oldPassword" class="form-control" id="oldPasswordInput" type="password" required>
            </div>
            <div class="message-wrapper">
                <c:if test="${not empty message}">
                    <h6 class="mx-3">${message}</h6>
                </c:if>
            </div>

            <div class="col">
                <button type="submit" class="btn btn-info" name="submit" value="edit">Сохранить</button>
            </div>
        </div>
    </form>
    <br> <br>
    <form method="get" action="<c:url value="/profile"/>">
        <button type="submit" class="btn btn-danger">Отмена</button>
    </form>
    <script src="<c:url value="/scripts/form-control.js"/>" charset="UTF-8">
    </script>
</t:mainLayout>
