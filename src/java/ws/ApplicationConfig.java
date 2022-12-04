/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author alethetwin
 */
@javax.ws.rs.ApplicationPath("ws")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(ws.AccessWS.class);
        resources.add(ws.CategoriaWS.class);
        resources.add(ws.EmpresaWS.class);
        resources.add(ws.PromocionWS.class);
        resources.add(ws.RestriccionWS.class);
        resources.add(ws.SucursalWS.class);
        resources.add(ws.UsuarioWS.class);
    }

}
