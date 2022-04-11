<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:mainLayout title="Редактирование статьи">
    <t:article-edit articleInstance="${articleToEdit}" tagList="${tagList}"></t:article-edit>
</t:mainLayout>
