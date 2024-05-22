package main.java.utils;

public class OfertaJuego{
    //Atributos de la clase
    private String titulo, precio, descuento, url;

    //Constructor con el mismo nombre de la clase
    public OfertaJuego(){}

    public void setTitulo(String title) {
    	this.titulo=title;
    }
    
    public String getTitulo() {
    	return this.titulo;
    }
    
    public void setPrecio(String price) {
    	this.precio=price;
    }
    
    public String getPrecio() {
    	return this.precio;
    }
    
    public void setDescuento(String discount) {
    	this.descuento=discount;
    }
    
    public String getDescuento() {
    	return this.descuento;
    }
    
    public void setUrl(String url) {
    	this.url=url;
    }
    
    public String getUrl() {
    	return this.url;
    }
}