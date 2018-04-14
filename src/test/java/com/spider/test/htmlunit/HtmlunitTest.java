/*
 * 文件名：HtmlunitTest.java 版权：深圳融信信息咨询有限公司 修改人：zhangqiuping 修改时间：@create_date
 * 2018年3月21日 下午4:46:51 修改内容：新增
 */
package com.spider.test.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * 
 * 
 * @author     zhangqiuping
 * @since      2.2.4
 * @create_date 2018年3月21日 下午4:46:51
 */
public class HtmlunitTest
{
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FailingHttpStatusCodeException,MalformedURLException,IOException
	{
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(false);
		String url = "https://xueqiu.com/S/SH601318";
		HtmlPage page = webClient.getPage(url);
		// HtmlDivision zhankai =
		// (HtmlDivision)page.getByXPath("//a[@class='unfold']").get(0);
		
		DomNode node = page.querySelector(".profile-summary");
		
		System.out.println("selector-->" + node.asText());
		
		final HtmlDivision div = (HtmlDivision)page.getByXPath("//div[@class='stock-name']").get(0);
		System.out.println("页面文本:" + div.asText());
	}
}
