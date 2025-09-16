<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%-- Подключаем общий header.jsp --%>
    <jsp:include page="/fragments/header.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">

<!-- 🔹 Навбар -->
<jsp:include page="/fragments/navbar.jsp"/>

<div class="container flex-grow-1 mt-4">
    <h2 class="text-center mb-4">СКЛАДСКИЕ ЗАПАСЫ</h2>

    <!-- Фильтры -->
    <form method="get" action="${pageContext.request.contextPath}/stock/findAll" class="d-flex mb-4">
        <!-- Склад -->
        <select name="warehouseId" class="form-select me-2">
            <option value="" ${empty selectedWarehouse ? 'selected' : ''}>Все склады</option>
            <c:forEach var="w" items="${warehouses}">
                <option value="${w.id}" ${selectedWarehouse == w.id ? 'selected' : ''}>${w.name}</option>
            </c:forEach>
        </select>

        <!-- Продукт -->
        <select name="productId" class="form-select me-2">
            <option value="" ${empty selectedProduct ? 'selected' : ''}>Все продукты</option>
            <c:forEach var="p" items="${products}">
                <option value="${p.id}" ${selectedProduct == p.id ? 'selected' : ''}>${p.name}</option>
            </c:forEach>
        </select>

        <!-- Сортировка -->
        <select name="sort" class="form-select me-2">
            <option value="desc" ${selectedSort == 'desc' ? 'selected' : ''}>Сначала новые</option>
            <option value="asc" ${selectedSort == 'asc' ? 'selected' : ''}>Сначала старые</option>
        </select>

        <button type="submit" class="btn btn-secondary">НАЙТИ</button>
    </form>

    <!-- Сетка складских запасов -->
    <div class="row">
        <c:forEach var="stock" items="${stocks}">
            <div class="col-md-3 mb-4">
                <div class="card card-custom p-3 h-100">

                    <!-- Изображение продукта -->
                    <c:if test="${not empty stock.product.imageUrl}">
                        <img src="${pageContext.request.contextPath}/uploads${stock.product.imageUrl}"
                             alt="Продукт"
                             class="mb-3"
                             style="max-height: 120px; object-fit: contain; border-radius: 5px;">
                    </c:if>

                    <!-- Название продукта -->
                    <p><b>Название:</b> ${stock.product.name}</p>

                    <!-- Склад -->
                    <p><b>Склад:</b> ${stock.warehouse.name}</p>

                    <!-- Количество -->
                    <p><b>Количество:</b> ${stock.quantity}</p>

                    <!-- Дата прихода -->
                    <p><b>Дата прихода:</b>
                            ${stock.formatter}
                    </p>

                    <!-- Кладовщик -->
                    <p><b>Принял:</b>
                            ${stock.storekeeper.lastName} ${stock.storekeeper.firstName}
                        <c:if test="${not empty stock.storekeeper.middleName}">
                            ${stock.storekeeper.middleName}
                        </c:if>
                    </p>

                    <!-- Действия -->
                    <div class="mt-auto">
                        <a href="${pageContext.request.contextPath}/stock/edit/${stock.id}"
                           class="btn btn-outline-dark btn-sm">РЕДАКТИРОВАТЬ</a>
                        <a href="${pageContext.request.contextPath}/stock/delete/${stock.id}"
                           class="btn btn-outline-secondary btn-sm me-2">УДАЛИТЬ</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <!-- Пагинация -->
    <c:if test="${totalPages > 1}">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center mt-4">

                <!-- Кнопка "назад" -->
                <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage - 1}&size=${pageSize}&warehouseId=${selectedWarehouse}&productId=${selectedProduct}&sort=${selectedSort}">‹</a>
                </li>

                <c:set var="start" value="${currentPage - 2 lt 0 ? 0 : currentPage - 2}" />
                <c:set var="end" value="${currentPage + 2 gt totalPages - 1 ? totalPages - 1 : currentPage + 2}" />

                <!-- Всегда первая страница -->
                <c:if test="${start > 0}">
                    <li class="page-item"><a class="page-link" href="?page=0&size=${pageSize}&warehouseId=${selectedWarehouse}&productId=${selectedProduct}&sort=${selectedSort}">1</a></li>
                    <c:if test="${start > 1}">
                        <li class="page-item disabled"><span class="page-link">...</span></li>
                    </c:if>
                </c:if>

                <!-- Страницы вокруг текущей -->
                <c:forEach var="i" begin="${start}" end="${end}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}&size=${pageSize}&warehouseId=${selectedWarehouse}&productId=${selectedProduct}&sort=${selectedSort}">${i + 1}</a>
                    </li>
                </c:forEach>

                <!-- Последняя страница -->
                <c:if test="${end < totalPages - 1}">
                    <c:if test="${end < totalPages - 2}">
                        <li class="page-item disabled"><span class="page-link">...</span></li>
                    </c:if>
                    <li class="page-item"><a class="page-link" href="?page=${totalPages - 1}&size=${pageSize}&warehouseId=${selectedWarehouse}&productId=${selectedProduct}&sort=${selectedSort}">${totalPages}</a></li>
                </c:if>

                <!-- Кнопка "вперёд" -->
                <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage + 1}&size=${pageSize}&warehouseId=${selectedWarehouse}&productId=${selectedProduct}&sort=${selectedSort}">›</a>
                </li>

            </ul>
        </nav>
    </c:if>
</div>

<!-- 🔹 Футер -->
<footer class="mt-auto">
    <jsp:include page="/fragments/footer.jsp"/>
</footer>

</body>
</html>
