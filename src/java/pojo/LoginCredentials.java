/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pojo;

/**
 *
 * @author alethetwin
 */
public class LoginCredentials {
    private String usuario;
    private String password;

    public LoginCredentials(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public LoginCredentials() {
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginCredentials{" + "usuario=" + usuario + ", password=" + password + '}';
    }
    
    
}