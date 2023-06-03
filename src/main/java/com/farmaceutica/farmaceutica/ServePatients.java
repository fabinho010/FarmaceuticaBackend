package com.farmaceutica.farmaceutica;

import Model.Doctor;
import Model.Paciente;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ServePatients", value = "/ServePatients")
public class ServePatients extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = request.getParameter("mail");
        String session = request.getParameter("session");
        Doctor doctor = new Doctor();
        try {
            if (doctor.isLogged(mail,session)){
                //Cargo la lista de medicinas
                List<String> listaPacientes = doctor.listaPacientes();
                Gson gson = new Gson();
                //Conversion a JSon mediante GSON
                String pacientes = gson.toJson(listaPacientes);
                response.setHeader("Content-Type", "application/json");
                response.getWriter().write(pacientes);
            }else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Credenciales inválidos");
            }
        } catch (SQLException e) {
            System.out.println("Error en ServePatients.doGet" + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
        }

    }


}
