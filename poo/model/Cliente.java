package co.edu.uniquindio.poo.model;

import java.util.ArrayList;
import java.util.Optional;

public class Cliente {
    private final String documento;
    private  final String nombreCompleto;
    private String telefono;
    private final String correo;
    private String direccion;
    private final ArrayList<Equipo> listaEquipos= new ArrayList<>();
    private final ArrayList<OrdenServicio> listaOrdenes= new ArrayList<>();
    private final EmpresaTechRepair ownedByEmpresaTechRepair;

    public Cliente(String documento, String nombreCompleto, String telefono, String correo, String direccion, EmpresaTechRepair ownedByEmpresaTechRepair) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.ownedByEmpresaTechRepair = ownedByEmpresaTechRepair;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Equipo> getEquipos() {
        return listaEquipos;
    }

    //CRUDS
    public String registrarEquipo(Equipo equipo) {
        return "";
    }
    public Optional<Equipo> buscarEquipo(String codigoEquipo) {
        return null;
    }
    public void agregarOrden(){

    }
    public String consultarOrdenes(){
        if (listaOrdenes.isEmpty()) {
            return "El cliente no tiene órdenes";
        }
        String mensaje="";
        for(OrdenServicio o: listaOrdenes){
            mensaje+=o.mostrarOrden()+"\n";
        }
        return mensaje;
    }


}
