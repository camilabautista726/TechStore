package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class ReparacionCorrectiva extends Servicio{
    private float horasTrabajo;
    private static final float TARIFA_HORA = 45000f;
    private final ArrayList<Repuesto> repuestosUtilizados = new ArrayList<>();

    public ReparacionCorrectiva(String codigo,String nombre, String descripcion, float horasTrabajo) {
        super(codigo, nombre, descripcion, 0f);
        this.horasTrabajo = horasTrabajo;
    }


    @Override
    public float calcularManoDeObra() {
        return horasTrabajo * TARIFA_HORA;
    }
}
