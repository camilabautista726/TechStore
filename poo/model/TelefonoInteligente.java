package co.edu.uniquindio.poo.model;

public class TelefonoInteligente extends Equipo{
    private String sistemaOperativo;
    private int almacenamientoGB;
    private int bateriaMah;
    private boolean compatible5G;

    public TelefonoInteligente(String codigo, String numeroSerie, String marca, String modelo, String fechaFabricacion, String descripcionEstadoFisico, String sistemaOperativo, int almacenamientoGB, int bateriaMah, boolean compatible5G) {
        super(codigo, numeroSerie, marca, modelo, fechaFabricacion, descripcionEstadoFisico);
        this.sistemaOperativo = sistemaOperativo;
        this.almacenamientoGB = almacenamientoGB;
        this.bateriaMah = bateriaMah;
        this.compatible5G = compatible5G;
    }

}

