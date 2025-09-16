package org.example.controller.warehouse;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Category;
import org.example.entity.Storekeeper;
import org.example.entity.Warehouse;
import org.example.service.CategoryService;
import org.example.service.WarehouseService;

import java.io.IOException;
import java.sql.SQLException;

public class WarehouseAddServlet extends HttpServlet {

    private WarehouseService warehouseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Получаем сервис из ServletContext (ручная "инъекция")
        Object svc = config.getServletContext().getAttribute("warehouseService");
        if (!(svc instanceof WarehouseService)) {
            throw new ServletException("warehouseService not initialized");
        }
        this.warehouseService = (WarehouseService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/warehouse/warehouse_add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String address = req.getParameter("address");

        Warehouse warehouse = new Warehouse(name.trim(), address.trim());

        try {
            warehouseService.add(warehouse);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/warehouse/findAll");
    }
}
