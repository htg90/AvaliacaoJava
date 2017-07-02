package br.com.htg.backend.data;

import br.com.htg.backend.infra.ConexaoJDBC;
import br.com.htg.backend.infra.ConexaoMysqlJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um objeto que fará o acesso ao banco de dados para
 * manipulação das informações.
 *
 * @author Helbert Gomes
 */
public class ProdutoDAO {

    /**
     * Constante que irá represetar um conexão via JDBC.
     */
    private final ConexaoJDBC CONN;

    /**
     * Consturor. É aqui onde se escolhe o banco de dados a ser usado.
     *
     * @throws ClassNotFoundException Lançada caso a classe do banco de dados
     * não seja localizada.
     * @throws SQLException Caso ocorra algum erro ao abrir a conexão com o
     * banco de dados.
     */
    public ProdutoDAO() throws ClassNotFoundException, SQLException {
        this.CONN = new ConexaoMysqlJDBC();
    }

    /**
     * Insere um produto dentro do banco de dados.
     *
     * @param p Produto a ser inserido.
     * @throws SQLException Lançada caso ocorra qualquer erro de SQL, ou nenhuma
     * linha inserida.
     */
    public void inserir(Produto p) throws SQLException {
        String sql1 = "INSERT INTO PRODUCT (NAME, DESCRIPTION, PARENT_PRODUCT_ID) "
                + "VALUES (?, ?, ?)";

        String sql2 = "INSERT INTO IMAGE (CAMINHO, TYPE, PRODUCT_ID) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement stmt1 = CONN.getConnection().prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement stmt2 = CONN.getConnection().prepareStatement(sql2)) {

            stmt1.setString(1, p.getName());
            stmt1.setString(2, p.getDescription());
            if (p.getIdParent() == null) {
                stmt1.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt1.setInt(3, p.getIdParent());
            }

            if (stmt1.executeUpdate() < 1) {
                throw new SQLException("Nenhuma registro foi inserido!");
            }

            Image i = p.getImage();
            i.setType(i.getCaminho());

            stmt2.setString(1, i.getCaminho());
            stmt2.setString(2, i.getType());

            //Recebe a chave do stmt1 referente ao produto
            ResultSet rs = stmt1.getGeneratedKeys();
            rs.next();
            stmt2.setInt(3, rs.getInt(1));

            if (stmt2.executeUpdate() < 1) {
                throw new SQLException("Nenhuma registro foi inserido!");
            }

            CONN.commit();
        } catch (SQLException ex) {
            CONN.rollback();
            throw ex;
        }
    }

    /**
     * Método que altera um determinado produto dentro do banco de dados.
     *
     * @param p Produto a ser alterado.
     * @throws SQLException
     */
    public void alterar(Produto p) throws SQLException {
        String sql1 = "UPDATE PRODUCT "
                + "SET NAME = ?, DESCRIPTION = ?, PARENT_PRODUCT_ID = ? "
                + "WHERE ID = ?";

        String sql2 = "UPDATE IMAGE "
                + "SET CAMINHO = ?, TYPE = ?, PRODUCT_ID = ? "
                + "WHERE ID = ?";

        try (PreparedStatement stmt1 = CONN.getConnection().prepareStatement(sql1);
                PreparedStatement stmt2 = CONN.getConnection().prepareStatement(sql2)) {

            stmt1.setString(1, p.getName());
            stmt1.setString(2, p.getDescription());
            stmt1.setInt(3, p.getIdParent());
            stmt1.setInt(4, p.getId());

            stmt2.setString(1, p.getImage().getCaminho());
            stmt2.setString(2, p.getImage().getType());
            stmt2.setInt(3, p.getImage().getProdutoId());
            stmt2.setInt(4, p.getImage().getId());

            if (stmt1.executeUpdate() < 1 || stmt2.executeUpdate() < 1) {
                throw new SQLException("Nenhum item foi alterado!");
            }

            CONN.commit();
        } catch (SQLException ex) {
            CONN.rollback();
            throw ex;
        }
    }

    /**
     * Método que exclui um determinado produto do banco de dados.
     *
     * @param id Id do produto que se deseja excluir.
     * @throws SQLException LAnçado caso ocorra algum erro no SQL, ou se nenhum
     * produto for deletado.
     */
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM PRODUCT WHERE ID = ?";

        try (PreparedStatement stmt = CONN.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);

            if (stmt.executeUpdate() < 1) {
                throw new SQLException("Nenhum produto foi deletado!");
            }

            CONN.commit();
        } catch (SQLException ex) {
            CONN.rollback();
            throw ex;
        }
    }

    /**
     * Seleciona um item do banco de dados.
     *
     * @param id ID do produto.
     * @return O Produto.
     * @throws SQLException Lançado caso ocorra algum erro de SQL.
     */
    public Produto selecionar(int id) throws SQLException {
        String sql = "SELECT * "
                + "FROM (PRODUCT P LEFT JOIN IMAGE I ON P.ID = I.PRODUCT_ID) "
                + "WHERE P.ID = ?";

        try (PreparedStatement stmt = CONN.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                return parse(rs);
            }

        } catch (SQLException ex) {
            throw ex;
        }
    }

    /**
     * Método que busca todos os produtos contidos no banco de dados.
     *
     * @return A lista de produtos ou uma lista vazia caso não tenha dados no
     * banco.
     * @throws SQLException Lançado caso ocorra algum erro de SQL.
     */
    public List<Produto> listar() throws SQLException {
        String sql = "SELECT * "
                + "FROM (PRODUCT P LEFT JOIN IMAGE I ON P.ID = I.PRODUCT_ID) "
                + "ORDER BY P.ID";

        try (PreparedStatement stmt = CONN.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                List<Produto> produtos = new ArrayList<>();
                while (rs.next()) {
                    produtos.add(parse(rs));
                }

                return produtos;
            }
        } catch (SQLException ex) {
            this.CONN.close();
            throw ex;
        }
    }

    /**
     * Método que retorna uma lista de produtos com relacionamentos com um
     * determinado produto pai.
     *
     * @param parent_product_id ID do produto pai.
     * @return A lista de produtos, ou null caso ocorra algum erro ou nenhuma
     * referencia encontrada.
     * @throws SQLException Lançada caso ocorra algum erro de SQL.
     */
    public List<Produto> listar(int parent_product_id) throws SQLException {
        String sql = "SELECT * "
                + "FROM (PRODUCT P LEFT JOIN IMAGE I ON P.ID = I.PRODUCT_ID) "
                + "WHERE P.PARENT_PRODUCT_ID = ? "
                + "ORDER BY P.ID";

        try (PreparedStatement stmt = CONN.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, parent_product_id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Produto> produtos = new ArrayList<>();
                while (rs.next()) {
                    produtos.add(parse(rs));
                }

                return produtos;
            }
        } catch (SQLException ex) {
            this.CONN.close();
            throw ex;
        }
    }

    /**
     * Lista um conjunto de imagem de um determinado id do produto.
     *
     * @param id_product Id do produto que se deseja localizar.
     * @return A lista de produtos.
     * @throws SQLException Lançada caso ocorra algum erro de SQL.
     */
    public List<Produto> listarPorImagem(int id_product) throws SQLException {
        String sql = "SELECT * "
                + "FROM (PRODUCT P LEFT JOIN IMAGE I ON P.ID = I.PRODUCT_ID) "
                + "WHERE P.ID = ? "
                + "ORDER BY P.ID";

        try (PreparedStatement stmt = CONN.getConnection().prepareStatement(sql)) {
            stmt.setInt(1, id_product);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Produto> produtos = new ArrayList<>();
                while (rs.next()) {
                    produtos.add(parse(rs));
                }

                return produtos;
            }
        } catch (SQLException ex) {
            this.CONN.close();
            throw ex;
        }
    }

    /**
     * Efetua o parse de um determinado ResultSet para Produo.
     *
     * @param rs ResulSet que irá conter os dados para efetuar o parse.
     * @return O produto contido no ResultSet.
     * @throws SQLException Lançado caso ocorra algum erro ao ler o ResultSet.
     */
    private Produto parse(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getInt("P.ID"));
        p.setName(rs.getString("P.NAME"));
        p.setDescription(rs.getString("P.DESCRIPTION"));
        p.setIdParent(rs.getInt("P.PARENT_PRODUCT_ID"));

        if (rs.getInt("I.ID") != 0) {
            Image i = new Image();
            i.setId(rs.getInt("I.ID"));
            i.setCaminho(rs.getString("I.CAMINHO"));
            i.setType(rs.getString("I.TYPE"));
            i.setProdutoId(rs.getInt("I.PRODUCT_ID"));

            p.setImage(i);
        }

        return p;
    }
}
