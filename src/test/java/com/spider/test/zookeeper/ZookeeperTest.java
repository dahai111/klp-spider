/*
 * 文件名：ZookeeperTest.java 版权：深圳融信信息咨询有限公司 修改人：zhangqiuping 修改时间：@create_date
 * 2018年2月27日 下午3:36:08 修改内容：新增
 */
package com.spider.test.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * TODO 添加类的一句话简单描述。
 * <p>
 * TODO 详细描述
 * 
 * 
 * @author     zhangqiuping
 * @since      2.2.4
 * @create_date 2018年2月27日 下午3:36:08
 */
public class ZookeeperTest
{
	
	public static void main(String[] args) throws IOException,InterruptedException,KeeperException
	{
		ZooKeeper zk = new ZooKeeper("192.168.9.118:2181,192.168.9.120:2181,192.168.9.121:2181",60000,new TestWatcher());
		
		// String result =ls
		// zk.create("/test/test5","test3".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		//
		// System.out.println(result);
		zk.getChildren("/logger",new TestWatcher());
		zk.create("/logger/logger_1","logger_1".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
		
	}
}
