package Model;

import dao.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Doctor extends Persona{
    //ATRIBUTOS
    private String pass;
    private LocalDateTime lastlog;
    private int session;
    private ArrayList <Chip> relaseList;

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

        String query = "Select * from doctor where mail=" + email + ";";
        Database db = new Database();
        db.initDatabaseConnection();
        ResultSet rs = db.loadSelect(query);
        try{
            if (rs.next()){
                String name = rs.getString("name");
                String mail = rs.getString("pass");
                String pass = rs.getString("mail");
                int session = rs.getInt("session");
                Date lastLogin = rs.getDate("last_log");
                //SETEO
                this.name = name;
                this.email = mail;
                this.pass = pass;
                this.session = session;
                this.lastlog = LocalDateTime.ofInstant(lastLogin.toInstant(), ZoneId.systemDefault());
                // Mostrar los valores obtenidos por pantalla
                System.out.println("Name: " + name);
                System.out.println("Email: " + mail);
                System.out.println("Password: " + pass);
                System.out.println("Session: " + session);
                System.out.println("Last Login: " + lastLogin);

            }
        } catch (SQLException e) {
            System.out.println("Error a doctor.load" + e.getMessage());
        }
    }

    public void login(String email, String password) throws SQLException {

        String query = "SELECT * FROM doctor WHERE mail = '" + email + "' AND pass = '" + password + "';";
        Database db = new Database();
        db.initDatabaseConnection();

        //Mejecuta la query
        ResultSet st = db.loadSelect(query);
        if (st != null){
            //Establezco el set last log
            this.setLastlog(LocalDateTime.now());
            //creao la sesion
            Random random = new Random();
            String code = "";
            for(int indice = 0; indice < 10; indice ++){
                code= code + Integer.toString(random.nextInt(10));
            }
            int session = Integer.parseInt(code);
            //Seteo la sesion
            this.setSession(session);

           query = "UPDATE doctor SET last_log = '" + this.getLastlog() + "', session = '" + this.session + "' WHERE mail = '" + email + "';";
            db.loadUpdate(query);
            this.load(email);
            //dentro de if se hace el load
            //Cierro la conexión con la base de datos
            db.closeDatabaseConnection();
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
