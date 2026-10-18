package co.edu.uniquindio.poo.app;

import co.edu.uniquindio.poo.model.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Optional;

public class Main {
    static void main() {
        EmpresaTechRepair empresaTechRepair = new EmpresaTechRepair("TechRepair", "901.234.567-8", "389 843 8738", "Calle 15 N° 23 - 45, Armenia, Quindío");
        // crear repuestos
        int opcion;
        do {
            JOptionPane.showMessageDialog(null, "===MENÚ===\n");
            opcion = Integer.valueOf(JOptionPane.showInputDialog(null,
                    "Por favor seleccione una opcion :\n" +
                            "1. Registrar cliente\n" +
                            "2. Registrar equipo\n" +
                            "3. Registrar técnico\n" +
                            "4. Registrar repuesto\n" +
                            "5. Crear orden de servicio\n" +
                            "6. Agregar detalle a una orden\n" +
                            "7. Asignar técnico\n" +
                            "8. Registrar repuestos utilizados\n" +
                            "9. Calcular valor de un detalle\n" +
                            "10. Calcular valor total de la orden\n" +
                            "11. Actualizar estado de un detalle\n" +
                            "12. Finalizar una orden\n" +
                            "13. Consultar órdenes de un cliente\n" +
                            "14. Consultar trabajos de un técnico\n" +
                            "15. Consultar equipos pendientes\n" +
                            "16. Generar reporte de servicios\n" +
                            "17. Generar reporte de técnicos\n" +
                            "18. Consultar inventario crítico\n" +
                            "0. Salir del sistema"));
            switch (opcion) {
                case 1:
                    registrarCliente(empresaTechRepair);
                    break;
                case 2:
                    registrarEquipo(empresaTechRepair);
                    break;
                case 3:
                    registrarTecnico(empresaTechRepair);
                    break;
                case 4:
                    registrarRepuesto(empresaTechRepair);
                    break;
                case 5:
                   crearOrdenServicio(empresaTechRepair);
                    break;
                case 6:
                  agregarDetalleOrden(empresaTechRepair);
                    break;
                case 7:

                    break;
                case 8:
                    registrarRepuestosUtilizados(empresaTechRepair);
                    break;
                case 9:
                    calcularValorDetalle(empresaTechRepair);
                    break;
                case 10:
                    calcularValorTotal(empresaTechRepair);
                    break;
                case 11:
                    actualizarEstadoDetalle(empresaTechRepair);
                    break;
                case 12:
                    finalizarOrden(empresaTechRepair);
                    break;
                case 13:

                    break;
                case 14:

                    break;
                case 15:

                    break;
                case 16:
                    break;
                case 17:

                    break;
                case 18:
                    consultarInventarioCritico(empresaTechRepair);
                    break;
                case 0:
                    break;
            }

        } while (opcion != 0);
    }

    public static void registrarCliente(EmpresaTechRepair empresaTechRepair) {
        String documento = JOptionPane.showInputDialog("Ingrese el documento de identidad del cliente:");
        String nombreCompleto = JOptionPane.showInputDialog("Ingrese el nombre completo del cliente:");
        String telefono = JOptionPane.showInputDialog("Ingrese el telefono  del cliente:");
        String correo = JOptionPane.showInputDialog("Ingrese el correo  del cliente:");
        String direccion = JOptionPane.showInputDialog("Ingrese la direccion del cliente:");
        Cliente clienteNuevo = new Cliente(documento, nombreCompleto, telefono, correo, direccion, empresaTechRepair);
        String resultado = empresaTechRepair.registrarCliente(clienteNuevo);
        JOptionPane.showMessageDialog(null, resultado);
    }

    public static void registrarEquipo(EmpresaTechRepair empresaTechRepair) {
        String documento = JOptionPane.showInputDialog("Ingrese el documento de identidad del cliente al cual pertene el equipo que desa registrar:");
        Optional<Cliente> clienteEncontrado = empresaTechRepair.buscarClientePorDocumento(documento);
        if (clienteEncontrado.isPresent()) {
            Cliente cliente = clienteEncontrado.get();
            String codigo = JOptionPane.showInputDialog("Ingrese el código del equipo:");
            String numeroSerie = JOptionPane.showInputDialog("Ingrese el número de serie:");
            String marca = JOptionPane.showInputDialog("Ingrese la marca:");
            String modelo = JOptionPane.showInputDialog("Ingrese el modelo:");
            String fechaFabricacion = JOptionPane.showInputDialog("Ingrese el año de fabricación:");
            String descripcionEstadoFisico = JOptionPane.showInputDialog("Describa el estado físico del equipo:");
            String ingresoTipoEquipo = JOptionPane.showInputDialog("Ingrese el tipo de equipo que desea registrar," +
                    " si el equipo esta compuesto de dos o mas palabras utilize un gion bajo _ por cada separacion :");
            TipoEquipo tipoEquipo = TipoEquipo.valueOf(ingresoTipoEquipo.toUpperCase());
            if (tipoEquipo == TipoEquipo.COMPUTADOR_PORTATIL) {
                float pantallaPulgadas = Float.parseFloat(JOptionPane.showInputDialog("Ingrese el tamaño de pantalla en pulgadas:"));
                int ramGB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de RAM en GB:"));
                int almacenamientoGB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de almacenamiento en GB:"));
                int ingresoBateriaExtraible = JOptionPane.showConfirmDialog(null, "¿El equipo tiene batería extraíble?"); //Muestra opciones si/no y recibe numeros, donde si=0 y  no=1
                boolean bateriaExtraible = (ingresoBateriaExtraible == JOptionPane.YES_OPTION); //determina el bboleano comparando los numeros
                Equipo nuevoEquipo = new ComputadorPortatil(codigo, numeroSerie, marca, modelo, fechaFabricacion, descripcionEstadoFisico, pantallaPulgadas, ramGB, almacenamientoGB, bateriaExtraible);
                String resultado = empresaTechRepair.registrarEquipo(cliente, nuevoEquipo);
                JOptionPane.showMessageDialog(null, resultado);
            }
            if (tipoEquipo == TipoEquipo.COMPUTADOR_ESCRITORIO) {
                String procesador = JOptionPane.showInputDialog("Ingrese el tipo de procesador:");
                int ramGB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de RAM en GB:"));
                int almacenamientoGB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de almacenamiento en GB:"));
                int ingresoGraficaDedicada = JOptionPane.showConfirmDialog(null, "¿El equipo posee tarjeta gráfica dedicada?");
                boolean graficaDedicada = (ingresoGraficaDedicada == JOptionPane.YES_OPTION);
                Equipo nuevoEquipo = new ComputadorEscritorio(codigo, numeroSerie, marca, modelo, fechaFabricacion, descripcionEstadoFisico, procesador, ramGB, almacenamientoGB, graficaDedicada);
                String resultado = empresaTechRepair.registrarEquipo(cliente, nuevoEquipo);
                JOptionPane.showMessageDialog(null, resultado);
            }
            if (tipoEquipo == TipoEquipo.TELEFONO_INTELIGENTE) {
                String sistemaOperativo = JOptionPane.showInputDialog("Ingrese el sistema operativo:");
                int almacenamientoGB = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de almacenamiento en GB:"));
                int bateriaMah = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la capacidad de la batería en mAh:"));
                int ingresoCompatible5G = JOptionPane.showConfirmDialog(null, "¿El teléfono es compatible con tecnología 5G?");
                boolean compatible5G = (ingresoCompatible5G == JOptionPane.YES_OPTION);
                Equipo nuevoEquipo = new TelefonoInteligente(codigo, numeroSerie, marca, modelo, fechaFabricacion, descripcionEstadoFisico, sistemaOperativo,  almacenamientoGB, bateriaMah, compatible5G );
                String resultado = empresaTechRepair.registrarEquipo(cliente, nuevoEquipo);
                JOptionPane.showMessageDialog(null, resultado);

            }
        }else{
            JOptionPane.showMessageDialog(null, "No hay ningun cliente registrado con ese documento");
        }
    }
    public static void registrarTecnico (EmpresaTechRepair empresaTechRepair) {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del tecnico:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del tecnico:");
        String documento = JOptionPane.showInputDialog("Ingrese el documento del tecnico:");
        Especialidad[] especialidades = Especialidad.values();
        byte opcionEspecialidad = Byte.parseByte(JOptionPane.showInputDialog(EmpresaTechRepair.seleccionarEspecialidad()));
        Especialidad especialidad = especialidades[opcionEspecialidad - 1];
        int opcionEstado= Integer.parseInt(JOptionPane.showInputDialog("Ingrese el estado laboral del técnico \n(0. Activo  1. Inactivo)"));
        EstadoLaboral estadoLaboral = EstadoLaboral.values()[opcionEstado];
        Tecnico tecnico= new Tecnico(codigo, nombre, documento, especialidad, estadoLaboral);
        String resultado = empresaTechRepair.registrarTecnico(tecnico);
    }

    public static void registrarRepuesto (EmpresaTechRepair empresaTechRepair) {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo del repuesto:");
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del repuesto:");
        String descripcion = JOptionPane.showInputDialog("Ingrese el descripcion del repuesto:");
        double precioUnitario= Double.parseDouble(JOptionPane.showInputDialog("Ingrese el precio unitario del producto:"));
        int cantidadDisponible= Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad disponible del producto:"));
        CategoriaCompatibilidad[] categorias = CategoriaCompatibilidad.values();
        byte opcionCategoria = Byte.parseByte(JOptionPane.showInputDialog(EmpresaTechRepair.seleccionarCategoria()));
        CategoriaCompatibilidad categoriaCompatibilidad = categorias[opcionCategoria - 1];
        Repuesto repuesto= new Repuesto(codigo, nombre, descripcion, precioUnitario, cantidadDisponible, categoriaCompatibilidad);
    }

    public static void crearOrdenServicio (EmpresaTechRepair empresaTechRepair) {
        String codigo = JOptionPane.showInputDialog("Ingrese el codigo de la orden:");
        Optional<OrdenServicio> ordenServicio= empresaTechRepair.buscarOrdenPorCodigo(codigo);
        if (ordenServicio.isPresent()) {
            JOptionPane.showMessageDialog(null,"Esta orden ya está en el sistema");
            return;
        }
        LocalDate textoFecha = LocalDate.parse(JOptionPane.showInputDialog("Ingrese la fecha (AAAA-MM-DD):"));
        String documentoCliente = JOptionPane.showInputDialog("Ingrese el documento del cliente:");
        Optional<Cliente> cliente = empresaTechRepair.buscarClientePorDocumento(documentoCliente);
        Cliente clienteBuscado= cliente.get();
        OrdenServicio orden = new OrdenServicio(codigo, textoFecha, clienteBuscado);
        // guardar la orden en la empresa
        String resultado = empresaTechRepair.registrarOrden(codigo, textoFecha, documentoCliente, clienteBuscado);
        JOptionPane.showMessageDialog(null, resultado);
    }
    //Metodo para ingresar los detalles
    public static DetalleServicio agregarDetalleOrden(EmpresaTechRepair empresaTechRepair) {
        String codigoOrden = JOptionPane.showInputDialog("Ingrese el codigo de la orden:");
        Optional<OrdenServicio> ordenServicio= empresaTechRepair.buscarOrdenPorCodigo(codigoOrden);
        if (ordenServicio.isPresent()) {
            JOptionPane.showMessageDialog(null,"Esta orden está disponible para agregar detalles");
            int repuesta= JOptionPane.showConfirmDialog(null, "¿Desea agregar un detalle?",
                    "Detalles", JOptionPane.YES_NO_OPTION);
            if (repuesta == JOptionPane.YES_OPTION) {
                String codigo = JOptionPane.showInputDialog("Ingrese el codigo del detalle:");
                String codigoEquipo = JOptionPane.showInputDialog("Ingrese el codigo del equipo:");
                Optional <Equipo> equipo = empresaTechRepair.buscarEquipoPorCodigo(codigoEquipo);
                String documentoTecnico = JOptionPane.showInputDialog("Ingrese el documento del tecnico:");
                Optional <Tecnico> tecnico= empresaTechRepair.buscarTecnicoPorDocumento(documentoTecnico);
                String observaciones = JOptionPane.showInputDialog("Ingrese las observaciones:");
                int codigoServicio;
                Servicio servicioEquipo= null;
                do {
                    codigoServicio = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el tipo de servicio:" +
                            "\n 1.Diagnóstico" +
                            "\n 2.Mantenimiento Preventivo" +
                            "\n 3.Reparación Correctiva."));
                    switch(codigoServicio) {
                        case 1: servicioEquipo= empresaTechRepair.crearDiagnostico(codigoEquipo);
                            break;
                        case 2: servicioEquipo= empresaTechRepair.crearMantenimiento(codigoEquipo);
                            break;
                        case 3: servicioEquipo= empresaTechRepair.crearReparacion(codigoEquipo);
                            break;
                    }
                }while (codigoServicio!=0);
                int opcionEstadoIntervencion= Integer.parseInt(JOptionPane.showInputDialog("Ingrese el estado de la intervención del equipo \n(0. Pendiente  1. En proceso  2. Finalizada)"));
                EstadoIntervencion estadoIntervencion = EstadoIntervencion.values()[opcionEstadoIntervencion];
                //Ingreso de los detalles de la orden
                if (equipo.isEmpty() || tecnico.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Equipo, servicio o técnico no encontrado.");
                    return null;
                }
                String resultado= empresaTechRepair.agregarDetalleAOrden(codigo, equipo.get(), servicioEquipo, tecnico.get(), observaciones, estadoIntervencion);
                JOptionPane.showMessageDialog(null, resultado);
            }
        }
       JOptionPane.showMessageDialog(null,"La orden no esta registrada");
        return null;
    }

    public static void registrarRepuestosUtilizados(EmpresaTechRepair empresaTechRepair){
        String codigoOrden = JOptionPane.showInputDialog("Ingrese el codigo de la orden:");
        String codigoDetalle = JOptionPane.showInputDialog("Ingrese el codigo del detalle:");
        String codigoRepuesto = JOptionPane.showInputDialog("Ingrese el código del repuesto:");
        try {
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad a utilizar:"));
            empresaTechRepair.usarRepuesto(codigoOrden, codigoDetalle, codigoRepuesto, cantidad);
            JOptionPane.showMessageDialog(null, "Repuesto registrado correctamente.");
        } catch (OrdenServicio.RepuestoNoEncontradoException | OrdenServicio.StockInsuficienteException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static void calcularValorDetalle(EmpresaTechRepair empresaTechRepair) {
        String codigoOrden = JOptionPane.showInputDialog("Ingrese el codigo de la orden:");
        String codigoDetalle = JOptionPane.showInputDialog("Ingrese el codigo del detalle:");
        String resultado= empresaTechRepair.calcularValorDetalle(codigoOrden, codigoDetalle);
        JOptionPane.showMessageDialog(null, resultado);
    }

    public static void calcularValorTotal(EmpresaTechRepair empresaTechRepair) {
        String codigoOrden = JOptionPane.showInputDialog("Ingrese el codigo de la orden:");
        String resultado= empresaTechRepair.calcularValorTotal(codigoOrden);
        JOptionPane.showMessageDialog(null, resultado);
    }

    public static void actualizarEstadoDetalle(EmpresaTechRepair empresaTechRepair) {
        String codigoOrden = JOptionPane.showInputDialog("Ingrese el codigo del orden:");
        String codigoDetalle = JOptionPane.showInputDialog("Ingrese el codigo del detalle:");
        String resultado=empresaTechRepair.actualizarEstadoDetalle(codigoOrden, codigoDetalle);
    }

    public static void finalizarOrden(EmpresaTechRepair empresaTechRepair) {
        String codigoOrden = JOptionPane.showInputDialog("Ingrese el codigo del orden:");
        String resultado=empresaTechRepair.finalizarOrden(codigoOrden);
    }

    //Opción 18:
    public static void consultarInventarioCritico(EmpresaTechRepair empresaTechRepair) {
        String resultadoInventario= empresaTechRepair.consultarInventarioCritico();
        JOptionPane.showMessageDialog(null, resultadoInventario);
    }
}
