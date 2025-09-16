<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <%-- Подключаем общий header --%>
  <jsp:include page="../fragments/header.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">

<!-- 🔹 Навбар -->
<jsp:include page="../fragments/navbar.jsp"/>

<div class="container flex-grow-1 mt-4">
  <h2 class="text-center mb-4">РЕДАКТИРОВАТЬ КАТЕГОРИЮ</h2>


  <div class="form-container">
    <meta charset="UTF-8">
    <form action="${pageContext.request.contextPath}/category/edit/${category.id}" method="post" accept-charset="UTF-8">
      <input type="hidden" name="id" value="${category.id}"/>

      <!-- Название -->
      <div class="mb-3">
        <label class="form-label">Название <span class="text-danger">*</span></label>
        <input type="text" name="name" class="form-control"
               value="${category != null ? category.name : ''}"/>

        <%-- Ошибка, если есть --%>
        <c:if test="${not empty errorName}">
          <div class="text-danger">${errorName}</div>
        </c:if>
      </div>

      <!-- Кнопка -->
      <div class="text-center">
        <button type="submit" class="btn-submit">Обновить</button>
      </div>
    </form>
  </div>
</div>

<!-- 🔹 Футер -->
<footer class="mt-auto">
  <jsp:include page="../fragments/footer.jsp"/>
</footer>

</body>
</html>
