package commons.conf;

import java.util.Properties;

public interface ConfigurationFactory {

    Configuration create(Properties properties);

}
