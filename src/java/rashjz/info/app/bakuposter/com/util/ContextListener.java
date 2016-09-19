/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rashjz.info.app.bakuposter.com.util;

import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(ContextListener.class.getName());

    // Public constructor is required by servlet spec
    public ContextListener() {
    }

    public void contextInitialized(ServletContextEvent e) {
        ServletContext ctx = e.getServletContext();
        LOG.info("contextInitialized  ws");
        DataBaseHelper.setDataSource(new WSRDataSource(Constants.JND_NAME));
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

//    public void configureLog4j(String prefix) {
//        String file = "WEB-INF" + System.getProperty("file.separator") + "log4j.properties";  
//        if (file != null) {
//            PropertyConfigurator.configure(prefix + file);
//            System.out.println("Log4J Logging started for application: " + prefix + file);
//        } else {
//            System.out.println("Log4J Is not configured for application GPPWEB: " + prefix + file);
//        }
//    }
}
