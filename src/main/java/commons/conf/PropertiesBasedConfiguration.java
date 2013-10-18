package commons.conf;

import java.util.Properties;

public class PropertiesBasedConfiguration implements Configuration {

    private final Properties properties;

    public PropertiesBasedConfiguration(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String get(String name) {
        if(properties.containsKey(name))
            return properties.getProperty(name);

        throw new IllegalArgumentException("Property not found: " + name);
    }

    @Override
    public String get(String name, String defaultValue) {
        if(properties.containsKey(name))
            return properties.getProperty(name);

        return defaultValue;
    }

    @Override
    public int getInt(String name, int defaultValue) {
        if(properties.containsKey(name))
            return Integer.parseInt(properties.getProperty(name));

        return defaultValue;
    }

}
