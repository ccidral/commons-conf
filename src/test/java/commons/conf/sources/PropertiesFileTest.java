package commons.conf.sources;

import commons.conf.PropertiesSource;
import commons.conf.UnavailablePropertiesSourceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static commons.conf.util.PropertiesBuilder.properties;
import static commons.conf.util.PropertiesBuilder.property;
import static org.junit.Assert.*;

public class PropertiesFileTest {

    private File file;
    private PropertiesSource source;

    @Before
    public void setUp() throws Throwable {
        file = File.createTempFile("test", ".properties");
        source = new PropertiesFile(file.getPath());
    }

    @After
    public void tearDown() throws Throwable {
        file.delete();
    }

    @Test
    public void test_source_is_not_available_when_file_does_not_exist() throws Throwable {
        file.delete();
        assertFalse(source.isAvailable());
    }

    @Test
    public void test_source_is_available_when_file_does_exist() throws Throwable {
        assertTrue(source.isAvailable());
    }

    @Test(expected = UnavailablePropertiesSourceException.class)
    public void test_throw_exception_on_attempt_to_get_properties_from_unavailable_source() throws Throwable {
        file.delete();
        source.getProperties();
    }

    @Test
    public void test_properties_is_not_null_when_source_is_available() throws Throwable {
        assertNotNull(source.getProperties());
    }

    @Test
    public void test_properties() throws Throwable {
        writeToFile(
            "foo=bar\n" +
            "hello=world\n"
        );

        Properties expectedProperties = properties(
            property("foo", "bar"),
            property("hello", "world")
        );

        assertEquals(expectedProperties, source.getProperties());
    }

    private void writeToFile(String text) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        try {
            writer.write(text);
            writer.flush();
        } finally {
            writer.close();
        }
    }

}
