<#ftl encoding="UTF-8"/>
<#-- Article Card Layout Macro -->
<#import "spring.ftl" as spring />
<#import "tag.ftl" as tag_layout />
<#macro contents article> <#-- @ftlvariable name="article" type="ru.renett.models.Article" -->
    <div class="col">
        <div class="card h-100">
            <a href="<@spring.url'/article?id=${article.id}'/>" target="_blank">
                <img src="<@spring.url'/resources/articles/${article.thumbnailPath}'/>" class="card-img-top" alt="thumbnail of article">
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
                            <img class="icon-img views-icon" src="<@spring.url'/assets/icons/eye.png'/>" alt="views count icon">
                            <p class="article-views-count">${article.viewAmount}</p>
                        </div>
                        <div class="col">
                            <img class="icon-img comment-icon" src="<@spring.url'/assets/icons/comment.png'/>" alt="comments count icon">
                            <p class="article-comments-count">${article.commentAmount}</p>
                        </div>
                        <div class="col">
                            <img class="icon-img like-icon" src="<@spring.url'/assets/icons/like.png'/>" alt="likes count icon">
                            <p class="article-comments-count">${article.likeAmount}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</#macro>