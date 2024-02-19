package DatabaseConnection;

import java.sql.Date;

public class Venta {
    private int idVenta;
    private int idCliente;
    private float precioTotal;
    private String tipoPago;
    private boolean isPagado;
    private java.sql.Date fecha;


    public Venta(int idVenta, int idCliente, float precioTotal, String tipoPago, boolean isPagado){
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.precioTotal = precioTotal;
        this.tipoPago = tipoPago;
        this.isPagado = isPagado;
        this.fecha = new java.sql.Date(System.currentTimeMillis());
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
