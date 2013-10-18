package commons.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class MultiSourceConfigurationLoader implements ConfigurationLoader {

    private final ConfigurationFactory factory;
    private final PropertiesSource[] sources;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MultiSourceConfigurationLoader(PropertiesSource... sources) {
        this(new DefaultConfigurationFactory(), sources);
    }

    public MultiSourceConfigurationLoader(ConfigurationFactory factory, PropertiesSource... sources) {
        this.factory = factory;
        this.sources = sources;
    }

    @Override
    public Configuration loadConfiguration() {
        if(!isAtLeastOneSourceAvailable())
            throw new ConfigurationNotFoundException();

        Properties allProperties = new Properties();

        for(PropertiesSource source : sources)
            if(source.isAvailable()) {
                Properties properties = source.getProperties();
                log("Found configuration source: "+source, properties);
                allProperties.putAll(properties);
            }

        log("Final configuration: ", allProperties);

        return factory.create(allProperties);
    }

    private boolean isAtLeastOneSourceAvailable() {
        for(PropertiesSource source : sources)
            if(source.isAvailable())
                return true;
        return false;
    }

    private void log(String message, Properties properties) {
        StringBuilder propertyLines = new StringBuilder();
        for(String propertyName : properties.stringPropertyNames())
            propertyLines.append(String.format("  > %s = %s%n", propertyName, properties.getProperty(propertyName)));
        logger.info(String.format("%s%n%s", message, propertyLines));
    }

}
