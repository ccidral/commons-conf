Commons Configuration
=====================

Usage:

```java
import commons.conf.Configuration;
import commons.conf.ConfigurationLoader;
import commons.conf.MultiSourceConfigurationLoader;
import commons.conf.sources.JndiProperties;
import commons.conf.sources.PropertiesFile;
import commons.conf.sources.PropertiesResourceFile;

import javax.naming.Context;
import javax.naming.InitialContext;

Context jndiContext = new InitialContext();

ConfigurationLoader configurationLoader = new MultiSourceConfigurationLoader(
    new PropertiesResourceFile("/myapp.properties"),
    new JndiProperties("my-config", jndiContext),
    new PropertiesFile("/programs/myapp/conf/myapp.properties")
);

Configuration configuration = configurationLoader.loadConfiguration();

String foo = configuration.get("foo");
int bar = configuration.getInt("bar");
```
