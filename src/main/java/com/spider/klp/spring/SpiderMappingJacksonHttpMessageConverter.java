package com.spider.klp.spring;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 
 * @author zhangqiuping
 *
 */
public class SpiderMappingJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter
{
	
	/**
	 * 日志工具类
	 */
	private static final Logger logger = LoggerFactory.getLogger(SpiderMappingJacksonHttpMessageConverter.class);
	
	@Override
	protected Object readInternal(Class<? extends Object> clazz,HttpInputMessage inputMessage) throws IOException,HttpMessageNotReadableException
	{
		return super.readInternal(clazz,inputMessage);
	}
	
	@Override
	protected void writeInternal(Object o,HttpOutputMessage outputMessage) throws IOException,HttpMessageNotWritableException
	{
		super.writeInternal(o,outputMessage);
		if(logger.isInfoEnabled())
			logger.info("\n" + JsonUtils.stringify(o));
		//
		// if(null != o)
		// {
		// JSONObject json = JSONObject.parseObject(o.toString());
		// Iterator<Entry<String,Object>> it = json.entrySet().iterator();
		// StringBuffer result = new
		// StringBuffer("\n").append("{").append("\n");
		// while(it.hasNext())
		// {
		// Entry<String,Object> entry = it.next();
		// Object value = entry.getValue();
		// result.append(" ").append("\"").append(entry.getKey()).append("\"").append(":");
		// if(value instanceof JSONArray)
		// {
		// result.append(writeListJson((JSONArray)value)).append("\n");
		// } else
		// {
		// result.append("\"").append(value).append("\"").append("\n");
		// }
		//
		// }
		// result.append("}").append("\n");
		// logger.info("返回数据：\n" + result.toString());
		// }
	}
	
	// private String writeListJson(JSONArray jsonArray)
	// {
	// if(null == jsonArray || jsonArray.isEmpty())
	// {
	// return "";
	// }
	// StringBuffer result = new StringBuffer("\n").append("   [").append("\n");
	// for(int i = 0;i < jsonArray.size();i++ )
	// {
	// Object object = jsonArray.get(i);
	// result.append("    \"").append(object).append("\"").append("\n");
	// }
	// result.append("   ]");
	// return result.toString();
	// }
}
