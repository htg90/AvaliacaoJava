package br.com.htg.backend.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Helbert Gomes
 */
public class ConexaoMysqlJDBC implements ConexaoJDBC {

    private Connection conn = null;
    
    public ConexaoMysqlJDBC() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        
        Properties props = new Properties();
        props.put("user", "root");
        props.put("password", "root");
        
        this.conn = DriverManager.getConnection("jdbc:mysql://localhost/avaliacao", props);
        this.conn.setAutoCommit(false);
    }
    
    @Override
    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void close() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMysqlJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void commit() throws SQLException {
        this.conn.commit();
        close();
    }

    @Override
    public void rollback() {
        if (this.conn != null) {
            try {
                this.conn.rollback();
            } catch (SQLException ex) {
                Logger.getLogger(ConexaoMysqlJDBC.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                close();
            }
        }
    }
    
}
