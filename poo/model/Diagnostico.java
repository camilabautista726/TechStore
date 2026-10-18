package co.edu.uniquindio.poo.model;

public class Diagnostico extends Servicio{
    public Diagnostico(String codigo,String nombre, String descripcion) { //No recibe la tarifa porque ya la tiene fija
        super(codigo, nombre, descripcion, 35000f);// Pero si la mete al constructor
    }

    @Override
    public String toString() {
        return "Diagnostico:" +
                "Codigo:'" + codigo + '\'' +
                "Nombre:" + nombre + '\'' +
                "Descripción:" + descripcion
                ;
    }

    @Override
    public float calcularManoDeObra() {
        return tarifaBase;
    }
}
