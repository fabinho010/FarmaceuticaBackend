package com.farmaceutica.farmaceutica;

import Model.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ServXips", value = "/ServXips")
public class ServXips extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mail = request.getParameter("mail");
        String session = request.getParameter("session");
        System.out.println(mail);
        System.out.println(session);

        Doctor doctor = new Doctor();
        boolean checkLog;

        try {
            checkLog = doctor.isLogged(mail,session);
            if (checkLog==true){
                System.out.println("Exito");
                doctor.loadRealeaseList();
                System.out.println("Hola vato");
                System.out.println(doctor.getRelaseList());
            }
        } catch (SQLException e) {
            System.out.println("Error en doctor.isLogged" + e.getMessage());
        }
    }

}
