<#ftl encoding="UTF-8"/>
<#-- Comment Edit Layout Macro -->
<#import "spring.ftl" as spring />

<#macro contents articleId> <#-- @ftlvariable name="comment" type="ru.renett.models.Comment" -->
    <div class="comment-edit-wrapper">
        <form action="<@spring.url'/newComment?id=${articleId}'/>" method="POST">
            <textarea class="comment-body" name="commentBody" placeholder="Введите текст комментария" required></textarea>
            <input name="articleId" value="${articleId}" type="hidden">
        <br>
        <button class="btn" type="submit">Отправить комментарий</button>
        </form>
    </div>
</#macro>