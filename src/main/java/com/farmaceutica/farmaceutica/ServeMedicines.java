package com.farmaceutica.farmaceutica;

import Model.Doctor;
import Model.Medicina;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServeMedicines", value = "/ServeMedicines")
public class ServeMedicines extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = request.getParameter("mail");
        String session = request.getParameter("session");
        Doctor doctor = new Doctor();

        try {
            if (doctor.isLogged(mail,session)){
                List<Medicina> listaMedicinas =doctor.listaMedicinas();
                Gson gson = new Gson();
                String medicinas = gson.toJson(listaMedicinas);
                response.setHeader("Content-Type","application/json");
                response.getWriter().write(medicinas);
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,"Credenciales inválidos");
            }
        } catch (SQLException e) {
            System.out.println("Error en ServePatients.doGet" + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
        }
    }

}
