/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package ws;

import com.google.gson.Gson;

import mybatis.MyBatisUtil;
import pojo.Empresa;
import pojo.Response;
import pojo.Usuario;
import utils.Constants;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;

import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author alethetwin
 */
@Path("empresa")
public class EmpresaWS {

    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    /**
     * Creates a new instance of EmpresaWS
     */
    public EmpresaWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Empresa> getAll(@QueryParam("representanteLegalId") Integer representanteLegalId) {
        List<Empresa> empresas = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                if(representanteLegalId == null) {
                    empresas = conn.selectList("empresa.readAll");
                } else {
                    empresas = conn.selectList("empresa.readAllByEncargadoId", representanteLegalId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }

        // Establecer el representate legal
        for(int i = 0; i<empresas.size(); i++) {
            Empresa empresa = empresas.get(i);
            Usuario usuario = new UsuarioWS().getById(empresa.getRepresentanteLegalId());
            empresa.setRepresentanteLegal(usuario);          
        }
        return empresas;
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Empresa getById(@PathParam("id") Integer id) {
        Empresa empresa = null;
        SqlSession conn = MyBatisUtil.getSession();
        if (conn != null) {
            try {
                empresa = conn.selectOne("empresa.readById", id);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }
        if(empresa != null) {
            Usuario usuario = new UsuarioWS().getById(empresa.getRepresentanteLegalId());
            empresa.setRepresentanteLegal(usuario); 
        }
        return empresa;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Empresa> create(String json) {
        Response<Empresa> response = new Response<>();
        Empresa empresa = gson.fromJson(json, Empresa.class);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("empresa.create", empresa);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.CREATE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.CREATE_OK);
                Empresa agregada = conn.selectOne("empresa.readByCorreo", empresa.getCorreo());
                Usuario usuario = new UsuarioWS().getById(agregada.getRepresentanteLegalId());
                agregada.setRepresentanteLegal(usuario); 
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
    public Response<Empresa> update(@PathParam("id") Integer id, String json) {
        Response<Empresa> response = new Response<>();
        Empresa empresa = gson.fromJson(json, Empresa.class);
        empresa.setId(id);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("empresa.update", empresa);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.UPDATE_OK);
                response.setContent(empresa);
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
    public Response<Empresa> delete(@PathParam("id") Integer id) {
        Response<Empresa> response = new Response<>();

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("empresa.delete", id);
            conn.commit();
            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.DELETE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.DELETE_OK);

                Empresa eliminada = new EmpresaWS().getById(id);
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
