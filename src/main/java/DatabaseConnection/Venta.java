package DatabaseConnection;

import java.util.Date;

public class Venta {
    private int idVenta;
    private int idCliente;
    private float precioTotal;
    private String tipoPago;
    private boolean isPagado;
    private Date fecha;


    public void Venta(int idVenta, int idCliente, float precioTotal, String tipoPago, boolean isPagado, Date fecha){
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.precioTotal = precioTotal;
        this.tipoPago = tipoPago;
        this.isPagado = isPagado;
        this.fecha = fecha;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public boolean isPagado() {
        return isPagado;
    }

    public Date getFecha() {
        return fecha;
    }


}
