<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:mainLayout title="Профиль"><%--@elvariable id="user" type="ru.kpfu.itis.renett.models.User"--%>
    <div class="profile-wrapper">
        <div class="edit-delete-wrapper">
            <a href="<c:url value="/editProfile"/>">
                <img class="icon-img edit-icon" src="<c:url value="/resources/icons/edit.png"/>" alt="edit">
            </a>
            <img class="icon-img delete-icon" id="delete-icon-request" data-id="${articleInstance.id}" src="<c:url value="/resources/icons/cancel.png"/>" alt="delete">
        </div>
        <div class="profile">
            <h1>Ваш аккаунт @${user.login}</h1>
            <h3>${user.firstName} ${user.secondName}</h3>
            <h3>E-mail: ${user.email} </h3>
            <a href="<c:url value="/logout"/>"><button class="btn btn-dark">Выйти</button></a>
        </div>
        <br>
        <hr>
        <div>
        <c:if test="${not empty likedArticlesList}">
            <div class="heading-second">Понравившиеся статьи: </div>
            <div class="articles-wrapper card-group row row-cols-3 g-4">
            <c:forEach items="${likedArticlesList}" var="likedArticle">
                <t:article-card articleInstance="${likedArticle}"></t:article-card>
            </c:forEach>
            </div>
        </c:if>
        </div>
    </div>
    <script>cntx = '${pageContext.request.contextPath}'
    console.log("CONTEX: " + cntx)</script>
    <script src="<c:url value="/scripts/profile-script.js"/>">
    </script>
</t:mainLayout>
