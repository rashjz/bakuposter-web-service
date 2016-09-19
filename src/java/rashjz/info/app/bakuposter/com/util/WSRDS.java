/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.app.bakuposter.com.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Mobby
 */
public  abstract class WSRDS {
     public abstract Connection connect() throws SQLException;
}
