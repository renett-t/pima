<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#import "layout/layout.ftlh" as layout/>

// todo
<@layout title=title> <#-- @ftlvariable name="title" type="java.lang.String" -->
<t:mainLayout title="Регистрация">
    <div class="registration-form-wrapper centered-content-wrapper">
        <form:form class="registration-form" method="POST" modelAttribute="userForm">
            <label for="nameInput" class="form-label">Ваше имя</label><br>
            <form:input path="firstName" class="input-field form-control" id="nameInput" type="text" autofocus="true"/>
            <form:errors path="firstName"/>
            <div class="error-field" id="error-nameInput">${usernameError}</div><br>

            <label for="name2Input" class="form-label">Ваша фамилия</label><br>
            <form:input path="secondName" class="input-field form-control" id="name2Input" type="text" autofocus="true"/>
            <form:errors path="secondName"/>

            <label for="emailInput" class="form-label">E-mail</label><br>
            <form:input path="email" class="input-field form-control" id="emailInput" type="text" autofocus="true"/>
            <form:errors path="email"/>
            <div class="error-field" id="error-emailInput"></div><br>

            <label for="loginInput" class="form-label">Юзернейм</label><br>
            <form:input path="username" class="input-field form-control" id="loginInput" type="text" autofocus="true"/>
            <form:errors path="username"/>
            <div class="error-field" id="error-loginInput"></div><br>

            <label for="passwordInput" class="form-label">Пароль</label><br>
            <form:input path="password" class="input-field form-control" id="passwordInput" type="password" autofocus="true"/>
            <form:errors path="password"/>
            <div class="error-field" id="error-passwordInput"></div><br>

            <label for="repeatedPasswordInput" class="form-label">Повторите пароль</label><br>
            <form:input path="passwordRepeat" class="input-field form-control" id="repeatedPasswordInput" type="password" autofocus="true"/>
            <form:errors path="passwordRepeat"/>
            <div class="error-field" id="error-repeatedPasswordInput">${passwordError}</div><br>

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

        </form:form>

</t:mainLayout>
<script>cntx = '${pageContext.request.contextPath}'
    console.log("CONTEX: " + cntx)</script>
<script src="<c:url value="/scripts/form-control.js"/>" charset="UTF-8">
</script>
