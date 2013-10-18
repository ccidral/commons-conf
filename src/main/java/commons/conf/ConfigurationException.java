package commons.conf;

import javax.naming.NamingException;

public class ConfigurationException extends RuntimeException {

    public ConfigurationException(NamingException cause) {
        super(cause);
    }

}
