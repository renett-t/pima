<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="ru.renett.models.User" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>

<@layout.contents "Профиль - Редактирование">
    <form method="POST" class="account-form">
        <div class="row row-cols-2">
            <div class="col">
                <label for="nameInput" class="form-label">Ваше имя</label><br>
                <input name="firstName" class="input-field form-control" id="nameInput" type="text"
                       value="${user.firstName}">
                <div class="error-field" id="error-nameInput"></div>
                <br>
            </div>
            <div class="col">
                <label for="name2Input" class="form-label">Ваша фамилия</label><br>
                <input name="secondName" class="form-control" id="name2Input" type="text" value="${user.secondName}">
            </div>
            <div class="col">
                <label for="emailInput" class="form-label">E-mail</label><br>
                <input name="email" class="input-field form-control" id="emailInput" type="text" value="${user.email}">
                <div class="error-field" id="error-emailInput"></div>
                <br>
            </div>
            <div class="col">
                <label for="loginInput" class="form-label">Логин</label><br>
                <input name="login" class="input-field form-control" id="loginInput" type="text"
                       value="${user.userName}">
                <div class="error-field" id="error-loginInput"></div>
                <br>
            </div>
            <div class="col">
                <label for="passwordInput" class="form-label">Новый пароль</label><br>
                <input name="password" class="input-field form-control" id="passwordInput" type="password">
                <div class="error-field" id="error-passwordInput"></div>
                <br>
            </div>
            <br>
            <div class="col">
                <label for="oldPasswordInput" class="form-label">Подтвердите изменение данных вводом прежнего
                    пароля</label><br>
                <input name="oldPassword" class="form-control" id="oldPasswordInput" type="password" required>
            </div>
            <#if message?has_content>
                <div class="message-wrapper">
                    <h6 class="mx-3">${message}</h6>
                    <br>
                </div>
            </#if>

            <div class="col">
                <button type="submit" class="btn btn-info" name="submit" value="edit">Сохранить</button>
            </div>
        </div>
    </form>
    <br> <br>
    <form method="get" action="<@spring.url'/profile'/>">
        <button type="submit" class="btn btn-danger">Отмена</button>
    </form>
    <script src="<@spring.url'/scripts/form-control.js'/>" charset="UTF-8">
    </script>
</@layout.contents>
