package commons.conf;

import java.util.Properties;

public class DefaultConfigurationFactory implements ConfigurationFactory {

    @Override
    public Configuration create(Properties properties) {
        return new PropertiesBasedConfiguration(properties);
    }

}
