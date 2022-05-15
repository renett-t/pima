<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="article" type="ru.renett.models.Article" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import 'macro_article_layout.ftl' as article_layout>

<@layout.contents article.title>
    <#if message?has_content>
        <h6>${message}</h6>
    <#else>
        <@article_layout.contents article/>
    </#if>
</@layout.contents>
