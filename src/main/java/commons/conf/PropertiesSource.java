package commons.conf;

import java.util.Properties;

public interface PropertiesSource {

    boolean isAvailable();

    Properties getProperties();

}
