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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

import mybatis.MyBatisUtil;
import pojo.Response;
import pojo.Sucursal;
import utils.Constants;

/**
 * REST Web Service
 *
 * @author alethetwin
 */
@Path("sucursal")
public class SucursalWS {

    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    /**
     * Creates a new instance of AccessWS
     */

    public SucursalWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Sucursal> getAll(@QueryParam("encargadoId") Integer encargadoId) {
        List<Sucursal> sucursales = null;

        SqlSession conn = MyBatisUtil.getSession();
        
        if(conn != null) {
            try {
                if(encargadoId == null) {
                    sucursales = conn.selectList("sucursal.readAll");
                } else {
                    sucursales = conn.selectList("sucursal.readByEncargadoId", encargadoId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        
        return sucursales;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Sucursal> create(String json) {
        Response<Sucursal> response = new Response<>();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("sucursal.create", sucursal);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.CREATE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.CREATE_OK);
                
                Sucursal agregada = conn.selectOne("sucursal.readByNombre", sucursal.getNombre());
                response.setContent(agregada);
            }
        } catch (Exception e) {
            response.setError(Boolean.TRUE);
            if(e.getCause() != null) response.setMessage(e.getCause().getMessage()); else response.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            conn.close();
        }

        return response;
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Sucursal> update(@PathParam("id") Integer id, String json) {
        Response<Sucursal> response = new Response<>();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);
        sucursal.setId(id);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("sucursal.update", sucursal);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.UPDATE_OK);
                
                Sucursal actualizada = conn.selectOne("sucursal.readById", sucursal.getId());
                response.setContent(actualizada);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Sucursal> delete(@PathParam("id") Integer id) {
        Response<Sucursal> response = new Response<>();

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("sucursal.delete", id);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.DELETE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.DELETE_OK);
                
                Sucursal eliminada = conn.selectOne("sucursal.readById", id);
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