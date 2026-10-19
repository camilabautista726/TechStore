package co.edu.uniquindio.poo.model;

import javax.swing.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DetalleServicio {
    private final String codigo;
    private final Equipo equipo;
    private final Servicio servicio;
    private Tecnico tecnicoAsignado;
    private final String observaciones;
    private EstadoIntervencion estadoIntervencion;
    private final Map<String, RepuestoUtilizado> repuestosUtilizados = new HashMap<>();
    private OrdenServicio ordenServicio;
    private EmpresaTechRepair ownedByEmpresaTechRepair;

    public DetalleServicio(String codigo, Equipo equipo, Servicio servicio,  String observaciones, EstadoIntervencion estado) {
        this.codigo = codigo;
        this.equipo = equipo;
        this.servicio = servicio;
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

    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
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

    public OrdenServicio getOrdenServicio() {
        return ordenServicio;
    }

    public void setOrdenServicio(OrdenServicio ordenServicio) {
        this.ordenServicio = ordenServicio;
    }

    @Override
    public String toString() {
        return "DetalleServicio{" + "\n"+
                "codigo='" + codigo + "\n"+
                "equipo=" + equipo.getCodigo() + "\n"+
                "marca del equipo=" + equipo.getMarca() + "\n"+
                "modelo del equipo=" + equipo.getModelo() + "\n"+
                "servicio=" + servicio.getCodigo()+ "\n"+
                "tecnicoAsignado=" + tecnicoAsignado.getNombre() + "\n"+
                "observaciones='" + observaciones + "\n"+
                "estadoIntervencion=" + estadoIntervencion.name() + "\n"+
                //toString devuelve todos los atributos, no se le puede aplicar un metodo porque no hay forma de pedir
                //los parámetros
                '}'+ "\n";
    }

    public Map<String, RepuestoUtilizado> getRepuestosUtilizados() {
        return Collections.unmodifiableMap(repuestosUtilizados);
    }

    public void asignarTecnico(){

    }

    public void agregarRepuesto(RepuestoUtilizado repuestoUtilizado) {
        repuestosUtilizados.put(repuestoUtilizado.getRepuesto().getCodigo(), repuestoUtilizado);
    }

    public double calcularValorDetalle(OrdenServicio ordenServicio, DetalleServicio detalleServicio) {
        double subTotal = 0;
        for( DetalleServicio detalle: ordenServicio.getListaDetalles()){
            for(RepuestoUtilizado repuestoUsado: detalle.getRepuestosUtilizados().values()){
                 subTotal= ((repuestoUsado.getRepuesto().getPrecioUnitario())*(repuestoUsado.getCantidad()));
                 subTotal+= servicio.calcularManoDeObra();
               return  subTotal;
            }
        }
        return subTotal;
    }
    public double calcularCostoRepuestos() {
        double total = 0;
        for (RepuestoUtilizado r : repuestosUtilizados.values()) {
            total += r.getRepuesto().getPrecioUnitario() * r.getCantidad();
        }
        return total;
    }

//NOTA IMPORTANTE: Aunque para  poo los metodos que van en las clases no deberían contener static
    //para este caso tuvimos que implementarlo con el fin de que el código pudiera compilar correctamente
    // y a su vez, para que el código pudiera ordenarse mejor
    public static String actualizarEstadoDetalle(DetalleServicio detalleServicio) {
        //StringBuilder para construir un mensaje
        StringBuilder menuEstadosIntervencion = new StringBuilder();
        //Muestra el menu del enum
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
    public boolean estaPendiente(){
        if(estadoIntervencion==EstadoIntervencion.FINALIZADA){
            return false;
        }
        return true;
    }


}