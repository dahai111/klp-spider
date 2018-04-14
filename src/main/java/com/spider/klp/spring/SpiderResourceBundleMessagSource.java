/*
 * 文件名：EQZResourceBundleMessagSource.java
 * 版权：
 * 修改人：zhangqiuping
 * 修改时间：2017年7月3日
 * 修改内容：新增
 */
package com.spider.klp.spring;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

/**
 * 
 * 重写 ： 重新加载properties类
 * @author     zhangqiuping
 * @since      2.2.4
 */
public class SpiderResourceBundleMessagSource extends ReloadableResourceBundleMessageSource {
    /**
     * 调测日志记录器。
     */
    private static final Logger logger = LoggerFactory.getLogger(SpiderResourceBundleMessagSource.class);
    
    @Override
    protected Properties loadProperties(Resource resource, String filename) throws IOException {
        logger.info("加载文件名称："+filename);
        return super.loadProperties(resource, filename);
    }
    
    
    
    
}
