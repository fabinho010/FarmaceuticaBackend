package com.farmaceutica.farmaceutica;

import Model.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "Release", value = "/Release")
public class Release extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String mail = request.getParameter("mail");
            String session = request.getParameter("session");
            String idXip = request.getParameter("idXip");
            String mailP = request.getParameter("paciente");
            String medicamento = request.getParameter("medicamento");
            String date = request.getParameter("fecha");

            Doctor doctor = new Doctor();
            //Para comprobar la fecha es correcta
             LocalDate fechaActual = LocalDate.now();
             LocalDate fechaIntro = LocalDate.parse(date);

            try {
                if (doctor.isLogged(mail,session)){
                    if (fechaIntro.isAfter(fechaActual)){
                        //Compruebo con un booleano si se se da de alta el chip
                       boolean respuesta = doctor.darAlta(Integer.parseInt(idXip),mail,Integer.valueOf(medicamento),mailP,fechaIntro);
                       if (respuesta){
                           response.getWriter().write("ok");
                       }
                    }else {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        response.getWriter().write("Fecha no válida");
                    }

                }else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Credenciales inválidos");
                }
            } catch (SQLException e) {
                System.out.println("Error en ServePatients.doGet" + e.getMessage());
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor");
            }
    }
}
