package org.example.controller.warehouse;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.WarehouseService;

import java.io.IOException;
import java.sql.SQLException;

public class WarehouseDeleteServlet extends HttpServlet {

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
        String path = req.getPathInfo(); // например: /5/delete
        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    warehouseService.delete(id);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/warehouse/findAll");
    }
}
