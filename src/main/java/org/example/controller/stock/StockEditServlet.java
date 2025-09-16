package org.example.controller.stock;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Product;
import org.example.entity.Stock;
import org.example.entity.Storekeeper;
import org.example.entity.Warehouse;
import org.example.service.ProductService;
import org.example.service.StockService;
import org.example.service.StorekeeperService;
import org.example.service.WarehouseService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StockEditServlet extends HttpServlet {

    private StockService stockService;
    private ProductService productService;
    private WarehouseService warehouseService;
    private StorekeeperService storekeeperService;

    public void init() throws ServletException {
        Object ss = getServletContext().getAttribute("stockService");
        Object ps = getServletContext().getAttribute("productService");
        Object ms = getServletContext().getAttribute("warehouseService");
        Object cs = getServletContext().getAttribute("storekeeperService");

        if (!(ss instanceof StockService) || !(ps instanceof ProductService) || !(ms instanceof WarehouseService) || !(cs instanceof StorekeeperService)) {
            throw new ServletException("Services not initialized");
        }

        stockService = (StockService) ss;
        productService = (ProductService) ps;
        warehouseService = (WarehouseService) ms;
        storekeeperService = (StorekeeperService) cs;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    Stock stock = stockService.findById(id);
                    req.setAttribute("stock", stock);
                    try {
                        req.setAttribute("products", productService.findAll());
                        req.setAttribute("warehouses", warehouseService.findAll());
                        req.setAttribute("storekeepers", storekeeperService.findAll());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }

        req.getRequestDispatcher("/stock/stock_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    LocalDateTime arrivalDate = null;
                    Long id = Long.parseLong(parts[1]);
                    Long warehouseId = Long.valueOf(req.getParameter("warehouseId"));
                    Long productId = Long.valueOf(req.getParameter("productId"));
                    String quantity = req.getParameter("quantity");
                    String arrivalDateStr = req.getParameter("arrivalDate");
                    if (arrivalDateStr != null && !arrivalDateStr.isEmpty()) {
                        arrivalDate = LocalDateTime.parse(arrivalDateStr);
                    }
                    Long storekeeperId = Long.valueOf(req.getParameter("storekeeperId"));

                    Stock stock;
                    try {
                        Warehouse warehouse = warehouseService.findById(warehouseId);
                        Storekeeper storekeeper = storekeeperService.findById(storekeeperId);
                        Product product = productService.findById(productId);
                        stock = new Stock(id, warehouse, product, quantity, arrivalDate, storekeeper);
                        stockService.update(stock, arrivalDate);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } catch (NumberFormatException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/stock/findAll");
    }
}
