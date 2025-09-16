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
    <h2 class="text-center mb-4">РЕДАКТИРОВАТЬ ПЕРСОНАЛ</h2>

    <div class="form-container">
        <meta charset="UTF-8">
        <form action="${pageContext.request.contextPath}/storekeeper/edit/${storekeeper.id}" method="post" accept-charset="UTF-8">
            <input type="hidden" name="id" value="${storekeeper.id}"/>

            <!-- Фамилия -->
            <div class="mb-3">
                <label class="form-label">Фамилия <span class="text-danger">*</span></label>
                <input type="text" name="lastName" class="form-control"
                       value="${storekeeper != null ? storekeeper.lastName : ''}"/>
                <c:if test="${not empty errorLastName}">
                    <div class="text-danger">${errorLastName}</div>
                </c:if>
            </div>

            <!-- Имя -->
            <div class="mb-3">
                <label class="form-label">Имя <span class="text-danger">*</span></label>
                <input type="text" name="firstName" class="form-control"
                       value="${storekeeper != null ? storekeeper.firstName : ''}"/>
                <c:if test="${not empty errorFirstName}">
                    <div class="text-danger">${errorFirstName}</div>
                </c:if>
            </div>

            <!-- Отчество -->
            <div class="mb-3">
                <label class="form-label">Отчество</label>
                <input type="text" name="middleName" class="form-control"
                       value="${storekeeper != null ? storekeeper.middleName : ''}"/>
                <c:if test="${not empty errorMiddleName}">
                    <div class="text-danger">${errorMiddleName}</div>
                </c:if>
            </div>

            <!-- Email -->
            <div class="mb-3">
                <label class="form-label">Email <span class="text-danger">*</span></label>
                <input type="email" name="email" class="form-control"
                       value="${storekeeper != null ? storekeeper.email : ''}"/>
                <c:if test="${not empty errorEmail}">
                    <div class="text-danger">${errorEmail}</div>
                </c:if>
            </div>

            <!-- Дата рождения -->
            <div class="mb-3">
                <label class="form-label">Дата рождения <span class="text-danger">*</span></label>
                <input type="date" name="birthday" class="form-control"
                       value="${storekeeper != null ? storekeeper.birthday : ''}"/>
                <c:if test="${not empty errorBirthday}">
                    <div class="text-danger">${errorBirthday}</div>
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
