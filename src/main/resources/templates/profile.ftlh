<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="user" type="ru.renett.dto.UserDto" -->
<#-- @ftlvariable name="likedArticles" type="java.util.List<ru.renett.dto.ArticleDto>" -->
<#import "spring.ftl" as spring />
<#import "macro_layout.ftl" as layout/>
<#import 'macro_article_card.ftl' as article_card_layout>

<#assign title>
    <@spring.message 'page.profile.title'/>
</#assign>
<@layout.contents title>
    <div class="profile-wrapper">
        <div class="edit-delete-wrapper">
            <a href="<@spring.url'/profile/edit'/>">
                <img class="icon-img edit-icon" src="<@spring.url'/assets/icons/edit.png'/>" alt="edit">
            </a>
            <img class="icon-img delete-icon" id="delete-icon-request" src="<@spring.url'/assets/icons/cancel.png'/>"
                 alt="delete">
        </div>
        <div class="profile">
            <h1><@spring.message 'page.profile.info.username_prefix'/>${user.userName}</h1>
            <h3>${user.firstName} ${user.secondName}</h3>
            <h3><@spring.message 'page.profile.info.email_prefix'/> ${user.email} </h3>
            <a href="<@spring.url'/logout'/>">
                <button class="btn btn-dark"><@spring.message 'page.profile.logout'/></button>
            </a>
        </div>
        <br>
        <hr>
        <div>
            <#if likedArticles?has_content>
                <div class="heading-second"><@spring.message 'page.profile.liked_articles'/></div>
                <div class="articles-wrapper card-group row row-cols-3 g-4">
                    <#list likedArticles as article>
                        <@article_card_layout.contents article/>
                    </#list>
                </div>
            </#if>
        </div>
    </div>
    <script src="<@spring.url'/scripts/profile-script.js'/>">
    </script>
</@layout.contents>
