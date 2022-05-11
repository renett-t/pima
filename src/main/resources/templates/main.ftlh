<#ftl encoding="UTF-8"/>
<#import "spring.ftl" as spring />
<#import "layout.ftlh" as layout/>
<#import "article_card.ftl" as article/>

<@layout.contents "Welcome!">
    <#if message??>
        <div class="message-wrapper">
            <h6>${message}</h6><#-- @ftlvariable name="message" type="java.lang.String" -->
        </div>
    </#if>
    <div class="articles-wrapper card-group row row-cols-3 g-4">
        <#if lwai??>
            <p>Последняя просмотренная Вами статья: </p>
            <@article.contents lwai> <#-- @ftlvariable name="lwai" type="ru.renett.models.Article" -->
            </@article.contents>
        </#if>
    </div>
</@layout.contents>