package com.apih5.service.impl;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.ComConst;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechat.entity.json.response.AMQrcodeResp;
import com.apih5.framework.api.wechat.entity.json.response.MMSendMsgResp;
import com.apih5.framework.api.wechat.entity.json.response.TMMaterialResp;
import com.apih5.framework.api.wechat.entity.json.response.TokenResp;
import com.apih5.framework.api.wechat.entity.json.response.UMUserInfoResp;
import com.apih5.framework.api.wechat.entity.xml.response.TextMsgResp;
import com.apih5.framework.api.wechat.service.WeChatInfoService;
import com.apih5.framework.api.wechat.service.WeChatService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseDbService;
import com.apih5.framework.api.wechatutils.CastUtil;
import com.apih5.framework.api.wechatutils.DateUtil;
import com.apih5.framework.api.wechatutils.JsonUtil;
import com.apih5.framework.api.wechatutils.MapUtil;
import com.apih5.framework.api.wechatutils.StringUtil;
import com.apih5.framework.api.wechatutils.XmlUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.emoji.EmojiUtil;
import cn.hutool.json.JSONObject;

/**
 * 微信请求处理核心Service类（接受从客户端的请求）
 * 
 * @author oh
 */
@Service("weChatService")
public class WeChatServiceImpl implements WeChatService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
  	@ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;
    
	@Autowired(required = true)
	private WeChatInfoService weChatInfoService;
	@Autowired(required = true)
	private UserService userService;
	@Autowired
	private Apih5Properties apih5Properties;
	@Autowired
	private Environment env;
	@Autowired(required = true)
	private WeChatEnterpriseDbService weChatEnterpriseDbService;
	
	public static Map<String, String> repeatMap = Maps.newHashMap();

	/**
	 * 接收腾讯信息并响应结果
	 * 
	 * @param request
	 *            发送请求的reques
	 * @return String 响应腾讯Xml格式字符串内容
	 * @throws Exception
	 */
	@Override
	public String coreService(HttpServletRequest request, String accountId) throws Exception {
		String respMessage = null;
		Object respBean = null;
		// 请求POST参数转换成Map
		Map<String, String> requestMap = MapUtil.formatRequestToMap(request);

		String repeatKey = requestMap.get("FromUserName") + "event";
		// 微信消息重排：假如服务器无法保证在五秒内处理并回复，可以直接回复空串，微信服务器不会对此作任何处理，并且不会发起重试
		if(repeatMap.containsKey(repeatKey)) {
			long beginDate = Long.parseLong(repeatMap.get(repeatKey));
			long endDate = Long.parseLong(requestMap.get("CreateTime"));
			if(endDate - beginDate <= 15) {
				return "";
			} else {
				repeatMap.put(repeatKey, requestMap.get("CreateTime"));
			}
		} else {
			repeatMap.put(repeatKey, requestMap.get("CreateTime"));
		}

		JSONObject wechatEventConfig = null;
		if(!StrUtil.equals("无", publicConfig.getProperty("wechat.event.coreService", ""))) {
			wechatEventConfig = new JSONObject(publicConfig.getProperty("wechat.event.coreService", ""));
		}
		// 返回消息标识
		String isScanSubscribe = "0";

		// 消息类型
		String msgType = requestMap.get("MsgType");
		String eventType = requestMap.get(ComConst.REQ_MESSAGE_TYPE_EVENT_TYPE);

		// 客户端消息请求处理
		if (ComConst.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
			// logger.info("---客户端发送【文本消息】---");
			respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_TEXT, null, requestMap);
		} else if (ComConst.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {
			// logger.info("---客户端发送【图片消息】---");
			respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_IMAGE, null, requestMap);
		} else if (ComConst.REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {
			// logger.info("---客户端发送【语音消息】---");
			respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_VOICE, null, requestMap);
			// 判断是否包含语音识别结果列，包含则开启了语音识别功能。将进行业务处理
			if (requestMap.containsKey(ComConst.REQ_MESSAGE_TYPE_VOICE_REC)) {
				requestMap.get(ComConst.REQ_MESSAGE_TYPE_VOICE_REC);
			} else {
			}
		} else if (ComConst.REQ_MESSAGE_TYPE_VIDEO.equals(msgType)) {
			// logger.info("---客户端发送【视频消息】---");
			respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_VIDEO, null, requestMap);
		} else if (ComConst.REQ_MESSAGE_TYPE_SHORTVIDEO.equals(msgType)) {
			// logger.info("---客户端发送【小视频消息】---");
			respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_SHORTVIDEO, null, requestMap);
		} else if (ComConst.REQ_MESSAGE_TYPE_LOCATION.equals(msgType)) {
			// logger.info("---客户端发送【地理位置消息】---");
			respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_LOCATION, null, requestMap);
		} else if (ComConst.REQ_MESSAGE_TYPE_LINK.equals(msgType)) {
			// logger.info("---客户端发送【链接消息】---");
			respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_LINK, null, requestMap);
		} else if (ComConst.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
			// logger.info("---客户端发送【事件推送】---");
			// 事件类型
			// String eventType = requestMap.get(ComConst.REQ_MESSAGE_TYPE_EVENT_TYPE);
			// logger.info("---客户端发送【事件类型】Event：【" + eventType + "】---");
			if (ComConst.EVENT_TYPE_VIEW.equals(eventType)) {
				// 事件类型:VIEW
				String eventKey = requestMap.get(ComConst.REQ_MESSAGE_TYPE_EVENT_TYPE_KEY);
				// logger.info("---客户端发送【事件类型KEY】EventKey：【" + eventKey + "】---");
				respBean = null;
			} else if (ComConst.EVENT_TYPE_CLICK.equals(eventType)) {
				// 事件类型:CLICK
				String eventKey = requestMap.get(ComConst.REQ_MESSAGE_TYPE_EVENT_TYPE_KEY);
				// logger.info("---客户端发送【事件类型KEY】EventKey：【" + eventKey + "】---");
				respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_EVENT, ComConst.EVENT_TYPE_CLICK,
						requestMap);
			} else if (ComConst.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
				// 事件类型：subscribe(订阅)
				if(wechatEventConfig != null) {
					String apiUrl = Apih5Properties.getWebUrl() + wechatEventConfig.getStr(eventType.toLowerCase());
//					// 中交服务号
//					if (accountId.contains("zj_fwh_woa_zl_id")) {
//					respBean = wechatZjQualityExtendClass.subscribeExtendMethod(requestMap, accountId, eventType);
					JSONObject paramJSONObject = new JSONObject();
					paramJSONObject.put("requestMap", requestMap);
					paramJSONObject.put("accountId", accountId);
					paramJSONObject.put("eventType", eventType);
					respBean = HttpUtil.sendPostJson(apiUrl, paramJSONObject.toString());
//					} // zz关注公众号
//					else if (accountId.contains("zz_wechat_fwh_05")) {
//					}
				} else {
					// 根据腾讯接口返回的
					String openid = requestMap.get("FromUserName");
					// 获取accessToken
					Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
					String accessToken = accessTokenMap.get("accessToken");

					Map<String, String> getParamMap = Maps.newHashMap();
					getParamMap.put("access_token", accessToken);
					getParamMap.put("openid", openid);
					getParamMap.put("lang", "zh_CN");
					JSONObject jsObj = (JSONObject) this.coreServiceByUserManage(9, getParamMap, null);
					UMUserInfoResp resp = (UMUserInfoResp) JsonUtil.fromJSON(jsObj.toString(), UMUserInfoResp.class);
					// user
					SysUser sysUser = new SysUser();
					sysUser.setAccountId(accountId);
					sysUser.setUserId(requestMap.get("FromUserName"));
					sysUser.setRealName(EmojiUtil.toHtml(resp.getNickname()));
					sysUser.setGender(String.valueOf(resp.getSex()));
					sysUser.setImageUrl(resp.getHeadimgurl());
					sysUser.setOpenid(openid);
					sysUser.setUserPwd(apih5Properties.getDefaultPassword());
					sysUser.setUserType("1");
					sysUser.setUserStatus("1");
					// 如果验证码都已通过
					SysUser userExists = userService.checkUserIdExists(sysUser);
					// 如果注册账号已存在
					if (userExists != null) {
						sysUser.setUserKey(userExists.getUserKey());
						userService.updateUserCommon(userExists);
					} else {
						userService.addUser(sysUser);
					}
					
					respBean = new TextMsgResp();
					((TextMsgResp) respBean).setToUserName(requestMap.get("FromUserName"));
					((TextMsgResp) respBean).setFromUserName(requestMap.get("ToUserName"));
					((TextMsgResp) respBean).setMsgType(ComConst.REQ_MESSAGE_TYPE_TEXT);
					((TextMsgResp) respBean).setCreateTime(CastUtil.castInt(DateUtil.getCurrentDatetime()));
					((TextMsgResp) respBean).setContent("欢迎关注!");
				}
			} else if (ComConst.EVENT_TYPE_SCAN.equals(eventType)) {
				// 事件类型:扫二维码关注
				if(wechatEventConfig != null) {
					String apiUrl = wechatEventConfig.getStr(eventType.toLowerCase());
//					if (accountId.contains("zj_fwh_woa_zl_id")) {
//					respBean = wechatZjQualityExtendClass.scanExtendMethod(requestMap, accountId, eventType);
					JSONObject paramJSONObject = new JSONObject();
					paramJSONObject.put("requestMap", requestMap);
					paramJSONObject.put("accountId", accountId);
					paramJSONObject.put("eventType", eventType);
					respBean = HttpUtil.sendPostJson(apiUrl, paramJSONObject.toString());
//					}
//					// zz扫码非首次关注
//					else if (accountId.contains("zz_wechat_fwh_05")) {
//					} 
				} else {
					// respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_EVENT, ComConst.EVENT_TYPE_SCAN, requestMap);
				}
			} else if (ComConst.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
				// 事件类型:取消关注
				if(wechatEventConfig != null) {
					String apiUrl = wechatEventConfig.getStr(eventType.toLowerCase());
//					if (accountId.contains("zj_fwh_woa_zl_id")) {
//						wechatZjQualityExtendClass.unsubscribeExtendMethod(requestMap);
						JSONObject paramJSONObject = new JSONObject();
						paramJSONObject.put("requestMap", requestMap);
						HttpUtil.sendPostJson(apiUrl, paramJSONObject.toString());
//					} // zz取消关注
//					else if (accountId.contains("zz_wechat_fwh_05")) {
//					} 
				}
				else {
					// respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_EVENT,  ComConst.EVENT_TYPE_UNSUBSCRIBE, requestMap);
				}
			} else if (ComConst.EVENT_TYPE_LOCATION.equals(eventType)) {
				// 事件类型:上报地理位置
				respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_EVENT, ComConst.EVENT_TYPE_LOCATION, requestMap);
			} else if (ComConst.EVENT_TYPE_USER_PAY_CELL.equals(eventType)) {
				// 事件类型:买单事件推送
				respBean = weChatInfoService.getResponseMsg(ComConst.REQ_MESSAGE_TYPE_EVENT, ComConst.EVENT_TYPE_USER_PAY_CELL, requestMap);
			} else {
				// 设置默认返回值
				respBean = new TextMsgResp();
				((TextMsgResp) respBean).setToUserName(requestMap.get("FromUserName"));
				((TextMsgResp) respBean).setFromUserName(requestMap.get("ToUserName"));
				((TextMsgResp) respBean).setMsgType(requestMap.get("MsgType"));
				((TextMsgResp) respBean).setCreateTime(CastUtil.castInt(DateUtil.getCurrentDatetime()));
				((TextMsgResp) respBean).setContent("小微暂时无法识别您的内容，请谅解!");
			}
		}
//		// 返回消息区分
//		if (accountId.contains("zj_fwh_woa_zl_id")) {
//			 respBean = wechatZjQualityExtendClass.textMsgRespExtendMethod(requestMap, eventType, isScanSubscribe);
//		} else if (accountId.contains("zz_wechat_fwh_05")) {
//		}
		// 获取返回消息内容为空
		if(respBean == null) {
			return "";
		}
		respMessage = new String(XmlUtil.beanToXml(respBean).getBytes(), "utf-8");
		if(repeatMap != null) {
			repeatMap.remove(repeatKey);
		}
		return respMessage;
	}

	/**
	 * 获取TOKEN
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public String getToken(Map<String, String> paramMap) {
		TokenResp resultBean = null;
		String paramStr = "grant_type=client_credential&" + MapUtil.mapToString(paramMap);

		String token = HttpUtil.sendGet(ComConst.GET_TOKEN_URL, paramStr);
		if (StringUtil.isNotEmpty(token)) {
			resultBean = JsonUtil.fromJSON(token, TokenResp.class);
			if (resultBean.getErrcode() != 0) {
				// 第二次获取TOKEN
				token = HttpUtil.sendGet(ComConst.GET_TOKEN_URL, paramStr);
				resultBean = JsonUtil.fromJSON(token, TokenResp.class);
			}
		} else {
			token = HttpUtil.sendGet(ComConst.GET_TOKEN_URL, paramStr);
			resultBean = JsonUtil.fromJSON(token, TokenResp.class);
		}

		return resultBean.getAccess_token();
	}

	/**
	 * 消息管理-发送消息
	 *
	 * @param urlType
	 *            HTTP请求接口区分 <br>
	 *            1：客服服务-发消息<br>
	 *            2：客服服务-依据分组群发消息<br>
	 *            3：客服服务-依据OPENID群发消息<br>
	 *            4：发送模板消息 <br>
	 * @param getParamMap HTTP请求GET参数Map
	 * @param postParamBean HTTP请求POST参数实体Bean
	 * @param postAccountBean 指定某客服发送消息的客服Bean（如果需要以某个客服帐号来发消息）
	 * @return Object HTTP请求响应结果Bean
	 */
	public Object coreServiceBySendMsg(int urlType, Map<String, String> getParamMap, Object postParamBean,
			Object postAccountBean) {
		String resutlJson = null;
		String httpUrl = null;

		// 判断HTTP请求类型
		if (urlType == 1) {
			/*
			 * 判断是否制定发送客服，并添加制定客服账号到Json
			 */
			String postParmaJson = null;
			// POST参数存在
			if (postParamBean != null && postAccountBean != null) {
				// POST参数Bean转换成String
				String postParam = JsonUtil.toJSON(postParamBean);
				String postAccount = JsonUtil.toJSON(postAccountBean);
				// 合并POSTJson
				postParmaJson = JsonUtil.mergeJson(postParam, postAccount);
			} else if (postParamBean != null && postAccountBean == null) {
				postParmaJson = JsonUtil.toJSON(postParamBean);
			}

			// 1：客服服务-发消息
			httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_SENDMSG + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, postParmaJson);
			// logger.info("发消息log:" + resutlJson);
		} else if (urlType == 2) {
			// 2：客服服务-依据分组群发消息
			httpUrl = ComConst.POST_MSG_MANAGE_GROUP_SENDMSGALL + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 3) {
			// 3：客服服务-依据OPENID群发消息
			httpUrl = ComConst.POST_MSG_MANAGE_OPENDID_SENDMSGALL + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 4) {
			// 4：发送模板消息
			httpUrl = ComConst.POST_MSG_MANAGE_TIMPLATE_SENDMSG + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		}

		return JsonUtil.fromJSON(resutlJson, MMSendMsgResp.class);
	}

	/**
	 * 素材管理
	 *
	 * @param urlType
	 *            HTTP请求接口区分 <br>
	 *            1：新增临时素材<br>
	 *            2：获取临时素材<br>
	 *            3：新增永久素材-图文<br>
	 *            4：新增永久素材-图文中图片(获取图片URL)<br>
	 *            5：新增永久素材-其他-图片（image）、语音（voice）、视频（video）和缩略图（thumb）<br>
	 *            6：获取永久素材-图文 <br>
	 *            7：获取永久素材-其他-图片（image）、语音（voice）、视频（video）和缩略图（thumb） <br>
	 *            8：删除永久素材 <br>
	 *            9：修改永久图文素材<br>
	 *            10:获取素材总数<br>
	 *            11:获取素材列表-图文<br>
	 *            12:获取素材列表-其他<br>
	 *            13:上传卡券LOGO<br>
	 * @param getParamMap HTTP请求GET参数Map
	 * @param postParamBean HTTP请求POST参数实体Bean
	 * @param filePath 上传或下载文件路径（包含文件名）
	 * @return Object HTTP请求响应结果Bean
	 */
	public Object coreServiceByMaterialManage(int urlType, Map<String, String> getParamMap, Object postParamBean,
			String filePath) {
		String httpUrl = null;
		String resultJson = null;
		if (urlType == 1) {
			// 1：新增临时素材
			httpUrl = ComConst.POST_MATE_MANAGE_TEMPORARY_MATERIAL_ADD + "?" + MapUtil.mapToString(getParamMap);
			File file = new File(filePath);
			// 发送HTTP请求
			resultJson = HttpUtil.uploadMedia(httpUrl, file, getParamMap.get("access_token"), getParamMap.get("type"));
		} else if (urlType == 2) {
			// 2：获取临时素材
			httpUrl = ComConst.GET_MATE_MANAGE_TEMPORARY_MATERIAL_GET + "?" + MapUtil.mapToString(getParamMap);
			File file = new File(filePath);
			// 发送HTTP请求
			resultJson = HttpUtil.downloadMedia(httpUrl, null, file);
		} else if (urlType == 3) {
			// 3：新增永久素材-图文
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_NEWS_MATERIAL_ADD + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 4) {
			// 4：新增永久素材-图文中图片(获取图片URL)
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_ADD + "?" + MapUtil.mapToString(getParamMap);
			File file = new File(filePath);
			// 发送HTTP请求
			resultJson = HttpUtil.uploadMedia(httpUrl, file, getParamMap.get("access_token"), getParamMap.get("type"));
		} else if (urlType == 5) {
			// 5：新增永久素材-其他-图片（image）、语音（voice）、视频（video）和缩略图（thumb）
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_OTHER_ADD + "?" + MapUtil.mapToString(getParamMap);
			File file = new File(filePath);
			// 发送HTTP请求
			resultJson = HttpUtil.uploadMedia(httpUrl, file, getParamMap.get("access_token"), getParamMap.get("type"));
		} else if (urlType == 6) {
			// 6：获取永久素材-图文
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_GET + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 7) {
			// 7：获取永久素材-其他
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_GET + "?" + MapUtil.mapToString(getParamMap);
			File file = new File(filePath);
			// 发送HTTP请求
			resultJson = HttpUtil.downloadMedia(httpUrl, JsonUtil.toJSON(postParamBean), file);
		} else if (urlType == 8) {
			// 8：删除永久素材
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_DEL + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 9) {
			// 9：修改永久图文素材
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_UPD + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 10) {
			// 10:获取素材总数
			// 发送HTTP请求
			resultJson = HttpUtil.sendGet(ComConst.GET_MATE_MANAGE_PERPETUAL_MATERIAL_COUNT_GET,
					MapUtil.mapToString(getParamMap));
		} else if (urlType == 11 || urlType == 12) {
			// 11:获取素材列表-图文
			// 12:获取素材列表-其他
			httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_GETLIST + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 13) {
			// httpUrl =
		}
		return JsonUtil.fromJSON(resultJson, TMMaterialResp.class);
	}

	// /**
	// * 自定义菜单管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：自定义菜单创建接口<br>
	// * 2：自定义菜单查询接口（包含个性化菜单） <br>
	// * 3：自定义菜单删除接口 <br>
	// * 4：创建个性化菜单接口 <br>
	// * 5：删除个性化菜单接口 <br>
	// * 6：测试个性化菜单匹配结果接口
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @return Object HTTP请求响应结果Bean
	// */
	// @Override
	// public Object coreServiceByMeunManage(int urlType, Map<String, String>
	// getParamMap, Object postParamBean) {
	// String resutlJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：自定义菜单创建接口
	// httpUrl = ComConst.POST_MENU_MANAGE_DEFINED_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 2) {
	// // 2：自定义菜单查询接口（包含个性化菜单）
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_MENU_MANAGE_DEFINED_GET,
	// param);
	// } else if (urlType == 3) {
	// // 3：自定义菜单删除接口
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_MENU_MANAGE_DEFINED_DEL,
	// param);
	// } else if (urlType == 4) {
	// // 4.创建个性化菜单接口
	// httpUrl = ComConst.POST_MENU_MANAGE_INDIV_DEFINED_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 5) {
	// // 5.删除个性化菜单接口
	// httpUrl = ComConst.POST_MENU_MANAGE_INDIV_DEFINED_DEL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 6) {
	// // 6.测试个性化菜单匹配结果接口
	// httpUrl = ComConst.POST_MENU_MANAGE_INDIV_DEFINED_TEST + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else {
	// }
	//
	// return JsonUtil.fromJSON(resutlJson, DMMenuResp.class);
	// }
	//
	// /**
	// * 消息管理-客服管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：添加客服帐号接口<br>
	// * 2：修改客服帐号接口 <br>
	// * 3：删除客服帐号接口 <br>
	// * 4：设置客服帐号的头像接口<br>
	// * 5：获取所有客服账号接口 <br>
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @param filePath
	// * 上传文件路径（包含文件名称）
	// * @return Object HTTP请求响应结果Bean
	// */
	// @Override
	// public Object coreServiceByCustomrManage(int urlType, Map<String, String>
	// getParamMap, Object postParamBean,
	// String filePath) {
	// String resutlJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：添加客服帐号接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 2) {
	// // 2：修改客服帐号接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_UPD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 3) {
	// // 3：删除客服帐号接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_DEL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 4) {
	// // 4：设置客服帐号的头像 接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_SET_IMAGE + "?" +
	// MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.httpUpload(httpUrl, filePath);
	// } else if (urlType == 5) {
	// // 5：获取所有客服账号接口
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_MSG_MANAGE_CUSTOMER_GETALL,
	// param);
	// }
	//
	// return JsonUtil.fromJSON(resutlJson, CMCustomrResp.class);
	// }
	//
	// /**
	// * 消息管理-新客服管理
	// *
	// * @param urlType HTTP请求接口区分 <br>
	// * 25：获取未接入会话列表 <br>
	// * @param getParamMap HTTP请求GET参数Map
	// * @param postParamBean HTTP请求POST参数实体Bean
	// * @param filePath 上传文件路径（包含文件名称）
	// * @return Object HTTP请求响应结果Bean
	// */
	// @Override
	// public JSONObject coreServiceByNewCustomrManage(int urlType, Map<String,
	// String> getParamMap, Object postParamBean,
	// String filePath) {
	// String resultJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 11) {
	// // 11：创建会话
	// resultJson = httpRequest(ComConst.POST_MSG_NEW_CUSTOMER_GETKFLIST,
	// getParamMap, postParamBean);
	// }
	// else if (urlType == 21) {
	// // 21：创建会话
	// resultJson = httpRequest(ComConst.POST_MSG_NEW_CUSTOMER_CREATE,
	// getParamMap, postParamBean);
	// }
	// else if (urlType == 24) {
	// // 24：新客服接口-获取客服会话列表
	// resultJson = httpRequest(ComConst.GET_MSG_NEW_CUSTOMER_GETSESSIONLIST,
	// getParamMap, postParamBean);
	// }
	// else if (urlType == 25) {
	// // 25：获取所有客服账号接口
	// resultJson = httpRequest(ComConst.GET_MSG_NEW_CUSTOMER_GETWAITCASE,
	// getParamMap, postParamBean);
	// }
	//
	// return JSONUtil.parseObj(resultJson);//JSONObject.fromObject(resultJson);
	// }
	//

	/**
	 * 用户管理-用户管理
	 * 
	 * @param urlType
	 *            HTTP请求接口区分 <br>
	 *            1：创建分组接口<br>
	 *            2：查询所有分组接口 <br>
	 *            3：查询用户所在分组接口 <br>
	 *            4：修改分组名接口<br>
	 *            5：移动用户分组接口 <br>
	 *            6：批量移动用户分组接口 <br>
	 *            7：删除分组接口 <br>
	 *            8：设置备注名 <br>
	 *            9: 获取用户基本信息<br>
	 *            10: 批量获取用户基本信息<br>
	 *            11:获取用户列表<br>
	 * @param getParamMap HTTP请求GET参数Map
	 * @param postParamBean HTTP请求POST参数实体Bean
	 * @return Object HTTP请求响应结果Bean
	 */
	public JSONObject coreServiceByUserManage(int urlType, Map<String, String> getParamMap, Object postParamBean) {
		String resutlJson = null;
		String httpUrl = null;

		// 判断HTTP请求类型
		if (urlType == 1) {
			// 1：创建分组接口
			httpUrl = ComConst.POST_USER_MANAGE_GROUP_ADD + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 2) {
			// 2：查询所有分组接口
			String param = MapUtil.mapToString(getParamMap);
			resutlJson = HttpUtil.sendGet(ComConst.GET_USER_MANAGE_GROUPALL_GET, param);
		} else if (urlType == 3) {
			// 3：查询用户所在分组接口
			httpUrl = ComConst.POST_USER_MANAGE_USERGROUP_GET + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 4) {
			// 4：修改分组名接口
			httpUrl = ComConst.POST_USER_MANAGE_GROUP_UPD + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 5) {
			// 5：移动用户分组接口
			httpUrl = ComConst.POST_USER_MANAGE_GROUP_MOVE + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 6) {
			// 6：批量移动用户分组接口
			httpUrl = ComConst.POST_USER_MANAGE_GROUP_MOVELIST + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 7) {
			// 7：删除分组接口
			httpUrl = ComConst.POST_USER_MANAGE_GROUP_DEL + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 8) {
			// 8：设置备注名
			httpUrl = ComConst.POST_USER_REMARK_UPD + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 9) {
			// 9:获取用户基本信息
			String param = MapUtil.mapToString(getParamMap);
			resutlJson = HttpUtil.sendGet(ComConst.GET_USER_INFO_GET, param);
		} else if (urlType == 10) {
			// 10: 批量获取用户基本信息
			httpUrl = ComConst.POST_USER_INFO_GETLIST + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
		} else if (urlType == 11) {
			// 11: 批量获取用户基本信息
			String param = MapUtil.mapToString(getParamMap);
			resutlJson = HttpUtil.sendGet(ComConst.GET_USER_GETLIST, param);
		} 
		else if (urlType == 12) {
			// 12: 获取用户基本信息（网页授权）
			String param = MapUtil.mapToString(getParamMap);
			resutlJson = HttpUtil.sendGet(ComConst.GET_USER_INFO_GET_AUTHENTIZATION_WEB, param);
		}

		// Object resutlBean = null;
		// if (urlType > 7) {
		// resutlBean = JsonUtil.fromJSON(resutlJson, UMUserInfoResp.class);
		// } else {
		// resutlBean = JsonUtil.fromJSON(resutlJson, UMUserGroupResp.class);
		// }
		//
		// return resutlBean;
		return new JSONObject(resutlJson);// JSONObject.fromObject(resutlJson);
	}

	/**
	 * 账户管理-生成带参数的二维码
	 *
	 * @param urlType
	 *            HTTP请求接口区分 <br>
	 *            1：创建二维码ticket接口<br>
	 *            2：通过ticket换取二维码接口 <br>
	 *            3：长链接转短链接接口<br>
	 * @param urlParamMap HTTP请求GET参数Map（url地址栏参数）
	 * @param postParamBean HTTP请求POST参数实体Bean
	 * @param downFile 创建的二维码文件名称（包含路径）
	 * @return Object HTTP请求响应结果Bean
	 */
	public Object coreServiceByAccountManage(int urlType, Map<String, String> getParamMap, Object postParamBean,
			String downFile) {
		Object result = null;
		String resutlJson = null;
		String httpUrl = null;

		// 判断HTTP请求类型
		if (urlType == 1) {
			// 1：创建二维码ticket接口
			httpUrl = ComConst.POST_ACCOUNT_MANAGE_TICKET_GET + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
			result = JsonUtil.fromJSON(resutlJson, AMQrcodeResp.class);
		} else if (urlType == 2) {
			// 2：通过ticket换取二维码接口
			String param = MapUtil.mapToString(getParamMap);
			httpUrl = ComConst.GET_ACCOUNT_MANAGE_QRCODE_GET + "?" + param;
			AMQrcodeResp aMTicketResp = new AMQrcodeResp();
			if (HttpUtil.httpDownload(httpUrl, downFile)) {
				aMTicketResp.setErrcode(0);
				aMTicketResp.setErrmsg("ok");
				result = aMTicketResp;
			} else {
				aMTicketResp.setErrcode(9);
				aMTicketResp.setErrmsg("download failure!");
				result = aMTicketResp;
			}
		} else if (urlType == 3) {
			// 3：长链接转短链接接口
			httpUrl = ComConst.POST_ACCOUNT_MANAGE_LONG2SHORT + "?" + MapUtil.mapToString(getParamMap);
			// 发送HTTP请求
			resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
			result = JsonUtil.fromJSON(resutlJson, AMQrcodeResp.class);
		}

		return result;
	}

	// /**
	// * 卡卷管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：创建卡卷<br>
	// * 2: 设置快速买单/设置微信买单接口<br>
	// * 3：设置自动核销<br>
	// * 4：Code解码接口（线上）<br>
	// * 5：查询CODE状态<br>
	// * 6：核销Code接口<br>
	// * 7：获取用户已领取卡券接口<br>
	// * 8：查看卡券详情<br>
	// * 9：批量查询卡券列表<br>
	// * 10：更改卡券信息接口<br>
	// * 11：修改库存接口<br>
	// * 12：更改Code接口<br>
	// * 13：删除卡券接口<br>
	// * 14：设置卡券失效接口<br>
	// * 15：拉取卡券概况数据接口<br>
	// * 16：获取免费券数据接口<br>
	// * 17：拉取会员卡数据接口<br>
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @return String HTTP请求响应结果String
	// */
	// public JSONObject coreServiceByCardVolumeManage(int urlType, Map<String,
	// String> getParamMap,
	// Object postParamBean) {
	// String resultJson = null;
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：创建卡卷
	// resultJson = httpRequest(ComConst.POST_CREATE_CARD, getParamMap,
	// postParamBean);
	// } else if (urlType == 2) {
	// // 2: 设置快速买单/设置微信买单接口
	// resultJson = httpRequest(ComConst.POST_PAYCELL, getParamMap,
	// postParamBean);
	// } else if (urlType == 3) {
	// // 3：设置自动核销
	// resultJson = httpRequest(ComConst.POST_SELFCONSUMECELL, getParamMap,
	// postParamBean);
	// } else if (urlType == 4) {
	// // 4：Code解码接口（线上）
	// resultJson = httpRequest(ComConst.POST_CODE_DECYPT, getParamMap,
	// postParamBean);
	// } else if (urlType == 5) {
	// // 5：查询CODE状态
	// resultJson = httpRequest(ComConst.POST_CODE_GET_STATUS, getParamMap,
	// postParamBean);
	// } else if (urlType == 6) {
	// // 6：核销Code接口
	// resultJson = httpRequest(ComConst.POST_CODE_VERIFICATION, getParamMap,
	// postParamBean);
	// } else if (urlType == 7) {
	// // 7：获取用户已领取卡券接口
	// resultJson = httpRequest(ComConst.POST_GET_USER_CARD_LIST, getParamMap,
	// postParamBean);
	// } else if (urlType == 8) {
	// // 8：查看卡券详情
	// resultJson = httpRequest(ComConst.POST_GET_CARD_INFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 9) {
	// // 9：批量查询卡券列表
	// resultJson = httpRequest(ComConst.POST_GET_CARDID_LIST, getParamMap,
	// postParamBean);
	// } else if (urlType == 10) {
	// // 10：更改卡券信息接口
	// resultJson = httpRequest(ComConst.POST_UPDATE_CARD_INFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 11) {
	// // 11：修改库存接口
	// resultJson = httpRequest(ComConst.POST_CARD_MODIFY_STOCK, getParamMap,
	// postParamBean);
	// } else if (urlType == 12) {
	// // 12：更改Code接口
	// resultJson = httpRequest(ComConst.POST_CARD_CODE_CHANGE, getParamMap,
	// postParamBean);
	// } else if (urlType == 13) {
	// // 13：删除卡券接口
	// resultJson = httpRequest(ComConst.POST_CART_DELETE, getParamMap,
	// postParamBean);
	// } else if (urlType == 14) {
	// // 14：设置卡券失效接口
	// resultJson = httpRequest(ComConst.POST_CODE_UNAVAILABLE, getParamMap,
	// postParamBean);
	// } else if (urlType == 15) {
	// // 15：拉取卡券概况数据接口
	// resultJson = httpRequest(ComConst.POST_GET_CARD_BIZUININFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 16) {
	// // 16：获取免费券数据接口
	// resultJson = httpRequest(ComConst.POST_GET_FREE_CARD_INFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 17) {
	// // 17：拉取会员卡数据接口
	// resultJson = httpRequest(ComConst.POST_GET_MEMBER_CARDINFO, getParamMap,
	// postParamBean);
	// }
	//
	// return JSONUtil.parseObj(resultJson);//JSONObject.fromObject(resultJson);
	// }
	//
	// /**
	// * 红包发放
	// *
	// * @param mchId 商家ID
	// * @param apiclientCert 证书地址
	// * @param postParamBean SSL请求POST参数实体Bean
	// * @return Map SSL请求响应结果
	// */
	// public Map<String, String> coreServiceByRedPack(String mchId,
	// String apiclientCert, Object postParamBean) {
	// Map<String, String> responseMap = null;
	// try {
	// // 拼装xml数据给ssl
	// String response = HttpUtil.sendSSL(ComConst.POST_RED_PACK_GET,
	// postParamBean.toString(), mchId, apiclientCert);
	// if(StringUtil.isNotEmpty(response)) {
	// logger.info("红包发放+coreServiceByRedPack==" +response);
	// responseMap = XmlUtil.parseXml(response);
	// }
	// else{
	// logger.info("红包发放异常+coreServiceByRedPack==" + response);
	// }
	//
	// } catch (Exception e){
	// logger.error("红包发放异常+coreServiceByRedPack=Exception=" + e.getMessage());
	// }
	// return responseMap;
	// }
	//
	// /**
	// * 发送HTTP请求(GET,POST)
	// *
	// * @param urlKey
	// * HTTP请求URL的配置文件中Key
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @return 请求就过 json格式字符串
	// */
	// private String httpRequest(String httpUrl, Map<String, String>
	// getParamMap, Object postParamBean) {
	// // 设置URL的GET参数
	// httpUrl = replaceUrlParma(httpUrl, getParamMap);
	//
	// // 发送HTTP请求
	// if (postParamBean == null) {
	// // GET
	// return HttpUtil.sendGet(httpUrl);
	// } else {
	// // POST
	// return HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// }
	// }
	//
	// /**
	// * 将Http请求get参数拼接到URL中
	// *
	// * @param url
	// * HTTP请求URL
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @return 带有get参数的URL
	// */
	// private String replaceUrlParma(String url, Map<String, String>
	// getParamMap) {
	// String replaceUrl = url;
	// if (getParamMap != null) {
	// for (String key : getParamMap.keySet()) {
	// if (url.indexOf(key) != -1) {
	// replaceUrl = replaceUrl.replace(key.toUpperCase(), getParamMap.get(key));
	// }
	// }
	// }
	//
	// return replaceUrl;
	// }

	//
	// /**
	// * 消息管理-发送消息
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：客服服务-发消息<br>
	// * 2：客服服务-依据分组群发消息<br>
	// * 3：客服服务-依据OPENID群发消息<br>
	// * 4：发送模板消息 <br>
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @param postAccountBean 指定某客服发送消息的客服Bean（如果需要以某个客服帐号来发消息）
	// * @return Object HTTP请求响应结果Bean
	// */
	// public Object coreServiceBySendMsg(int urlType, Map<String, String>
	// getParamMap, Object postParamBean,
	// Object postAccountBean) {
	// String resutlJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// /*
	// * 判断是否制定发送客服，并添加制定客服账号到Json
	// */
	// String postParmaJson = null;
	// // POST参数存在
	// if (postParamBean != null && postAccountBean != null) {
	// // POST参数Bean转换成String
	// String postParam = JsonUtil.toJSON(postParamBean);
	// String postAccount = JsonUtil.toJSON(postAccountBean);
	// // 合并POSTJson
	// postParmaJson = JsonUtil.mergeJson(postParam, postAccount);
	// } else if (postParamBean != null && postAccountBean == null) {
	// postParmaJson = JsonUtil.toJSON(postParamBean);
	// }
	//
	// // 1：客服服务-发消息
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_SENDMSG + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, postParmaJson);
	// logger.info("发消息log:" + resutlJson);
	// } else if (urlType == 2) {
	// // 2：客服服务-依据分组群发消息
	// httpUrl = ComConst.POST_MSG_MANAGE_GROUP_SENDMSGALL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 3) {
	// // 3：客服服务-依据OPENID群发消息
	// httpUrl = ComConst.POST_MSG_MANAGE_OPENDID_SENDMSGALL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 4) {
	// // 4：发送模板消息
	// httpUrl = ComConst.POST_MSG_MANAGE_TIMPLATE_SENDMSG + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// }
	//
	// return JsonUtil.fromJSON(resutlJson, MMSendMsgResp.class);
	// }
	//
	// /**
	// * 素材管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：新增临时素材<br>
	// * 2：获取临时素材<br>
	// * 3：新增永久素材-图文<br>
	// * 4：新增永久素材-图文中图片(获取图片URL)<br>
	// * 5：新增永久素材-其他-图片（image）、语音（voice）、视频（video）和缩略图（thumb）<br>
	// * 6：获取永久素材-图文 <br>
	// * 7：获取永久素材-其他-图片（image）、语音（voice）、视频（video）和缩略图（thumb） <br>
	// * 8：删除永久素材 <br>
	// * 9：修改永久图文素材<br>
	// * 10:获取素材总数<br>
	// * 11:获取素材列表-图文<br>
	// * 12:获取素材列表-其他<br>
	// * 13:上传卡券LOGO<br>
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @param filePath
	// * 上传或下载文件路径（包含文件名）
	// * @return Object HTTP请求响应结果Bean
	// */
	// public Object coreServiceByMaterialManage(int urlType, Map<String,
	// String> getParamMap, Object postParamBean,
	// String filePath) {
	// String httpUrl = null;
	// String resultJson = null;
	// if (urlType == 1) {
	// // 1：新增临时素材
	// httpUrl = ComConst.POST_MATE_MANAGE_TEMPORARY_MATERIAL_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// File file = new File(filePath);
	// // 发送HTTP请求
	// resultJson = HttpUtil.uploadMedia(httpUrl, file,
	// getParamMap.get("access_token"), getParamMap.get("type"));
	// } else if (urlType == 2) {
	// // 2：获取临时素材
	// httpUrl = ComConst.GET_MATE_MANAGE_TEMPORARY_MATERIAL_GET + "?" +
	// MapUtil.mapToString(getParamMap);
	// File file = new File(filePath);
	// // 发送HTTP请求
	// resultJson = HttpUtil.downloadMedia(httpUrl, null, file);
	// } else if (urlType == 3) {
	// // 3：新增永久素材-图文
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_NEWS_MATERIAL_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 4) {
	// // 4：新增永久素材-图文中图片(获取图片URL)
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// File file = new File(filePath);
	// // 发送HTTP请求
	// resultJson = HttpUtil.uploadMedia(httpUrl, file,
	// getParamMap.get("access_token"), getParamMap.get("type"));
	// } else if (urlType == 5) {
	// // 5：新增永久素材-其他-图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_OTHER_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// File file = new File(filePath);
	// // 发送HTTP请求
	// resultJson = HttpUtil.uploadMedia(httpUrl, file,
	// getParamMap.get("access_token"), getParamMap.get("type"));
	// } else if (urlType == 6) {
	// // 6：获取永久素材-图文
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_GET + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 7) {
	// // 7：获取永久素材-其他
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_GET + "?" +
	// MapUtil.mapToString(getParamMap);
	// File file = new File(filePath);
	// // 发送HTTP请求
	// resultJson = HttpUtil.downloadMedia(httpUrl,
	// JsonUtil.toJSON(postParamBean), file);
	// } else if (urlType == 8) {
	// // 8：删除永久素材
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_DEL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 9) {
	// // 9：修改永久图文素材
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_UPD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 10) {
	// // 10:获取素材总数
	// // 发送HTTP请求
	// resultJson =
	// HttpUtil.sendGet(ComConst.GET_MATE_MANAGE_PERPETUAL_MATERIAL_COUNT_GET,
	// MapUtil.mapToString(getParamMap));
	// } else if (urlType == 11 || urlType == 12) {
	// // 11:获取素材列表-图文
	// // 12:获取素材列表-其他
	// httpUrl = ComConst.POST_MATE_MANAGE_PERPETUAL_MATERIAL_GETLIST + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resultJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 13) {
	// // httpUrl =
	// }
	// return JsonUtil.fromJSON(resultJson, TMMaterialResp.class);
	// }
	//
	// /**
	// * 自定义菜单管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：自定义菜单创建接口<br>
	// * 2：自定义菜单查询接口（包含个性化菜单） <br>
	// * 3：自定义菜单删除接口 <br>
	// * 4：创建个性化菜单接口 <br>
	// * 5：删除个性化菜单接口 <br>
	// * 6：测试个性化菜单匹配结果接口
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @return Object HTTP请求响应结果Bean
	// */
	// @Override
	// public Object coreServiceByMeunManage(int urlType, Map<String, String>
	// getParamMap, Object postParamBean) {
	// String resutlJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：自定义菜单创建接口
	// httpUrl = ComConst.POST_MENU_MANAGE_DEFINED_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 2) {
	// // 2：自定义菜单查询接口（包含个性化菜单）
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_MENU_MANAGE_DEFINED_GET,
	// param);
	// } else if (urlType == 3) {
	// // 3：自定义菜单删除接口
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_MENU_MANAGE_DEFINED_DEL,
	// param);
	// } else if (urlType == 4) {
	// // 4.创建个性化菜单接口
	// httpUrl = ComConst.POST_MENU_MANAGE_INDIV_DEFINED_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 5) {
	// // 5.删除个性化菜单接口
	// httpUrl = ComConst.POST_MENU_MANAGE_INDIV_DEFINED_DEL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 6) {
	// // 6.测试个性化菜单匹配结果接口
	// httpUrl = ComConst.POST_MENU_MANAGE_INDIV_DEFINED_TEST + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else {
	// }
	//
	// return JsonUtil.fromJSON(resutlJson, DMMenuResp.class);
	// }
	//
	// /**
	// * 消息管理-客服管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：添加客服帐号接口<br>
	// * 2：修改客服帐号接口 <br>
	// * 3：删除客服帐号接口 <br>
	// * 4：设置客服帐号的头像接口<br>
	// * 5：获取所有客服账号接口 <br>
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @param filePath
	// * 上传文件路径（包含文件名称）
	// * @return Object HTTP请求响应结果Bean
	// */
	// @Override
	// public Object coreServiceByCustomrManage(int urlType, Map<String, String>
	// getParamMap, Object postParamBean,
	// String filePath) {
	// String resutlJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：添加客服帐号接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 2) {
	// // 2：修改客服帐号接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_UPD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 3) {
	// // 3：删除客服帐号接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_DEL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 4) {
	// // 4：设置客服帐号的头像 接口
	// httpUrl = ComConst.POST_MSG_MANAGE_CUSTOMER_SET_IMAGE + "?" +
	// MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.httpUpload(httpUrl, filePath);
	// } else if (urlType == 5) {
	// // 5：获取所有客服账号接口
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_MSG_MANAGE_CUSTOMER_GETALL,
	// param);
	// }
	//
	// return JsonUtil.fromJSON(resutlJson, CMCustomrResp.class);
	// }
	//
	// /**
	// * 消息管理-新客服管理
	// *
	// * @param urlType HTTP请求接口区分 <br>
	// * 25：获取未接入会话列表 <br>
	// * @param getParamMap HTTP请求GET参数Map
	// * @param postParamBean HTTP请求POST参数实体Bean
	// * @param filePath 上传文件路径（包含文件名称）
	// * @return Object HTTP请求响应结果Bean
	// */
	// @Override
	// public JSONObject coreServiceByNewCustomrManage(int urlType, Map<String,
	// String> getParamMap, Object postParamBean,
	// String filePath) {
	// String resultJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 11) {
	// // 11：创建会话
	// resultJson = httpRequest(ComConst.POST_MSG_NEW_CUSTOMER_GETKFLIST,
	// getParamMap, postParamBean);
	// }
	// else if (urlType == 21) {
	// // 21：创建会话
	// resultJson = httpRequest(ComConst.POST_MSG_NEW_CUSTOMER_CREATE,
	// getParamMap, postParamBean);
	// }
	// else if (urlType == 24) {
	// // 24：新客服接口-获取客服会话列表
	// resultJson = httpRequest(ComConst.GET_MSG_NEW_CUSTOMER_GETSESSIONLIST,
	// getParamMap, postParamBean);
	// }
	// else if (urlType == 25) {
	// // 25：获取所有客服账号接口
	// resultJson = httpRequest(ComConst.GET_MSG_NEW_CUSTOMER_GETWAITCASE,
	// getParamMap, postParamBean);
	// }
	//
	// return JSONUtil.parseObj(resultJson);//JSONObject.fromObject(resultJson);
	// }
	//
	// /**
	// * 用户管理-用户管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：创建分组接口<br>
	// * 2：查询所有分组接口 <br>
	// * 3：查询用户所在分组接口 <br>
	// * 4：修改分组名接口<br>
	// * 5：移动用户分组接口 <br>
	// * 6：批量移动用户分组接口 <br>
	// * 7：删除分组接口 <br>
	// * 8：设置备注名 <br>
	// * 9: 获取用户基本信息<br>
	// * 10: 批量获取用户基本信息<br>
	// * 11:获取用户列表<br>
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @return Object HTTP请求响应结果Bean
	// */
	// public JSONObject coreServiceByUserManage(int urlType, Map<String,
	// String> getParamMap, Object postParamBean) {
	// String resutlJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：创建分组接口
	// httpUrl = ComConst.POST_USER_MANAGE_GROUP_ADD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 2) {
	// // 2：查询所有分组接口
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_USER_MANAGE_GROUPALL_GET,
	// param);
	// } else if (urlType == 3) {
	// // 3：查询用户所在分组接口
	// httpUrl = ComConst.POST_USER_MANAGE_USERGROUP_GET + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 4) {
	// // 4：修改分组名接口
	// httpUrl = ComConst.POST_USER_MANAGE_GROUP_UPD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 5) {
	// // 5：移动用户分组接口
	// httpUrl = ComConst.POST_USER_MANAGE_GROUP_MOVE + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 6) {
	// // 6：批量移动用户分组接口
	// httpUrl = ComConst.POST_USER_MANAGE_GROUP_MOVELIST + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 7) {
	// // 7：删除分组接口
	// httpUrl = ComConst.POST_USER_MANAGE_GROUP_DEL + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 8) {
	// // 8：设置备注名
	// httpUrl = ComConst.POST_USER_REMARK_UPD + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 9) {
	// // 9:获取用户基本信息
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_USER_INFO_GET, param);
	// } else if (urlType == 10) {
	// // 10: 批量获取用户基本信息
	// httpUrl = ComConst.POST_USER_INFO_GETLIST + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// } else if (urlType == 11) {
	// // 11: 批量获取用户基本信息
	// String param = MapUtil.mapToString(getParamMap);
	// resutlJson = HttpUtil.sendGet(ComConst.GET_USER_GETLIST, param);
	// }
	//
	//// Object resutlBean = null;
	//// if (urlType > 7) {
	//// resutlBean = JsonUtil.fromJSON(resutlJson, UMUserInfoResp.class);
	//// } else {
	//// resutlBean = JsonUtil.fromJSON(resutlJson, UMUserGroupResp.class);
	//// }
	////
	//// return resutlBean;
	// return JSONUtil.parseObj(resutlJson);//JSONObject.fromObject(resutlJson);
	// }
	//
	// /**
	// * 账户管理-生成带参数的二维码
	// *
	// * @param urlType HTTP请求接口区分 <br>
	// * 1：创建二维码ticket接口<br>
	// * 2：通过ticket换取二维码接口 <br>
	// * 3：长链接转短链接接口<br>
	// * @param urlParamMap HTTP请求GET参数Map（url地址栏参数）
	// * @param postParamBean HTTP请求POST参数实体Bean
	// * @param downFile 创建的二维码文件名称（包含路径）
	// * @return Object HTTP请求响应结果Bean
	// */
	// public Object coreServiceByAccountManage(int urlType, Map<String, String>
	// getParamMap,
	// Object postParamBean, String downFile) {
	// Object result = null;
	// String resutlJson = null;
	// String httpUrl = null;
	//
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：创建二维码ticket接口
	// httpUrl = ComConst.POST_ACCOUNT_MANAGE_TICKET_GET + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// result = JsonUtil.fromJSON(resutlJson, AMQrcodeResp.class);
	// } else if (urlType == 2) {
	// // 2：通过ticket换取二维码接口
	// String param = MapUtil.mapToString(getParamMap);
	// httpUrl = ComConst.GET_ACCOUNT_MANAGE_QRCODE_GET + "?" + param;
	// AMQrcodeResp aMTicketResp = new AMQrcodeResp();
	// if (HttpUtil.httpDownload(httpUrl, downFile)) {
	// aMTicketResp.setErrcode(0);
	// aMTicketResp.setErrmsg("ok");
	// result = aMTicketResp;
	// } else {
	// aMTicketResp.setErrcode(9);
	// aMTicketResp.setErrmsg("download failure!");
	// result = aMTicketResp;
	// }
	// } else if (urlType == 3) {
	// // 3：长链接转短链接接口
	// httpUrl = ComConst.POST_ACCOUNT_MANAGE_LONG2SHORT + "?" +
	// MapUtil.mapToString(getParamMap);
	// // 发送HTTP请求
	// resutlJson = HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// result = JsonUtil.fromJSON(resutlJson, AMQrcodeResp.class);
	// }
	//
	// return result;
	// }
	//
	// /**
	// * 卡卷管理
	// *
	// * @param urlType
	// * HTTP请求接口区分 <br>
	// * 1：创建卡卷<br>
	// * 2: 设置快速买单/设置微信买单接口<br>
	// * 3：设置自动核销<br>
	// * 4：Code解码接口（线上）<br>
	// * 5：查询CODE状态<br>
	// * 6：核销Code接口<br>
	// * 7：获取用户已领取卡券接口<br>
	// * 8：查看卡券详情<br>
	// * 9：批量查询卡券列表<br>
	// * 10：更改卡券信息接口<br>
	// * 11：修改库存接口<br>
	// * 12：更改Code接口<br>
	// * 13：删除卡券接口<br>
	// * 14：设置卡券失效接口<br>
	// * 15：拉取卡券概况数据接口<br>
	// * 16：获取免费券数据接口<br>
	// * 17：拉取会员卡数据接口<br>
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @return String HTTP请求响应结果String
	// */
	// public JSONObject coreServiceByCardVolumeManage(int urlType, Map<String,
	// String> getParamMap,
	// Object postParamBean) {
	// String resultJson = null;
	// // 判断HTTP请求类型
	// if (urlType == 1) {
	// // 1：创建卡卷
	// resultJson = httpRequest(ComConst.POST_CREATE_CARD, getParamMap,
	// postParamBean);
	// } else if (urlType == 2) {
	// // 2: 设置快速买单/设置微信买单接口
	// resultJson = httpRequest(ComConst.POST_PAYCELL, getParamMap,
	// postParamBean);
	// } else if (urlType == 3) {
	// // 3：设置自动核销
	// resultJson = httpRequest(ComConst.POST_SELFCONSUMECELL, getParamMap,
	// postParamBean);
	// } else if (urlType == 4) {
	// // 4：Code解码接口（线上）
	// resultJson = httpRequest(ComConst.POST_CODE_DECYPT, getParamMap,
	// postParamBean);
	// } else if (urlType == 5) {
	// // 5：查询CODE状态
	// resultJson = httpRequest(ComConst.POST_CODE_GET_STATUS, getParamMap,
	// postParamBean);
	// } else if (urlType == 6) {
	// // 6：核销Code接口
	// resultJson = httpRequest(ComConst.POST_CODE_VERIFICATION, getParamMap,
	// postParamBean);
	// } else if (urlType == 7) {
	// // 7：获取用户已领取卡券接口
	// resultJson = httpRequest(ComConst.POST_GET_USER_CARD_LIST, getParamMap,
	// postParamBean);
	// } else if (urlType == 8) {
	// // 8：查看卡券详情
	// resultJson = httpRequest(ComConst.POST_GET_CARD_INFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 9) {
	// // 9：批量查询卡券列表
	// resultJson = httpRequest(ComConst.POST_GET_CARDID_LIST, getParamMap,
	// postParamBean);
	// } else if (urlType == 10) {
	// // 10：更改卡券信息接口
	// resultJson = httpRequest(ComConst.POST_UPDATE_CARD_INFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 11) {
	// // 11：修改库存接口
	// resultJson = httpRequest(ComConst.POST_CARD_MODIFY_STOCK, getParamMap,
	// postParamBean);
	// } else if (urlType == 12) {
	// // 12：更改Code接口
	// resultJson = httpRequest(ComConst.POST_CARD_CODE_CHANGE, getParamMap,
	// postParamBean);
	// } else if (urlType == 13) {
	// // 13：删除卡券接口
	// resultJson = httpRequest(ComConst.POST_CART_DELETE, getParamMap,
	// postParamBean);
	// } else if (urlType == 14) {
	// // 14：设置卡券失效接口
	// resultJson = httpRequest(ComConst.POST_CODE_UNAVAILABLE, getParamMap,
	// postParamBean);
	// } else if (urlType == 15) {
	// // 15：拉取卡券概况数据接口
	// resultJson = httpRequest(ComConst.POST_GET_CARD_BIZUININFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 16) {
	// // 16：获取免费券数据接口
	// resultJson = httpRequest(ComConst.POST_GET_FREE_CARD_INFO, getParamMap,
	// postParamBean);
	// } else if (urlType == 17) {
	// // 17：拉取会员卡数据接口
	// resultJson = httpRequest(ComConst.POST_GET_MEMBER_CARDINFO, getParamMap,
	// postParamBean);
	// }
	//
	// return JSONUtil.parseObj(resultJson);//JSONObject.fromObject(resultJson);
	// }
	//
	// /**
	// * 红包发放
	// *
	// * @param mchId 商家ID
	// * @param apiclientCert 证书地址
	// * @param postParamBean SSL请求POST参数实体Bean
	// * @return Map SSL请求响应结果
	// */
	// public Map<String, String> coreServiceByRedPack(String mchId,
	// String apiclientCert, Object postParamBean) {
	// Map<String, String> responseMap = null;
	// try {
	// // 拼装xml数据给ssl
	// String response = HttpUtil.sendSSL(ComConst.POST_RED_PACK_GET,
	// postParamBean.toString(), mchId, apiclientCert);
	// if(StringUtil.isNotEmpty(response)) {
	// logger.info("红包发放+coreServiceByRedPack==" +response);
	// responseMap = XmlUtil.parseXml(response);
	// }
	// else{
	// logger.info("红包发放异常+coreServiceByRedPack==" + response);
	// }
	//
	// } catch (Exception e){
	// logger.error("红包发放异常+coreServiceByRedPack=Exception=" + e.getMessage());
	// }
	// return responseMap;
	// }
	//
	// /**
	// * 发送HTTP请求(GET,POST)
	// *
	// * @param urlKey
	// * HTTP请求URL的配置文件中Key
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @param postParamBean
	// * HTTP请求POST参数实体Bean
	// * @return 请求就过 json格式字符串
	// */
	// private String httpRequest(String httpUrl, Map<String, String>
	// getParamMap, Object postParamBean) {
	// // 设置URL的GET参数
	// httpUrl = replaceUrlParma(httpUrl, getParamMap);
	//
	// // 发送HTTP请求
	// if (postParamBean == null) {
	// // GET
	// return HttpUtil.sendGet(httpUrl);
	// } else {
	// // POST
	// return HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	// }
	// }
	//
	// /**
	// * 将Http请求get参数拼接到URL中
	// *
	// * @param url
	// * HTTP请求URL
	// * @param getParamMap
	// * HTTP请求GET参数Map
	// * @return 带有get参数的URL
	// */
	// private String replaceUrlParma(String url, Map<String, String>
	// getParamMap) {
	// String replaceUrl = url;
	// if (getParamMap != null) {
	// for (String key : getParamMap.keySet()) {
	// if (url.indexOf(key) != -1) {
	// replaceUrl = replaceUrl.replace(key.toUpperCase(), getParamMap.get(key));
	// }
	// }
	// }
	//
	// return replaceUrl;
	// }

	//---------↓↓↓私有方法↓↓↓--------
	@Override
	public JSONObject getJsSdkTicket(int urlType, Map<String, String> getParamMap) {
		String resultJson = null;
		if (urlType == 1) {
			resultJson = httpRequest("get.get_fwh_jsapi_ticket", getParamMap, null);
		}
		return new JSONObject(resultJson);
	}

	private String httpRequest(String urlKey, Map<String, String> getParamMap, Object postParamBean) {
		// String httpUrl = AppProperty.get(urlKey, null,
		// "api-wechat-enterprise");
		String httpUrl = env.getProperty(urlKey);
		httpUrl = replaceUrlParma(httpUrl, getParamMap);
		if (postParamBean == null) {
			return HttpUtil.sendGet(httpUrl);
		}
		return HttpUtil.sendPost(httpUrl, JsonUtil.toJSON(postParamBean));
	}

	private String replaceUrlParma(String url, Map<String, String> getParamMap) {
		String replaceUrl = url;
		if (getParamMap != null) {
			for (String key : getParamMap.keySet()) {
				if (url.indexOf(key) != -1) {
					replaceUrl = replaceUrl.replace(key.toUpperCase(), (CharSequence) getParamMap.get(key));
				}
			}
		}
		return replaceUrl;
	}
	//---------↑↑↑私有方法↑↑↑--------
}
