<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="lwai" type="ru.renett.dto.ArticleDto" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import "macro_article_card.ftl" as article/>

<#assign title>
    <@spring.message 'page.main.title'/>
</#assign>
<@layout.contents title>
    <#if message??>
        <div class="message-wrapper">
            <h6>${message}</h6>
        </div>
    </#if>
    <div class="articles-wrapper card-group row row-cols-3 g-4">
        <#if lwai??>
            <p> <@spring.message 'page.main.lwai'/></p>
            <@article.contents lwai>
            </@article.contents>
        </#if>
    </div>
</@layout.contents>
