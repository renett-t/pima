<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--@elvariable id="articleInstance" type="ru.kpfu.itis.renett.models.Article"--%>
<t:mainLayout title="${articleInstance.title}">
    <c:if test="${not empty message}">
        <h6>${message}</h6>
    </c:if>
    <c:if test="${empty message}">
        <t:article articleInstance="${articleInstance}"></t:article>
    </c:if>
</t:mainLayout>
