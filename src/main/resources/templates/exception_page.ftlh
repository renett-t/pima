<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="code" type="java.lang.String" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import "macro_article_card.ftl" as article/>

<#assign title>
    <@spring.message 'page.exception.title'/>
</#assign>

<@layout.contents title>
    <#if message??>
        <div class="message-wrapper centered-content-wrapper">
            <h6>${message}</h6>
        </div>
    </#if>
    <#assign url>
        <@spring.url'/assets/error/'/>${code}.jpg
    </#assign>
    <div class="error-wrapper">
        <img src="${url}" alt="Something went wrong.">
    </div>
</@layout.contents>