package co.edu.uniquindio.poo.model;

public class ComputadorPortatil extends Equipo{
    private float pantallaPulgadas;
    private int ramGB;
    private int almacenamientoGB;
    private boolean bateriaExtraible;

    public ComputadorPortatil(String codigo, String numeroSerie, String marca, String modelo, String fechaFabricacion, String descripcionEstadoFisico, float pantallaPulgadas, int ramGB, int almacenamientoGB, boolean bateriaExtraible) {
        super(codigo, numeroSerie, marca, modelo, fechaFabricacion, descripcionEstadoFisico);
        this.pantallaPulgadas = pantallaPulgadas;
        this.ramGB = ramGB;
        this.almacenamientoGB = almacenamientoGB;
        this.bateriaExtraible = bateriaExtraible;
    }
}

