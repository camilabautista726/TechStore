package co.edu.uniquindio.poo.model;

import javax.swing.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DetalleServicio {
    private final String codigo;
    private final Equipo equipo;
    private final Servicio servicio;
    private final Tecnico tecnicoAsignado;
    private final String observaciones;
    private EstadoIntervencion estadoIntervencion;
    private final Map<String, RepuestoUtilizado> repuestosUtilizados = new HashMap<>();
    private EmpresaTechRepair ownedByEmpresaTechRepair;

    public DetalleServicio(String codigo, Equipo equipo, Servicio servicio, Tecnico tecnicoAsignado, String observaciones, EstadoIntervencion estado) {
        this.codigo = codigo;
        this.equipo = equipo;
        this.servicio = servicio;
        this.tecnicoAsignado = tecnicoAsignado;
        this.observaciones = observaciones;
        this.estadoIntervencion = estado;
    }



    public String getCodigo() {
        return codigo;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public EstadoIntervencion getEstado() {
        return estadoIntervencion;
    }

    public void setEstado(EstadoIntervencion estado) {
        this.estadoIntervencion = estado;
    }

    public Map<String, RepuestoUtilizado> getRepuestosUtilizados() {
        return Collections.unmodifiableMap(repuestosUtilizados);
    }

    public void asignarTecnico(){

    }

    public void agregarRepuesto(){

    }

    public void calcularValorTotalDetalle(){

    }


    public void agregarRepuesto(RepuestoUtilizado repuestoUtilizado) {
        repuestosUtilizados.put(repuestoUtilizado.getRepuesto().getCodigo(), repuestoUtilizado);
    }

    public String calcularValorDetalle(OrdenServicio ordenServicio, DetalleServicio detalleServicio) {
        StringBuilder resultado = new StringBuilder();
        for( DetalleServicio detalle: ordenServicio.getListaDetalles()){
            for(RepuestoUtilizado repuestoUsado: detalle.getRepuestosUtilizados().values()){
                float subTotal= (float) ((repuestoUsado.getRepuesto().getPrecioUnitario())*(repuestoUsado.getCantidad()));
                resultado.append(repuestoUsado.getRepuesto().getNombre() + " * "+
                        repuestoUsado.getCantidad() + " = "+subTotal);
            }
        }
        return resultado.toString();
    }

    public static String actualizarEstadoDetalle(DetalleServicio detalleServicio) {
        StringBuilder menuEstadosIntervencion = new StringBuilder();
        menuEstadosIntervencion.append("Seleccione el estado de la intervención:\n");
        EstadoIntervencion[] intervenciones = EstadoIntervencion.values();
        for (int i = 0; i < intervenciones.length; i++) {
            menuEstadosIntervencion.append(i + 1)
                    .append(". ")
                    .append(intervenciones[i])
                    .append("\n");
        }
        String opcion = (JOptionPane.showInputDialog(null, menuEstadosIntervencion));
        if (opcion == null || opcion.trim().isEmpty()) {
            return "Operación cancelada.";
        }
        try {
            int ajuste = Integer.parseInt(opcion);
            if (ajuste < 1 || ajuste > intervenciones.length) {
                return "Opción inválida. Seleccione un número de la lista.";
            }
            EstadoIntervencion nuevoEstado = intervenciones[ajuste - 1];
            detalleServicio.setEstado(nuevoEstado);
            return "El estado de la intervención fue actualizado a: " + nuevoEstado;
        }catch (NumberFormatException e) {
            return "Error: Debe ingresar un número válido.";
        }
    }

    public static String actualizarEstadoOrden(OrdenServicio ordenServicio) {
        StringBuilder verificacionEstadosOrden = new StringBuilder();
        for(DetalleServicio aux: ordenServicio.getListaDetalles()) {
            if(aux.getEstado()!= EstadoIntervencion.FINALIZADA) {
                return "La orden no se puede finalizar porque aún hay detalles pendientes";
            }
        }
        ordenServicio.setEstado(EstadoOrden.FINALIZADA);
        return "La orden fue finalizada.";
    }


}