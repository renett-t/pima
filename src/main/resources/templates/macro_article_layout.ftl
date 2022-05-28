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
            <img class="article-thumbnail-img" src="<@spring.url'/thumbnails/${article.thumbnail}'/>"
                 alt="article thumbnail">
        </div>
        <div class="article-heading-elements">
            <div class="article-title-controls-wrapper">
                <h3>${article.title}</h3>
                <#if owned?? && owned == true>
                    <div class="edit-delete-wrapper">
                        <form action="<@spring.url'/articles/${article.id}/edit'/>" method="GET">
                            <button class="image-button">
                                <img class="icon-img edit-icon" src="<@spring.url'/assets/icons/edit.png'/>" alt="edit">
                            </button>
                        </form>
                        <form action="<@spring.url'/articles/${article.id}/delete'/>" method="POST">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button class="image-button">
                                <img class="icon-img delete-icon" id="delete-icon-request" data-id="${article.id}"
                                     src="<@spring.url'/assets/icons/cancel.png'/>" alt="delete">
                            </button>
                        </form>
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
            ${article.body}
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
                         src="<@spring.url'/assets/icons/like-active.png'/>" alt="likes count icon"
                         data-article="${article.id}">
                <#else>
                    <img class="icon-img like-icon" id="like-icon-request"
                         src="<@spring.url'/assets/icons/like.png'/>" alt="likes count icon"
                         data-article="${article.id}">
                </#if>
                <input type="hidden" id="article-id" value="${article.id}">
            </div>
        </div>
        <hr>
        <div class="article-comments-wrapper">
            <div id="article-comments-section">
                <p class="comments-heading"> <@spring.message 'page.article.comments'/> </p>
                <#list article.comments as comment>
                    <@comment_layout.contents comment />
                </#list>
            </div>
            <br>
            <hr>
            <br>
            <#if isAuthenticated?? && isAuthenticated == true>
                <@comment_edit.contents article.id />
                <br>
                <script src="<@spring.url'/scripts/create-comment.js'/>" charset="UTF-8">
                </script>
            <#else>
                <div>
                    <@spring.message 'page.article.comments.no_authentication'/>
                    <a class="" href="<@spring.url'/signIn'/>">
                        <@spring.message 'page.article.comments.sing_in'/>
                    </a>
                </div>
            <br>
            </#if>
        </div>
    </div>
    <script src="<@spring.url'/scripts/create-like.js'/>" charset="UTF-8">
    </script>
</#macro>
