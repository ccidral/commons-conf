package commons.conf.util;

import java.util.Properties;

public class PropertiesBuilder {

    public static Properties properties(Property... list) {
        Properties properties = new Properties();
        for(Property property : list)
            properties.setProperty(property.name, property.value);
        return properties;
    }

    public static Property property(String name, String value) {
        return new Property(name, value);
    }

    public static class Property {
        public final String name, value;

        public Property(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

}
