package DatabaseConnection;

public class Detalle_venta {
    private int idDetalle_venta;
    private int idVenta;
    private int idProducto;
    private int cantidad;

    public void Detalle_venta(int idDetalle_venta, int idVenta, int idProducto, int cantidad){
        this.idDetalle_venta = idDetalle_venta;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public int getIdDetalle_venta() {
        return idDetalle_venta;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }


}
