package co.edu.uniquindio.poo.model;

public abstract class Servicio {
    protected String codigo;
    protected String nombre;
    protected String descripcion;
    protected float tarifaBase;
    private EmpresaTechRepair ownedByEmpresaTechRepair;

    public Servicio(String codigo,String nombre, String descripcion, float tarifaBase) {
        this.tarifaBase = tarifaBase;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(float tarifaBase) {
        this.tarifaBase = tarifaBase;
    }
    public abstract float calcularManoDeObra();

}
