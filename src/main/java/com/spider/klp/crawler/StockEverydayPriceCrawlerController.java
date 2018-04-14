/*
 * 文件名：StockEverydayPriceCrawler.java 版权：深圳融信信息咨询有限公司 修改人：zhangqiuping
 * 修改时间：@create_date 2018年3月22日 上午11:48:48 修改内容：新增
 */
package com.spider.klp.crawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.spider.core.base.BaseController;
import com.spider.core.excel.ReadExcelFilesUtil;
import com.spider.core.result.ResultVO;
import com.spider.core.util.ConllectionUtils;
import com.spider.klp.crawler.facade.StockEverydayPriceFacade;

/**
 * 
 * 股票每日价格爬虫控制器
 * @author     zhangqiuping
 * @since      2.2.4
 * @create_date 2018年3月22日 上午11:48:48
 */
@Controller
public class StockEverydayPriceCrawlerController extends BaseController
{
	
	/**
	 * 调测日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(StockEverydayPriceCrawlerController.class);
	
	/**每日股票价格门面类*/
	@Autowired
	private StockEverydayPriceFacade stockEverydayPriceFacade;
	
	/**
	 * 股票列表导入
	 * @return
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws OpenXML4JException 
	 * @throws IOException 
	 */
	@RequestMapping(value = "/stocks/import",method = RequestMethod.GET)
	@ResponseBody
	public ResultVO importStokList() throws IOException,OpenXML4JException,ParserConfigurationException,SAXException
	{
		if(LOG.isDebugEnabled())
			LOG.debug("StockEverydayPriceFacade.importStokList-->" + "/stocks/import");
		
		List<String[]> datas = ReadExcelFilesUtil.read("D:\\excel\\stocks.xlsx","stocks",2);
		if(ConllectionUtils.isEmpty(datas))
			return this.failed();
		
		stockEverydayPriceFacade.saveStocks(datas);
		return this.success();
	}
	
	/**
	 * 抓取股票每日信息
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws FailingHttpStatusCodeException 
	 */
	@RequestMapping("/everyday/info")
	@ResponseBody
	public ResultVO stockEverydayPrice() throws MalformedURLException,IOException
	{
		stockEverydayPriceFacade.spiderStockEverydayInfo();
		return this.success();
	}
	
	@RequestMapping("/everyday/info2")
	@ResponseBody
	public ResultVO stockEverydayPrice2() throws MalformedURLException,IOException
	{
		stockEverydayPriceFacade.spiderStockEverydayInfo();
		return this.success();
	}
	
}
