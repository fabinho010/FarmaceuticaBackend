package Model;

public class Paciente extends Persona{

    //Constructores

    public Paciente() {
        super();
    }

    public Paciente(String name, String email) {
        super(name, email);
    }
    //Metodo
    @Override
    public void load(String id) {

    }


    @Override
    public String toString() {
        return "Paciente{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
