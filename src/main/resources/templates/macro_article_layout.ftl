<#ftl encoding="UTF-8"/>
<#-- Article Layout Macro -->
<#import "spring.ftl" as spring />
<#import "macro_tag.ftl" as tag_layout />
<#import "macro_comment.ftl" as comment_layout />
<#import "macro_comment_edit.ftl" as comment_edit />
<#-- @ftlvariable name="article" type="ru.renett.models.Article" -->
<#-- @ftlvariable name="liked" type="java.lang.Boolean" -->
<#-- @ftlvariable name="owned" type="java.lang.Boolean" -->
<#-- @ftlvariable name="isAuthenticated" type="java.lang.Boolean" -->

<#--вообще, кажется было бы интересно layout card edit соединить в один файл)-->

<#macro contents article>
    <div class="article-wrapper">
        <div class="article-thumbnail">
            <img class="article-thumbnail-img" src="<@spring.url'/assets/articles/${article.thumbnailPath}'/>" alt="article thumbnail">
        </div>
        <div class="article-heading-elements">
            <div class="article-title-controls-wrapper">
                <h3>${article.title}</h3>
                <#if owned?? && owned == true>
                    <div class="edit-delete-wrapper">
                        <a href="<@spring.url'/editArticle?id=${article.id}'/>">
                            <img class="icon-img edit-icon" src="<@spring.url'/resources/icons/edit.png'/>" alt="edit">
                        </a>
                        <img class="icon-img delete-icon" id="delete-icon-request" data-id="${articleInstance.id}"
                             src="<@spring.url'/resources/icons/cancel.png'/>" alt="delete">
                    </div>
                </#if>
            </div>
            <div class="article-heading-author"> Опубликовано пользователем ${article.author.userName}, ${article.publishedAt.toLocaleString()}</div>
            <#list article.tags as tag>
                <@tag_layout.contents true tag/>
            </#list>
        </div>
        <hr>
        <div class="article-body">${article.body}</div>
        <hr>
        <div class="article-footer-wrapper">
            <div class="article-views-count">${article.viewAmount}
                <img class="icon-img views-icon" src="<@spring.url'/resources/icons/eye.png'/>" alt="v">
            </div>
            <div class="article-comments-count">${article.commentAmount}
                <img class="icon-img comment-icon" src="<@spring.url'/resources/icons/comment.png'/>" alt="comments count icon">
            </div>
            <div class="article-likes-count">
                <span id="article-likes-count">${article.likeAmount}</span>

                <#if liked?? && liked == true>
                    <img class="icon-img like-icon liked" id="like-icon-request" src="<@spring.url'/resources/icons/like-active.png'/>" alt="likes count icon">
                <#else>
                    <img class="icon-img like-icon" id="like-icon-request" src="<@spring.url'/resources/icons/like.png'/>" alt="likes count icon">
                </#if>
                <input type="hidden" id="article-id" value="${article.id}">
            </div>
        </div>
        <hr>
        <div class="article-comments-wrapper">
            <p class="comments-heading">Комментарии:</p>
            <#list article.commentList as comment>
                <@comment_layout.contents comment />
            </#list>
            <br>
            <hr>
            <br>
            <#if isAuthenticated?? && isAuthenticated == true>
                <div>
                    Войдите, чтобы оставить комментарий: <a class="" href="<@spring.url'/signIn'/>">Вход</a>
                </div>
                <br>
                <script src="<@spring.url'/scripts/article-display-script-non-auth.js'/>" charset="UTF-8">
                </script>
            <#else>
                <@comment_edit.contents article.id />
            <br>
                <script src="<@spring.url'/scripts/article-display-scripts-auth.js'/>" charset="UTF-8">
                </script>
            </#if>
        </div>
    </div>
</#macro>