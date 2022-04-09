<%@page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Welcome!">
    <div class="message-wrapper">
        <c:if test="${not empty message}">
            <h6>${message}</h6>
        </c:if>
    </div>
    <div class="articles-wrapper card-group row row-cols-3 g-4">
    <%--@elvariable id="lwai" type="ru.kpfu.itis.renett.models.Article"--%>
    <c:if test="${not empty lwai}">
        <p>Последняя просмотренная Вами статья: </p>
        <t:article-card articleInstance="${lwai}"></t:article-card>
    </c:if>
    </div>
</t:mainLayout>
