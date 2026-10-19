package co.edu.uniquindio.poo.model;

public class MantenimientoPreventivo extends Servicio{
    private TipoEquipo tipoDispositivoMantenimiento;
    public MantenimientoPreventivo(String codigo,String nombre, String descripcion, TipoEquipo tipoDispositivoMantenimiento) {
        super(codigo, nombre, descripcion, 50000f);
        this.tipoDispositivoMantenimiento = tipoDispositivoMantenimiento;
    }




    public TipoEquipo getTipoDispositivoMantenimiento() {
        return tipoDispositivoMantenimiento;
    }

    public void setTipoDispositivoMantenimiento(TipoEquipo tipoDispositivoMantenimiento) {
        this.tipoDispositivoMantenimiento = tipoDispositivoMantenimiento;
    }

    @Override
    public double calcularManoDeObra() {
        return tarifaBase;
    }

    public static double calcularValorTotalOrden(OrdenServicio ordenServicio) {
        double total = 0;
        for(DetalleServicio detalleServicio: ordenServicio.getListaDetalles()){
            if (detalleServicio.getEquipo().getTipoEquipo()==TipoEquipo.TELEFONO_INTELIGENTE){
                total= 40000;
            } else if (detalleServicio.getEquipo().getTipoEquipo()==TipoEquipo.COMPUTADOR_ESCRITORIO) {
                total= 70000;
            } else if (detalleServicio.getEquipo().getTipoEquipo()==TipoEquipo.COMPUTADOR_PORTATIL) {
                total= 60000;
            }
            return  total;
        }
        return total;
    }
}


