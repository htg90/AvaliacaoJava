package br.com.htg.backend.controllers;

import br.com.htg.backend.data.Produto;
import br.com.htg.backend.data.ProdutoDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Classe que representa um controlador para o servidor backend do produto. É
 * nela onde serão chamados todos os método referente ao CRUD do banco de dados.
 *
 * @author Helbert Gomes
 */
@Path("produtos")
public class ProdutoController {

    private ProdutoDAO dao;

    public ProdutoController() {
        try {
            this.dao = new ProdutoDAO();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
//            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que retorna uma lista de produtos.
     *
     * @return A lista de produtos.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Produto> listProdutos() {
        try {
            return dao.listar();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que retorna uma lista de produtos com relação a um determinado
     * produto pai.
     *
     * @param parent_product_id ID do produto pai que se deseja procurar.
     * @return A lista de produtos.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("idParent{id}/")
    public List<Produto> listProdutos(@PathParam("id") int parent_product_id) {
        try {
            return dao.listar(parent_product_id);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que retorna uma lista com os produtos em relação ao um espefícico
     * id.
     *
     * @param id ID a ser procurado na tabela.
     * @return A lista de produtos.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("id{id}/")
    public List<Produto> listProdutosImagem(@PathParam("id") int id) {
        try {
            return dao.listarPorImagem(id);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que retorna um determinado produto.
     *
     * @param id Id do produto que se deseja procurar.
     * @return O produto solicitado.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public Produto getProduto(@PathParam("id") int id) {
        try {
            return dao.selecionar(id);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Método que insere um novo produto dentro do banco de dados.
     *
     * @param p Produto a ser inserido.
     * @return código 200 de OK do http, ou código 500 caso contráro.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(Produto p) {
        try {
            dao.inserir(p);
            return Response.status(Response.Status.OK).build();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Atualiza um determinado produto.
     *
     * @param p Produto a ser atualizado.
     * @return código 200 de OK do http, ou código 500 caso contráro.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(Produto p) {
        try {
            dao.alterar(p);
            return Response.status(Response.Status.OK).build();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deleta um determinado produto.
     *
     * @param id Id do produto que se deseja excluir.
     * @return código 200 de OK do http, ou código 500 caso contráro.
     */
    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") int id) {
        try {
            dao.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
