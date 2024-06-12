package org.example.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorProperties {

    private int instanceOrder;

    private String instanceLanguage;

    private String instanceDesc;

    private String instanceName;

    private String instanceEndpoint;

    private boolean userSigarEnable;

    private boolean serverInfoEnable;

    private String serverInfoIp;

    private int serverInfoRate;

    private String serverUrl;

    private int connectTimeout;

    private int socketTimeout;

    private int connectionRequestTimeout;

    private boolean jvmInfoEnable;

    private int jvmInfoRate;

    private int heartbeat;

}
