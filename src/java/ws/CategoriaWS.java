/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ws;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

import mybatis.MyBatisUtil;
import pojo.Categoria;
import pojo.Response;
import utils.Constants;

import javax.ws.rs.Produces;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author alethetwin
 */
@Path("categoria")
public class CategoriaWS {

    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    /**
     * Creates a new instance of EmpresaWS
     */
    public CategoriaWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Categoria> getAll() {
        List<Categoria> categorias = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                categorias = conn.selectList("categoria.readAll");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return categorias;
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Categoria getById(@PathParam("id") Integer id) {
        Categoria categoria = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                categoria = conn.selectOne("categoria.readById", id);
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        return categoria;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Categoria> create(String json) {
        Response<Categoria> response = new Response<>();
        Categoria categoria = gson.fromJson(json, Categoria.class);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("categoria.create", categoria);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.CREATE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.CREATE_OK);
                
                Categoria agregada = conn.selectOne("categoria.readByNombre", categoria.getNombre());
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
    public Response<Categoria> update(@PathParam("id") Integer id, String json) {
        Response<Categoria> response = new Response<>();
        Categoria categoria = gson.fromJson(json, Categoria.class);
        categoria.setId(id);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("categoria.update", categoria);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.UPDATE_OK);
                response.setContent(categoria);
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
    public Response<Categoria> delete(@PathParam("id") Integer id) {
        Response<Categoria> response = new Response<>();

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("categoria.delete", id);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.DELETE_FAIL);
            } else {
                response.setError(true);
                response.setMessage(Constants.DELETE_OK);

                Categoria eliminada = conn.selectOne("categoria.readById", id);
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
