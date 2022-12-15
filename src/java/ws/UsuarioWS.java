/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ws;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

import mybatis.MyBatisUtil;
import pojo.Response;
import pojo.Usuario;
import utils.Constants;

/**
 * REST Web Service
 *
 * @author alethetwin
 */

@Path("usuario")
public class UsuarioWS {

    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    public UsuarioWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getAll() {
        List<Usuario> usuarios = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                usuarios = conn.selectList("usuario.readAllActive");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return usuarios;
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getById(@PathParam("id") Integer id) {
        Usuario usuario = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                usuario = conn.selectOne("usuario.readById", id);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return usuario;
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Usuario> update(@PathParam("id") Integer id, String usuarioJSON) {
        Response<Usuario> response = new Response<>();

        Usuario usuario = gson.fromJson(usuarioJSON, Usuario.class);
        usuario.setId(id);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("usuario.update", usuario);
            conn.commit();
            if (result == 0) {
                response.setError(Boolean.TRUE);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(Boolean.FALSE);
                response.setMessage(Constants.UPDATE_OK);

                response.setContent(usuario);
            }
        } catch (Exception e) {
            response.setError(Boolean.TRUE);
            response.setMessage(e.getCause().getMessage());
            e.printStackTrace();
        } finally {
            conn.close();
        }

        return response;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Usuario> create(String usuarioJSON) {
        Response<Usuario> response = new Response<Usuario>();

        Usuario usuario = gson.fromJson(usuarioJSON, Usuario.class);
        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("usuario.create", usuario);
            conn.commit();
            if (result == 0) {
                response.setError(Boolean.TRUE);
                response.setMessage(Constants.CREATE_FAIL);
            } else {
                response.setError(Boolean.FALSE);
                response.setMessage(Constants.CREATE_OK);

                Usuario agregado = conn.selectOne("usuario.readByCorreo", usuario.getCorreo());

                response.setContent(agregado);
            }
        } catch (Exception e) {
            response.setError(Boolean.TRUE);
            response.setMessage(e.getCause().getMessage());
            e.printStackTrace();
        } finally {
            conn.close();
        }

        return response;
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Usuario> delete(@PathParam("id") Integer id) {
        Response<Usuario> response = new Response<>();

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("usuario.delete", id);
            conn.commit();
            if (result == 0) {
                response.setError(Boolean.TRUE);
                response.setMessage(Constants.DELETE_FAIL);
            } else {
                response.setError(Boolean.FALSE);
                response.setMessage(Constants.DELETE_OK);

                Usuario eliminado = conn.selectOne("usuario.readById", id);

                response.setContent(eliminado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

        return response;
    }

    @Path("/{id}/foto")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Usuario> setFoto(@PathParam("id") Integer id, byte[] foto) {
        Response<Usuario> response = new Response<>();

        SqlSession conn = MyBatisUtil.getSession();
        
        HashMap<String, Object> parametros = new HashMap<>();
        
        parametros.put("id", id);
        parametros.put("foto", foto);

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("usuario.setFoto", parametros);
            conn.commit();
            if (result == 0) {
                response.setError(Boolean.TRUE);
                response.setMessage("No se pudo establecer la foto");
            } else {
                response.setError(Boolean.FALSE);
                response.setMessage("Foto establecida con éxito");

                Usuario usuaroActualizado = conn.selectOne("usuario.readById", id);

                response.setContent(usuaroActualizado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }

        return response;
    }
}
