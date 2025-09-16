package org.example.controller.stock;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Product;
import org.example.entity.Stock;
import org.example.entity.Storekeeper;
import org.example.entity.Warehouse;
import org.example.service.*;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StockAddServlet extends HttpServlet {

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
        try {
            req.setAttribute("products", productService.findAll());
            req.setAttribute("warehouses", warehouseService.findAll());
            req.setAttribute("storekeepers", storekeeperService.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/stock/stock_add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        warehouse_id, product_id, quantity, arrival_date, storekeeper_id
        LocalDateTime arrivalDate = null;
        req.setCharacterEncoding("UTF-8");
        Long warehouseId = Long.valueOf(req.getParameter("warehouseId"));
        Long productId = Long.valueOf(req.getParameter("productId"));
        String quantity = req.getParameter("quantity");
        String arrivalDateStr = req.getParameter("arrivalDate");
        if (arrivalDateStr == null || arrivalDateStr.isEmpty()) {
            arrivalDate = LocalDateTime.now();
        }
        else {
            arrivalDate = LocalDateTime.parse(arrivalDateStr);
        }
        Long storekeeperId = Long.valueOf(req.getParameter("storekeeperId"));

        Stock stock;
        try {
            Warehouse warehouse = warehouseService.findById(warehouseId);
            Storekeeper storekeeper = storekeeperService.findById(storekeeperId);
            Product product = productService.findById(productId);
            stock = new Stock(warehouse, product, quantity, arrivalDate, storekeeper);
            stockService.add(stock);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/stock/findAll");
    }
}

