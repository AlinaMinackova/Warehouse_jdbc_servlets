package org.example.controller.warehouse;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Category;
import org.example.entity.Warehouse;
import org.example.service.WarehouseService;

import java.io.IOException;
import java.sql.SQLException;

public class WarehouseEditServlet extends HttpServlet {

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

        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]); // "5"
                    Warehouse warehouse = warehouseService.findById(id);
                    req.setAttribute("warehouse", warehouse);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }

        req.getRequestDispatcher("/warehouse/warehouse_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]); // "5"
                    String name = req.getParameter("name");
                    String address = req.getParameter("address");
                    Warehouse warehouse = new Warehouse(id, name.trim(), address.trim());
                    warehouseService.update(warehouse);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/warehouse/findAll");
    }
}
