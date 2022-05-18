<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>

<#assign title>
    <@spring.message 'page.sign_up.title'/>
</#assign>
<@layout.contents title>
    <div class="registration-form-wrapper centered-content-wrapper">
        <h1 class="h3 mb-3 fw-normal"><@spring.message 'page.sign_up.form.title'/></h1>
        <form class="registration-form" method="POST" action="<@spring.url'/signUp'/>">
            <@spring.bind "signUpForm" />
            <p class="message-wrapper">${spring.status.errorMessage}</p>

            <label for="nameInput" class="form-label"><@spring.message 'page.sign_up.form.label.firstName'/></label><br>
            <#assign fNamePlaceholder>
                <@spring.message 'page.sign_up.form.placeholder.firstName'/>
            </#assign>
            <@spring.formInput 'signUpForm.firstName' 'class="input-field form-control" id="nameInput" autofocus="true" placeholder="${fNamePlaceholder}"'/>
            <@spring.showErrors "<br>", "error-field error"/>
            <div class="error-field" id="error-nameInput"></div>
            <br>

            <label for="name2Input"
                   class="form-label"><@spring.message 'page.sign_up.form.label.secondName'/></label><br>
            <#assign sNamePlaceholder>
                <@spring.message 'page.sign_up.form.placeholder.secondName'/>
            </#assign>
            <@spring.formInput 'signUpForm.secondName' 'class="input-field form-control" id="name2Input" placeholder="${sNamePlaceholder}"'/>
            <div class="error-field" id="error-nameInput"></div>
            <br>

            <label for="emailInput" class="form-label"><@spring.message 'page.sign_up.form.label.email'/></label><br>
            <#assign emailPlaceholder>
                <@spring.message 'page.sign_up.form.placeholder.email'/>
            </#assign>
            <@spring.formInput 'signUpForm.email' 'class="input-field form-control" id="emailInput" placeholder="${emailPlaceholder}" required'/>
            <@spring.showErrors "<br>", "error-field error"/>
            <div class="error-field" id="error-emailInput"></div>
            <br>

            <label for="loginInput" class="form-label"><@spring.message 'page.sign_up.form.label.userName'/></label><br>
            <#assign userNamePlaceholder>
                <@spring.message 'page.sign_up.form.placeholder.userName'/>
            </#assign>
            <@spring.formInput 'signUpForm.userName' 'class="input-field form-control" id="loginInput" placeholder="${userNamePlaceholder}" required'/>
            <div class="error-field" id="error-loginInput"></div>
            <br>

            <label for="passwordInput"
                   class="form-label"><@spring.message 'page.sign_up.form.label.password'/></label><br>
            <#assign passwordPlaceholder>
                <@spring.message 'page.sign_up.form.placeholder.password'/>
            </#assign>
            <@spring.formPasswordInput 'signUpForm.password' 'class="input-field form-control" id="passwordInput" placeholder="${passwordPlaceholder}" required'/>
            <div class="error-field" id="error-passwordInput"></div>
            <br>

            <label for="repeatedPasswordInput"
                   class="form-label"><@spring.message 'page.sign_up.form.label.repeatPassword'/></label><br>
            <#assign repeatPasswordPlaceholder>
                <@spring.message 'page.sign_up.form.placeholder.repeatPassword'/>
            </#assign>
            <@spring.formPasswordInput 'signUpForm.passwordRepeat' 'class="input-field form-control" id="repeatedPasswordInput" placeholder="${repeatPasswordPlaceholder}" autofocus="true"'/>
            <div class="error-field" id="error-repeatedPasswordInput"></div>
            <br>
            <br>
            <div class="form-check">
                <input class="form-check-input" type="checkbox" value="" id="agreement" required>
                <label class="form-check-label" for="agreement">
                    <@spring.message 'page.sign_up.form.label.agreement'/>
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
            <button class="btn btn-primary" type="submit"><@spring.message 'page.sign_up.form.btn.submit'/></button>
        </form>
    </div>

    <script src="<@spring.url'/scripts/form-control.js'/>" charset="UTF-8">
    </script>
</@layout.contents>
