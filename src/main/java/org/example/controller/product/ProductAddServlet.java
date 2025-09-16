package org.example.controller.product;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.entity.Category;
import org.example.entity.Manufacturer;
import org.example.entity.Product;
import org.example.entity.Warehouse;
import org.example.service.CategoryService;
import org.example.service.ManufacturerService;
import org.example.service.ProductService;
import org.example.service.WarehouseService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;

@MultipartConfig
public class ProductAddServlet extends HttpServlet {

    private ProductService productService;
    private ManufacturerService manufacturerService;
    private CategoryService categoryService;

    public void init() throws ServletException {
        Object ps = getServletContext().getAttribute("productService");
        Object ms = getServletContext().getAttribute("manufacturerService");
        Object cs = getServletContext().getAttribute("categoryService");

        if (!(ps instanceof ProductService) || !(ms instanceof ManufacturerService) || !(cs instanceof CategoryService)) {
            throw new ServletException("Services not initialized");
        }

        productService = (ProductService) ps;
        manufacturerService = (ManufacturerService) ms;
        categoryService = (CategoryService) cs;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        не забудь добавить отображение категории и производителей

        try {
            req.setAttribute("manufacturers", manufacturerService.findAll());
            req.setAttribute("categories", categoryService.findAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        req.getRequestDispatcher("/product/product_add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

//        name, manufacturer_id, category_id, image_url, life_days, weight, composition
        String name = req.getParameter("name");
        String lifeDaysStr = req.getParameter("lifeDays");
        String weightStr = req.getParameter("weight");


        Integer lifeDays = (lifeDaysStr != null && !lifeDaysStr.isEmpty()) ? Integer.valueOf(lifeDaysStr) : null;
        BigDecimal weight = (weightStr != null && !weightStr.isEmpty()) ? new BigDecimal(weightStr.replace(",", ".")) : null;
        String composition = req.getParameter("composition");

        String manufacturerIdStr = req.getParameter("manufacturerId");
        String categoryIdStr = req.getParameter("categoryId");

        Part filePart = req.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        if (fileName != null && !fileName.isEmpty()) {
            String uploadsDir = getServletContext().getRealPath("/uploads");
            File uploadsFolder = new File(uploadsDir);

            File file = new File(uploadsFolder, fileName);

            try (InputStream input = filePart.getInputStream();
                 FileOutputStream output = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
            }
            fileName = "/" + fileName;
        }
        else {
            fileName = "/default.jpg";
        }

        try {
            Long manufacturerId = Long.parseLong(manufacturerIdStr);
            Long categoryId = Long.parseLong(categoryIdStr);

            Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
            Category category = categoryService.findById(categoryId);

            Product product = new Product(name.trim(), manufacturer, category, fileName, lifeDays, weight, composition);

            productService.add(product);

        } catch (NumberFormatException | SQLException e) {
            throw new ServletException(e);
        }

        resp.sendRedirect(req.getContextPath() + "/product/findAll");
    }
}
