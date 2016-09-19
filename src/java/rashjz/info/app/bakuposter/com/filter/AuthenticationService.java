/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.app.bakuposter.com.filter;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Rashad Javadov
 */
public class AuthenticationService {

    private static final Logger LOG = Logger.getLogger(AuthenticationService.class.getName());

    public boolean authenticate(String authCredentials) {
        boolean authenticationStatus = false;
        if (null == authCredentials) {
            return false;
        }
        // header value format will be "Basic encodedstring" for Basic
        // authentication. Example "Basic YWRtaW46YWRtaW4="
        final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = null;
        try {
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(encodedUserPassword);
//            System.out.println(decodedBytes);
            usernameAndPassword = new String(decodedBytes, "UTF-8");

            final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");

            final String username = tokenizer.nextToken();
            final String password = tokenizer.nextToken();
            LOG.info("tokenizer :::  " + username + "  ::::::: " + password);
            // we have fixed the userid and password as admin
            // call some UserService/LDAP here
            authenticationStatus = "rashjz".equals(username) && "parket470".equals(password);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return authenticationStatus;
    }
}
