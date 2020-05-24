package base.config;

import org.apache.commons.configuration.*;

import java.util.Iterator;

public class ConfigurationHolder {

    private ConfigurationHolder() {

    }

    private static final String DEFAULT_CONFIG_FILE = "src\\test\\java\\base\\config\\testapi-default.properties";


    private static volatile CompositeConfiguration configuration;

    public static Configuration configuration() {
        if (configuration == null) {
            initializeConfiguration();
        }
        return configuration;
    }

    private static void initializeConfiguration() {
        try {
            configuration = new CompositeConfiguration();
            configuration.addConfiguration(new SystemConfiguration());
            configuration.addConfiguration(new PropertiesConfiguration(DEFAULT_CONFIG_FILE));
            Iterator keys = configuration.getKeys();

            while (keys.hasNext()) {
                Object key = keys.next();
            }

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
