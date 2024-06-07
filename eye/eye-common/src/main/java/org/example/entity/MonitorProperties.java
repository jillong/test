package org.example.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorProperties {

    private MonitorJvmInfoProperties monitorJvmInfoProperties;

    private MonitoreServerProperties monitoreServerProperties;

    private MonitorInstanceProperties monitorInstanceProperties;

    private MonitorHeartbeatProperties monitorHeartbeatProperties;

    private MonitorServerInfoProperties monitorServerInfoProperties;
}
