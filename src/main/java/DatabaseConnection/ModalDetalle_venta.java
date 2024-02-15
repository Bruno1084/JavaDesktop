package DatabaseConnection;

public class ModalDetalle_venta {
    private final long codigoProducto;
    private final String nombreProducto;
    private final int cantidadProducto;
    private final float precioProducto;
    private final String marcaProducto;

    public ModalDetalle_venta(long codigoProducto, String nombreProducto, int cantidadProducto, float precioProducto, String marcaProducto){
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioProducto = precioProducto;
        this.marcaProducto = marcaProducto;
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

    public String getMarcaProducto() {
        return marcaProducto;
    }
}
