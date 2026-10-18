package co.edu.uniquindio.poo.model;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class EmpresaTechRepair {
    private final String nombreEmpresa;
    private final String nit;
    private final String telefono;
    private final String direccion;
    private final ArrayList<Cliente> listaClientes = new ArrayList<>();
    private final ArrayList<Equipo> listaEquipos = new ArrayList<>();
    private final ArrayList<Tecnico> listaTecnicos = new ArrayList<>();
    private final ArrayList<OrdenServicio> ordenes = new ArrayList<>();
    private final ArrayList<Servicio> listaServicios = new ArrayList<>();
    private final HashMap<Repuesto, Integer> listaRepuestos= new HashMap<>();

    public EmpresaTechRepair(String nombreEmpresa, String nit, String telefono, String direccion) {
        this.nombreEmpresa = nombreEmpresa;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
    }


    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getNit() {
        return nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public ArrayList<Cliente> getClientes() {
        return listaClientes;
    }

    public ArrayList<Tecnico> getListaTecnicos() {
        return listaTecnicos;
    }

    public ArrayList<OrdenServicio> getOrdenes() {
        return ordenes;
    }

    //CRUDS
    public String registrarCliente(Cliente cliente) {
        Optional<Cliente>clienteEncontrado=buscarClientePorDocumento(cliente.getDocumento());
        if(clienteEncontrado.isEmpty()){
            listaClientes.add(cliente);
            return "El cliente ha sido registrado exitosamente";
        }
        return "No se pudo registrar, ya existe un cliente con esa informacion";
    }

    public Optional <Cliente> buscarClientePorDocumento(String documento) {
        return listaClientes.stream().filter(c-> documento.equals(c.getDocumento())).findFirst();
    }


    public String registrarEquipo(Cliente cliente, Equipo equipo) {
        listaEquipos.add();
        return "";
    }


    public Optional <Equipo> buscarEquipoPorCodigo(String codigo) {
        return listaEquipos.stream().filter(c-> codigo.equals(c.getCodigo())).findFirst();
    }

    public String registrarTecnico(Tecnico tecnico) {
        listaTecnicos.add(tecnico);
        return "";
    }


    public Optional <Tecnico> buscarTecnicoPorDocumento(String documento) {
        return listaTecnicos.stream().filter(c-> documento.equals(c.getCodigo())).findFirst();
    }

    public String registrarOrden (String codigo, LocalDate fecha, String documentoCliente, Cliente cliente ) {
        Optional<OrdenServicio> detalleOrden=buscarOrdenPorCodigo(codigo);
        if(detalleOrden.isPresent()){
        return "Ya existe una orden con ese código.";
        }
        OrdenServicio orden = new OrdenServicio(codigo, fecha, cliente);
        ordenes.add(orden);
        return "Orden registrada correctamente.";
    }



    public Optional<OrdenServicio> buscarOrdenPorCodigo(String codigoOrden) {
        return ordenes.stream().filter(c-> codigoOrden.equals(c.getCodigo())).findFirst();
    }

    public String agregarDetalleAOrden(String codigo, Equipo equipo, Servicio servicio, Tecnico tecnicoAsignado, String observaciones, EstadoIntervencion estado) {
        Optional<OrdenServicio> detalleOrden=buscarOrdenPorCodigo(codigo);
        if(detalleOrden.isEmpty()){
            return "Esta orden no existe";
        }
        detalleOrden.get().agregarDetalle(codigo, equipo, servicio, tecnicoAsignado, observaciones, estado);
        return "Detalle agregado correctamente.";
    }

    public String consultarEquiposPendientes() {
        return "";
    }
    public String GeneraReporteDeServicios(){
        return "";
    }
    public String GenerarReporteDeTecnicos(){
        return "";
    }

    public static String seleccionarEspecialidad() {
        StringBuilder menuEspecialidades = new StringBuilder();
        menuEspecialidades.append("Seleccione la especialidad" +
                "\nEspecialidades: ");
        Especialidad[] especialidades = Especialidad.values();
        for (int i = 0; i < especialidades.length; i++) {
            menuEspecialidades.append(i+1).append(".").append(especialidades[i]).append("\n");
        }
        return menuEspecialidades.toString();
    }

    public static Object seleccionarCategoria() {
        StringBuilder menuCategorias = new StringBuilder();
        menuCategorias.append("Seleccione la categoria " +
                "\nCategorías: ");
        CategoriaCompatibilidad[] categorias = CategoriaCompatibilidad.values();
        for (int i = 0; i < categorias.length; i++) {
            menuCategorias.append(i+1).append(".").append(categorias[i]).append("\n");
        }
        return menuCategorias.toString();
    }

    //Crear: Metodos para crear un servicio según el tipo de servicio que se necesite
    public Diagnostico crearDiagnostico(String codigoEquipo) {
        String nombreEquipo = JOptionPane.showInputDialog("Ingrese el nombre del equipo");
        String descripcionEquipo = JOptionPane.showInputDialog("Ingrese la descripcion del equipo");
        Diagnostico diagnostico= new Diagnostico(codigoEquipo, nombreEquipo, descripcionEquipo);
        return diagnostico;
    }


    public MantenimientoPreventivo crearMantenimiento(String codigoEquipo) {
        String nombreEquipo = JOptionPane.showInputDialog("Ingrese el nombre del equipo");
        String descripcionEquipo = JOptionPane.showInputDialog("Ingrese la descripcion del equipo");
        StringBuilder menuTipoEquipos = new StringBuilder();
        menuTipoEquipos.append("Seleccione el tipo del equipo: " +
                "\nTipos de equipos: ");
        TipoEquipo[] tipos = TipoEquipo.values();
        for (int i = 0; i < tipos.length; i++) {
            menuTipoEquipos.append(i+1).append(".").append(tipos[i]).append("\n");
        }
        int opcion= Integer.parseInt(JOptionPane.showInputDialog( menuTipoEquipos.toString()));
        TipoEquipo tipoEquipo = tipos[opcion-1];
        MantenimientoPreventivo mantenimientoPreventivo= new MantenimientoPreventivo (codigoEquipo, nombreEquipo, descripcionEquipo, tipoEquipo);
        return mantenimientoPreventivo;
    }


    public ReparacionCorrectiva crearReparacion(String codigoEquipo) {
        String nombreEquipo = JOptionPane.showInputDialog("Ingrese el nombre del equipo");
        String descripcionEquipo = JOptionPane.showInputDialog("Ingrese la descripcion del equipo");
        int horas= Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de horas que requiere la reparación: "));
        ReparacionCorrectiva reparacionCorrectiva= new ReparacionCorrectiva(codigoEquipo, nombreEquipo, descripcionEquipo, horas);
        return reparacionCorrectiva;
    }

    public Optional<DetalleServicio> buscarDetallePorCodigo(String codigoDetalle) {
        //Pedimos el stream de ordenes
        return ordenes.stream()
                //FlatMap convierte la lista de detalles de cada orden en una lista completa
                .flatMap(orden -> orden.getListaDetalles().stream())
                //Filter permite filtrarla para que encuentre la que tiene un codigo igual al que le dimos
                .filter(detalle -> detalle.getCodigo().equals(codigoDetalle))
                .findFirst();
    }

    public void usarRepuesto(String codigoOrden, String codigoDetalle, String codigoRepuesto, int cantidad)
            throws OrdenServicio.RepuestoNoEncontradoException, OrdenServicio.StockInsuficienteException {

        OrdenServicio orden = buscarOrdenPorCodigo(codigoOrden)
                .orElseThrow(() -> new OrdenServicio.RepuestoNoEncontradoException("No existe la orden " + codigoOrden));

        DetalleServicio detalle = orden.getListaDetalles().stream()
                .filter(d -> d.getCodigo().equals(codigoDetalle))
                .findFirst()
                .orElseThrow(() -> new OrdenServicio.RepuestoNoEncontradoException("No existe el detalle " + codigoDetalle));

        Repuesto repuesto = OrdenServicio.buscarRepuestoPorCodigo(codigoRepuesto)
                .orElseThrow(() -> new OrdenServicio.RepuestoNoEncontradoException("No existe el repuesto " + codigoRepuesto));

        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (repuesto.getCantidadDisponible() < cantidad) {
            throw new OrdenServicio.StockInsuficienteException("Stock insuficiente. Disponible: " + repuesto.getCantidadDisponible());
        }

        repuesto.descontarStock(cantidad);
        detalle.agregarRepuesto(new RepuestoUtilizado(repuesto, cantidad));
    }


    public String calcularValorDetalle(String codigoOrden, String codigoDetalle)  {
        Optional<OrdenServicio> orden=buscarOrdenPorCodigo(codigoOrden);
        if(orden.isEmpty()){
            return "No existe una orden con ese código.";
        }
        Optional<DetalleServicio> detalleOrden= buscarDetallePorCodigo(codigoDetalle);
        if(detalleOrden.isEmpty()){
            return "No existe un detalle con ese código.";
        }
        OrdenServicio ordenServicio = orden.get();
        DetalleServicio detalleServicio = detalleOrden.get();
        String resultado= detalleServicio.calcularValorDetalle(ordenServicio, detalleServicio);
        return resultado;
    }

    public String calcularValorTotal(String codigoOrden) {
        Optional<OrdenServicio> orden=buscarOrdenPorCodigo(codigoOrden);
        if(orden.isEmpty()){
            return "No existe una orden con este código";
        }

        String total= OrdenServicio.calcularValorTotalOrden(orden.get());
        return "Valor total de la orden " + codigoOrden + ": " + total ;
    }

    public String actualizarEstadoDetalle(String codigoOrden, String codigoDetalle) {
        Optional<OrdenServicio> orden=buscarOrdenPorCodigo(codigoOrden);
        if(orden.isEmpty()){
            return "No existe una orden con este código";
        }
        Optional<DetalleServicio> detalleOrden= buscarDetallePorCodigo(codigoDetalle);
        if(detalleOrden.isEmpty()){
            return "No existe un detalle con este código";
        }
        DetalleServicio detalleServicio = detalleOrden.get();
        String resultado= DetalleServicio.actualizarEstadoDetalle(detalleServicio);
        return resultado;
    }


    public String finalizarOrden(String codigoOrden) {
        Optional<OrdenServicio> orden=buscarOrdenPorCodigo(codigoOrden);
        if(orden.isEmpty()){
            return "No existe una orden con este código";
        }
        OrdenServicio ordenServicio = orden.get();
        String resultado= DetalleServicio.actualizarEstadoOrden(ordenServicio);
        return resultado;
    }

    public String consultarInventarioCritico() {
        ArrayList<Repuesto>repuestos= new ArrayList<>();
        for(Repuesto repuesto : this.listaRepuestos.keySet()) {
            if(repuesto.getCantidadDisponible() <= 5) {
                repuestos.add(repuesto);
            }
        }
        return repuestos.toString();
    }
}
