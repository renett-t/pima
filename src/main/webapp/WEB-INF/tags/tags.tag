<%@tag description="Article Card Tag" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@attribute name="tag" required="true" type="ru.renett.models.old.Tag"%>
<%@attribute name="isBlankPage" required="true" type="java.lang.Boolean"%>
<c:if test="${isBlankPage}">
    <a class="tag-wrapper" target="_blank" href="<c:url value="/articles?tag=${tag.id}"/>">${tag.title}</a>
</c:if>
<c:if test="${not isBlankPage}">
    <a class="tag-wrapper" href="<c:url value="/articles?tag=${tag.id}"/>">${tag.title}</a>
</c:if>
