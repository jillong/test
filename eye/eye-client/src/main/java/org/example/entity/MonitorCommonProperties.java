package org.example.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.AppServerTypeEnums;

@Getter
@Setter
public class MonitorCommonProperties {

    /**
     * 应用实例端点（服务端、代理端、客户端、UI端）
     */
    protected String instanceEndpoint;

    /**
     * 应用实例ID
     */
    protected String instanceId;

    /**
     * 应用实例名
     */
    protected String instanceName;

    /**
     * 应用实例描述
     */
    protected String instanceDesc;

    /**
     * 应用实例程序语言
     */
    protected String instanceLanguage;

    /**
     * 应用服务器类型
     */
    protected AppServerTypeEnums appServerType;

    /**
     * IP地址
     */
    protected String ip;

    /**
     * 计算机名
     */
    protected String computerName;

    /**
     * 链路信息
     */
    //private Chain chain;
}
