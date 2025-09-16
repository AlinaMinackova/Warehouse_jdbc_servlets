<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="header container-fluid">
    <div class="container">
        <div class="row g-2 mb-3">
            <!-- СКЛАДЫ -->
            <div class="col-3 dropdown">
                <button class="btn btn-light btn-custom dropdown-toggle" type="button" id="dropdownWarehouses" data-bs-toggle="dropdown" aria-expanded="false">
                    СКЛАДЫ
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownWarehouses">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/warehouse/findAll">Список складов</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/warehouse/add">Добавить склад</a></li>
                </ul>
            </div>

            <!-- ПРОДУКЦИЯ -->
            <div class="col-3 dropdown">
                <button class="btn btn-light btn-custom dropdown-toggle" type="button" id="dropdownProducts" data-bs-toggle="dropdown" aria-expanded="false">
                    ПРОДУКЦИЯ
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownProducts">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/product/findAll">Список продукции</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/product/add">Добавить продукцию</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/category/findAll">Список категорий</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/category/add">Добавить категорию</a></li>
                </ul>
            </div>

            <!-- ПРОИЗВОДИТЕЛИ -->
            <div class="col-3 dropdown">
                <button class="btn btn-light btn-custom dropdown-toggle" type="button" id="dropdownManufacturers" data-bs-toggle="dropdown" aria-expanded="false">
                    ПРОИЗВОДИТЕЛИ
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownManufacturers">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/manufacturer/findAll">Список производителей</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/manufacturer/add">Добавить производителя</a></li>
                </ul>
            </div>

            <!-- ПОСТУПЛЕНИЯ -->
            <div class="col-3 dropdown">
                <button class="btn btn-light btn-custom dropdown-toggle" type="button" id="dropdownStaff" data-bs-toggle="dropdown" aria-expanded="false">
                    ПОСТУПЛЕНИЯ
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownStaff">
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/stock/findAll">Посмотреть поступления</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/stock/add">Добавить поступление</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/storekeeper/findAll">Посмотреть персонал</a></li>
                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/storekeeper/add">Добавить персонал</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>
