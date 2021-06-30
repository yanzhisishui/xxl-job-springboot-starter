package com.syc.xxljob.autoconfigure;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.xxl.job.core.handler.IJobHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(IJobHandler.class)
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {
    /**
     * 预留初始化和销毁方法
     * */
    @Bean(initMethod = "start", destroyMethod = "destroy")
    /**
     * 当程序中没有注入 XxlJobExecutor 时才会将我们这个注入到 Spring
     * */
    @ConditionalOnMissingBean
    public XxlJobExecutor xxlJobExecutor(XxlJobProperties xxlJobProperties,
                                         ObjectProvider<XxlJobExecutorCustomizer> customizers) {
        XxlJobExecutor xxlJobExecutor = new XxlJobSpringExecutor();
        // 调度中心配置
        xxlJobExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        // 执行器配置
        xxlJobExecutor.setAppname(xxlJobProperties.getExecutor().getAppName());
        xxlJobExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        xxlJobExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
        xxlJobExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());
        // 预留的 customizer配置
        customizers.orderedStream().forEach(customizer -> customizer.customize(xxlJobExecutor));
        return xxlJobExecutor;
    }
}
