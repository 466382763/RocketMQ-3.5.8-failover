/**
 * 
 */
package com.alibaba.rocketmq.example.constant;

/**
 * create by liudaomu on 2016年11月9日
 */
public class MQConfig {
	static String proGroup = "p_test_tran";
	public static String conGroup = "c_test_tran";
	static String namesrvAddr = "192.168.38.130:9876";
	static String topic = "cash";
	static String tag = "123123";
	public static String getProGroup() {
		return proGroup;
	}
	public static void setProGroup(String proGroup) {
		MQConfig.proGroup = proGroup;
	}
	public static String getConGroup() {
		return conGroup;
	}
	public static void setConGroup(String conGroup) {
		MQConfig.conGroup = conGroup;
	}
	public static String getNamesrvAddr() {
		return namesrvAddr;
	}
	public static void setNamesrvAddr(String namesrvAddr) {
		MQConfig.namesrvAddr = namesrvAddr;
	}
	public static String getTopic() {
		return topic;
	}
	public static void setTopic(String topic) {
		MQConfig.topic = topic;
	}
	public static String getTag() {
		return tag;
	}
	public static void setTag(String tag) {
		MQConfig.tag = tag;
	}
	
	
}
