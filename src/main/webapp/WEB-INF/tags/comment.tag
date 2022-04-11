<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@attribute name="commentInstance" required="true" type="ru.renett.models.old.Comment" %>
<%@attribute name="parentCommentInstance" type="ru.renett.models.old.Comment" %>

<div class="comment-wrapper">
    <div class="comment-heading-wrapper">
        <img class="comment-profile-icon" src="<c:url value="/resources/icons/profile.png"/>" alt="profile pic">
        <div><b>@${commentInstance.author.username} <b></b></div>
        <div>${commentInstance.publishedAt.toLocaleString()}</div>
    </div>
    <div class="comment-body-wrapper">
        <p>${commentInstance.body}</p>
    </div>
    <div class="comment-footer-wrapper">
        <button class="reply-button" id="${commentInstance.id}" data-article="${commentInstance.article.id}" name="parentComment" value="${parentCommentInstance.id}">Ответить</button>
        <div id="comment-edit-wrapper-${commentInstance.id}"></div>
    </div>
    <div class="child-comments-wrapper">
        <c:forEach var="child" items="${commentInstance.childComments}">
            <t:comment commentInstance="${child}" parentCommentInstance="${commentInstance}"/>
        </c:forEach>
    </div>
</div>
