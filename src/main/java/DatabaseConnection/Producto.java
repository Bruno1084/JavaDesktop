package DatabaseConnection;

import java.util.Date;

public class Producto {
    private final int id;
    private final String nombre;
    private final float precio;
    private final int stock;
    private final String marca;
    private final String categoria;
    private final Date vencimiento;
    private final String ctoNeto;

    public Producto(int id, String nombre, float precio, int stock, String marca, String categoria, Date vencimiento, String ctoNeto){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.marca = marca;
        this.categoria = categoria;
        this.vencimiento = vencimiento;
        this.ctoNeto = ctoNeto;
    }


    public int getID(){
        return id;
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

    public Date getVencimiento() {
        return vencimiento;
    }

    public String getCtoNeto() {
        return ctoNeto;
    }
}
