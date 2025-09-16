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
    <h2 class="text-center mb-4">ДОБАВИТЬ ПОСТУПЛЕНИЕ</h2>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/stock/add" method="post" accept-charset="UTF-8">

            <!-- Склад -->
            <div class="mb-3">
                <label class="form-label">Склад <span class="text-danger">*</span></label>
                <select name="warehouseId" class="form-control" required>
                    <option value="" disabled ${stock.warehouseId == null ? 'selected' : ''}>Выберите склад</option>
                    <c:forEach var="w" items="${warehouses}">
                        <option value="${w.id}" ${stock != null && stock.warehouseId == w.id ? 'selected' : ''}>
                                ${w.name}
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${not empty errorWarehouse}">
                    <div class="text-danger">${errorWarehouse}</div>
                </c:if>
            </div>

            <!-- Продукт -->
            <div class="mb-3">
                <label class="form-label">Продукт <span class="text-danger">*</span></label>
                <select name="productId" class="form-control" required>
                    <option value="" disabled ${stock.productId == null ? 'selected' : ''}>Выберите продукт</option>
                    <c:forEach var="p" items="${products}">
                        <option value="${p.id}" ${stock != null && stock.productId == p.id ? 'selected' : ''}>
                                ${p.name}
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${not empty errorProduct}">
                    <div class="text-danger">${errorProduct}</div>
                </c:if>
            </div>

            <!-- Количество -->
            <div class="mb-3">
                <label class="form-label">Количество <span class="text-danger">*</span></label>
                <input type="number" name="quantity" class="form-control" min="1"
                       value="${stock != null ? stock.quantity : ''}" required/>
                <c:if test="${not empty errorQuantity}">
                    <div class="text-danger">${errorQuantity}</div>
                </c:if>
            </div>

            <!-- Дата прихода -->
            <div class="mb-3">
                <label class="form-label">Дата и время прихода <span class="text-danger">*</span></label>
                <input type="datetime-local" name="arrivalDate" class="form-control"
                       value="${stock != null ? stock.arrivalDate : ''}"/>
                <c:if test="${not empty errorArrivalDate}">
                    <div class="text-danger">${errorArrivalDate}</div>
                </c:if>
            </div>

            <!-- Кладовщик -->
            <div class="mb-3">
                <label class="form-label">Кладовщик <span class="text-danger">*</span></label>
                <select name="storekeeperId" class="form-control" required>
                    <option value="" disabled ${stock.storekeeperId == null ? 'selected' : ''}>Выберите кладовщика</option>
                    <c:forEach var="s" items="${storekeepers}">
                        <option value="${s.id}" ${stock != null && stock.storekeeperId == s.id ? 'selected' : ''}>
                                ${s.lastName} ${s.firstName} ${s.middleName != null ? s.middleName : ''}
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${not empty errorStorekeeper}">
                    <div class="text-danger">${errorStorekeeper}</div>
                </c:if>
            </div>

            <!-- Кнопка -->
            <div class="text-center">
                <button type="submit" class="btn-submit">Сохранить</button>
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
