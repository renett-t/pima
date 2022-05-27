<#ftl encoding="UTF-8"/>
<#-- Comment Edit Layout Macro -->
<#-- @ftlvariable name="comment" type="ru.renett.dto.CommentDto" -->
<#-- @ftlvariable name="articleId" type="java.lang.Long" -->
<#import "spring.ftl" as spring />

<#macro contents articleId>
    <div class="comment-edit-wrapper">
        <form>
            <#assign commentBodyPlaceholder>
                <@spring.message 'page.article.comments.body'/>
            </#assign>
            <textarea id="commentBody" class="comment-body" name="commentBody" placeholder="${commentBodyPlaceholder}"
                      required></textarea>
            <input id="articleId" name="articleId" value="${articleId}" type="hidden" required>
            <input id="parentId" name="parentId" value="-1" type="hidden" required>
        </form>
        <br>
        <button id="create-comment-request" class="btn" type="submit"
                data-article="${articleId}"><@spring.message 'page.comment.send'/></button>
    </div>
</#macro>
