<%@tag description="Article Editing Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="articleInstance" type="ru.renett.models.old.Article" %>
<%@attribute name="tagList" required="true" type="java.util.List"%>

<div class="article-edit-wrapper">
    <form action="<c:url value="/editArticle"/>" method="POST" enctype="multipart/form-data">
        <br>
        <c:if test="${not empty articleInstance}">
            <input type="hidden" name="articleId" value="${articleInstance.id}">
            <h3>Текущее изображение статьи:</h3>
            <img class="article-thumbnail-img" src="<c:url value="resources/articles/${articleInstance.thumbnailPath}"/>" alt="article thumbnail">
            <br>
        </c:if>
        <label for="thumbnailImage"> Выберите изображение для вашей статьи: </label>
        <input class="form-control" type="file" name="thumbnailImage" id="thumbnailImage" accept=".jpg, .jpeg, .png">
        <div class="message-wrapper">
            <c:if test="${not empty message}">
                <h6>${message}</h6>
            </c:if>
        </div>
        <br> <br>
        <label for="article-title"> Название статьи: </label><br>
        <input class="form-control form-control-lg" id="article-title" type="text" name="title" placeholder="Введите название Вашей статьи" value="<c:out default="" value="${aititle}"/>" required>
        <br> <br>
        <label for="article-body"> Основное содержимое статьи: </label><br>
        <textarea id="article-body" name="articleBody" placeholder="Основное содержимое"><c:out default="" value="${aibody}"/></textarea>
<%--        <input id="article-body-input" type="hidden" name="articleBody" value="<c:out default="" value="${articleInstance.body}"/>">--%>
        <br> <br>
        <p> Выберите тэги: </p>
        <div class="tags-wrapper form-check" data-taglist="<c:out default="" value="${articleInstance.tagList}"/>">
                <input class="form-check-input" type="checkbox" id="tag-1" name="tag" value="-1">
                <label class="form-check-label" for="tag-1">Не выбрано</label>
                <br>
            <c:forEach var="tag" items="${tagList}">
                <input class="form-check-input"  type="checkbox" id="tag${tag.id}" name="tag" value="${tag.id}">
                <label class="form-check-label" for="tag${tag.id}">${tag.title}</label>
                <br>
            </c:forEach>
        </div>
        <div class="centered-content-wrapper">
            <c:if test="${empty articleInstance}">
                <button id="submit" class="btn btn-success" type="submit" name="submit" value="create">Создать статью</button>
            </c:if>
            <c:if test="${not empty articleInstance}">
                <button id="submit" class="btn btn-success" type="submit" name="submit" value="edit">Отредактировать статью</button>
            </c:if>
        </div>
    </form>
    <script src="https://cdn.ckeditor.com/ckeditor5/31.0.0/classic/ckeditor.js"></script>
    <script src="<c:url value="/scripts/wysiwyg-config.js"/>"></script>
    <script src="<c:url value="/scripts/edit-article.js"/>" charset="UTF-8">
    </script>
</div>
