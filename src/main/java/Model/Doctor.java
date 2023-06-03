package Model;

import dao.Database;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Doctor extends Persona {
    //ATRIBUTOS
    private String pass;
    private LocalDateTime lastlog;
    private int session;
    private ArrayList<Chip> relaseList;

    //Constructores

    public Doctor() {
        super();
    }

    public Doctor(String name, String email, String pass, LocalDateTime lastlog, int session, ArrayList<Chip> relaseList) {
        super(name, email);
        this.pass = pass;
        this.lastlog = lastlog;
        this.session = session;
        this.relaseList = relaseList;
    }

    public Doctor(String name, String email, String pass, LocalDateTime lastlog, int session) {
        super(name, email);
        this.pass = pass;
        this.lastlog = lastlog;
        this.session = session;
    }

    //Metodos

    @Override
    public void load(String id) {

        String query = "SELECT * FROM doctor where mail= '" + id + "';";
        Database db = new Database();
        db.initDatabaseConnection();

        try {
            ResultSet rs = db.getStatement().executeQuery(query);
            if (rs.next()) {
                String name = rs.getString("name");
                id = rs.getString("mail"); // Corrección: asignar el valor a "mail"
                String pass = rs.getString("pass"); // Corrección: asignar el valor a "pass"
                int session = rs.getInt("session");
                Timestamp lastloginTimestamp = rs.getTimestamp("last_log");
                LocalDateTime lastLogin = lastloginTimestamp.toLocalDateTime();
                //SETEO
                this.name = name;
                this.email = id;
                this.pass = pass;
                this.session = session;
                this.lastlog = lastLogin;
                // Mostrar los valores obtenidos por pantalla
                System.out.println("Name: " + name);
                System.out.println("Email: " + id);
                System.out.println("Password: " + pass);
                System.out.println("Session: " + session);
                System.out.println("Last Login: " + lastLogin);
                rs.close();
                db.closeDatabaseConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error en doctor.load: " + e.getMessage());
        }
        db.closeDatabaseConnection();
    }

    public void login(String email, String password) throws SQLException {

        String query = "SELECT * FROM doctor WHERE mail = '" + email + "' AND pass = '" + password + "';";
        Database db = new Database();
        db.initDatabaseConnection();
        //Mejecuta la query
        ResultSet st = db.getStatement().executeQuery(query);
        if (st.next()) {
            //Establezco el set last log
            this.setLastlog(LocalDateTime.now());
            //creao la sesion
            Random random = new Random();
            String code = "1";
            for (int indice = 0; indice < 9; indice++) {
                code = code + Integer.toString(random.nextInt(10));
            }
            int session = Integer.parseInt(code);
            //Seteo la sesion
            this.setSession(session);

            query = "UPDATE doctor SET last_log = '" + this.getLastlog() + "', session = '" + this.session + "' WHERE mail = '" + email + "';";
            db.loadUpdate(query);
            load(email);
            st.close();
            //dentro de if se hace el load
            //Cierro la conexión con la base de datos
            db.closeDatabaseConnection();
        }
        db.closeDatabaseConnection();
    }

    public boolean isLogged(String email,String session) throws SQLException {
        String query = "SELECT * FROM doctor where mail = '" + email +"' AND session = '"+ session+"';";
        Database database = new Database();
        try {
            database.initDatabaseConnection();
            //Inicio conexion base de datos
            ResultSet resultSet = database.getStatement().executeQuery(query);
            //Si me devuelve la fila
            if (resultSet.next()){
                    return true;
            }
        }finally {
            database.closeDatabaseConnection();
        }
       return false;
    }
    public void loadRealeaseList(){
        String mail = this.getEmail();
        ArrayList<Chip> listXip = new ArrayList<>();
        Chip chip ;
        Medicina medicine = new Medicina();
        Paciente paciente = new Paciente();
        LocalDate fechaHoy = LocalDate.now();
        String query = "SELECT * FROM xip where doctor_mail= '" + mail + "' AND date >= '"+ fechaHoy+"';";
        //Para 3 bases de datos para mis 3 consultas diferentes.
        Database database = new Database();
        Database database1 = new Database();
        Database database2 = new Database();
        try {
            database.initDatabaseConnection();
            ResultSet resultSet = database.getStatement().executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int medicina = resultSet.getInt("id_medicine");
                //Obtengo el objeto medicina en la base de datos
                query= "SELECT * FROM medicine WHERE id ='"+ medicina +"';";
                database1.initDatabaseConnection();
                 ResultSet resultSet1 = database1.getStatement().executeQuery(query);
                if (resultSet1.next()){

                    int medicina_id = resultSet1.getInt("id");
                    String name = resultSet1.getString("name");
                    double tmax = resultSet1.getDouble("tmax");
                    double tmin = resultSet1.getDouble("tmin");
                    medicine = new Medicina(medicina_id,name,(float) tmax,(float) tmin);

                }
                database1.closeDatabaseConnection();
                String pacient = resultSet.getString("id_patient");
                //Obtengo el objeto paciente
                query="SELECT * FROM patient WHERE mail= '" + pacient +"';";
                database2.initDatabaseConnection();
                ResultSet resultSet2 = database2.getStatement().executeQuery(query);
                if (resultSet2.next()){
                    mail = resultSet2.getString("mail");
                    String nombre = resultSet2.getString("name");
                    paciente=new Paciente(mail,nombre);

                }
                database2.closeDatabaseConnection();
                Date date = resultSet.getDate("date");
                chip = new Chip(id,this.getEmail(),medicine,paciente,date);
                listXip.add(chip);

            }

            //Añado la lista al doctor
            if (listXip.isEmpty()){
                database.closeDatabaseConnection();
                System.out.println("No hay nada");
            }else{
                this.setRelaseList(listXip);
                database.closeDatabaseConnection();
                System.out.println("Lista metida");

            }

        } catch (SQLException e) {
            System.out.println("Error en la query" + e.getMessage());
        }


    }

    public String getTable (){
        StringBuilder tabla = new StringBuilder();
        ArrayList<Chip> listaChip = this.getRelaseList();

        //Creo el html,insertando los valores de cada columna en su resoectiva casilla
        tabla.append("<table>");
        tabla.append("<thead>");
        tabla.append("<tr>" +
                "<th>Id</th>" +
                "<th>Doctor_Mail</th>" +
                "<th>Id_Medicina</th>" +
                "<th>Id_Paciente</th>" +
                "<th>Fecha</th>" +
                "</tr>");
        tabla.append("</thead>");
        tabla.append("<tbody>");
        // Recorre la lista de xips y agrega filas a la tabla
        for (Chip chip : listaChip) {
            tabla.append("<tr>");
            tabla.append("<td>").append(chip.getId()).append("</td>");
            tabla.append("<td>").append(chip.getDoctor_mail()).append("</td>");
            tabla.append("<td>").append(chip.getMedicina().getId()).append("</td>");
            tabla.append("<td>").append(chip.getPaciente().getEmail()).append("</td>");
            tabla.append("<td>").append(chip.getFechaFin()).append("</td>");
            tabla.append("</tr>");
        }
        tabla.append("</tbody>");
        tabla.append("</table>");
        return  tabla.toString();
    }

    public List listaPacientes() throws SQLException {
        String query = "SELECT mail FROM patient;";
        Database database = new Database();
        List<String> listaPacientes = new ArrayList<>();

        try {
            database.initDatabaseConnection();
            ResultSet resultSet = database.getStatement().executeQuery(query);
            while (resultSet.next()){
                String paciente = resultSet.getString("mail");
                listaPacientes.add(paciente);
            }
        } finally {
            database.closeDatabaseConnection();
        }
        return listaPacientes;
    }

    public List listaMedicinas() throws SQLException {
        String query = "SELECT * FROM medicine;";
        Database database = new Database();
        List<Medicina> listaMedicina = new ArrayList<>();
        Medicina medicina;
        try {
            database.initDatabaseConnection();
            ResultSet resultSet = database.getStatement().executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double tmax =resultSet.getDouble("tmax");
                double tmin = resultSet.getDouble("tmin");
                medicina = new Medicina(id,name,(float) tmax,(float)tmin);
                listaMedicina.add(medicina);
            }
        } finally {
            database.closeDatabaseConnection();
        }
        return listaMedicina;
    }

    public boolean darAlta(int idXip,String mailD,int medicamento,String mailP,LocalDate fecha) throws SQLException {
        java.sql.Date date = java.sql.Date.valueOf(fecha);
        String query =  "INSERT INTO xip (id, doctor_mail, id_medicine, id_patient, date) " +
                "VALUES (" + idXip + ", '" + mailD + "', " + medicamento + ", '" + mailP + "', '" + date + "');";
        Database database = new Database();
        try {
            database.initDatabaseConnection();
            int rowsAffected = database.getStatement().executeUpdate(query);
            if (rowsAffected > 0){
                return true;
            }
        }finally {
            database.closeDatabaseConnection();
        }
        return false;
    }


    //Getters y Setters

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public LocalDateTime getLastlog() {
        return lastlog;
    }

    public void setLastlog(LocalDateTime lastlog) {
        this.lastlog = lastlog;
    }


    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }

    public ArrayList<Chip> getRelaseList() {
        return relaseList;
    }

    public void setRelaseList(ArrayList<Chip> relaseList) {
        this.relaseList = relaseList;
    }
}
