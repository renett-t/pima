<#ftl encoding="UTF-8"/>
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- Article Card Layout Macro -->
<#import "spring.ftl" as spring />
<#import "macro_tag.ftl" as tag_layout />

<#macro contents article tags> <#-- @ftlvariable name="article" type="ru.renett.models.Article" -->

    <div class="article-edit-wrapper">
        <form action="<@spring.url'/editArticle'/>" method="POST" enctype="multipart/form-data">
            <br>

            <#if article.id?has_content>
                <input type="hidden" name="articleId" value="${article.id}">
                <h3>Текущее изображение статьи:</h3>
                <img class="article-thumbnail-img" src="<@spring.url'/assets/articles/${article.thumbnailPath}'/>"
                     alt="article thumbnail">
                <br>
            </#if>

            <label for="thumbnailImage"> Выберите изображение для вашей статьи: </label>
            <input class="form-control" type="file" name="thumbnailImage" id="thumbnailImage"
                   accept=".jpg, .jpeg, .png">
            <#if message?has_content>
                <div class="message-wrapper">
                    <h6>${message}</h6>
                </div>
            </#if>
            <br> <br>
            <label for="article-title"> Название статьи: </label><br>
            <input class="form-control form-control-lg" id="article-title" type="text" name="title"
                   placeholder="Введите название Вашей статьи" value="${article.title}"/>" required>
            <br> <br>
            <label for="article-body"> Основное содержимое статьи: </label><br>
            <textarea id="article-body" name="articleBody"
                      placeholder="Основное содержимое"> ${article.body}</textarea>
            <#--<%--        <input id="article-body-input" type="hidden" name="articleBody" value="<c:out default="" value="${article.body}"/>">--%>-->
            <br> <br>
            <p> Выберите тэги: </p>
            <div class="tags-wrapper form-check" data-taglist="${article.tags}">
                <input class="form-check-input" type="checkbox" id="tag-1" name="tag" value="-1">
                <label class="form-check-label" for="tag-1">Не выбрано</label>
                <br>
                <#list tags as tag>
                    <input class="form-check-input" type="checkbox" id="tag${tag.id}" name="tag" value="${tag.id}">
                    <label class="form-check-label" for="tag${tag.id}">${tag.title}</label>
                    <br>
                </#list>
            </div>
            <div class="centered-content-wrapper">
                <#if article.id?has_content>
                    <button id="submit" class="btn btn-success" type="submit" name="submit" value="edit">Отредактировать
                        статью
                    </button>
                <#else>
                    <button id="submit" class="btn btn-success" type="submit" name="submit" value="create">Создать
                        статью
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