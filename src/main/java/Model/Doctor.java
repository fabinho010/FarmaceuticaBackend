package Model;

import dao.Database;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Doctor extends Persona{
    //ATRIBUTOS
    private String pass;
    private LocalDateTime lastlog;
    private String session;
    private ArrayList <Chip> relaseList;

    //Constructores

    public Doctor() {
        super();
    }

    public Doctor(String name, String email, String pass, LocalDateTime lastlog, String session, ArrayList<Chip> relaseList) {
        super(name, email);
        this.pass = pass;
        this.lastlog = lastlog;
        this.session = session;
        this.relaseList = relaseList;
    }

    public Doctor(String name, String email, String pass, LocalDateTime lastlog, String session) {
        super(name, email);
        this.pass = pass;
        this.lastlog = lastlog;
        this.session = session;
    }

    //Metodos

    public Doctor login(String email, String password) throws SQLException {
        Doctor doctor= new Doctor();
        doctor= Database.loginDoctor(email,password);
        return doctor;
    }
    @Override
    public void load(String id) {

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

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public ArrayList<Chip> getRelaseList() {
        return relaseList;
    }

    public void setRelaseList(ArrayList<Chip> relaseList) {
        this.relaseList = relaseList;
    }
}
