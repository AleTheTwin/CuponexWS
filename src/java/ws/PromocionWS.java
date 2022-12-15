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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

import mybatis.MyBatisUtil;
import pojo.Categoria;
import pojo.Promocion;
import pojo.Response;
import pojo.Sucursal;
import utils.Constants;

/**
 * REST Web Service
 *
 * @author alethetwin
 */
@Path("promocion")
public class PromocionWS {

    @Context
    private UriInfo context;
    private Gson gson = new Gson();

    /**
     * Creates a new instance of PromocionWS
     */

    public PromocionWS() {
    }

    /**
     * @param sucursalId parámetro de query, si no es nulo, solo se regresaran las promociones de la sucursal con el id enviado. De ser nulo se regresan todas las promociones.
     * @return List<Promocion> lista de las promociones recuperadas
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Promocion> getAll(@QueryParam("sucursalId") Integer sucursalId) {
        List<Promocion> promociones = null;

        SqlSession conn = MyBatisUtil.getSession();

        if (conn != null) {
            try {
                if (sucursalId == null) {
                    promociones = conn.selectList("promocion.readAllActive");
                } else {
                    promociones = conn.selectList("promocion.readBySucursalId", sucursalId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }

        for (int i = 0; i < promociones.size(); i++) {
            Promocion p = promociones.get(i);
            Categoria categoria = new CategoriaWS().getById(p.getCategoriaId());

            p.setCategoria(categoria);
        }

        return promociones;
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Promocion getById(@PathParam("id") Integer id) {
        Promocion promocion = null;

        SqlSession conn = MyBatisUtil.getSession();

        if (conn != null) {
            try {
                promocion = conn.selectOne("promocion.readById", id);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn.close();
            }
        }

        Categoria categoria = new CategoriaWS().getById(promocion.getCategoriaId());
        promocion.setCategoria(categoria);

        return promocion;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Promocion> create(String json) {
        Response<Promocion> response = new Response<>();
        Promocion promocion = gson.fromJson(json, Promocion.class);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("promocion.create", promocion);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.CREATE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.CREATE_OK);

                Promocion agregada = conn.selectOne("promocion.readByNombre", promocion.getNombre());
                response.setContent(agregada);
            }
        } catch (Exception e) {
            response.setError(Boolean.TRUE);
            if (e.getCause() != null)
                response.setMessage(e.getCause().getMessage());
            else
                response.setMessage(e.getMessage());
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
    public Response<Promocion> update(@PathParam("id") Integer id, String json) {
        Response<Promocion> response = new Response<>();
        Promocion promocion = gson.fromJson(json, Promocion.class);
        promocion.setId(id);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("promocion.update", promocion);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.UPDATE_OK);

                Promocion actualizada = conn.selectOne("promocion.readById", promocion.getId());
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
    public Response<Promocion> delete(@PathParam("id") Integer id) {
        Response<Promocion> response = new Response<>();

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.update("promocion.delete", id);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.DELETE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.DELETE_OK);

                Promocion eliminada = conn.selectOne("promocion.readById", id);
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

    @Path("/{id}/sucursal")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Promocion> addToSucursal(@PathParam("id") Integer id, String json) {
        Response<Promocion> response = new Response<>();
        Sucursal sucursal = gson.fromJson(json, Sucursal.class);

        HashMap<String, Integer> params = new HashMap<>();

        params.put("promocionId", id);
        params.put("sucursalId", sucursal.getId());

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.insert("promocion.addToSucursal", params);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.UPDATE_OK);

                Promocion actualizada = conn.selectOne("promocion.readById", id);
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

    @Path("/{id}/sucursal/{sucursalId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Promocion> removeFromSucursal(@PathParam("id") Integer id,@PathParam("sucursalId") Integer sucursalId) {
        Response<Promocion> response = new Response<>();

        HashMap<String, Integer> params = new HashMap<>();

        params.put("promocionId", id);
        params.put("sucursalId", sucursalId);

        SqlSession conn = MyBatisUtil.getSession();

        if (conn == null) {
            response.setError(true);
            response.setMessage(Constants.ERROR_DE_CONEXION_DB);
            return response;
        }

        try {
            int result = conn.delete("promocion.removeFromSucursal", params);
            conn.commit();

            if (result == 0) {
                response.setError(true);
                response.setMessage(Constants.UPDATE_FAIL);
            } else {
                response.setError(false);
                response.setMessage(Constants.UPDATE_OK);

                Promocion actualizada = conn.selectOne("promocion.readById", id);
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



    @Path("/{id}/foto")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response<Promocion> setFoto(@PathParam("id") Integer id, byte[] foto) {
        Response<Promocion> response = new Response<>();

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
            int result = conn.update("promocion.setFoto", parametros);
            conn.commit();
            if (result == 0) {
                response.setError(Boolean.TRUE);
                response.setMessage("No se pudo establecer la foto");
            } else {
                response.setError(Boolean.FALSE);
                response.setMessage("Foto establecida con éxito");

                Promocion usuaroActualizado = conn.selectOne("promocion.readById", id);

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
