<%@ page contentType="text/html;charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="<c:url value="/main"/>">pIMa</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="<c:url value="/main"/>">Главная</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            Статьи
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item" href="<c:url value="/articles"/>">Все статьи</a></li>
                            <li><a class="dropdown-item" href="<c:url value="/articles?tag=guitar"/>">Гитара</a></li>
                            <li><a class="dropdown-item" href="<c:url value="/articles?tag=music-theory"/>">Теория музыки</a></li>
                            <li><a class="dropdown-item" href="<c:url value="/articles?tag=songs"/>">Разборы песен</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="nav-profile-wrapper">
            <%--@elvariable id="authorized" type="java.util.boolean"--%>
            <c:if test="${authorized}">
                <a href="<c:url value="/profile"/>">
                    <img class="nav-profile-icon" src="<c:url value="/resources/icons/profile.png"/>" alt="profile icon">
                </a>
            </c:if>
            <c:if test="${not authorized}">
                <a href="<c:url value="/signin"/>">
                    <img class="nav-profile-icon" src="<c:url value="/resources/icons/profile.png"/>" alt="profile icon">
                </a>
            </c:if>
        </div>
    </nav>
</header>

