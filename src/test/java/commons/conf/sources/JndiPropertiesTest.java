package commons.conf.sources;

import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import java.util.Properties;

import static commons.conf.util.PropertiesBuilder.properties;
import static commons.conf.util.PropertiesBuilder.property;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JndiPropertiesTest {

    private PropertiesSource source;
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = mock(Context.class);
        source = new JndiProperties("foobar", context);
    }

    private void given_that_jndi_context_is_unavailable() throws NamingException {
        when(context.lookup("foobar")).thenThrow(new NoInitialContextException());
    }

    private void given_that_jndi_name_is_not_found() throws NamingException {
        when(context.lookup("foobar")).thenThrow(new NameNotFoundException());
    }

    private void given_that_jndi_name_is_bound_to(Properties properties) throws NamingException {
        when(context.lookup("foobar")).thenReturn(properties);
    }

    @Test
    public void test_source_is_unavailable_when_jndi_context_is_unavailable() throws Throwable {
        given_that_jndi_context_is_unavailable();
        assertFalse(source.isAvailable());
    }

    @Test
    public void test_source_is_unavailable_when_jndi_name_is_not_found() throws Throwable {
        given_that_jndi_name_is_not_found();
        assertFalse(source.isAvailable());
    }

    @Test
    public void test_source_is_available_when_jndi_name_is_found() throws Throwable {
        given_that_jndi_name_is_bound_to(new Properties());
        assertTrue(source.isAvailable());
    }

    @Test(expected = UnavailablePropertiesSourceException.class)
    public void test_throw_exception_on_attempt_to_get_properties_from_unavailable_source() throws Throwable {
        given_that_jndi_name_is_not_found();
        source.getProperties();
    }

    @Test
    public void test_properties_is_not_null_when_source_is_available() throws Throwable {
        given_that_jndi_name_is_bound_to(new Properties());
        assertNotNull(source.getProperties());
    }

    @Test
    public void test_properties() throws Throwable {
        given_that_jndi_name_is_bound_to(properties(
            property("foo", "bar"),
            property("hello", "world")
        ));

        Properties expectedProperties = properties(
            property("foo", "bar"),
            property("hello", "world")
        );

        assertEquals(expectedProperties, source.getProperties());
    }

}
