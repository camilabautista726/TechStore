package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class ReparacionCorrectiva extends Servicio{
    private float horasTrabajo;
    private static final double TARIFA_HORA = 45000f;
    private final ArrayList<Repuesto> repuestosUtilizados = new ArrayList<>();

    public ReparacionCorrectiva(String codigo,String nombre, String descripcion, float horasTrabajo) {
        super(codigo, nombre, descripcion, 0f);
        this.horasTrabajo = horasTrabajo;
    }



    @Override
    public double calcularManoDeObra() {
        return horasTrabajo * TARIFA_HORA;
    }

    public static double calcularValorTotalOrden(OrdenServicio ordenServicio) {
        double valorTotal = 0;
        double valorRepuestos = ordenServicio.getListaDetalles().stream()
                .flatMap(detalle -> detalle.getRepuestosUtilizados().values().stream())
                .mapToDouble(r -> r.getRepuesto().getPrecioUnitario() * r.getCantidad())
                .sum();
        valorTotal +=valorRepuestos;
        valorTotal = valorTotal + TARIFA_HORA;
        return valorTotal;
    }
}
