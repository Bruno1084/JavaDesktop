package DatabaseConnection;

import java.util.Date;

public class Producto {
    private final int id;
    private final long barCodigo;
    private final String nombre;
    private final float precio;
    private final int stock;
    private final String marca;
    private final String categoria;
    private final String ctoNeto;

    public Producto(int id, long barCodigo, String nombre, float precio, int stock, String marca, String categoria, String ctoNeto){
        this.id = id;
        this.barCodigo = barCodigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.marca = marca;
        this.categoria = categoria;
        this.ctoNeto = ctoNeto;
    }


    public int getID(){
        return id;
    }

    public long getBarCodigo(){
        return barCodigo;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getMarca() {
        return marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCtoNeto() {
        return ctoNeto;
    }
}
