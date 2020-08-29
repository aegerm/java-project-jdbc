package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationProperties {

    private static ConfigurationProperties instance;
    private static Properties properties;

    public static ConfigurationProperties getInstance() {
        if (instance == null) {
            instance = new ConfigurationProperties();
        }
        return instance;
    }

    public ConfigurationProperties() {
        loadProperties();
    }

    private Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("application.properties")) {
            properties = new Properties();
            properties.load(fs);
            return properties;
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getProperties(String key) {
        return properties.getProperty(key);
    }
}