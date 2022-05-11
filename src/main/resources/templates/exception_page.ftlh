<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#import "layout.ftlh" as layout/>
<#import "article_card.ftl" as article/>

<#assign title>
    <@spring.message 'page.exception.title'/>
</#assign>

<@layout.contents title>
    <#if message??>
        <div class="message-wrapper">
            <h6>${message}</h6><#-- @ftlvariable name="message" type="java.lang.String" -->
        </div>
    </#if>
    <div class="error-wrapper">
        <img src="<@spring.url'/static/assets/error/500.jpg'/>" alt="Something went wrong.">
    </div>
</@layout.contents>