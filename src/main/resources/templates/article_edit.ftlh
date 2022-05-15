<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="article" type="ru.renett.models.Article" -->
<#-- @ftlvariable name="tags" type="java.util.List<ru.renett.models.Tag>" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import 'macro_article_edit.ftl' as article_edit>

<#assign title>
    <@spring.message 'page.article_edit.title'/>
</#assign>
<@layout.contents title>
    <@article_edit.contents article tags/>
</@layout.contents>
