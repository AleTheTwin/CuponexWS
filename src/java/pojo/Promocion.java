/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

import java.util.List;

/**
 *
 * @author alethetwin
 */
public class Promocion {
    private Integer id;
    private Integer categoriaId;
    private Categoria categoria;
    private List<Restriccion> restricciones;
    private String nombre;
    private String descripcion;
    private String fechaDeInicio;
    private String fechaDeTermino;
    private String tipoDePromocion;
    private Double descuento;
    private Double costo;
    private Boolean estatus;

    public Promocion() {
    }

    public Promocion(Integer id, Integer categoriaId, Categoria categoria, List<Restriccion> restricciones, String nombre, String descripcion, String fechaDeInicio, String fechaDeTermino, String tipoDePromocion, Double descuento, Double costo, Boolean estatus) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.categoria = categoria;
        this.restricciones = restricciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeInicio = fechaDeInicio;
        this.fechaDeTermino = fechaDeTermino;
        this.tipoDePromocion = tipoDePromocion;
        this.descuento = descuento;
        this.costo = costo;
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<Restriccion> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<Restriccion> restricciones) {
        this.restricciones = restricciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaDeInicio() {
        return fechaDeInicio;
    }

    public void setFechaDeInicio(String fechaDeInicio) {
        this.fechaDeInicio = fechaDeInicio;
    }

    public String getFechaDeTermino() {
        return fechaDeTermino;
    }

    public void setFechaDeTermino(String fechaDeTermino) {
        this.fechaDeTermino = fechaDeTermino;
    }

    public String getTipoDePromocion() {
        return tipoDePromocion;
    }

    public void setTipoDePromocion(String tipoDePromocion) {
        this.tipoDePromocion = tipoDePromocion;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    @Override
    public String toString() {
        return "Promocion{" + "id=" + id + ", categoriaId=" + categoriaId + ", categoria=" + categoria + ", restricciones=" + restricciones + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fechaDeInicio=" + fechaDeInicio + ", fechaDeTermino=" + fechaDeTermino + ", tipoDePromocion=" + tipoDePromocion + ", descuento=" + descuento + ", costo=" + costo + ", estatus=" + estatus + '}';
    }
    
    
}
