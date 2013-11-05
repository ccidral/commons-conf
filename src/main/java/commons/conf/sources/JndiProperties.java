package commons.conf.sources;

import commons.conf.ConfigurationException;
import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.Properties;

public class JndiProperties implements PropertiesSource {

    private final String jndiName;
    private final Context context;
    private final Logger logger = LoggerFactory.getLogger(getClass());

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

        } catch (NamingException e) {
            logger.warn("JNDI name '" + jndiName + "' could not be looked up", e);
            return false;
        }

        return true;
    }

}
