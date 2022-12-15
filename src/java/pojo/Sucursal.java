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
public class Sucursal {
    private Integer id;
    private Integer empresaId;
    private Empresa empresa;
    private String nombre;
    private String direccion;
    private String codigoPostal;
    private String colonia;
    private String ciudad;
    private String telefono;
    private Double latitud;
    private Double longitud;
    private Integer encargadoId;
    private Usuario encargado;
    private List<Promocion> promociones;
    private Boolean estatus;

    public Sucursal(Integer id, Integer empresaId, Empresa empresa, String nombre, String direccion,
            String codigoPostal, String colonia, String ciudad, String telefono, Double latitud, Double longitud,
            Integer encargadoId, Usuario encargado, List<Promocion> promociones, Boolean estatus) {
        this.id = id;
        this.empresaId = empresaId;
        this.empresa = empresa;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.encargadoId = encargadoId;
        this.encargado = encargado;
        this.promociones = promociones;
        this.estatus = estatus;
    }

    public Sucursal() {
    }

    public Boolean getEstatus() {
        return estatus;
    }

    public void setEstatus(Boolean estatus) {
        this.estatus = estatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitd() {
        return longitud;
    }

    public void setLongitd(Double longitud) {
        this.longitud = longitud;
    }

    public Integer getEncargadoId() {
        return encargadoId;
    }

    public void setEncargadoId(Integer encargadoId) {
        this.encargadoId = encargadoId;
    }

    public Usuario getencargado() {
        return encargado;
    }

    public void setencargado(Usuario encargado) {
        this.encargado = encargado;
    }

    public List<Promocion> getPromociones() {
        return promociones;
    }

    public void setPromociones(List<Promocion> promociones) {
        this.promociones = promociones;
    }

    public void addPromocion(Promocion promocion) {
        this.promociones.add(promocion);
    }
    
}
