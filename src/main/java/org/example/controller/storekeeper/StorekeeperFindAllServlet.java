package org.example.controller.storekeeper;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Manufacturer;
import org.example.entity.Storekeeper;
import org.example.service.StorekeeperService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StorekeeperFindAllServlet extends HttpServlet {
    private StorekeeperService storekeeperService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Object svc = config.getServletContext().getAttribute("storekeeperService");
        if (!(svc instanceof StorekeeperService)) {
            throw new ServletException("storekeeperService not initialized");
        }
        this.storekeeperService = (StorekeeperService) svc;
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

            List<Storekeeper> storekeepers;
            if (keyword != null && !keyword.trim().isEmpty()) {
                storekeepers = storekeeperService.search(keyword.trim());
                req.setAttribute("keyword", keyword); // чтобы отобразить в input
            } else {
                storekeepers = storekeeperService.findAll();
            }

            // Если хочешь постранично:
            int fromIndex = Math.min(page * size, storekeepers.size());
            int toIndex = Math.min(fromIndex + size, storekeepers.size());
            List<Storekeeper> storekeeperPage = storekeepers.subList(fromIndex, toIndex);

            // Ставим атрибуты для JSP
            req.setAttribute("storekeepers", storekeeperPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", size);
            req.setAttribute("totalPages", (int) Math.ceil((double) storekeepers.size() / size));

            // Передаём управление JSP
            req.getRequestDispatcher("/storekeeper/storekeeper_list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
