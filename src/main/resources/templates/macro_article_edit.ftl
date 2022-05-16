<#ftl encoding="UTF-8"/>
<#-- Article Card Layout Macro -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="article" type="ru.renett.dto.ArticleDto" -->
<#-- @ftlvariable name="tags" type="java.util.List<ru.renett.dto.TagDto>" -->
<#import "spring.ftl" as spring />
<#import "macro_tag.ftl" as tag_layout />

<#macro contents article tags>
    <div class="article-edit-wrapper">
        <form method="POST" enctype="multipart/form-data">
            <br>
            <#if article.id?has_content>
                <input type="hidden" name="articleId" value="${article.id}">
                <h3><@spring.message 'page.article_edit.current_thumbnail'/></h3>
                <img class="article-thumbnail-img" src="<@spring.url'/assets/articles/${article.thumbnailPath}'/>"
                     alt="article thumbnail">
                <br>
            <#else>
                <input type="hidden" name="articleId" value="-1">
            </#if>

            <label for="thumbnailImage"> <@spring.message 'page.article_edit.form.label.thumbnail'/> </label>
            <input class="form-control" type="file" name="thumbnailImage" id="thumbnailImage"
                   accept=".jpg, .jpeg, .png">
            <#if message?has_content>
                <div class="message-wrapper">
                    <h6>${message}</h6>
                </div>
            </#if>
            <br> <br>
            <label for="article-title"> <@spring.message 'page.article_edit.form.label.title'/> </label>
            <br>
            <#assign titlePlaceholder>
                <@spring.message 'page.article_edit.form.placeholder.title'/>
            </#assign>
            <input class="form-control form-control-lg" id="article-title" type="text" name="title"
                   placeholder="${titlePlaceholder}" value="${article.title}" required>
            <br> <br>
            <label for="article-body"> <@spring.message 'page.article_edit.form.label.body'/> </label>
            <br>
            <#assign bodyPlaceholder>
                <@spring.message 'page.article_edit.form.placeholder.body'/>
            </#assign>
            <textarea id="article-body" name="body"
                      placeholder="${bodyPlaceholder} содержимое"> ${article.body} </textarea>
            <#--<%--        <input id="article-body-input" type="hidden" name="articleBody" value="<c:out default="" value="${article.body}"/>">--%>-->
            <br> <br>
            <p> <@spring.message 'page.article_edit.form.tags'/> </p>
            <div class="tags-wrapper form-check" data-taglist="${article.tags}">
                <br>
                <#list tags as tag>
                    <#if tag.title=="-1">
                        <br>
                        <input class="form-check-input" type="checkbox" id="tag-1" name="tags" value="-1">
                        <label class="form-check-label"
                               for="tag-1"><@spring.message 'page.article_edit.form.label.tags.not_chosen'/></label>
                    <#else>
                        <input class="form-check-input" type="checkbox" id="tag${tag.id}" name="tags" value="${tag.id}">
                        <label class="form-check-label" for="tag${tag.id}">${tag.title}</label>
                    </#if>
                    <br>
                </#list>
            </div>
            <div class="centered-content-wrapper">
                <#if article.id?has_content>
                    <button id="submit" class="btn btn-success" type="submit" name="submit" value="edit">
                        <@spring.message 'page.article_edit.form.btn.save_changes'/>
                    </button>
                <#else>
                    <button id="submit" class="btn btn-success" type="submit" name="submit" value="create">
                        <@spring.message 'page.article_edit.form.btn.create'/>
                    </button>
                </#if>
            </div>
        </form>
        <script src="https://cdn.ckeditor.com/ckeditor5/31.0.0/classic/ckeditor.js"></script>
        <script src="<@spring.url'/scripts/wysiwyg-config.js'/>"></script>
        <script src="<@spring.url'/scripts/edit-article.js'/>" charset="UTF-8">
        </script>
    </div>
</#macro>
