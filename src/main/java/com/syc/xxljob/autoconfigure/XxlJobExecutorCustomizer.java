package com.syc.xxljob.autoconfigure;

import com.xxl.job.core.executor.XxlJobExecutor;

/**
 * 预留一个自定义配置的接口
 */
@FunctionalInterface
public interface XxlJobExecutorCustomizer {

    void customize(final XxlJobExecutor xxlJobExecutor);

}
