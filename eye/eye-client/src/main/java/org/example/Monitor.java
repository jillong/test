package org.example;

import org.example.entity.MonitorProperties;
import org.example.utils.AnalysisPropertiesUtil;

import java.io.IOException;
import java.util.Properties;

/**
 * Hello world!
 */
public class Monitor {

    private Monitor() {

    }

    public static void start(String propertiesUrl) throws IOException {
        run(propertiesUrl);
    }

    public static void start() throws IOException {
        run(null);
    }

    private static void run(String propertiesUrl) throws IOException {
        Properties properties = AnalysisPropertiesUtil.getProperties(propertiesUrl);
        MonitorProperties monitorProperties = AnalysisPropertiesUtil.cleanProperties(properties);


    }
}
