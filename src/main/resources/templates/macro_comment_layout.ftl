<#ftl encoding="UTF-8"/>
<#-- Comment Layout Macro -->
<#import "spring.ftl" as spring />

<#macro contents comment> <#-- @ftlvariable name="comment" type="ru.renett.models.Comment" -->
    <div class="comment-wrapper">
        <div class="comment-heading-wrapper">
            <img class="comment-profile-icon" src="<@spring.url'/resources/icons/profile.png'/>" alt="profile pic">
            <div><b>@${comment.author.userName}</b>
            </div>
            <div>${comment.publishedAt.toLocaleString()}</div>
        </div>
        <div class="comment-body-wrapper">
            <p>${comment.body}</p>
        </div>
        <div class="comment-footer-wrapper">
<#--            check parent id -->
            <button class="reply-button" id="${comment.id}" data-article="${comment.article.id}" name="parentComment" value="${comment.parentComment.id}">Ответить</button>
            <div id="comment-edit-wrapper-${comment.id}"></div>
        </div>
        <div class="child-comments-wrapper">
            <#list comment.childComments as child>
                <@contents child/>
            </#list>
        </div>
    </div>

</#macro>