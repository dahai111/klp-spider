/*
 * 文件名：MappingJacksonHttpMessageConverter.java 版权：深圳融信信息咨询有限公司 修改人：zhangqiuping
 * 修改时间：@create_date 2017年10月18日 下午1:46:34 修改内容：新增
 */
package com.spider.klp.spring;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * TODO 添加类的一句话简单描述。
 * <p>
 * TODO 详细描述
 * 
 * 
 * @author     zhangqiuping
 * @since      2.2.4
 * @create_date 2017年10月18日 下午1:46:34
 */
public class MappingJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter
{
	
	/**
	 * 调测日志记录器。
	 */
	private static final Logger logger = LoggerFactory.getLogger(MappingJacksonHttpMessageConverter.class);
	
	@Override
	protected void writeInternal(Object object,HttpOutputMessage outputMessage) throws IOException,HttpMessageNotWritableException
	{
		super.writeInternal(object,outputMessage);
		// 打印日志
		if(logger.isInfoEnabled())
		{
			logger.info("接口返回：" + JsonUtils.stringify(object));
		}
	}
}
