package commons.conf.sources;

import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.util.Properties;

public class JndiProperties implements PropertiesSource {

    private final String jndiName;
    private final Context context;

    public JndiProperties(String jndiName, Context context) {
        this.jndiName = jndiName;
        this.context = context;
    }

    @Override
    public boolean isAvailable() {
        try {
            context.lookup(jndiName);
        } catch (NameNotFoundException notFound) {
            return false;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Properties getProperties() {
        if(!isAvailable())
            throw new UnavailablePropertiesSourceException();

        try {
            return (Properties) context.lookup(jndiName);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), jndiName);
    }

}
