<#ftl encoding="UTF-8"/>
<#-- Comment Layout Macro -->
<#-- @ftlvariable name="comment" type="ru.renett.models.Comment" -->
<#import "spring.ftl" as spring />

<#macro contents comment>
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
            <button class="reply-button" id="${comment.id}" data-article="${comment.article.id}" name="parentComment"
                    value="${comment.parentComment.id}"><@spring.message 'page.comment.reply'/></button>
            <div id="comment-edit-wrapper-${comment.id}"></div>
        </div>
        <div class="child-comments-wrapper">
            <#list comment.childComments as child>
                <@contents child/>
            </#list>
        </div>
    </div>
</#macro>
