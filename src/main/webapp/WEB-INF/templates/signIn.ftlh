<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#import "layout/layout.ftlh" as layout/>

<@layout title=title> <#-- @ftlvariable name="title" type="java.lang.String" -->
    <div class="registration-form-wrapper">
<#--        <@spring.bind path='signInDto' />-->
        <h1 class="h3 mb-3 fw-normal">Войти</h1>
        <form class="authorization-form" method="POST" action="<@spring.url'/signIn'/>">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <div class="form-floating">
                <@spring.formInput "userName" "class='form-control' name='userName' id='floatingInput' placeholder='name@example.com' required"/>
                <label for="floatingInput">Ваш логин</label>
            </div>
            <div class="form-floating">
                <@spring.formPasswordInput "password" "class='form-control' name='password' id='floatingPassword' placeholder='Password' required"/>
                <label for="floatingPassword">Пароль</label>
            </div>
            <#if message??> <#-- @ftlvariable name="message" type="java.lang.String" -->
                <div class="message-wrapper">
                    <h6>${message}</h6>
                <br>
                </div>
            </#if>
            <button class="btn btn-primary" type="submit">Войти</button>
        </form>
        <br>
            <div>Ещё нет аккаунта? <br> <a class="" href="<@spring.url'/signUp'/>">Регистрация</a>
        </div>
        <br>
        <div>
            <a href="<@spring.url'/vkOauth'/>"><img class="vk-auth-icon" src="<@spring.url'/resources/icons/vk-logo-mediator.png'/>" alt="authorization via vk.com">
            Зайти через Вконтакте</a>
        </div>
    </div>
</@layout>

<script>cntx = '<@spring.url''>'
    console.log("CONTEX: " + cntx)</script>
<script src="<@spring.url'/scripts/form-control.js'>" charset="UTF-8"/>
