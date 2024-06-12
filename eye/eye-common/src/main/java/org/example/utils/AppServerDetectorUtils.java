package org.example.utils;

import com.liferay.portal.kernel.util.ServerDetector;
import org.example.enums.AppServerTypeEnums;

public class AppServerDetectorUtils {

    public static final String JETTY_CLASS = "/org/mortbay/jetty/Server.class";

    public static final String UNDERTOW_CLASS = "/io/undertow/Undertow.class";

    public static final String NETTY_CLASS = "/reactor/netty/http/server/HttpServer.class";


    public static AppServerTypeEnums getAppServerTypeEnum() {
        if (ServerDetector.isTomcat()) {
            return AppServerTypeEnums.TOMCAT;
        }
        if (ServerDetector.isWebLogic()) {
            return AppServerTypeEnums.WEBLOGIC;
        }
        if (isUndertow()) {
            return AppServerTypeEnums.UNDERTOW;
        }
        if (isJetty()) {
            return AppServerTypeEnums.JETTY;
        }
        if (isNetty()) {
            return AppServerTypeEnums.NETTY;
        }
        return AppServerTypeEnums.UNKNOWN;
    }

    private static boolean isJetty() {
        Class<?> c = AppServerDetectorUtils.class;
        return c.getResource(JETTY_CLASS) != null;
    }

    /**
     * <p>
     * 是不是Undertow服务器
     * </p>
     */
    private static boolean isUndertow() {
        Class<?> c = AppServerDetectorUtils.class;
        return c.getResource(UNDERTOW_CLASS) != null;
    }

    /**
     * <p>
     * 是不是Netty服务器
     * </p>
     */
    private static boolean isNetty() {
        Class<?> c = AppServerDetectorUtils.class;
        return c.getResource(NETTY_CLASS) != null;
    }
}
