<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%-- Подключаем общий header.jsp --%>
    <jsp:include page="../fragments/header.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">

<!-- 🔹 Навбар -->
<jsp:include page="../fragments/navbar.jsp"/>

<!-- Основной контейнер -->
<div class="container flex-grow-1 mt-4">

    <h2 class="text-center mb-4">СКЛАДЫ</h2>

    <!-- Поиск -->
    <form class="d-flex mb-4" action="${pageContext.request.contextPath}/warehouse/findAll" method="get">
        <input class="form-control me-2" type="search" name="keyword"
               placeholder="Поиск по названию или адресу"
               value="${keyword}">
        <button class="btn btn-secondary" type="submit">НАЙТИ</button>
    </form>

    <!-- Таблица складов -->
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Название</th>
            <th>Адрес</th>
            <th>Действия</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="warehouse" items="${warehouses}">
            <tr>
                <td>${warehouse.name}</td>
                <td>${warehouse.address}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/warehouse/edit/${warehouse.id}"
                       class="btn btn-outline-dark btn-sm me-2">РЕДАКТИРОВАТЬ</a>
                    <a href="${pageContext.request.contextPath}/warehouse/delete/${warehouse.id}"
                       class="btn btn-outline-dark btn-sm me-2">УДАЛИТЬ</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

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
    <jsp:include page="../fragments/footer.jsp"/>
</footer>

</body>
</html>
