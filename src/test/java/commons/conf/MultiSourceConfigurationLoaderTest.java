package commons.conf;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static commons.conf.util.PropertiesBuilder.properties;
import static commons.conf.util.PropertiesBuilder.property;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MultiSourceConfigurationLoaderTest {

    private ConfigurationLoader configurationLoader;
    private PropertiesSource source1;
    private PropertiesSource source2;
    private ConfigurationFactory factory;

    @Before
    public void setUp() {
        source1 = mock(PropertiesSource.class);
        source2 = mock(PropertiesSource.class);
        factory = mock(ConfigurationFactory.class);
        configurationLoader = new MultiSourceConfigurationLoader(factory, source1, source2);
    }

    private static void given_that_source_is_not_available(PropertiesSource source) {
        when(source.isAvailable()).thenReturn(false);
    }

    private static void given_that_source_is_available(PropertiesSource source, Properties properties) {
        when(source.isAvailable()).thenReturn(true);
        when(source.getProperties()).thenReturn(properties);
    }

    @Test(expected = ConfigurationNotFoundException.class)
    public void test_throw_exception_when_all_configuration_sources_are_unavailable() {
        given_that_source_is_not_available(source1);
        given_that_source_is_not_available(source2);
        configurationLoader.loadConfiguration();
    }

    @Test
    public void test_configuration_is_created_when_at_least_one_source_is_available() {
        given_that_source_is_not_available(source1);
        given_that_source_is_available(source2, properties());

        Configuration configuration = mock(Configuration.class);
        when(factory.create(any(Properties.class))).thenReturn(configuration);

        assertSame(configuration, configurationLoader.loadConfiguration());
    }

    @Test
    public void test_merge_configuration_sources_in_order_of_definition() {
        given_that_source_is_available(source1, properties(
            property("foo", "bar"),
            property("greeting", "hello")
        ));

        given_that_source_is_available(source2, properties(
            property("color", "black"),
            property("greeting", "hi")
        ));

        Properties mergedProperties = properties(
            property("color", "black"),
            property("foo", "bar"),
            property("greeting", "hi")
        );

        Configuration configuration = mock(Configuration.class);
        when(factory.create(mergedProperties)).thenReturn(configuration);

        assertSame(configuration, configurationLoader.loadConfiguration());
    }

}
