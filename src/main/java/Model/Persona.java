package Model;

public abstract class Persona {
    protected String name;
    protected String email;

    public Persona() {
    }

    public Persona(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public abstract void load(String id);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
