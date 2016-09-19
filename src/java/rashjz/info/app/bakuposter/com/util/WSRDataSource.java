/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.app.bakuposter.com.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale; 
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
 

/**
 *
 * @author Rashad Javadov
 */
public class WSRDataSource extends WSRDS {
    private static final Logger logger = Logger.getLogger(WSRDataSource.class.getName());

    private final String jndiName;

    public WSRDataSource(String jndiName) {
        this.jndiName = jndiName;
    }

    @Override
    public Connection connect() throws SQLException { 
        Connection connection = null;
        Locale def = Locale.getDefault();
        try {

            Locale.setDefault(new Locale("en", "US"));
            Context initContext = new InitialContext();
//            Context envContext = (Context) initContext.lookup("java:/comp/env");
            //setting up in context init
            DataSource ds = (DataSource) initContext.lookup(jndiName);
            connection = ds.getConnection();
            connection.setAutoCommit(false);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            Locale.setDefault(def);
        } 
        return connection;
    }

}
