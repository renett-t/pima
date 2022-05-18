<#ftl encoding="UTF-8"/>
<#-- Comment Layout Macro -->
<#-- @ftlvariable name="comment" type="ru.renett.dto.CommentDto" -->
<#import "spring.ftl" as spring />

<#macro contents comment>
    <div class="comment-wrapper">
        <div class="comment-heading-wrapper">
            <img class="comment-profile-icon" src="<@spring.url'/resources/icons/profile.png'/>" alt="profile pic">
            <div><b>@${comment.authorUserName}</b>
            </div>
            <div>${comment.publishedAt}</div>
        </div>
        <div class="comment-body-wrapper">
            <p>${comment.body}</p>
        </div>
        <div class="comment-footer-wrapper">
            <#--            check parent id -->
            <button class="reply-button" id="${comment.id}" name="parentComment"
                    value="${comment.parentId}"><@spring.message 'page.comment.reply'/></button>
            <div id="comment-edit-wrapper-${comment.id}"></div>
        </div>
        <div class="child-comments-wrapper">
            <#list comment.childComments as child>
                <@contents child/>
            </#list>
        </div>
    </div>
</#macro>
