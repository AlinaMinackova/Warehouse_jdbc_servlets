<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%-- Подключаем header.jsp --%>
    <jsp:include page="/fragments/header.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">

<!-- 🔹 Навбар -->
<jsp:include page="/fragments/navbar.jsp"/>

<div class="container flex-grow-1 mt-4">
    <h2 class="text-center">КАТЕГОРИИ</h2>

    <!-- Поиск -->
    <form class="d-flex mb-4" action="${pageContext.request.contextPath}/category/findAll" method="get">
        <input class="form-control me-2" type="search" name="keyword"
               placeholder="Поиск по названию"
               value="${keyword}">
        <button class="btn btn-secondary" type="submit">НАЙТИ</button>
    </form>

    <!-- Список категорий -->
    <c:forEach var="category" items="${categories}">
        <div class="card card-custom p-3 d-flex flex-row justify-content-between align-items-center mb-2">
            <div>
                <b>${category.name}</b>
            </div>
            <div>
                <a href="${pageContext.request.contextPath}/category/edit/${category.id}"
                   class="btn btn-outline-dark btn-sm me-2">РЕДАКТИРОВАТЬ</a>
                <a href="${pageContext.request.contextPath}/category/delete/${category.id}"
                   class="btn btn-outline-dark btn-sm me-2">УДАЛИТЬ</a>
            </div>
        </div>
    </c:forEach>

    <!-- Пагинация -->
    <c:if test="${totalPages > 1}">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center mt-4">

                <!-- Кнопка "назад" -->
                <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage - 1}&size=${pageSize}&keyword=${keyword}">‹</a>
                </li>

                <c:set var="start" value="${currentPage - 2 lt 0 ? 0 : currentPage - 2}" />
                <c:set var="end" value="${currentPage + 2 gt totalPages - 1 ? totalPages - 1 : currentPage + 2}" />

                <!-- Всегда первая страница -->
                <c:if test="${start > 0}">
                    <li class="page-item"><a class="page-link" href="?page=0&size=${pageSize}&keyword=${keyword}">1</a></li>
                    <c:if test="${start > 1}">
                        <li class="page-item disabled"><span class="page-link">...</span></li>
                    </c:if>
                </c:if>

                <!-- Страницы вокруг текущей -->
                <c:forEach var="i" begin="${start}" end="${end}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}&size=${pageSize}&keyword=${keyword}">${i + 1}</a>
                    </li>
                </c:forEach>

                <!-- Последняя страница -->
                <c:if test="${end < totalPages - 1}">
                    <c:if test="${end < totalPages - 2}">
                        <li class="page-item disabled"><span class="page-link">...</span></li>
                    </c:if>
                    <li class="page-item"><a class="page-link" href="?page=${totalPages - 1}&size=${pageSize}&keyword=${keyword}">${totalPages}</a></li>
                </c:if>

                <!-- Кнопка "вперёд" -->
                <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage + 1}&size=${pageSize}&keyword=${keyword}">›</a>
                </li>

            </ul>
        </nav>
    </c:if>
</div>

<!-- 🔹 Футер (прибит к низу) -->
<footer class="mt-auto">
    <jsp:include page="/fragments/footer.jsp"/>
</footer>

</body>
</html>
