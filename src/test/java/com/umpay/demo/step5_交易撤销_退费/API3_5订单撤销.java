package com.umpay.demo.step5_交易撤销_退费;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.umpay.call.BaseAPI;
import com.umpay.demo.step0_准备工作.EnvConfig;
import com.umpay.util.AddSign;
import com.umpay.util.HttpUtilClient;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * description: 交易撤销测试类
 * author: zhanghuanqi
 * date: 2019/8/19
 * time: 下午4:24
 */
public class API3_5订单撤销 extends BaseAPI {
	public String queryUrl = EnvConfig.url + "pay/cancelOrder";
	
	@SuppressWarnings("unchecked")
	@Test
	public void cancel_商户撤销交易() throws Exception{
		TreeMap<String, Object> reqPay = new TreeMap<String, Object>();
		reqPay.put("acqSpId", EnvConfig.acqSpId);//代理商编号	10	M	代理商编号(联动平台分配)
		reqPay.put("acqMerId", acqMerId);//商户号	8	M	商户号(联动平台分配)
		reqPay.put("orderNo", orderNo);//商户订单号	64	M	商户的支付订单号
		reqPay.put("signature", "");

		//对请求报文做加签处理
		String reqpay = AddSign.addSign(reqPay);
		Map<String, Object> reqMap = JSONObject.toJavaObject(JSONObject.parseObject(reqpay), Map.class);
		try{
			//发送post请求
			String result = HttpUtilClient.doPostJson(queryUrl, new JSONObject(), reqMap);
			System.out.println("输出请求结果:"+result);

			//将响应报文转成map
			Map<String, Object> treeMap = JSON.parseObject(result, TreeMap.class);

			String respCode = (String) treeMap.get("respCode");
			if ("00".equals(respCode)) {
				org.junit.Assert.assertTrue("订单撤销成功", true);
			}else{
				String respMsg = (String) treeMap.get("respMsg");
				org.junit.Assert.assertTrue("订单撤销失败：" + respMsg, false);
			}
		}catch (Exception e) {
			org.junit.Assert.assertTrue("订单撤销异常", false);
		}
	}
	
}
