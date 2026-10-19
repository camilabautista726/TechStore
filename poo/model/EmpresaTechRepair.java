package co.edu.uniquindio.poo.model;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
    //Getters y setters________________________________
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
    //CRUDS________________________________________________________
    public String registrarCliente(Cliente cliente) {
        Optional<Cliente>clienteEncontrado=buscarClientePorDocumento(cliente.getDocumento());
        if(clienteEncontrado.isEmpty()){
            listaClientes.add(cliente);
            return "El cliente ha sido registrado exitosamente";
        }
        return "No se pudo registrar, ya existe un cliente con esa informacion";
    }

    public String registrarRepuesto(Repuesto repuesto) {
        //Busca el repuesto pero como se trata de un HashMap hay que usar el anyMatch
        boolean existe = listaRepuestos.keySet().stream()
                .anyMatch(r -> r.getCodigo().equals(repuesto.getCodigo()));
        if (existe) {
            return "Ya existe un repuesto con ese código.";
        }
        listaRepuestos.put(repuesto, repuesto.getCantidadDisponible());
        return "Repuesto registrado correctamente.";
    }

    public Optional <Cliente> buscarClientePorDocumento(String documento) {
        return listaClientes.stream().filter(c-> documento.equals(c.getDocumento())).findFirst();
    }

    public String registrarEquipo(Cliente cliente, Equipo equipo) {
        Optional<Equipo> equipoEncontrado = buscarEquipoPorCodigo(equipo.getCodigo());
        if (equipoEncontrado.isEmpty()) {
            listaEquipos.add(equipo);
            cliente.registrarEquipo(equipo);
            return "Equipo registrado y asociado al cliente exitosamente";
        }
        return "Ya existe un equipo con esa información";
    }

    public Optional <Equipo> buscarEquipoPorCodigo(String codigo) {
        return listaEquipos.stream().filter(c-> codigo.equals(c.getCodigo())).findFirst();
    }
    public String registrarTecnico(Tecnico tecnico) {
        Optional<Tecnico> tecnicoEncontrado = buscarTecnicoPorDocumento(tecnico.getDocumento());
        if (tecnicoEncontrado.isEmpty()) {
            listaTecnicos.add(tecnico);
            return "El tecnico fue registrado exitosamente";
        }
        return "Ya existe un tecnico con esa informacion";
    }

    public String asignarTecnicoADetalle(String codigoOrden, String codigoDetalle) {
        //Verificar si la orden y el detalle existen
        Optional<OrdenServicio> ordenEncontrada = buscarOrdenPorCodigo(codigoOrden);
        if (ordenEncontrada.isEmpty()) {
            return "No existe una orden con el código " + codigoOrden;
        }
        OrdenServicio orden= ordenEncontrada.get();
        Optional<DetalleServicio> detalleOpt = buscarDetallePorCodigo(codigoDetalle);
        if (detalleOpt.isEmpty()) {
            return "No existe un detalle con el código " + codigoDetalle;
        }
        //Traer el detalle y la especialidad que necesita según su tipo de equipo
        DetalleServicio detalle = detalleOpt.get();
        Especialidad especialidadRequerida = detalle.getEquipo().getEspecialidadRequerida();
        //Buscar el técnico que cumpla con esa especialidad. Recordar que técnico es una clase abstracta
        Optional<Tecnico> tecnicoAutorizado = buscarTecnicoAutorizado(especialidadRequerida);
        if (tecnicoAutorizado.isEmpty()) {
            return "Error: No hay ningún técnico activo y autorizado para atender la especialidad "
                    + especialidadRequerida;
        }
        Tecnico tecnico = tecnicoAutorizado.get();
        // Se añade el técnico al detalle y se añade a su lista de asignaciones
        detalle.setTecnicoAsignado(tecnico);
        tecnico.agregarDetalleAsignado(detalle);
        return "Técnico " + tecnico.getNombre() + " (Documento: " + tecnico.getDocumento()
                + ") asignado exitosamente al detalle " + codigoDetalle;
    }

    private Optional<Tecnico> buscarTecnicoAutorizado(Especialidad especialidadRequerida) {
        return listaTecnicos.stream().filter(t -> t.estaAutorizado(especialidadRequerida) ).min(Comparator.comparingInt(Tecnico::cantidadDetallesSignados));
    }

    public Optional <Tecnico> buscarTecnicoPorDocumento(String documento) {
        return listaTecnicos.stream().filter(c-> documento.equals(c.getCodigo())).findFirst();
    }

    public String registrarOrden (String codigo, LocalDate fecha, String documentoCliente, Cliente cliente ) {
        Optional<OrdenServicio> detalleOrden=buscarOrdenPorCodigo(codigo);
        if(detalleOrden.isPresent()){
            return "Ya existe una orden con ese código.";
        }
        //La orden se crea primero con estos datos, los detalles se asignan con otro metodo para que pueda conformar la lista
        OrdenServicio orden = new OrdenServicio(codigo, fecha, cliente);
        ordenes.add(orden);
        return "Orden registrada correctamente.";
    }

    public Optional<OrdenServicio> buscarOrdenPorCodigo(String codigoOrden) {
        return ordenes.stream().filter(c-> codigoOrden.equals(c.getCodigo())).findFirst();
    }

    public Optional<RepuestoUtilizado> buscarRepuestoPorCodigo(String codigoOrden, String codigoRepuesto) {
        //Verifica la existencia de la orden
        Optional<OrdenServicio> detalleOrden=buscarOrdenPorCodigo(codigoOrden);
        if(detalleOrden.isEmpty()){
            return Optional.empty();
        }
        //FlatMap: basicamente une las listas de la lista que las contiene (flat= aplanar). Busca los repuestos dentro de la lista repuestos utilizados
        //saca sus valores, en este caso sus codigos y los filtra hasta encontrar el codigo que se le ha dado
        return detalleOrden.get().getListaDetalles().stream().flatMap(detalle -> detalle.getRepuestosUtilizados().values().stream())
                .filter(repuestoUsado -> repuestoUsado.getRepuesto().getCodigo().equals(codigoRepuesto))
                .findFirst();
    }


    public String agregarDetalleAOrden(String codigo, Equipo equipo, Servicio servicio, String observaciones, EstadoIntervencion estado) {
        Optional<OrdenServicio> detalleOrden=buscarOrdenPorCodigo(codigo);
        if(detalleOrden.isEmpty()){
            return "Esta orden no existe";
        }
        //Agregar detalle permite ir creando la lista de detalles que tiene cada orden
        detalleOrden.get().agregarDetalle(codigo, equipo, servicio, observaciones, estado);
        return "Detalle agregado correctamente.";
    }


    public static Object seleccionarCategoria() {
        //StringBuilder funciona igual que un mensaje, solo que para ir armándolo se debe usar el append()
        StringBuilder menuCategorias = new StringBuilder();
        menuCategorias.append("Seleccione la categoria " +
                "\nCategorías: ");
        //Como las clases enum funcionan como arreglos, se extraen sus categorias y se "clonan" para poder mostrarlas sin problema
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
        //Acá hace lo mismo, trae el enum, lo "clona" y lo muestra
        TipoEquipo[] tipos = TipoEquipo.values();
        for (int i = 0; i < tipos.length; i++) {
            menuTipoEquipos.append(i+1).append(".").append(tipos[i]).append("\n");
        }
        //La opcion que el usuario ingrese ahora será el tipo de equipo al que se le hará mantenimiento
        //Se le resta uno para que coincida con las enumeraciones del int (0, 1, 2...)
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
        //Revisar la existencia de ordenes y detalles
        OrdenServicio orden = buscarOrdenPorCodigo(codigoOrden)
                .orElseThrow(() -> new OrdenServicio.RepuestoNoEncontradoException("No existe la orden " + codigoOrden));
        //Busca el repuesto en la lista, de no encontrarlo lanzará la excepcion
        DetalleServicio detalle = orden.getListaDetalles().stream()
                .filter(d -> d.getCodigo().equals(codigoDetalle))
                .findFirst()
                .orElseThrow(() -> new OrdenServicio.RepuestoNoEncontradoException("No existe el detalle " + codigoDetalle));
        Repuesto repuesto = buscarRepuestoPorCodigo(codigoOrden, codigoRepuesto)
                .orElseThrow(() -> new OrdenServicio.RepuestoNoEncontradoException("No existe el repuesto " + codigoRepuesto)).getRepuesto();
        if (cantidad <= 0) {
            //En caso de que si se encuentre el repuesto pero su cantidad no sea compatible, lanzará un ilegalArgument
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
        double resultado= detalleServicio.calcularValorDetalle();
        //Se busca el nombre para poder mostrarlo en el return
        RepuestoUtilizado usado = detalleOrden.get().getRepuestosUtilizados().get(codigoDetalle);
        return "Subtotal de " + usado.getRepuesto().getNombre() + " : " + resultado;
    }

    public String calcularValorTotal(String codigoOrden) {
        Optional<OrdenServicio> orden=buscarOrdenPorCodigo(codigoOrden);
        if(orden.isEmpty()){
            return "No existe una orden con este código";
        }
        double total= OrdenServicio.calcularValorTotalOrden(orden.get());
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
    public String consultarEquiposPendientes() {
        String mensaje = "";
        ArrayList<Equipo> listaEquiposPendientesCompleta = new ArrayList<>();
        for (OrdenServicio o : ordenes) {
            for (Equipo e : o.obtenerEquiposPendientes()) {
                listaEquiposPendientesCompleta.add(e);
            }
        }
        if (listaEquiposPendientesCompleta.isEmpty()) {
            return "No hay equipos sin finalizar";
        }
        for (Equipo e : listaEquiposPendientesCompleta) {
            mensaje += e.toString() + "\n";
        }
        return mensaje;
    }

    public String generarReporteServicios() {
        if(ordenes.isEmpty()){
            return "Todavia no hay ordenes de servicio";
        }
        int contadorDiag = 0;
        int contadorMan = 0;
        int contadorRep = 0;
        double totalDiag = 0;
        double totalMan = 0;
        double totalRep = 0;

        for (OrdenServicio o : ordenes) {
            for (DetalleServicio d : o.getListaDetalles()) {
                if (d.getServicio() instanceof Diagnostico) {
                    contadorDiag++;
                    totalDiag += d.calcularValorDetalle();
                } else if (d.getServicio() instanceof MantenimientoPreventivo) {
                    contadorMan++;
                    totalMan += d.calcularValorDetalle();
                } else if (d.getServicio() instanceof ReparacionCorrectiva) {
                    contadorRep++;
                    totalRep += d.calcularValorDetalle();
                }

            }

        }
        return "Servicio por tipo:\n" +
                "Cantidad de servicios de tipo Diagnostico:" + contadorDiag + "\n" + "Total facturado:" + totalDiag + "\n" +
                "Cantidad de servicios de tipo Mantenimiento Preventivo:" + contadorMan + "\n" + "Total facturado:" + totalMan + "\n" +
                "Cantidad de servicios de tipo Reparaciones correctivas:" + contadorRep + "\n" + "Total facturado:" + totalRep + "\n";

    }

    public String generarReporteTecnicos() {
        if (listaTecnicos.isEmpty()) {
            return "No hay tecnicos registrados";
        }
        String mensaje = "";
        for (Tecnico t : listaTecnicos) {
            mensaje += t.getNombre() + ":\n" +
                    "Cantidad de intervenciones finalizadas:" + t.contarDetallesFinalizados() + "\n" +
                    "Valor de los servicios atendidos:" + t.calcularTotalFinalizados() + "\n";

        }
        return mensaje;
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