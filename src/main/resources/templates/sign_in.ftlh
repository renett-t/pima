<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>

<@layout.contents "Вход">
    <div class="registration-form-wrapper">
        <h1 class="h3 mb-3 fw-normal">Войти</h1>
        <form class="authorization-form" method="POST" action="<@spring.url'/signIn'/>">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <div class="form-floating">
                <input type="text" id="userName" name="userName" class="form-control" placeholder="Username" required autofocus>
                <label for="userName">Ваш логин</label>
            </div>
            <div class="form-floating">
                <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                <label for="password">Пароль</label>
            </div>
            <div class="form-floating">
                <label for="rememberMe">Запомнить меня</label>
                <input type="checkbox" id="rememberMe" name="rememberMe">
            </div>
            <#if error??>
<#--                https://stackoverflow.com/questions/13261794/display-error-messages-in-spring-login-->
                <p>Неверный логин или пароль</p>
            </#if>
            <br>
            <button class="btn btn-primary" type="submit">Войти</button>
        </form>
        <br>
            <div>Ещё нет аккаунта? <br> <a class="" href="<@spring.url'/signUp'/>">Регистрация</a>
        </div>
        <br>
        <div>
            <a href="<@spring.url'/vkOauth'/>"><img class="vk-auth-icon" src="<@spring.url'/assets/icons/vk-logo-mediator.png'/>" alt="authorization via vk.com">
            Зайти через Вконтакте</a>
        </div>
    </div>

    <script src="<@spring.url'/scripts/form-control.js'/>" charset="UTF-8">
    </script>
</@layout.contents>