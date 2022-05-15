<#ftl encoding="UTF-8"/>
<#-- Article Card Layout Macro -->
<#-- @ftlvariable name="isBlank" type="java.lang.Boolean" -->
<#-- @ftlvariable name="tag" type="ru.renett.models.Tag" -->
<#import "spring.ftl" as spring />

<#macro contents isBlank tag>
    <#if isBlank == true>
        <a class="tag-wrapper" target="_blank" href="<@spring.url'/articles?tag=${tag.id}'/>">${tag.title}</a>
    <#else>
        <a class="tag-wrapper" href="<@spring.url'/articles?tag=${tag.id}'/>">${tag.title}</a>
    </#if>
</#macro>
