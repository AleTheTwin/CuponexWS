/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ws;

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
import pojo.Restriccion;
import utils.Constants;

/**
 * REST Web Service
 *
 * @author alethetwin
 */
@Path("restriccion")
public class RestriccionWS {

    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    /**
     * Creates a new instance of EmpresaWS
     */
    public RestriccionWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Restriccion> getAll() {
        List<Restriccion> restricciones = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                restricciones = conn.selectList("restriccion.readAll");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return restricciones;
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Restriccion> getById(@PathParam("id") Integer id) {
        Response<Restriccion> response = new Response<>();
        Restriccion restriccion = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                restriccion = conn.selectOne("restriccion.readById", id);
                if(restriccion == null) {
                    response.setError(Boolean.TRUE);
                    response.setMessage(Constants.SELECT_FAIL);
                } else {
                    response.setError(Boolean.FALSE);
                    response.setMessage(Constants.SELECT_OK);
                    response.setContent(restriccion);
                }
            } catch (Exception e) {
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Restriccion> create(String json) {
        Response<Restriccion> response = new Response<>();
        Restriccion restriccion = gson.fromJson(json, Restriccion.class);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("restriccion.create", restriccion);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.CREATE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.CREATE_OK);
                
                Restriccion agregada = conn.selectOne("restriccion.readByNombre", restriccion.getNombre());
                response.setContent(agregada);
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

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Restriccion> update(@PathParam("id") Integer id, String json) {
        Response<Restriccion> response = new Response<>();
        Restriccion restriccion = gson.fromJson(json, Restriccion.class);
        restriccion.setId(id);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("restriccion.update", restriccion);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.UPDATE_OK);
                response.setContent(restriccion);
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
    public Response<Restriccion> delete(@PathParam("id") Integer id) {
        Response<Restriccion> response = new Response<>();

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("restriccion.delete", id);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.DELETE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.DELETE_OK);

                Restriccion eliminada = conn.selectOne("restriccion.readById", id);
                response.setContent(eliminada);
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
}
