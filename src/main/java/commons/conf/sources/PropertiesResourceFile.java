package commons.conf.sources;

import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesResourceFile implements PropertiesSource {

    private final String resourcePath;

    public PropertiesResourceFile(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public boolean isAvailable() {
        return getResourceStream() != null;
    }

    @Override
    public Properties getProperties() {
        if(!isAvailable())
            throw new UnavailablePropertiesSourceException();
        InputStream inputStream = getResourceStream();
        return loadPropertiesFrom(inputStream);
    }

    private Properties loadPropertiesFrom(InputStream inputStream) {
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private InputStream getResourceStream() {
        return getClass().getResourceAsStream(resourcePath);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), resourcePath);
    }

}
