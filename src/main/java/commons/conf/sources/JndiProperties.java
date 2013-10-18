package commons.conf.sources;

import commons.conf.ConfigurationException;
import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
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
        return doesJndiNameExist();
    }

    @Override
    public Properties getProperties() {
        if(!isAvailable())
            throw new UnavailablePropertiesSourceException();

        return getPropertiesFromJndiContext();
    }

    private Properties getPropertiesFromJndiContext() {
        try {
            return (Properties) context.lookup(jndiName);
        } catch (NamingException e) {
            throw new ConfigurationException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), jndiName);
    }

    private boolean doesJndiNameExist() {
        try {
            context.lookup(jndiName);

        } catch (NoInitialContextException notFound) {
            return false;

        } catch (NameNotFoundException notFound) {
            return false;

        } catch (NamingException e) {
            throw new ConfigurationException(e);
        }

        return true;
    }

}
