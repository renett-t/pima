<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>

<@layout.contents title="Регистрация"> <#-- @ftlvariable name="title" type="java.lang.String" -->
    <div class="registration-form-wrapper centered-content-wrapper">
        <@spring.bind "signUpDto" />

        <form class="registration-form" method="POST" action="<@spring.url'/signUp'/>" >

<#--            <#assign firstNamePlaceholder>-->
<#--                <@spring.message 'page.sign_up.placeholder.firstName'/>-->
<#--            </#assign>-->
            <label for="nameInput" class="form-label">Ваше имя</label><br>
            <@spring.formInput 'signUpDto.firstName' 'class="input-field form-control" id="nameInput" autofocus="true" required'/>
<#--            <@spring.showErrors "<br>", "error text-danger "/>-->
            <div class="error-field" id="error-nameInput"></div><br>

            <label for="name2Input" class="form-label">Ваша фамилия</label><br>
            <@spring.formInput 'signUpDto.secondName' 'class="input-field form-control" id="name2Input" required'/>
            <div class="error-field" id="error-nameInput"></div><br>

            <label for="emailInput" class="form-label">E-mail</label><br>
            <@spring.formInput 'signUpDto.email' 'class="input-field form-control" id="emailInput" required'/>
            <div class="error-field" id="error-emailInput"></div><br>

            <label for="loginInput" class="form-label">Юзернейм</label><br>
            <@spring.formInput 'signUpDto.userName' 'class="input-field form-control" id="loginInput" required'/>
            <div class="error-field" id="error-loginInput"></div><br>

            <label for="passwordInput" class="form-label">Пароль</label><br>
            <@spring.formInput 'signUpDto.password' 'class="input-field form-control" id="passwordInput" type="password" required'/>
            <div class="error-field" id="error-passwordInput"></div><br>

            <label for="repeatedPasswordInput" class="form-label">Повторите пароль</label><br>
            <@spring.formInput 'signUpDto.passwordRepeat' 'class="input-field form-control" id="repeatedPasswordInput" type="password" autofocus="true"'/>
            <div class="error-field" id="error-repeatedPasswordInput"></div><br>
            <br>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="agreement" required>
                <label class="form-check-label" for="agreement">
                    Согласен с политикой конфиденциальности и условиями обработки персональных данных
                </label>
            </div>
            <br>
            <#if message??>
                <div class="message-wrapper">
                    <h6 class="mx-3">${message}</h6>
                    <br>
                </div>
            </#if>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <button class="btn btn-primary" type="submit">Зарегистрироваться</button>
        </form>
    </div>

    <script src="<@spring.url'/scripts/form-control.js'/>" charset="UTF-8">
    </script>
</@layout.contents>