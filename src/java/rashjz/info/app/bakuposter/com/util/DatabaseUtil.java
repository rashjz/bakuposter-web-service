package rashjz.info.app.bakuposter.com.util;

import java.sql.*;
import javax.sql.DataSource;

/**
 * @author : Rashad Javadov
 */
public class DatabaseUtil {

    public static void close(CallableStatement cs, Connection con) {
        try {
            if (cs != null) {
                cs.close();
            }

            if (con != null) {
                con.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (ps != null) {
                ps.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void close(ResultSet rs, CallableStatement cs, Connection con) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (cs != null) {
                cs.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

//    public static DataSource getDataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
//        dataSource.setUrl("jdbc:oracle:thin:@192.168.56.102:1521:orcl");
//        dataSource.setUsername("NAME");
//        dataSource.setPassword("name");
//        return dataSource;
//    }
    public static Connection connect() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver"); 
             Class.forName("com.mysql.jdbc.Driver");
//            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:15211:lsim", "RASHAD", "Par0l gerek Cetin 0lsun!");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bakuposter", "root", "123456");
//             connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:15211:lsim", "RSB_USER", "rsb_user!!!");
//               connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:lsim", "RSB_USER", "rsb_user!!!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }
}
