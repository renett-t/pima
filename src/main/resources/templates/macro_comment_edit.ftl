<#ftl encoding="UTF-8"/>
<#-- Comment Edit Layout Macro -->
<#-- @ftlvariable name="comment" type="ru.renett.models.Comment" -->
<#-- @ftlvariable name="articleId" type="java.lang.Long" -->
<#import "spring.ftl" as spring />

<#macro contents articleId>
    <div class="comment-edit-wrapper">
        <form action="<@spring.url'/articles/${articleId}/comments/new'/>" method="POST">
            <textarea class="comment-body" name="commentBody" placeholder="Введите текст комментария"
                      required></textarea>
            <input name="articleId" value="${articleId}" type="hidden">
            <input name="parentId" value="-1" type="hidden">
            <br>
            <button class="btn" type="submit"><@spring.message 'page.comment.send'/></button>
        </form>
    </div>
</#macro>
