package commons.conf.sources;

import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import java.util.Properties;

import static commons.conf.util.PropertiesBuilder.properties;
import static commons.conf.util.PropertiesBuilder.property;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JndiPropertiesTest {

    @Test
    public void test_source_is_unavailable_when_jndi_name_is_not_found() throws Throwable {
        Context context = mock(Context.class);
        when(context.lookup("foobar")).thenThrow(new NameNotFoundException());

        PropertiesSource source = new JndiProperties("foobar", context);
        assertFalse(source.isAvailable());
    }

    @Test
    public void test_source_is_available_when_jndi_name_is_found() throws Throwable {
        Context context = mock(Context.class);
        when(context.lookup("foobar")).thenReturn(new Properties());

        PropertiesSource source = new JndiProperties("foobar", context);
        assertTrue(source.isAvailable());
    }

    @Test(expected = UnavailablePropertiesSourceException.class)
    public void test_throw_exception_on_attempt_to_get_properties_from_unavailable_source() throws Throwable {
        Context context = mock(Context.class);
        when(context.lookup("foobar")).thenThrow(new NameNotFoundException());

        PropertiesSource source = new JndiProperties("foobar", context);
        source.getProperties();
    }

    @Test
    public void test_properties_is_not_null_when_source_is_available() throws Throwable {
        Context context = mock(Context.class);
        when(context.lookup("foobar")).thenReturn(new Properties());

        PropertiesSource source = new JndiProperties("foobar", context);
        assertNotNull(source.getProperties());
    }

    @Test
    public void test_properties() throws Throwable {
        Context context = mock(Context.class);
        when(context.lookup("foobar")).thenReturn(properties(
            property("foo", "bar"),
            property("hello", "world")
        ));

        PropertiesSource source = new JndiProperties("foobar", context);

        Properties expectedProperties = properties(
            property("foo", "bar"),
            property("hello", "world")
        );
        assertEquals(expectedProperties, source.getProperties());
    }

}
