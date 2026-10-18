package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class OrdenServicio {
    private final String codigo;
    private final LocalDate fechaCreacion;
    private final Cliente cliente;
    private final ArrayList<DetalleServicio> listaDetalles=new ArrayList<>();
    private EstadoOrden estado;

    public OrdenServicio(String codigo, LocalDate fechaCreacion, Cliente cliente) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
    }

    public static String calcularValorTotalOrden(OrdenServicio ordenServicio) {
        float valorTotal=0f;
        for(DetalleServicio detalleServicio: ordenServicio.getListaDetalles()){
            valorTotal+= Float.parseFloat((detalleServicio.calcularValorDetalle(ordenServicio,detalleServicio)));
            valorTotal+=Float.parseFloat(String.valueOf(detalleServicio.getServicio().calcularManoDeObra()));
        }
        return "Valor total de la orden " + ordenServicio.getCodigo() + ": " + valorTotal;
    }


    public String getCodigo() {
        return codigo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<DetalleServicio> getListaDetalles() {
        return listaDetalles;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }
    public void agregarDetalle (DetalleServicio detalleServicio){

    }
    public void finalizar(){}

    public void agregarDetalle(String codigo, Equipo equipo, Servicio servicio, Tecnico tecnicoAsignado, String observaciones, EstadoIntervencion estado) {
        DetalleServicio detalle= new DetalleServicio(codigo, equipo, servicio, tecnicoAsignado, observaciones, estado);
        listaDetalles.add(detalle);
    }
    public static class RepuestoNoEncontradoException extends Exception {
        public RepuestoNoEncontradoException(String mensaje) {
            super(mensaje);
        }
    }
    public static class StockInsuficienteException extends Exception {
        public StockInsuficienteException(String mensaje) {
            super(mensaje);
        }
    }

}
