package DatabaseConnection;

public class ModalDetalle_venta {
    private final int idProducto;
    private final long codigoProducto;
    private final String nombreProducto;
    private final int cantidadProducto;
    private final float precioProducto;

    public ModalDetalle_venta(int idProducto, long codigoProducto, String nombreProducto, int cantidadProducto, float precioProducto){
        this.idProducto = idProducto;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public long getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public float getPrecioProducto() {
        return precioProducto;
    }

}
