package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppServerTypeEnums {

    /**
     * Tomcat
     */
    TOMCAT("Tomcat"),

    /**
     * Undertow
     */
    UNDERTOW("Undertow"),

    /**
     * Jetty
     */
    JETTY("Jetty"),

    /**
     * WebLogic
     */
    WEBLOGIC("WebLogic"),

    /**
     * Netty
     */
    NETTY("Netty"),

    /**
     * 未知
     */
    UNKNOWN("未知");

    /**
     * 应用服务器类型名
     */
    private final String name;
}
