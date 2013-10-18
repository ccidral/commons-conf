package commons.conf;

import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class PropertiesBasedConfigurationTest {

    private Configuration configuration;

    @Before
    public void setUp() throws Exception {
        Properties properties = new Properties();
        properties.put("string.first", "foo");
        properties.put("string.second", "bar");
        properties.put("int.first", "123");
        properties.put("int.second", "7654");
        configuration = new PropertiesBasedConfiguration(properties);
    }

    @Test
    public void testGetString() {
        assertEquals("foo", configuration.get("string.first"));
        assertEquals("bar", configuration.get("string.second"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testThrowExceptionWhenStringPropertyIsNotFound() {
        configuration.get("non-existent-property");
    }

    @Test
    public void testGetStringDefaultValue() {
        assertEquals("foo", configuration.get("string.first", "sample default value"));
        assertEquals("bar", configuration.get("string.second", "sample default value"));
        assertEquals("hello", configuration.get("non-existent-property", "hello"));
    }

    @Test
    public void testGetInt() {
        assertEquals(123, configuration.getInt("int.first", 999));
        assertEquals(7654, configuration.getInt("int.second", 999));
    }

    @Test
    public void testGetIntDefaultValue() {
        assertEquals(1001, configuration.getInt("non-existent-property", 1001));
    }

}
