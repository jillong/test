package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseRequestPackage extends MonitorCommonProperties {

    /**
     * ID
     */
    private String id;

    /**
     * 时间
     */
    private Date dateTime;

    /**
     * 附加信息
     */
    private String extraMsg;
}
