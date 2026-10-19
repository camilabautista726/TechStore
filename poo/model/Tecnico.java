package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class Tecnico {
    private final String codigo;
    private final String nombre;
    private final String documento;
    private Especialidad especialidad;
    private EstadoLaboral estadoLaboral;
    private ArrayList<DetalleServicio> listaDetallesAsignados;
    private EmpresaTechRepair ownedByEmpresaTechRepair;

    public Tecnico(String codigo, String nombre, String documento, Especialidad especialidad, EstadoLaboral estadoLaboral) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.documento = documento;
        this.especialidad = especialidad;
        this.estadoLaboral = estadoLaboral;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public EstadoLaboral getEstadoLaboral() {
        return estadoLaboral;
    }

    public void setEstadoLaboral(EstadoLaboral estadoLaboral) {
        this.estadoLaboral = estadoLaboral;
    }

    public void agregarDetalleAsignado(DetalleServicio detalle) {
        listaDetallesAsignados.add(detalle);
    }

    public int cantidadDetallesSignados() {
        int cantDetalles = listaDetallesAsignados.size(); //size guarda un int
        return cantDetalles;
    }

    public String consultarTrabajos() {
        if (listaDetallesAsignados.isEmpty()) {
            return "El técnico no tiene trabajos asignados";
        }
        String pendientes = "";
        String enProceso = "";
        String finalizados = "";
        for (DetalleServicio d : listaDetallesAsignados) {
            if (d.getEstado() == EstadoIntervencion.PENDIENTE) {
                pendientes+= d.toString() + "\n";
            } else if (d.getEstado() == EstadoIntervencion.EN_PROCESO) {
                enProceso+= d.toString() + "\n";
            } else if (d.getEstado() == EstadoIntervencion.FINALIZADA) {
                finalizados+= d.toString() + "\n";
            }
        }
        return "Trabajos de " + this.getNombre() + ":\n" +
                "PENDIENTES:\n" + pendientes + "\n" +
                "EN PROCESO:\n" + enProceso + "\n" +
                "FINALIZADOS:\n" + finalizados;
    }
    public int contarDetallesFinalizados (){
        int contador=0;
        for(DetalleServicio d: listaDetallesAsignados){
            if(d.getEstado()==EstadoIntervencion.FINALIZADA){
                contador++;
            }
        }
        return contador;
    }
    public double calcularTotalFinalizados(){
        double total=0;
        for(DetalleServicio d: listaDetallesAsignados){
            if(d.getEstado()==EstadoIntervencion.FINALIZADA){
                total += d.calcularValorDetalle(d.getOrdenServicio(), d);
            }
        }
        return total;
    }
}