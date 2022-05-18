<#ftl encoding="UTF-8"/>
<#-- Article Layout Macro -->
<#-- @ftlvariable name="article" type="ru.renett.dto.ArticleDto" -->
<#-- @ftlvariable name="liked" type="java.lang.Boolean" -->
<#-- @ftlvariable name="owned" type="java.lang.Boolean" -->
<#-- @ftlvariable name="isAuthenticated" type="java.lang.Boolean" -->
<#import "spring.ftl" as spring />
<#import "macro_tag.ftl" as tag_layout />
<#import "macro_comment_layout.ftl" as comment_layout />
<#import "macro_comment_edit.ftl" as comment_edit />

<#--вообще, кажется было бы интересно layout card edit соединить в один файл)-->

<#macro contents article>
    <div class="article-wrapper">
        <div class="article-thumbnail">
            <img class="article-thumbnail-img" src="<@spring.url'/assets/articles/${article.thumbnail}'/>"
                 alt="article thumbnail">
        </div>
        <div class="article-heading-elements">
            <div class="article-title-controls-wrapper">
                <h3>${article.title}</h3>
                <#if owned?? && owned == true>
                    <div class="edit-delete-wrapper">
                        <a href="<@spring.url'/articles/${article.id}/edit'/>" target="_blank">
                            <img class="icon-img edit-icon" src="<@spring.url'/assets/icons/edit.png'/>" alt="edit">
                        </a>
                        <img class="icon-img delete-icon" id="delete-icon-request" data-id="${article.id}"
                             src="<@spring.url'/assets/icons/cancel.png'/>" alt="delete">
                    </div>
                </#if>
            </div>
            <div class="article-heading-author"><@spring.message 'page.article.published_by'/>${article.author.userName}
                , ${article.publishedAt}</div>
            <#list article.tags as tag>
                <@tag_layout.contents true tag/>
            </#list>
        </div>
        <hr>
        <div class="article-body">
            <#outputformat "HTML">
                ${article.body}
            </#outputformat>
        </div>
        <hr>
        <div class="article-footer-wrapper">
            <div class="article-views-count">${article.views}
                <img class="icon-img views-icon" src="<@spring.url'/assets/icons/eye.png'/>" alt="v">
            </div>
            <div class="article-comments-count">${article.commentsAmount}
                <img class="icon-img comment-icon" src="<@spring.url'/assets/icons/comment.png'/>"
                     alt="comments count icon">
            </div>
            <div class="article-likes-count">
                <span id="article-likes-count">${article.likes}</span>

                <#if liked?? && liked == true>
                    <img class="icon-img like-icon liked" id="like-icon-request"
                         src="<@spring.url'/assets/icons/like-active.png'/>" alt="likes count icon">
                <#else>
                    <img class="icon-img like-icon" id="like-icon-request"
                         src="<@spring.url'/assets/icons/like.png'/>" alt="likes count icon">
                </#if>
                <input type="hidden" id="article-id" value="${article.id}">
            </div>
        </div>
        <hr>
        <div class="article-comments-wrapper">
            <p class="comments-heading"> <@spring.message 'page.article.comments'/> </p>
            <#list article.comments as comment>
                <@comment_layout.contents comment />
            </#list>
            <br>
            <hr>
            <br>
            <#if isAuthenticated?? && isAuthenticated == true>
                <div>
                    <@spring.message 'page.article.comments.no_authentication'/>
                    <a class="" href="<@spring.url'/signIn'/>">
                        <@spring.message 'page.article.comments.sing_in'/>
                    </a>
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
