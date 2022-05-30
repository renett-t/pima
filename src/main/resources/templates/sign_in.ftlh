<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="vkOauthUrl" type="java.lang.String" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>

<#assign title>
    <@spring.message 'page.sign_in.title'/>
</#assign>
<@layout.contents title>
    <div class="registration-form-wrapper">
        <h1 class="h3 mb-3 fw-normal"><@spring.message 'page.sign_in.form.title'/></h1>
        <form class="authorization-form" method="POST" action="<@spring.url'/signIn'/>">
            <div class="form-floating">
                <#assign userNamePlaceholder>
                    <@spring.message 'page.sign_in.form.placeholder.userName'/>
                </#assign>
                <input type="text" id="userName" name="userName" class="form-control"
                       placeholder="${userNamePlaceholder}" required autofocus>
                <label for="userName"><@spring.message 'page.sign_in.form.label.userName'/></label>
            </div>
            <div class="form-floating">
                <#assign passwordPlaceholder>
                    <@spring.message 'page.sign_in.form.placeholder.password'/>
                </#assign>
                <input type="password" id="password" name="password" class="form-control"
                       placeholder="${passwordPlaceholder}" required>
                <label for="password"><@spring.message 'page.sign_in.form.label.password'/></label>
            </div>
            <div class="form-floating">
                <label for="rememberMe"><@spring.message 'page.sign_in.form.label.rememberMe'/></label>
                <input type="checkbox" id="rememberMe" name="rememberMe">
            </div>

            <br>
            <#if error??>
                <div class="message-wrapper">
                    <h6 class="mx-3"><@spring.message 'page.sign_in.form.error'/></h6>
                    <br>
                </div>
            </#if>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <button class="btn btn-primary" type="submit"><@spring.message 'page.sign_in.form.btn.submit'/></button>
        </form>
        <br>
        <div><@spring.message 'page.sing_in.no_registration'/><br> <a class="" href="<@spring.url'/signUp'/>">Регистрация</a>
        </div>
        <br>
        <div>
            <a href="${vkOauthUrl}"><img class="vk-auth-icon"
                                         src="<@spring.url'/assets/icons/vk-logo-mediator.png'/>"
                                         alt="authorization via vk.com"><@spring.message 'page.sing_in.via_vk'/>
            </a>
        </div>
    </div>

    <script src="<@spring.url'/scripts/form-control.js'/>" charset="UTF-8">
    </script>
</@layout.contents>
