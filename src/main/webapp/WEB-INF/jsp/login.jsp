<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<t:mainLayout title="Регистрация">
  <sec:authorize access="isAuthenticated()">
    <% response.sendRedirect("/"); %>
  </sec:authorize>

  <div class="registration-form-wrapper">

    <form class="authorization-form" method="POST" action="${pageContext.request.contextPath}/login">
      <h1 class="h3 mb-3 fw-normal">Войти</h1>

      <div class="form-floating">
        <input type="text" class="form-control" name="username" id="floatingInput" placeholder="name@example.com" autofocus="true" required>
        <label for="floatingInput">Ваш логин</label>
      </div>
      <div class="form-floating">
        <input type="password" class="form-control" name="password" id="floatingPassword" placeholder="Password" required>
        <label for="floatingPassword">Пароль</label>
      </div>
      <div class="message-wrapper">
        <c:if test="${not empty message}">
          <h6>${message}</h6>
        </c:if>
        <br>
      </div>
      <button class="btn btn-primary" type="submit">Войти</button>
    </form>
    <br>
    <div>Ещё нет аккаунта? <br> <a class="" href="<c:url value="/registration"/>">Регистрация</a>
    </div>
    <br>
    <div>
      <a href="<c:url value="/vkOauth"/>"><img class="vk-auth-icon" src="<c:url value="/resources/icons/vk-logo-mediator.png"/>" alt="authorization via vk.com">
        Зайти через Вконтакте</a>
    </div>
  </div>

</t:mainLayout>
<script>cntx = '${pageContext.request.contextPath}'
console.log("CONTEX: " + cntx)</script>
<script src="<c:url value="/scripts/form-control.js"/>" charset="UTF-8">
</script>
