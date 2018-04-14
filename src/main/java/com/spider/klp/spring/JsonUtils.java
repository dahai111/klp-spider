package com.spider.klp.spring;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spider.core.exception.SpiderException;
import com.spider.core.result.ResultCode;

/**
 * JSON工具类
 * 
 * @author 龙汀
 *
 */
public class JsonUtils
{
	private static ObjectMapper objectMapper = null;
	
	/**
	 * 调测日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
	
	static
	{
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
		objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,false);
		
		// objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
		// true) ;
	}
	
	/**
	 * 
	 * 对象转JSON
	 * 
	 * @param value
	 * @return
	 */
	public static String toJSON(Object value)
	{
		try
		{
			return objectMapper.writeValueAsString(value);
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStr(String json,String v)
	{
		
		String error = null;
		JsonNode rootNode = null;
		if(null != json)
		{
			try
			{
				rootNode = objectMapper.readValue(json,JsonNode.class);
			} catch(IOException e)
			{
				LOG.error("JSON解析异常",e);
				throw new SpiderException(ResultCode.FAID,"系统异常");
			}
		}
		if(null != rootNode)
		{
			if(rootNode.path(v).isArray() || rootNode.path(v).isContainerNode())
			{
				error = rootNode.path(v).toString();
			} else if(rootNode.path(v).isValueNode())
			{
				error = rootNode.path(v).textValue();
			}
		}
		if("null".equalsIgnoreCase(error))
		{
			error = null;
		}
		return error;
	}
	
	/**
	 * 
	 * 处理对象输出带换行空格格式的JSON字符串，用于记录日志。
	 * 
	 * { "code" : "", "desc" : "" }
	 * 
	 * @param o
	 * @return
	 */
	public static String stringify(Object o)
	{
		DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
		printer.indentArraysWith(new DefaultIndenter());
		try
		{
			return objectMapper.writer(printer).writeValueAsString(o);
		} catch(JsonProcessingException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * JSON转对象
	 * 
	 * @param json
	 * @param type
	 * @return
	 */
	public static <T>T toObj(String json,Class<T> type)
	{
		try
		{
			return objectMapper.readValue(json,type);
		} catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	public static <T>T toObj(byte[] bs,Class<T> type)
	{
		try
		{
			return objectMapper.readValue(bs,type);
		} catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * json字符串转化为list
	 * 
	 * @param <T>
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public static <T>List<T> toJavaBeanList(String content,TypeReference<List<T>> typeReference)
	{
		try
		{
			return objectMapper.readValue(content,typeReference);
		} catch(JsonParseException e)
		{
			throw new RuntimeException("json to list error.",e);
		} catch(JsonMappingException e)
		{
			throw new RuntimeException("json to list error.",e);
		} catch(IOException e)
		{
			throw new RuntimeException("json to list error.",e);
		}
	}
	
	/**
	 * 
	 * 对象tostring
	 * 
	 * @param o
	 * @return
	 */
	public static String toString(Object o)
	{
		StringBuffer sb = new StringBuffer();
		sb.append("{").append(o.getClass().getSimpleName()).append(":").append(toJSON(o)).append("}");
		return sb.toString();
	}
	
	/**
	 * 			集合转String
	 * @param o
	 * @return
	 */
	public static String listToString(Object o)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(o.getClass().getSimpleName()).append(":").append(toJSON(o));
		return sb.toString();
	}
	
}
