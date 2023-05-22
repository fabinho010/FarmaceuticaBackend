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
        Doctor doctor = new Doctor();
        boolean checkLog;

        try {
            checkLog = doctor.isLogged(mail,session);
            if (checkLog==true){
                System.out.println("Exito");
                //Cargo los datos al objeto doctor
                doctor.load(mail);
                System.out.println(doctor.getEmail());
                doctor.loadRealeaseList();
                if (doctor.getRelaseList().isEmpty()){
                    response.sendError(HttpServletResponse.SC_NOT_FOUND,"La lista de Chips esta vacía.");
                } else {
                    response.getWriter().write(doctor.getTable());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error en doctor.isLogged" + e.getMessage());
        }
    }

}
