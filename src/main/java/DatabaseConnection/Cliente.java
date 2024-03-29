package DatabaseConnection;

public class Cliente{
    private int idCliente;
    private String nombre;
    private String direccion;
    private long telefono;

    public Cliente(int idCliente, String nombre, String direccion){
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public long getTelefono(){
        return telefono;
    }

    public void setTelefono(long telefono){
        this.telefono = telefono;
    }

}
