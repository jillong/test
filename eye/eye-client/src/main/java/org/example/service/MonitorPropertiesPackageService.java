package org.example.service;

import org.apache.commons.beanutils.BeanUtils;
import org.example.entity.MonitorCommonProperties;
import org.example.entity.MonitorProperties;
import org.example.utils.AnalysisPropertiesUtil;
import org.example.utils.AppServerDetectorUtils;
import org.example.utils.OsUtils;

import java.lang.reflect.InvocationTargetException;

public class MonitorPropertiesPackageService {

    public <T extends MonitorCommonProperties> T getHeartbeatPackage(T packageProperties)
            throws InvocationTargetException, IllegalAccessException {

        MonitorProperties monitorProperties = AnalysisPropertiesUtil.getMonitorProperties();
        BeanUtils.copyProperties(packageProperties, monitorProperties);
        // 应用服务器类型
        packageProperties.setAppServerType(AppServerDetectorUtils.getAppServerTypeEnum());
        // 计算机名
        packageProperties.setComputerName(OsUtils.getComputerName());
        // 应用实例ID
        //packageProperties.setInstanceId(InstanceGenerator.getInstanceId());
        return packageProperties;
    }
}
