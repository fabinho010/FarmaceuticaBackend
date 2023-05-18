package Model;

import java.util.Date;

public class Chip {
    //Atributos
    private int id;
    private String doctor_mail;
    private Medicina medicina;
    private Paciente paciente;
    private Date fechaFin;

    //Constructores


    public Chip() {
    }

    public Chip(int id, String doctor_mail, Medicina medicina, Paciente paciente, Date fechaFin) {
        this.id = id;
        this.doctor_mail = doctor_mail;
        this.medicina = medicina;
        this.paciente = paciente;
        this.fechaFin = fechaFin;
    }

//Metodos

    public void load(int id){

    }

    //Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctor_mail() {
        return doctor_mail;
    }

    public void setDoctor_mail(String doctor_mail) {
        this.doctor_mail = doctor_mail;
    }

    public Medicina getMedicina() {
        return medicina;
    }

    public void setMedicina(Medicina medicina) {
        this.medicina = medicina;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }


    @Override
    public String toString() {
        return "Chip{" +
                "id=" + id +
                ", doctor_mail='" + doctor_mail + '\'' +
                ", medicina=" + medicina +
                ", paciente=" + paciente +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
