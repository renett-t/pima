<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import "macro_article_card.ftl" as article/>

<#assign title>
    <@spring.message 'page.exception.title'/>
</#assign>

<@layout.contents title>
    <#if message??>
        <div class="message-wrapper">
            <h6>${message}</h6>
        </div>
    </#if>
    <div class="error-wrapper">
        <img src="<@spring.url'/static/assets/error/500.jpg'/>" alt="Something went wrong.">
    </div>
</@layout.contents>