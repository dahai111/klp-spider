/*
 * 文件名：StockEverydayPriceFacade.java 版权：深圳融信信息咨询有限公司 修改人：zhangqiuping
 * 修改时间：@create_date 2018年3月23日 下午2:15:57 修改内容：新增
 */
package com.spider.klp.crawler.facade;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.eqz.elasticsearch.api.IElasticsearchIndexService;
import com.eqz.elasticsearch.api.impl.DefaultElasticsearchIndexService;
import com.spider.core.bo.StockPriceBO;
import com.spider.core.selenium.SeleniumFactory;
import com.spider.core.stock.model.StockPO;
import com.spider.core.stock.service.StockService;

/**
 * 
 * @author     zhangqiuping
 * @since      2.2.4
 * @create_date 2018年3月23日 下午2:15:57
 */
@Service
public class StockEverydayPriceFacade
{
	
	/**
	 * 调测日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(StockEverydayPriceFacade.class);
	
	/**时间模板:YYYYMMdd*/
	private static final SimpleDateFormat format = new SimpleDateFormat("YYYYMMdd");
	
	@Autowired
	private StockService stockService;
	
	private IElasticsearchIndexService elasticsearchIndexService = new DefaultElasticsearchIndexService();
	
	/**抓取股票每日信息url模板*/
	private static final String format_url = "http://quote.eastmoney.com/%s.html";
	
	/**
	 * 保存股票列表
	 * @param datas
	 */
	public void saveStocks(List<String[]> datas)
	{
		if(LOG.isDebugEnabled())
			LOG.debug("StockEverydayPriceFacade.saveStocks-->" + JSONObject.toJSONString(datas));
		List<StockPO> stocks = stockService.toStocks(datas);
		stockService.save(stocks);
	}
	
	/**
	 * 获取全部股票
	 * @return
	 * @author zhangqiuping
	 */
	public List<StockPO> getAllStock()
	{
		return stockService.selectAll();
	}
	
	public void spiderStockEverydayInfo()
	{
		WebDriver driver = SeleniumFactory.createChromeWebDriver();
		List<StockPO> stocks = getAllStock();
		for(StockPO stock:stocks)
		{
			System.out.println("股票代码-->" + stock.getCode());
			String url = String.format(format_url,stock.getBourse().toLowerCase() + stock.getCode());
			doSpiderStockEverydayInfo(url,stock,driver);
		}
		
	}
	
	public void doSpiderStockEverydayInfo(String url,StockPO stock,WebDriver driver)
	{
		try
		{
			spiderStockEverydayInfo(url,stock,driver);
		} catch(Exception e)
		{
			LOG.error(e.getMessage(),e);
			SeleniumFactory.close(driver);
			driver = SeleniumFactory.createFirefoxWebDriver();
		}
	}
	
	public void spiderStockEverydayInfo(String url,StockPO stock,WebDriver driver)
	{
		driver.get(url);
		String price = driver.findElement(By.id("price9")).getText();
		if("停牌".equals(price) || ("终止上市".equals(price) || "暂停上市".equals(price)))
		{
			System.out.println(stock.getCode() + "-->" + price);
			stockService.deleteStock(stock.getCode());
			return;
		}
		String start = driver.findElement(By.id("gt1")).getText();
		String max = driver.findElement(By.id("gt2")).getText();
		String min = driver.findElement(By.id("gt9")).getText();
		String turnoverRatio = driver.findElement(By.id("gt4")).getText();
		String quantityRatio = driver.findElement(By.id("gt11")).getText();
		String dealCount = driver.findElement(By.id("gt5")).getText();
		String dealMoney = driver.findElement(By.id("gt12")).getText();
		String sy = driver.findElement(By.id("gt6")).getText();
		String sj = driver.findElement(By.id("gt13")).getText();
		String capitalization = driver.findElement(By.id("gt7")).getText();
		String circulateCapitalization = driver.findElement(By.id("gt14")).getText();
		WebElement webElement = driver.findElement(By.id("rgt3"));
		String range = webElement.getText();
		String isRise = webElement.getAttribute("class");
		
		WebDriverWait wait = new WebDriverWait(driver,10,100);// 显示等待加载元素10秒超时，每100毫秒检查一次
		
		String superIn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .cdlr span"))).getText();
		String superOut = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .cdlc span"))).getText();
		
		String largeIn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .ddlr span"))).getText();
		String largeOut = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .ddlc span"))).getText();
		
		String mediumIn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .zdlr span"))).getText();
		String mediumOut = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .zdlc span"))).getText();
		
		String smallIn = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .xdlr span"))).getText();
		String smallOut = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#zjlxcjfbt .xdlc span"))).getText();
		
		StockPriceBO stockPrice = new StockPriceBO(range,isRise,stock.getCode(),stock.getName(),price,start,max,min,quantityRatio,turnoverRatio,dealCount,dealMoney,sj,sy,capitalization,
		        circulateCapitalization,superIn,superOut,largeIn,largeOut,mediumIn,mediumOut,smallIn,smallOut);
		saveStockEverydayInfo(stockPrice);
	}
	
	public void saveStockEverydayInfo(StockPriceBO stockPrice)
	{
		System.out.println(stockPrice.getCode() + "--->" + JSONObject.toJSONString(stockPrice));
		try
		{
			String dateString = format.format(new Date());
			String id = stockPrice.getCode() + "_" + dateString;
			stockPrice.setDateString(dateString);
			elasticsearchIndexService.index("stock_everyday_info","everyday_price",id,JSONObject.toJSONString(stockPrice));
		} catch(UnknownHostException e)
		{
			LOG.error("Failed to saveStockEverydayInfo-->" + e.getMessage(),e);
		}
	}
	
	public StockService getStockService()
	{
		return stockService;
	}
	
	public void setStockService(StockService stockService)
	{
		this.stockService = stockService;
	}
	
	public IElasticsearchIndexService getElasticsearchIndexService()
	{
		return elasticsearchIndexService;
	}
	
	public void setElasticsearchIndexService(IElasticsearchIndexService elasticsearchIndexService)
	{
		this.elasticsearchIndexService = elasticsearchIndexService;
	}
	
}
