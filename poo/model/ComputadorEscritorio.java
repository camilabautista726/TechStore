package co.edu.uniquindio.poo.model;

public class ComputadorEscritorio extends Equipo{
    private String procesador;
    private int ramGB;
    private int almacenamientoGB;
    private boolean graficaDedicada;

    public ComputadorEscritorio(String codigo, String numeroSerie, String marca, String modelo, String fechaFabricacion, String descripcionEstadoFisico, String procesador, int ramGB, int almacenamientoGB, boolean graficaDedicada) {
        super(codigo, numeroSerie, marca, modelo, fechaFabricacion, descripcionEstadoFisico);
        this.procesador = procesador;
        this.ramGB = ramGB;
        this.almacenamientoGB = almacenamientoGB;
        this.graficaDedicada = graficaDedicada;
    }
}