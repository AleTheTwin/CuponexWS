/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ws;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

import mybatis.MyBatisUtil;
import pojo.LoginCredentials;
import pojo.Response;
import pojo.Usuario;

/**
 * REST Web Service
 *
 * @author alethetwin
 */
@Path("acceso")
public class AccessWS {

    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    /**
     * Creates a new instance of AccessWS
     */
    public AccessWS() {
    }
    
    @Path("admin")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Usuario> loginAdmin(String credencialesJSON) {
        LoginCredentials credenciales = gson.fromJson(credencialesJSON, LoginCredentials.class);
        
        Response<Usuario> response = new Response<>();
        Usuario usuario = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null) {
            try {
                usuario = conn.selectOne("usuario.loginAdmin", credenciales.getUsuario());
                if(usuario != null && usuario.getPassword().equals(credenciales.getPassword())) {
                    response.setError(Boolean.FALSE);
                    response.setMessage("Credenciales correctas");
                    response.setContent(usuario);
                } else {
                    response.setError(Boolean.TRUE);
                    response.setMessage("Credenciales incorrectas");
                }
            } catch(Exception e) {
                response.setError(Boolean.TRUE);
                response.setMessage(e.getCause().getMessage());
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return response;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Usuario> login(String credencialesJSON) {
        LoginCredentials credenciales = gson.fromJson(credencialesJSON, LoginCredentials.class);
        
        Response<Usuario> response = new Response<>();
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null) {
            try {
                Usuario usuario = conn.selectOne("usuario.login", credenciales.getUsuario());
                if(usuario != null && usuario.getPassword().equals(credenciales.getPassword())) {
                    response.setError(Boolean.FALSE);
                    response.setMessage("Credenciales correctas");
                    response.setContent(usuario);
                } else {
                    response.setError(Boolean.TRUE);
                    response.setMessage("Credenciales incorrectas");
                }
            } catch(Exception e) {
                response.setError(Boolean.TRUE);
                response.setMessage(e.getCause().getMessage());
            } finally {
                conn.close();
            }
        }
        return response;
    }
}
