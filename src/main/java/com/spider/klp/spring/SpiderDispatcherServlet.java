package com.spider.klp.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;

import com.spider.core.util.ApplicationContextUtil;

/**
 * 采集信息前段控制器
 * @author zhangqiuping
 *
 */
public class SpiderDispatcherServlet extends DispatcherServlet
{
	
	// private static final Logger LOG =
	// LoggerFactory.getLogger(CustomerDispatcherServlet.class);
	
	private static final long serialVersionUID = 3227151640270724730L;
	
	/**
	 * 重写初始化ApplicationContext方法
	 */
	@Override
	protected WebApplicationContext createWebApplicationContext(ApplicationContext parent)
	{
		WebApplicationContext webApplicationContext = super.createWebApplicationContext(parent);
		logger.info(("CollectionInfoDispatcherServlet.WebApplicationContext 初始化spring web容器。。。。。。"));
		ApplicationContextUtil.set(webApplicationContext);// 将WebApplicationContext缓存起来
		
		// 设置selenium驱动
		System.setProperty("webdriver.gecko.driver","E:\\eclipse-yixin-ii\\workspace\\klp-spider\\src\\main\\resources\\geckodriver.exe");
		System.setProperty("webdriver.chrome.driver","E:\\eclipse-yixin-ii\\workspace\\klp-spider\\src\\main\\resources\\chromedriver.exe");
		return webApplicationContext;
	}
	
	@Override
	protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception
	{
		return super.getHandler(request);
	}
	
	/**
	 *加载系统缓存
	 */
	public static void loadSystemConfig()
	{
		batcheRefresh();
	}
	
	/**批量刷新*/
	private static void batcheRefresh()
	{
		// int start = 0;// 批量刷新从0开始
		// int size = 100;// 默认定义100批量
		// 查询一批量
		// ISystemCacheService service =
		// ApplicationContextUtil.get().getBean("systemCacheService",ISystemCacheService.class);
		// List<SystemCachePO> datas = service.getPage(start,size);
		// while(!CollectionUtils.isEmpty(datas))
		// {
		// refresh(datas);// 刷新缓存
		// start += 100;
		// LOG.info("系统缓存位置：" + start);
		// datas = service.getPage(start,size);
		// if(CollectionUtils.isEmpty(datas))
		// {
		// return;
		// }
		// }
	}
	
	/**刷新缓存*/
	// private static void refresh(List<SystemCachePO> datas)
	// {
	// AbstractRedisClient redisClient =
	// ApplicationContextUtil.get().getBean(AbstractRedisClient.class);
	// for(SystemCachePO sc:datas)
	// {
	// // 如果key字段为空则之间返回
	// if(StringUtils.isBlank(sc.getKey()))
	// {
	// continue;
	// }
	// if(LOG.isDebugEnabled())
	// LOG.debug("存入缓存，或者更新缓存：" + sc.getField() + "-->" + sc);
	// redisClient.hset(sc.getKey(),sc.getField(),sc.getValue());
	// }
	// }
}
