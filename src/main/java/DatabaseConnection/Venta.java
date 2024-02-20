package DatabaseConnection;

import java.sql.Date;

public class Venta {
    private final int id;
    private final int idCliente;
    private final String nombreCliente;
    private final float precio;
    private final String tipoPago;
    private final boolean pagado;
    private final java.sql.Date fecha;

    public Venta(int id, int idCliente, String nombreCliente, float precio, String tipoPago, boolean pagado, Date fecha){
        this.id = id;
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.precio = precio;
        this.tipoPago = tipoPago;
        this.pagado = pagado;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public float getPrecio() {
        return precio;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public boolean isPagado() {
        return pagado;
    }

    public Date getFecha() {
        return fecha;
    }
}
