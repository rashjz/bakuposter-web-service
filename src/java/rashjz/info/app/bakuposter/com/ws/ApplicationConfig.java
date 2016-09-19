package rashjz.info.app.bakuposter.com.ws;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
 

@javax.ws.rs.ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(rashjz.info.app.bakuposter.com.ws.BakufunWSResource.class);
    }
}
