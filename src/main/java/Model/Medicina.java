package Model;

public class Medicina {
    //Atributos
    private int id;
    private String name;
    private float tMax;
    private float tMin;

    //Constructores

    public Medicina() {
    }

    public Medicina(int id, String name, float tMax, float tMin) {
        this.id = id;
        this.name = name;
        this.tMax = tMax;
        this.tMin = tMin;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float gettMax() {
        return tMax;
    }

    public void settMax(float tMax) {
        this.tMax = tMax;
    }

    public float gettMin() {
        return tMin;
    }

    public void settMin(float tMin) {
        this.tMin = tMin;
    }

}
