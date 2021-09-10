package com.apih5.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.asyncmsg.SendMsgAsyncTaskService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.AppMsgUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("sendMsgAsyncTaskService")
public class SendMsgAsyncTaskServiceImpl implements SendMsgAsyncTaskService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private UserService userService;
	@Autowired(required = true)
	private WeChatEnterpriseService weChatEnterpriseService;
	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * 异步消息
	 */
	@Async
	@Override
	public ResponseEntity sendMsg(HttpServletRequest request, String title, Map<String, String> flowVarMap,
			String realName, JSONArray jsonArrayUserkey) {
		if (flowVarMap != null && flowVarMap.size() > 0) {
			try {
				// 企业微信消息推送
				String msgUrl = flowVarMap.get("msgUrl");
				if (StrUtil.isNotEmpty(msgUrl)) {
					String accountId = flowVarMap.get("accountId");
					String msgUrlFlag = flowVarMap.get("msgUrlFlag");
					// 方案评审使用ygj_zhaozz,因待办太多，不想自己处理，所以不需要接受消息
					if(jsonArrayUserkey.toString().indexOf("ygj_zhaozz")<0) {
					    sendMsg(request, accountId, msgUrl, title, msgUrlFlag, jsonArrayUserkey);
					}
				}
			} catch (Exception e) {
				LoggerUtils.printDebugLogger(logger, "企业号：审批异步消息通知失败！");
			}

			String msg = realName + "于" + DateUtil.formatDateTime(new Date()) + "，提交一份审核,请您确认。";
			try {
				// APP消息推送
				String appKey = flowVarMap.get("appKey");
				if (StrUtil.isNotEmpty(appKey)) {
					try {
						String appSecret = flowVarMap.get("appSecret");
						AppMsgUtils.pushAppMessage(JSONUtil.toList(jsonArrayUserkey, String.class), msg, appKey,
								appSecret);
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
				LoggerUtils.printDebugLogger(logger, "app：审批异步消息通知失败！");
			}

			try {
				msg = "【" + title + "】，" + DateUtil.formatDateTime(new Date()) + "由" + realName + "处理。";
				// 消息触发发送给客户端浏览器
				JSONObject jsonObject = new JSONObject();
				jsonObject.set("responseMessage", msg);
				template.convertAndSend("/topic/getResponse", jsonObject);
			} catch (Exception e) {
				LoggerUtils.printDebugLogger(logger, "webSocketServer：审批异步消息通知失败！");
			}
		}

		return repEntity.ok("ok");
	}

	/**
	 * 微信消息发送
	 * 
	 * @param request
	 * @param msgUrl
	 * @param msg
	 * @param jsonArrayUser
	 */
	@Override
	public void weChatSendMsg(HttpServletRequest request, String accountId, String msgUrl, String msg, JSONArray jsonArrayUserkey) {
		try {
			String users = "";
			if (jsonArrayUserkey != null) {
				for (Iterator<Object> iterator = jsonArrayUserkey.iterator(); iterator.hasNext();) {
					String userId = userService.getSysUserByUserKey((String) iterator.next()).getUserId();
					if (StrUtil.isEmpty(users)) {
						users += userId;
					} else {
						users += "|" + userId;
					}
				}
				if (StrUtil.isNotEmpty(users)) {
					JSONObject sendResult = weChatEnterpriseService.sendMsgText(request, accountId, users, msg);
					LoggerUtils.printDebugLogger(logger, "微信发送消息=" + sendResult.toString());
				}
			}
		} catch (Exception e) {
//			LoggerUtils.printDebugLogger(logger, "微信发送消息失败="+e.getMessage());
		}
	}

	/**
	 * 微信消息发送
	 * 
	 * @param request
	 * @param msg 消息内容
	 * @param userIds 用户id(已竖线分割如，zhangsan|lisi|....)
	 */
	@Async
	@Override
	public JSONObject weChatSendMsg(HttpServletRequest request, String accountId, String msg, String userIds) {
		try {
			if (StrUtil.isNotEmpty(userIds)) {
				JSONObject sendResult = weChatEnterpriseService.sendMsgText(true, accountId, userIds, msg);
				LoggerUtils.printDebugLogger(logger, "微信发送消息=" + sendResult.toString());
				return sendResult;
			}
		} catch (Exception e) {
//			LoggerUtils.printDebugLogger(logger, "微信发送消息失败="+e.getMessage());
		}
		return new JSONObject("异常");
	}
	
	@Async
	@Override
	public JSONObject sendOtherApi(String token, JSONObject jsonObject) {
	    if(jsonObject.getStr("url") != null && jsonObject.getStr("url").indexOf("http://")>=0) {
	        String url = jsonObject.getStr("url");
	        HttpUtil.sendPostToken(url, jsonObject.toString(), token);
	    } else {
	        String url = Apih5Properties.getWebUrl() + jsonObject.getStr("url");
	        HttpUtil.sendPostToken(url, jsonObject.toString(), token);
	    }
		return null;
	}
	
	private void sendMsg(HttpServletRequest request, String accountId, String msgUrl, String msg, String msgUrlFlag, JSONArray jsonArrayUser) {
		try {
			String users = "";
			if (jsonArrayUser != null) {
				for (Iterator<Object> iterator = jsonArrayUser.iterator(); iterator.hasNext();) {
					String userId = userService.getSysUserByUserKey((String) iterator.next()).getUserId();
					if (StrUtil.isEmpty(users)) {
						users += userId;
					} else {
						users += "|" + userId;
					}
				}
				String msgHttp = "<a href=\"" + msgUrl + "\">" + "您有新的待办任务，请点击查看详情" + "</a>";
				if(!StrUtil.equals("1", msgUrlFlag)) {
				    if(msgUrl.indexOf("http://") < 0) {
				        msgHttp = "您有新的待办任务【" + msg + "】，请点击查看详情";
				    }
				} else {
				    msgHttp = "您有新的待办任务【" + msg + "】，" + "<a href=\"" + msgUrl + "\">" + "请点击查看详情" + "</a>";
				}
				if (StrUtil.isNotEmpty(users)) {
					JSONObject sendResult = weChatEnterpriseService.sendMsgText(request, accountId, users, msgHttp);
					LoggerUtils.printDebugLogger(logger, "微信发送消息=" + sendResult.toString());
				}
			}
		} catch (Exception e) {
//			LoggerUtils.printDebugLogger(logger, "微信发送消息失败="+e.getMessage());
		}
	}
}
