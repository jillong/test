package org.example.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.util.Map;

public class OsUtils {

    private static final Map<String, String> ENVS = System.getenv();

    public static boolean isWindowsOs() {
        boolean isWindowsOs = false;
        String osName = System.getProperty("os.name");
        if (StringUtils.containsIgnoreCase(osName, "windows")) {
            isWindowsOs = true;
        }
        return isWindowsOs;
    }

    public static String getComputerName() {

        if (isWindowsOs()) {
            return ENVS.get("COMPUTERNAME");
        } else {
            try {
                return InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                return "UnknownHost";
            }
        }
    }

}
