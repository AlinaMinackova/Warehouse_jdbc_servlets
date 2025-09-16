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
import java.time.LocalDate;

public class StorekeeperEditServlet extends HttpServlet {

    private StorekeeperService storekeeperService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Получаем сервис из ServletContext (ручная "инъекция")
        Object svc = config.getServletContext().getAttribute("storekeeperService");
        if (!(svc instanceof StorekeeperService)) {
            throw new ServletException("storekeeperService not initialized");
        }
        this.storekeeperService = (StorekeeperService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo();

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    Storekeeper storekeeper = storekeeperService.findById(id);
                    req.setAttribute("storekeeper", storekeeper);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }

        req.getRequestDispatcher("/storekeeper/storekeeper_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    String lastName = req.getParameter("lastName");
                    String firstName = req.getParameter("firstName");
                    String middleName = req.getParameter("middleName");
                    String email = req.getParameter("email");
                    LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));

                    Storekeeper storekeeper = new Storekeeper(id, lastName.trim(), firstName.trim(), middleName.trim(), email.trim(), birthday);
                    storekeeperService.update(storekeeper);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/storekeeper/findAll");
    }
}
