package org.example.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeartbeatPackage extends BaseRequestPackage{

    /**
     * 心跳频率
     */
    private long rate;
}
