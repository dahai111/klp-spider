/*
 * 文件名：TestWatcher.java 版权：深圳融信信息咨询有限公司 修改人：zhangqiuping 修改时间：@create_date
 * 2018年2月27日 下午4:27:42 修改内容：新增
 */
package com.spider.test.zookeeper;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author     zhangqiuping
 * @since      2.2.4
 * @create_date 2018年2月27日 下午4:27:42
 */
public class TestWatcher implements Watcher
{
	
	/**
	 * 调测日志记录器。
	 */
	private static final Logger logger = LoggerFactory.getLogger(TestWatcher.class);
	
	@Override
	public void process(WatchedEvent event)
	{
		logger.info("进入process()方法...event = " + event);
		
		if(event == null)
		{
			return;
		}
		
		KeeperState keeperState = event.getState(); // 连接状态
		EventType eventType = event.getType(); // 事件类型
		String path = event.getPath(); // 受影响的path
		AtomicInteger seq = new AtomicInteger();
		String logPrefix = "【Watcher-" + seq.incrementAndGet() + "】";
		logger.info(String.format("%s收到Watcher通知...",logPrefix));
		logger.info(String.format("%s连接状态：%s",logPrefix,keeperState));
		logger.info(String.format("%s事件类型：%s",logPrefix,eventType));
		logger.info(String.format("%s受影响的path：%s",logPrefix,path));
		System.err.println(event.getPath());
	}
}
