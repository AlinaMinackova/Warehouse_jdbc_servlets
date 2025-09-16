package org.example;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.example.config.DataSourceConfig;
import org.example.dao.*;
import org.example.dao.CategoryDao;
import org.example.dao.ManufacturerDao;
import org.example.dao.ProductDao;
import org.example.dao.StockDao;
import org.example.dao.StorekeeperDao;
import org.example.dao.WarehouseDao;
import org.example.service.*;
import org.example.service.CategoryService;
import org.example.service.ManufacturerService;
import org.example.service.ProductService;
import org.example.service.StockService;
import org.example.service.StorekeeperService;
import org.example.service.WarehouseService;


public class AppContextListener implements ServletContextListener {

    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Создаём пул через отдельный класс
        dataSource = DataSourceConfig.createDataSource();

        // Создаём DAO и сервис
        CategoryDao categoryDao = new CategoryDao(dataSource);
        CategoryService categoryService = new CategoryService(categoryDao);

        // Создаём DAO и сервис
        ManufacturerDao manufacturerDao = new ManufacturerDao(dataSource);
        ManufacturerService manufacturerService = new ManufacturerService(manufacturerDao);

        // Создаём DAO и сервис
        StorekeeperDao storekeeperDao = new StorekeeperDao(dataSource);
        StorekeeperService storekeeperService = new StorekeeperService(storekeeperDao);

        // Создаём DAO и сервис
        WarehouseDao warehouseDao = new WarehouseDao(dataSource);
        WarehouseService warehouseService = new WarehouseService(warehouseDao);

        // Создаём DAO и сервис
        ProductDao productDao = new ProductDao(dataSource);
        ProductService productService = new ProductService(productDao, manufacturerService, categoryService);

        // Создаём DAO и сервис
        StockDao stockDao = new StockDao(dataSource);
        StockService stockService = new StockService(stockDao, warehouseService, productService, storekeeperService);

        // Сохраняем в ServletContext
        ServletContext context = sce.getServletContext();
        context.setAttribute("dataSource", dataSource);
        context.setAttribute("categoryService", categoryService);
        context.setAttribute("manufacturerService", manufacturerService);
        context.setAttribute("storekeeperService", storekeeperService);
        context.setAttribute("warehouseService", warehouseService);
        context.setAttribute("productService", productService);
        context.setAttribute("stockService", stockService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (dataSource != null) {
            dataSource.close(); // закрываем все соединения пула
        }
    }
}