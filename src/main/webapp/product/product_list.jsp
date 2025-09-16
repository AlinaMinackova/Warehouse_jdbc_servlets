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

<div class="container flex-grow-1 mt-4">
    <h2 class="text-center mb-4">СПИСОК ПРОДУКТОВ</h2>

    <!-- Фильтры -->
    <form method="get" action="${pageContext.request.contextPath}/product/findAll" class="d-flex mb-4">

        <!-- Производитель -->
        <select name="manufacturerId" class="form-select me-2">
            <option value="" ${empty selectedManufacturer ? 'selected' : ''}>Все производители</option>
            <c:forEach var="m" items="${manufacturers}">
                <option value="${m.id}" ${selectedManufacturer == m.id ? 'selected' : ''}>
                        ${m.name}
                </option>
            </c:forEach>
        </select>

        <!-- Категория -->
        <select name="categoryId" class="form-select me-2">
            <option value="" ${empty selectedCategory ? 'selected' : ''}>Все категории</option>
            <c:forEach var="c" items="${categories}">
                <option value="${c.id}" ${selectedCategory == c.id ? 'selected' : ''}>
                        ${c.name}
                </option>
            </c:forEach>
        </select>

        <button type="submit" class="btn btn-secondary">НАЙТИ</button>
    </form>

    <!-- Сетка продуктов -->
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-md-3 mb-4">
                <div class="card card-custom p-3 h-100">

                    <!-- Изображение -->
                    <c:if test="${not empty product.imageUrl}">
                        <img src="${pageContext.request.contextPath}/uploads${product.imageUrl}"
                             alt="Изображение продукта"
                             class="mb-3"
                             style="max-height: 120px; object-fit: contain; border-radius: 5px;">
                    </c:if>

                    <!-- Название -->
                    <p><b>Название:</b> ${product.name}</p>

                    <!-- Производитель -->
                    <p><b>Производитель:</b> ${product.manufacturer.name}</p>

                    <!-- Категория -->
                    <p><b>Категория:</b> ${product.category.name}</p>

                    <!-- Срок годности -->
                    <p><b>Срок годности (дней):</b> ${product.lifeDays}</p>

                    <!-- Вес -->
                    <p><b>Вес (г):</b> ${product.weight}</p>


                    <!-- Состав -->
                    <p>
                        <b>Состав:</b>
                        <button type="button" class="btn btn-outline-secondary btn-sm"
                                data-bs-toggle="modal"
                                data-bs-target="#compositionModal_${product.id}">
                            Посмотреть состав
                        </button>
                    </p>

                    <!-- Модальное окно -->
                    <div class="modal fade"
                         id="compositionModal_${product.id}"
                         tabindex="-1"
                         aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">Состав продукта</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
                                </div>
                                <div class="modal-body">
                                    <p>${product.composition}</p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Закрыть</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Действия -->
                    <div class="mt-auto">
                        <a href="${pageContext.request.contextPath}/product/edit/${product.id}"
                           class="btn btn-outline-dark btn-sm me-2">РЕДАКТИРОВАТЬ</a>
                        <a href="${pageContext.request.contextPath}/product/delete/${product.id}"
                           class="btn btn-outline-secondary btn-sm">УДАЛИТЬ</a>
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
                    <a class="page-link" href="?page=${currentPage - 1}&size=${pageSize}&manufacturerId=${selectedManufacturer}&categoryId=${selectedCategory}">‹</a>
                </li>

                <c:set var="start" value="${currentPage - 2 lt 0 ? 0 : currentPage - 2}" />
                <c:set var="end" value="${currentPage + 2 gt totalPages - 1 ? totalPages - 1 : currentPage + 2}" />

                <!-- Всегда первая страница -->
                <c:if test="${start > 0}">
                    <li class="page-item"><a class="page-link" href="?page=0&size=${pageSize}&manufacturerId=${selectedManufacturer}&categoryId=${selectedCategory}">1</a></li>
                    <c:if test="${start > 1}">
                        <li class="page-item disabled"><span class="page-link">...</span></li>
                    </c:if>
                </c:if>

                <!-- Страницы вокруг текущей -->
                <c:forEach var="i" begin="${start}" end="${end}">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}&size=${pageSize}&manufacturerId=${selectedManufacturer}&categoryId=${selectedCategory}">${i + 1}</a>
                    </li>
                </c:forEach>

                <!-- Последняя страница -->
                <c:if test="${end < totalPages - 1}">
                    <c:if test="${end < totalPages - 2}">
                        <li class="page-item disabled"><span class="page-link">...</span></li>
                    </c:if>
                    <li class="page-item"><a class="page-link" href="?page=${totalPages - 1}&size=${pageSize}&manufacturerId=${selectedManufacturer}&categoryId=${selectedCategory}">${totalPages}</a></li>
                </c:if>

                <!-- Кнопка "вперёд" -->
                <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
                    <a class="page-link" href="?page=${currentPage + 1}&size=${pageSize}&manufacturerId=${selectedManufacturer}&categoryId=${selectedCategory}">›</a>
                </li>

            </ul>
        </nav>
    </c:if>
</div>

<!-- 🔹 Футер -->
<footer class="mt-auto">
    <jsp:include page="../fragments/footer.jsp"/>
</footer>

</body>
</html>
