<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="user" type="ru.renett.dto.UserDto" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>

<#assign title>
    <@spring.message 'page.profile_edit.title'/>
</#assign>
<@layout.contents title>
    <h2>@${user.userName}</h2>
    <form method="POST" class="account-form" action="<@spring.url'/profile/edit'/>">
        <@spring.bind "updateUserForm" />
        <p class="message-wrapper">${spring.status.errorMessage}</p>
        <div class="row row-cols-2">
            <div class="col">

                <label for="nameInput"
                       class="form-label"><@spring.message 'page.profile_edit.form.label.firstName'/></label><br>
                <#assign fNamePlaceholder>
                    <@spring.message 'page.profile_edit.form.placeholder.firstName'/>
                </#assign>
                <@spring.formInput 'updateUserForm.firstName' 'class="input-field form-control" id="nameInput" autofocus="true" placeholder=${fNamePlaceholder} value="${user.firstName}"'/>
                <@spring.showErrors "<br>", "error-field error"/>
                <div class="error-field" id="error-nameInput"></div>
            </div>
            <br>
            <div class="col">
                <label for="name2Input"
                       class="form-label"><@spring.message 'page.profile_edit.form.label.secondName'/></label><br>
                <#assign sNamePlaceholder>
                    <@spring.message 'page.profile_edit.form.placeholder.secondName'/>
                </#assign>
                <@spring.formInput 'updateUserForm.secondName' 'class="input-field form-control" id="name2Input" placeholder=${sNamePlaceholder} value="${user.secondName}"'/>
                <@spring.showErrors "<br>", "error-field error"/>
            </div>
            <br>
            <div class="col">
                <label for="password"
                       class="form-label"><@spring.message 'page.profile_edit.form.label.password'/></label><br>
                <#assign passwordPlaceholder>
                    <@spring.message 'page.profile_edit.form.placeholder.password'/>
                </#assign>
                <@spring.formPasswordInput "updateUserForm.password" "class='input-field form-control test' placeholder=${passwordPlaceholder} required"/>
                <@spring.showErrors "<br>", "error-field error"/>
            </div>
            <#if message?has_content>
                <div class="message-wrapper">
                    <h6 class="mx-3">${message}</h6>
                    <br>
                </div>
            </#if>

            <br>
            <div class="col">
                <br>
                <button type="submit" class="btn btn-info" name="submit"><@spring.message 'page.profile_edit.form.btn.submit'/></button>
            </div>
        </div>
    </form>
    <br>
    <form method="get" action="<@spring.url'/profile'/>">
        <button type="submit" class="btn btn-danger"><@spring.message 'page.profile_edit.form.btn.cancel'/></button>
    </form>
    <script src="<@spring.url'/scripts/form-control.js'/>" charset="UTF-8">
    </script>
</@layout.contents>
