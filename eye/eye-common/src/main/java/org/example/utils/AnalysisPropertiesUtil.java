package org.example.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.example.entity.MonitorHeartbeatProperties;
import org.example.entity.MonitorInstanceProperties;
import org.example.entity.MonitorJvmInfoProperties;
import org.example.entity.MonitorProperties;
import org.example.entity.MonitorServerInfoProperties;
import org.example.entity.MonitoreServerProperties;
import org.example.exception.IllegalPropertiesException;
import org.example.exception.NotFindPropertyException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AnalysisPropertiesUtil {

    public static String[] instanceEndpoints = {"client", "agent", "server", "ui"};

    public static Properties getProperties(String propertiesUrl) throws IOException {

        if (StringUtils.isBlank(propertiesUrl)) {
            propertiesUrl = "classpath:monitor.properties";
        }

        Properties properties = new Properties();
        InputStream propertiesAsStream
                = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesUrl);
        properties.load(propertiesAsStream);

        return properties;
    }

    public static MonitorProperties cleanProperties(Properties properties) {

        MonitorProperties monitorProperties = new MonitorProperties();
        MonitoreServerProperties monitoreServerProperties = cleanServerProperties(properties);
        MonitorInstanceProperties monitorInstanceProperties = cleanInstanceProperties(properties);
        MonitorHeartbeatProperties monitorHeartbeatProperties = cleanHeartbeatProperties(properties);
        MonitorServerInfoProperties monitorServerInfoProperties = cleanServerInfoProperties(properties);
        MonitorJvmInfoProperties monitorJvmInfoProperties = cleanJvmInfoProperties(properties);

        monitorProperties.setMonitoreServerProperties(monitoreServerProperties);
        monitorProperties.setMonitorInstanceProperties(monitorInstanceProperties);
        monitorProperties.setMonitorHeartbeatProperties(monitorHeartbeatProperties);
        monitorProperties.setMonitorServerInfoProperties(monitorServerInfoProperties);
        monitorProperties.setMonitorJvmInfoProperties(monitorJvmInfoProperties);
        return monitorProperties;
    }

    private static MonitorJvmInfoProperties cleanJvmInfoProperties(Properties properties) {

        String jvmInfoEnableString = StringUtils.trim(properties.getProperty("monitoring.jvm-info.enable"));
        boolean jvmInfoEnable =
                StringUtils.isNotBlank(jvmInfoEnableString) && Boolean.parseBoolean(jvmInfoEnableString);

        int jvmInfoRate;
        String jvmInfoRateString = StringUtils.trim(properties.getProperty("monitoring.jvm-info.rate"));
        if (StringUtils.isBlank(jvmInfoRateString) || Integer.parseInt(jvmInfoRateString) < 30) {
            jvmInfoRate = 60;
        } else {
            jvmInfoRate = Integer.parseInt(jvmInfoRateString);
        }

        MonitorJvmInfoProperties monitorJvmInfoProperties = new MonitorJvmInfoProperties();
        monitorJvmInfoProperties.setJvmInfoEnable(jvmInfoEnable);
        monitorJvmInfoProperties.setJvmInfoRate(jvmInfoRate);
        return monitorJvmInfoProperties;

    }

    private static MonitorServerInfoProperties cleanServerInfoProperties(Properties properties) {

        String serverInfoEnableString = StringUtils.trim(properties.getProperty("monitoring.server-info.enable"));
        boolean serverInfoEnable =
                StringUtils.isNotBlank(serverInfoEnableString) && Boolean.parseBoolean(serverInfoEnableString);

        int serverInfoRate;
        String serverInfoRateString = StringUtils.trim(properties.getProperty("monitoring.server-info.rate"));
        if (StringUtils.isBlank(serverInfoRateString) || Integer.parseInt(serverInfoRateString) < 30) {
            serverInfoRate = 60;
        } else {
            serverInfoRate = Integer.parseInt(serverInfoRateString);
        }

        String serverInfoIp = StringUtils.trim(properties.getProperty("monitoring.server-info.ip"));
        if (serverInfoIp != null && IpAddressUtils.isIpAddress(serverInfoIp)) {
            throw new IllegalPropertiesException("IPV4地址不合法");
        }

        String userSigarEnableString
                = StringUtils.trim(properties.getProperty("monitoring.server-info.user-sigar-enable"));
        boolean userSigarEnable =
                StringUtils.isNotBlank(userSigarEnableString) && Boolean.parseBoolean(userSigarEnableString);

        MonitorServerInfoProperties monitorServerInfoProperties = new MonitorServerInfoProperties();
        monitorServerInfoProperties.setServerInfoRate(serverInfoRate);
        monitorServerInfoProperties.setServerInfoIp(serverInfoIp);
        monitorServerInfoProperties.setServerInfoEnable(serverInfoEnable);
        monitorServerInfoProperties.setUserSigarEnable(userSigarEnable);
        return monitorServerInfoProperties;

    }

    private static MonitorHeartbeatProperties cleanHeartbeatProperties(Properties properties) {

        int heartbeatRate;
        String heartbeatRateString = StringUtils.trim(properties.getProperty("monitoring.heartbeat.rate"));

        if (StringUtils.isBlank(heartbeatRateString) || Integer.parseInt(heartbeatRateString) < 30) {
            heartbeatRate = 60;
        } else {
            heartbeatRate = Integer.parseInt(heartbeatRateString);
        }

        MonitorHeartbeatProperties monitorHeartbeatProperties = new MonitorHeartbeatProperties();
        monitorHeartbeatProperties.setHeartbeat(heartbeatRate);
        return monitorHeartbeatProperties;

    }

    private static MonitorInstanceProperties cleanInstanceProperties(Properties properties) {
        String instanceOrderString = StringUtils.trim(properties.getProperty("monitoring.own.instance.order"));
        int instanceOrder = instanceOrderString == null ? 1 : Integer.parseInt(instanceOrderString);

        String instanceEndpoint = StringUtils.trim(properties.getProperty("monitoring.own.instance.endpoint"));
        if (StringUtils.isBlank(instanceEndpoint)) {
            instanceEndpoint = "client";
        }

        if (!ArrayUtils.contains(instanceEndpoints, instanceEndpoint)) {
            throw new IllegalPropertiesException("实例端点类型错误");
        }

        String instanceName = StringUtils.trim(properties.getProperty("monitoring.own.instance.name"));
        if (StringUtils.isBlank(instanceName)) {
            throw new NotFindPropertyException("没有设置实例名称");
        }

        String instanceDesc = StringUtils.trim(properties.getProperty("monitoring.own.instance.desc"));

        String instanceLanguage = StringUtils.trim(properties.getProperty("monitoring.own.instance.language"));
        if (StringUtils.isBlank(instanceLanguage)) {
            instanceLanguage = "java";
        }

        MonitorInstanceProperties monitorInstanceProperties = new MonitorInstanceProperties();
        monitorInstanceProperties.setInstanceDesc(instanceDesc);
        monitorInstanceProperties.setInstanceLanguage(instanceLanguage);
        monitorInstanceProperties.setInstanceEndpoint(instanceEndpoint);
        monitorInstanceProperties.setInstanceName(instanceName);
        monitorInstanceProperties.setInstanceOrder(instanceOrder);

        return monitorInstanceProperties;
    }

    private static MonitoreServerProperties cleanServerProperties(Properties properties) {

        String serverUrl = properties.getProperty("monitoring.server.url");
        if (StringUtils.isBlank(serverUrl)) {
            throw new NotFindPropertyException("monitor server url can not null");
        }

        serverUrl = StringUtils.trim(serverUrl);
        int connectTimeout = disposeTimeout(properties.getProperty("monitoring.server.connect-timeout"));
        int socketTimeout = disposeTimeout(properties.getProperty("monitoring.server.socket-timeout"));
        int connectionRequestTimeout
                = disposeTimeout(properties.getProperty("monitoring.server.connection-request-timeout"));

        MonitoreServerProperties monitoreServerProperties = new MonitoreServerProperties();
        monitoreServerProperties.setServerUrl(serverUrl);
        monitoreServerProperties.setConnectTimeout(connectTimeout);
        monitoreServerProperties.setSocketTimeout(socketTimeout);
        monitoreServerProperties.setConnectionRequestTimeout(connectionRequestTimeout);
        return monitoreServerProperties;

    }

    private static int disposeTimeout(String timeout) {

        int lineNumber = 0;
        timeout = StringUtils.trim(timeout);

        if (Integer.parseInt(timeout) < lineNumber) {
            throw new IllegalPropertiesException("timeout must greater than 0");
        }

        if (StringUtils.isBlank(timeout)) {
            timeout = "15000";
        }

        return Integer.parseInt(timeout);
    }

}
