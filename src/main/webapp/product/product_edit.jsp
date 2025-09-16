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
    <h2 class="text-center mb-4">РЕДАКТИРОВАТЬ ПРОДУКТ</h2>

    <div class="form-container">
        <form action="${pageContext.request.contextPath}/product/edit/${product.id}"
              method="post" enctype="multipart/form-data">

            <!-- PUT для имитации -->
            <input type="hidden" name="_method" value="put"/>

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
                    <option value="" disabled>Выберите производителя</option>
                    <c:forEach var="m" items="${manufacturers}">
                        <option value="${m.id}"
                                <c:if test="${product != null && product.manufacturerId == m.id}">selected</c:if>>
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
                    <option value="" disabled>Выберите категорию</option>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}"
                                <c:if test="${product != null && product.categoryId == c.id}">selected</c:if>>
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
                    <img src="${pageContext.request.contextPath}/uploads${product.imageUrl}"
                         alt="Изображение продукта"
                         class="mb-3"
                         style="max-height: 120px; object-fit: contain; border-radius: 5px;">
                </div>
                <input class="form-control" type="file" id="imageInput" name="file">
            </div>

            <script>
                document.addEventListener("DOMContentLoaded", () => {
                    const input = document.getElementById("imageInput");
                    const currentImage = document.getElementById("currentImage");

                    input.addEventListener("change", () => {
                        const file = input.files[0];
                        if (file) {
                            const reader = new FileReader();
                            reader.onload = e => {
                                currentImage.src = e.target.result;
                                currentImage.style.display = "block";
                            };
                            reader.readAsDataURL(file);
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
