<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="articles" type="java.util.List<ru.renett.dto.ArticleDto>" -->
<#-- @ftlvariable name="searchTag" type="ru.renett.dto.TagDto" -->
<#-- @ftlvariable name="isAuthenticated" type="java.lang.Boolean" -->
<#-- @ftlvariable name="userArticles" type="java.util.List<ru.renett.dto.ArticleDto>" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import 'macro_article_card.ftl' as article_card_layout>

<#assign title>
    <@spring.message 'page.articles.title'/>
</#assign>
<@layout.contents title>
    <div class="articles-wrapper">
        <#if isAuthenticated?? && isAuthenticated == true>
            <div class="heading-second"> <@spring.message 'page.articles.users_articles'/> </div>
            <div class="user-articles-wrapper card-group row row-cols-3 g-4 justify-content-md-center">
                <#if userArticles?has_content>
                    <#list userArticles as userArticle>
                        <@article_card_layout.contents userArticle/>
                    </#list>
                </#if>

                <div class="col">
                    <div class="card h-100">
                        <a href="<@spring.url'/articles/new'/>">
                            <div class="card-img-create-wrapper row">
                                <div class="col">
                                    <img src="<@spring.url'/assets/icons/new.png'/>"
                                                      class="card-img-top card-img-create"
                                                      alt="create new article image">
                                </div>
                            </div>
                            <div class="card-body">
                                <h5 class="card-title"><@spring.message 'page.articles.create_new'/></h5>
                            </div>
                        </a>
                    </div>
                </div>
            </div>
            <br>
            <hr>
            <br>
        </#if>
        <#if searchTag?has_content>
            <div class="heading-second"> <@spring.message 'page.articles.by_tag'/> <b>${searchTag.title}</b>: </div>
        <#else>
            <div class="heading-second"> <@spring.message 'page.articles.all'/> </div>
        </#if>

        <div class="all-articles-wrapper card-group row row-cols-3 g-4 justify-content-md-center">
            <#list articles as article>
                <@article_card_layout.contents article/>
            </#list>
        </div>
    </div>
</@layout.contents>