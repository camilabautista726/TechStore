package co.edu.uniquindio.poo.model;

public abstract class Equipo {
    private final String codigo;
    private final String numeroSerie;
    private final String marca;
    private final String modelo;
    private final String fechaFabricacion;
    private String descripcionEstadoFisico;

    public Equipo(String codigo, String numeroSerie, String marca, String modelo, String fechaFabricacion, String descripcionEstadoFisico) {
        this.codigo = codigo;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.fechaFabricacion = fechaFabricacion;
        this.descripcionEstadoFisico = descripcionEstadoFisico;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getFechaFabricacion() {
        return fechaFabricacion;
    }

    public String getDescripcionEstadoFisico() {
        return descripcionEstadoFisico;
    }
}

