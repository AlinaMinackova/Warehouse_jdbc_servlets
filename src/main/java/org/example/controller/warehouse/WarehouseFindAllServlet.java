package org.example.controller.warehouse;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Manufacturer;
import org.example.entity.Warehouse;
import org.example.service.WarehouseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class WarehouseFindAllServlet extends HttpServlet {
    private WarehouseService warehouseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Object svc = config.getServletContext().getAttribute("warehouseService");
        if (!(svc instanceof WarehouseService)) {
            throw new ServletException("warehouseService not initialized");
        }
        this.warehouseService = (WarehouseService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Получаем параметры страницы и размера
            int page = 0;
            int size = 5;

            String pageParam = req.getParameter("page");
            String sizeParam = req.getParameter("size");

            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
            if (sizeParam != null) {
                size = Integer.parseInt(sizeParam);
            }

            String keyword = req.getParameter("keyword");

            List<Warehouse> warehouses;
            if (keyword != null && !keyword.trim().isEmpty()) {
                warehouses = warehouseService.search(keyword.trim());
                req.setAttribute("keyword", keyword); // чтобы отобразить в input
            } else {
                warehouses = warehouseService.findAll();
            }

            // Если хочешь постранично:
            int fromIndex = Math.min(page * size, warehouses.size());
            int toIndex = Math.min(fromIndex + size, warehouses.size());
            List<Warehouse> warehousePage = warehouses.subList(fromIndex, toIndex);

            // Ставим атрибуты для JSP
            req.setAttribute("warehouses", warehousePage);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", size);
            req.setAttribute("totalPages", (int) Math.ceil((double) warehouses.size() / size));

            // Передаём управление JSP
            req.getRequestDispatcher("/warehouse/warehouse_list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
