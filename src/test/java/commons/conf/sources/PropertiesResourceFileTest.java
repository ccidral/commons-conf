package commons.conf.sources;

import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;
import org.junit.Test;

import java.util.Properties;

import static commons.conf.util.PropertiesBuilder.properties;
import static commons.conf.util.PropertiesBuilder.property;
import static org.junit.Assert.*;

public class PropertiesResourceFileTest {

    @Test
    public void test_source_is_unavailable_when_resource_file_does_not_exist() {
        PropertiesSource source = new PropertiesResourceFile("/conf/non_existent_resource_file");
        assertFalse(source.isAvailable());
    }

    @Test
    public void test_source_is_available_when_resource_file_does_exist() {
        PropertiesSource source = new PropertiesResourceFile("/conf/PropertiesResourceFileTest.properties");
        assertTrue(source.isAvailable());
    }

    @Test(expected = UnavailablePropertiesSourceException.class)
    public void test_throw_exception_on_attempt_to_get_properties_from_unavailable_source() {
        PropertiesResourceFile source = new PropertiesResourceFile("/conf/non_existent_resource_file");
        source.getProperties();
    }

    @Test
    public void test_properties_is_not_null_when_source_is_available() {
        PropertiesSource source = new PropertiesResourceFile("/conf/PropertiesResourceFileTest.properties");
        assertNotNull(source.getProperties());
    }

    @Test
    public void test_properties() {
        PropertiesSource source = new PropertiesResourceFile("/conf/PropertiesResourceFileTest.properties");
        Properties expectedProperties = properties(
            property("foo", "bar"),
            property("hello", "world")
        );
        assertEquals(expectedProperties, source.getProperties());
    }

}
