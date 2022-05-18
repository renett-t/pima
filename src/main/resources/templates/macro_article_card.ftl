<#ftl encoding="UTF-8"/>
<#-- Article Card Layout Macro -->
<#-- @ftlvariable name="article" type="ru.renett.dto.ArticleDto" -->
<#import "spring.ftl" as spring />
<#import "macro_tag.ftl" as tag_layout />

<#macro contents article>
    <div class="col">
        <div class="card h-100">
            <a href="<@spring.url'/articles/${article.id}'/>" target="_blank">
                <img src="<@spring.url'/thumbnails/${article.thumbnail}'/>" class="card-img-top"
                     alt="thumbnail of article">
                <h5 class="card-title">${article.title}</h5>
            </a>
            <div class="card-body">
                <div>
                    <#list article.tags as tag>
                        <@tag_layout.contents false tag />
                    </#list>
                    <#if article.tags?size = 0>
                        <br>
                    </#if>
                </div>
                <br>
                <div class="card-footer row row-cols-3 justify-content-md-start">
                    <div class="views-comments-wrapper">
                        <div class="col">
                            <img class="icon-img views-icon" src="<@spring.url'/assets/icons/eye.png'/>"
                                 alt="views count icon">
                            <p class="article-views-count">${article.views}</p>
                        </div>
                        <div class="col">
                            <img class="icon-img comment-icon" src="<@spring.url'/assets/icons/comment.png'/>"
                                 alt="comments count icon">
                            <p class="article-comments-count">${article.commentsAmount}</p>
                        </div>
                        <div class="col">
                            <img class="icon-img like-icon" src="<@spring.url'/assets/icons/like.png'/>"
                                 alt="likes count icon">
                            <p class="article-comments-count">${article.likes}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>
