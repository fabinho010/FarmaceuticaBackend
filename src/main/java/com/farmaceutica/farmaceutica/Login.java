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
        Doctor doctor = new Doctor();
        try {
            doctor= doctor.login(email,password);
            if(doctor != null){
                SecureRandom secureRandom = new SecureRandom();
                byte[] randomBytes= new byte[10];
                secureRandom.nextBytes(randomBytes);
                String codigoSesion =Base64.getEncoder().encodeToString(randomBytes);
                response.getWriter().write(codigoSesion);
            }else {
                response.getWriter().write("null");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
