<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="tags" type="java.util.List<ru.renett.models.Tag>" -->
<#-- @ftlvariable name="article" type="ru.renett.models.Article" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import 'macro_article_edit.ftl' as article_edit>

<@layout.contents "Edit">
    <@article_edit.contents article tags/>
</@layout.contents>