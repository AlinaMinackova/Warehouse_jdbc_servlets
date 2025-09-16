<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <%-- Подключаем общий header --%>
    <jsp:include page="/fragments/header.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">

<!-- 🔹 Навбар -->
<jsp:include page="/fragments/navbar.jsp"/>

<div class="container flex-grow-1 mt-4">
    <h2 class="text-center mb-4">ДОБАВИТЬ ПРОДУКТ</h2>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/product/add" method="post" enctype="multipart/form-data">

            <!-- Название -->
            <div class="mb-3">
                <label class="form-label">Название <span class="text-danger">*</span></label>
                <input type="text" name="name" class="form-control" maxlength="255"
                       value="${product != null ? product.name : ''}" required/>

                <c:if test="${not empty errorName}">
                    <div class="text-danger">${errorName}</div>
                </c:if>
            </div>

            <!-- Производитель -->
            <div class="mb-3">
                <label class="form-label">Производитель <span class="text-danger">*</span></label>
                <select name="manufacturerId" class="form-control" required>
                    <option value="" disabled ${product.manufacturerId == null ? 'selected' : ''}>Выберите производителя</option>
                    <c:forEach var="m" items="${manufacturers}">
                        <option value="${m.id}" ${product != null && product.manufacturerId == m.id ? 'selected' : ''}>
                                ${m.name}
                        </option>
                    </c:forEach>
                </select>

                <c:if test="${not empty errorManufacturer}">
                    <div class="text-danger">${errorManufacturer}</div>
                </c:if>
            </div>

            <!-- Категория -->
            <div class="mb-3">
                <label class="form-label">Категория <span class="text-danger">*</span></label>
                <select name="categoryId" class="form-control" required>
                    <option value="" disabled ${product.categoryId == null ? 'selected' : ''}>Выберите категорию</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}" ${product != null && product.categoryId == c.id ? 'selected' : ''}>
                                ${c.name}
                        </option>
                    </c:forEach>
                </select>

                <c:if test="${not empty errorCategory}">
                    <div class="text-danger">${errorCategory}</div>
                </c:if>
            </div>

            <!-- Фото -->
            <div class="mb-3">
                <label class="form-label fw-semibold">Фото</label>
                <div class="mb-2">
                    <img id="previewImage"
                         src="${product != null && product.imageUrl != null ? product.imageUrl : '#'}"
                         alt="Предпросмотр"
                         class="img-thumbnail"
                         style="max-width: 200px; max-height: 200px; ${product != null && product.imageUrl != null ? 'display:block;' : 'display:none;'}">
                </div>
                <input class="form-control" type="file" id="imageInput" name="file">
            </div>

            <script>
                document.addEventListener("DOMContentLoaded", () => {
                    const input = document.getElementById("imageInput");
                    const preview = document.getElementById("previewImage");

                    input.addEventListener("change", () => {
                        const file = input.files[0];
                        if (file) {
                            const reader = new FileReader();
                            reader.onload = e => {
                                preview.src = e.target.result;
                                preview.style.display = "block";
                            };
                            reader.readAsDataURL(file);
                        } else {
                            preview.src = "#";
                            preview.style.display = "none";
                        }
                    });
                });
            </script>

            <!-- Срок годности -->
            <div class="mb-3">
                <label class="form-label">Срок годности (дн.) <span class="text-danger">*</span></label>
                <input type="number" name="lifeDays" class="form-control" min="1"
                       value="${product != null ? product.lifeDays : ''}" required/>
                <c:if test="${not empty errorLifeDays}">
                    <div class="text-danger">${errorLifeDays}</div>
                </c:if>
            </div>

            <!-- Вес -->
            <div class="mb-3">
                <label class="form-label">Вес (кг) <span class="text-danger">*</span></label>
                <input type="number" name="weight" class="form-control" step="0.01" min="0.01"
                       value="${product != null ? product.weight : ''}" required/>
                <c:if test="${not empty errorWeight}">
                    <div class="text-danger">${errorWeight}</div>
                </c:if>
            </div>

            <!-- Состав -->
            <div class="mb-3">
                <label class="form-label">Состав</label>
                <textarea name="composition" class="form-control" rows="3">${product != null ? product.composition : ''}</textarea>
                <c:if test="${not empty errorComposition}">
                    <div class="text-danger">${errorComposition}</div>
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
    <jsp:include page="/fragments/footer.jsp"/>
</footer>

</body>
</html>
