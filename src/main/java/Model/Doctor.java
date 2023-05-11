package Model;

import dao.Database;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        /*
        String quey = "Select * from doctor wherer mail" + email + "AND" + "password = " + password";";
        bbbd bd = new bbdd();
        bd.connection();
        ResultSet st = bd.loadSelect(query);


        try{
        this.setNmae()
        this.setset mail getStrin "maiil"
        this pass
        }cach (SQL){
        sout --> "Error a doctor.looad: " + e.getMesssage
        }
        * */

    }

    public Doctor login(String email, String password) throws SQLException {
        /*
        String quey = "Select * from doctor wherer mail" + email + "AND" + "password = " + password";";
        bbbd bd = new bbdd();
        bd.connection();
        ResultSet st = bd.loadSelect(query);

        if (rs != null){
            this.setLastLog=(LocalDatetime.now()
            Random rd = new Random();
            String code = "";
            for(int indice = 0; indice < 10; indice ++){
                code= random.nesxtInt(10);
            }
            int session = Integer.parseint(code);
            this.session(session);
            quey = "UPDATE  doctor  SET laslog = " + this.getLastLog()+ ", session= "+ this.session+ "where email=" + email + ";" ";
            this.load(email);
            //dentro de if se hace el load
            }
        * */
        Doctor doctor;
        Database db = new Database();
        doctor= db.loginDoctor(email,password);
        return doctor;
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
