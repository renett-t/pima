<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Создание статьи">
    <div class="heading-second"> Создайте свою статью! </div>
    <t:article-edit tagList="${tagList}"></t:article-edit>
</t:mainLayout>
