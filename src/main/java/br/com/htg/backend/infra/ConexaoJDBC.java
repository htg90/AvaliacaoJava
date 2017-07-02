package br.com.htg.backend.infra;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface que representa uma regra de negócio para a conexão com o banco de
 * dados utilizando JDBC.
 *
 * @author Helbert Gomes
 */
public interface ConexaoJDBC {

    /**
     * Método que retorna uma conexão com o banco de dados.
     *
     * @return A conexão com o banco de dados.
     */
    public Connection getConnection();

    /**
     * Método que faz o fechamento de uma determinada conexão com o banco de
     * dados..
     */
    public void close();

    /**
     * Método que faz com o banco de dados comita os dados, ou seja, faz com que
     * o banco de dados insira as ações dentro das tabelas.
     *
     * @throws SQLException Lançado caso ocorra algum erro ao efetuar o commit
     * no banco de dados.
     */
    public void commit() throws SQLException;

    /**
     * Método que faz com que toda a alteração feita dentro das tabelas em uma
     * determinada transação seja desfeita.
     */
    public void rollback();
}
