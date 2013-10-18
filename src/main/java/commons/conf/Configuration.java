package commons.conf;

public interface Configuration {

    String get(String name);

    String get(String name, String defaultValue);

    int getInt(String name, int defaultValue);

}
