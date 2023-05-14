package com.farmaceutica.farmaceutica;

import Model.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
;
            Doctor doctor = new Doctor();
            try {
                doctor.login(email, password);
                if(doctor.getSession() > 0) {
                    response.getWriter().write(String.valueOf(doctor.getSession()));
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Credenciales inválidos");
                }

            } catch (SQLException e) {
                System.out.println("Error en Login.doGet" + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
            }

        } else {
            // Envía una respuesta de error al cliente indicando que los parámetros son inválidos
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
        }
    }

}
