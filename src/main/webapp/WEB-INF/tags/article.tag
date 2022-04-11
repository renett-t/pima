<%@tag description="Article Displaying Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="articleInstance" required="true" type="ru.renett.models.old.Article" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<div class="article-wrapper">
    <div class="article-thumbnail">
        <img class="article-thumbnail-img" src="<c:url value="/resources/articles/${articleInstance.thumbnailPath}"/>" alt="article thumbnail">
    </div>
    <div class="article-heading-elements">
        <div class="article-title-controls-wrapper">
            <h3>${articleInstance.title}</h3>
            <c:if test="${not empty author}">
                <div class="edit-delete-wrapper">
                    <a href="<c:url value="/editArticle?id=${articleInstance.id}"/>">
                        <img class="icon-img edit-icon" src="<c:url value="/resources/icons/edit.png"/>" alt="edit">
                    </a>
                    <img class="icon-img delete-icon" id="delete-icon-request" data-id="${articleInstance.id}" src="<c:url value="/resources/icons/cancel.png"/>" alt="delete">
                </div>
            </c:if>
        </div>
        <div class="article-heading-author"> Опубликовано пользователем ${articleInstance.author.username}, ${articleInstance.publishedAt.toLocaleString()}</div>
        <c:forEach var="tagInstance" items="${articleInstance.tagList}">
            <t:tags tag="${tagInstance}" isBlankPage="true"></t:tags>
        </c:forEach>
    </div>
    <hr>
    <div class="article-body">${articleInstance.body}</div>
    <hr>
    <div class="article-footer-wrapper">
        <div class="article-views-count">${articleInstance.viewAmount}
            <img class="icon-img views-icon" src="<c:url value="/resources/icons/eye.png"/>" alt="v">
        </div>
        <div class="article-comments-count">${articleInstance.commentAmount}
            <img class="icon-img comment-icon" src="<c:url value="/resources/icons/comment.png"/>" alt="comments count icon">
        </div>
        <div class="article-likes-count"><span id="article-likes-count">${articleInstance.likeAmount}</span>
             <c:if test="${not empty liked}">
                 <img class="icon-img like-icon liked" id="like-icon-request" src="<c:url value="/resources/icons/like-active.png"/>" alt="likes count icon">
             </c:if>
             <c:if test="${empty liked}">
                 <img class="icon-img like-icon" id="like-icon-request" src="<c:url value="/resources/icons/like.png"/>" alt="likes count icon">
             </c:if>
             <input type="hidden" id="article-id" value="${articleInstance.id}">
         </div>
    </div>
    <hr>
    <div class="article-comments-wrapper">
        <p class="comments-heading">Комментарии:</p>
        <c:forEach var="commentInstance" items="${articleInstance.commentList}">
            <t:comment commentInstance="${commentInstance}"></t:comment>
        </c:forEach>
        <br><hr><br>
        <c:if test="${not authorized}">
            <div>
                Войдите, чтобы оставить комментарий: <a class="" href="<c:url value="/signin"/>">Вход</a>
            </div>
            <br>
            <script src="<c:url value="/scripts/article-display-script-non-auth.js"/>"charset="UTF-8">
            </script>
        </c:if>
        <c:if test="${authorized}">
            <t:comment-edit id="${articleInstance.id}"></t:comment-edit>
            <br>
            <script>cntx = '${pageContext.request.contextPath}';
            activeLikeIconSrc = '<c:url value="/resources/icons/like-active.png"/>';
            likeIconSrc = '<c:url value="/resources/icons/like.png"/>';
            </script>
            <script src="<c:url value="/scripts/article-display-scripts-auth.js"/>" charset="UTF-8">
            </script>
        </c:if>
    </div>

</div>


