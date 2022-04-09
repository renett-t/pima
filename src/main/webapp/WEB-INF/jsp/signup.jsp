<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="ru.kpfu.itis.renett.models.User" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Регистрация">
    <div class="registration-form-wrapper centered-content-wrapper">
        <form class="registration-form" action="<c:url value="/signup"/>" method="POST">
            <label for="nameInput" class="form-label">Ваше имя</label><br>
            <input name="firstName" class="input-field form-control" id="nameInput" type="text" value="<c:out default="" value="${sessionScope.firstName}"/>" required>
            <div class="error-field" id="error-nameInput"></div><br>
            <label for="name2Input" class="form-label">Ваша фамилия</label><br>
            <input name="secondName" class="form-control" id="name2Input" type="text" value="<c:out default="" value="${sessionScope.secondName}"/>" required>

            <label for="emailInput" class="form-label">E-mail</label><br>
            <input name="email" class="input-field form-control" id="emailInput" type="text" value="<c:out default="" value="${sessionScope.email}"/>" required>
            <div class="error-field" id="error-emailInput"></div><br>

            <label for="loginInput" class="form-label">Логин (никнэйм)</label><br>
                <input name="login" class="input-field form-control" id="loginInput" type="text" value="<c:out default="" value="${sessionScope.login}"/>" required>
                <div class="error-field" id="error-loginInput"></div><br>

            <label for="passwordInput" class="form-label">Пароль</label><br>
            <input name="password" class="input-field form-control" id="passwordInput" type="password" required>
            <div class="error-field" id="error-passwordInput"></div><br>

            <label for="repeatedPasswordInput" class="form-label">Повторите пароль</label><br>
            <input name="repeatedPassword" class="input-field form-control" id="repeatedPasswordInput" type="password" required>
            <div class="error-field" id="error-repeatedPasswordInput"></div><br>

            <br><div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="agreement" required>
                <label class="form-check-label" for="agreement">
                    Согласен с политикой конфиденциальности и условиями обработки персональных данных
                </label>
            </div>
            <br>
            <div class="message-wrapper">
                <c:if test="${not empty message}">
                    <h6 class="mx-3">${message}</h6>
                </c:if>
                <br>
            </div>

            <button class="btn btn-primary" type="submit">Зарегистрироваться</button>

        </form>
        <br>
        <div>
            Уже зарегистрированы?<br>
            <a class="" href="<c:url value="/signin"/>">   Вход</a>
        </div>
    </div>
</t:mainLayout>
<script>cntx = '${pageContext.request.contextPath}'
console.log("CONTEX: " + cntx)</script>
<script src="<c:url value="/scripts/form-control.js"/>" charset="UTF-8">
</script>
