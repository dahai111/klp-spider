package com.spider.klp.spring;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.spider.core.exception.SpiderException;
import com.spider.core.result.ResultCode;
import com.spider.core.result.ResultVO;

/**
 * 采集数据项目：统一异常处理
 * @author zhangqiuping
 *
 */
public class SpiderExceptionHandler implements HandlerExceptionResolver
{
	
	private static final Logger LOG = LoggerFactory.getLogger(SpiderExceptionHandler.class);
	
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse respose,Object object,Exception ex)
	{
		LOG.error(ex.getMessage(),ex);
		OutputStream out = null;
		ResultVO error = null;
		try
		{
			if(ex instanceof SpiderException)
			{
				SpiderException exception = (SpiderException)ex;
				if(StringUtils.isBlank(exception.getCode()))
					error = new ResultVO(ResultCode.FAID,exception.getMessage());
				else error = new ResultVO(exception.getCode(),exception.getMessage(),null);
			} else
			{
				error = new ResultVO(ResultCode.FAID,"系统异常",null);
			}
			respose.addHeader("Content-Type","text/html;charset=UTF-8");
			respose.setCharacterEncoding("UTF-8");
			out = respose.getOutputStream();
			out.write(error.toString().getBytes());
			out.flush();
		} catch(IOException e)
		{
			LOG.error(ex.getMessage(),ex);
		}
		finally
		{
			if(null != out)
			{
				try
				{
					out.close();
				} catch(IOException e)
				{
					LOG.error("统一异常处理，关闭输出流发生异常：" + e.getMessage(),e);
				}
			}
		}
		return new ModelAndView();
	}
	
}
