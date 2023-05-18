package Model;

import dao.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        ResultSet st = db.loadSelect(query);
        if (st.getRow() > 0) {
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
        Doctor doctor = new Doctor();
        try {
            database.initDatabaseConnection();
            //Inicio conexion base de datos
            ResultSet resultSet = database.loadSelect(query);
            //Si me devuelve la fila
            if (resultSet.getRow()>0){
                //Obtengo la contraseña para hacer el login
                String pass = resultSet.getString("pass");
                //Llamo al metodo login
                doctor.login(email,pass);
                //Confirmo las creendenciales
                if (doctor.getSession()>0){
                    resultSet.close();
                    return true;
                }
            }
        }finally {
            database.closeDatabaseConnection();
        }
       return false;
    }
    public void loadRealeaseList(){
        String mail = this.getEmail();
        System.out.println(mail);
        ArrayList<Chip> listXip = new ArrayList<>();
        Chip chip ;
        Medicina medicine = new Medicina();
        Paciente paciente = new Paciente();
        LocalDate fechaHoy = LocalDate.now();
        String query = "SELECT * FROM xip where doctor_mail= '" + mail + "' AND date >= '"+ fechaHoy+"';";
        Database database = new Database();
        try {
            database.initDatabaseConnection();
            ResultSet resultSet = database.loadSelect(query);
            System.out.println("Entro 1");
            while (resultSet.next()){
                System.out.println("Entro ya");
                int id = resultSet.getInt("id");
                mail = resultSet.getString("doctor_mail");
                int medicina = resultSet.getInt("id_medicine");
                //Obtengo el objeto medicina en la base de datos
                query= "SELECT * FROM medicine WHERE id ='"+ medicina +"';";
                resultSet = database.loadSelect(query);
                if (resultSet.next()){
                    int medicina_id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    double tmax = resultSet.getDouble("tmax");
                    double tmin = resultSet.getDouble("tmin");
                    medicine = new Medicina(medicina_id,name,(float) tmax,(float) tmin);
                }
                String pacient = resultSet.getString("id_patient");
                //Obtengo el objeto paciente
                query="SELECT * FROM patient WHERE mail= '" + pacient +"';";
                resultSet = database.loadSelect(query);
                if (resultSet.next()){
                    mail = resultSet.getString("mail");
                    String nombre = resultSet.getString("name");
                    paciente=new Paciente(mail,nombre);
                }
                Date date = resultSet.getDate("date");
                chip = new Chip(id,medicine,paciente,date);
                listXip.add(chip);
            }
            //Añado la lista al doctor
            if (listXip.isEmpty()){
                System.out.println("No hay na");
            }else{
                this.setRelaseList(listXip);
                System.out.println("Lista metida");
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Error en la query" + e.getMessage());
        }


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
