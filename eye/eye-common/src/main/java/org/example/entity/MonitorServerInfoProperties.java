package org.example.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorServerInfoProperties {

    private boolean userSigarEnable;

    private boolean serverInfoEnable;

    private String serverInfoIp;

    private int serverInfoRate;
}
