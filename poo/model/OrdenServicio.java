package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;

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
    public String toString() {
        return "OrdenServicio{\n" +
                "codigo='" + codigo + '\n' +
                "fechaCreacion=" + fechaCreacion +"\n"+
                "cliente=" + cliente.getNombreCompleto() +"\n"+
                "estado=" + estado.name() +"\n"+
                "total=" + calcularValorTotalOrden(this)+"\n"+
                '}'+ "\n";
    }

    public static double calcularValorTotalOrden(OrdenServicio ordenServicio) {
        double valorTotal=0;
        for(DetalleServicio detalleServicio: ordenServicio.getListaDetalles()){
            if(detalleServicio.getServicio()instanceof ReparacionCorrectiva reparacionCorrectiva){
                valorTotal=ReparacionCorrectiva.calcularValorTotalOrden(ordenServicio);
            }else if(detalleServicio.getServicio()instanceof MantenimientoPreventivo mantenimientoPreventivo){
                valorTotal=MantenimientoPreventivo.calcularValorTotalOrden(ordenServicio);
            }else if(detalleServicio.getServicio()instanceof Diagnostico diagnostico){
                valorTotal=Diagnostico.calcularValorTotalOrden(ordenServicio);
            }
        }
        return valorTotal;
    }

    public void agregarDetalle(String codigo, Equipo equipo, Servicio servicio, String observaciones, EstadoIntervencion estado) {
        DetalleServicio detalle= new DetalleServicio(codigo, equipo, servicio,  observaciones, estado);
        detalle.setOrdenServicio(this);
        listaDetalles.add(detalle);
    }
    //Exception_____________________________________________________________
    //Deben tener static para que puedan funcionar
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
    //________________________________________________________________________
    public String mostrarOrden (){
        String mensaje= this.toString();
        for(DetalleServicio d: listaDetalles){
            mensaje+=d.toString()+ "\n";
        }
        return mensaje;
    }
    public ArrayList<Equipo>obtenerEquiposPendientes(){
        ArrayList<Equipo> listaEquiposPendientes= new ArrayList<>();
        for(DetalleServicio d: listaDetalles){
            if(d.estaPendiente()){
                listaEquiposPendientes.add(d.getEquipo());
            }
        }
        return listaEquiposPendientes;
    }

}