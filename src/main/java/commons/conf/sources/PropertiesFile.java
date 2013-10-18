package commons.conf.sources;

import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile implements PropertiesSource {

    private final String filePath;

    public PropertiesFile(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean isAvailable() {
        return new File(filePath).exists();
    }

    @Override
    public Properties getProperties() {
        if(!isAvailable())
            throw new UnavailablePropertiesSourceException();
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), filePath);
    }

}
