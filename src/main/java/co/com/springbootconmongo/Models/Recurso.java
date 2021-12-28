package co.com.springbootconmongo.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Recurso")
public class Recurso {

    @Id
    private String id;
    private String nombre;
    private String tipo;
    private String tematica;
    private boolean prestado;
    private Date fechaPrestado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }

    public boolean isPrestado() {
        return prestado;
    }

    public void setPrestado(boolean prestado) {
        this.prestado = prestado;
    }

    public Date getFechaPrestado() {
        return fechaPrestado;
    }

    public void setFechaPrestado(Date fechaPrestado) {
        this.fechaPrestado = fechaPrestado;
    }
}
