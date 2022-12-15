/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author alethetwin
 */
public class Promocion {
    private Integer id;
    private Integer categoriaId;
    private Categoria categoria;
    private String restriccionesString;
    private String nombre;
    private String descripcion;
    private String fechaDeInicio;
    private String fechaDeTermino;
    private String tipoDePromocion;
    private Double descuento;
    private Boolean estatus;
    private String foto;

    public Promocion(Integer id, Integer categoriaId, Categoria categoria,
            String nombre, String descripcion, String fechaDeInicio, String fechaDeTermino, String tipoDePromocion,
            Double descuento, Boolean estatus, String foto, String restriccioneString) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.categoria = categoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaDeInicio = fechaDeInicio;
        this.fechaDeTermino = fechaDeTermino;
        this.tipoDePromocion = tipoDePromocion;
        this.descuento = descuento;
        this.estatus = estatus;
        this.foto = foto;
        this.restriccionesString = restriccioneString;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Promocion() {
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

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }
    
    public String getRestriccionesString() {
        return restriccionesString;
    }

    public void setRestriccionesString(String restriccionesString) {
        this.restriccionesString = restriccionesString;
    }

}
