package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.ComConst;
import com.apih5.framework.api.wechat.service.WeChatInfoService;
import com.apih5.framework.api.wechatenterprise.WEComConst;
import com.apih5.framework.api.wechatenterprise.entity.xml.response.Article;
import com.apih5.framework.api.wechatenterprise.entity.xml.response.NewsMsgResp;
import com.apih5.framework.api.wechatenterprise.entity.xml.response.TextMsgResp;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.wechatutils.CastUtil;
import com.apih5.framework.api.wechatutils.DateUtil;
import com.apih5.service.BaseAccountService;

import cn.hutool.core.util.StrUtil;

/**
 * 消息管理 1.发送消息-被动回复消息
 * 
 * @author oh
 */
@Service("weChatInfoService")
public class WeChatInfoServiceImpl implements WeChatInfoService {

//	@Autowired(required = true)
//	private BaseAccountMapper accountMapper;
//
	@Autowired(required = true)
	private BaseAccountService accountService;

	@Autowired(required = true)
	private WeChatEnterpriseService wechatEnterpriseService;

	/**
	 * 获取 被动回复消息
	 * 
	 * @param msgType
	 *            接受消息类型
	 * @param eventType
	 *            接受消息的事件类型
	 * @param reqMap
	 *            接受消息reqMap
	 * @return Object 回复消息Bean
	 * @throws Exception
	 */
	@Override
	public Object getResponseMsg(String msgType, String eventType, Map<String, String> reqMap) {
		// 响应Msg的Bean
		Object responseBean = null;

		/*
		 * 判断接受请求消息类型
		 */
		if (StrUtil.isEmpty(msgType)) {
			// 获取默认被动回复消息
		} else if (WEComConst.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
			// 接受消息消息-回复文本消息
			responseBean = new TextMsgResp();
			((TextMsgResp) responseBean).setContent("暂时无法识别您的内容，请谅解!");
			((TextMsgResp) responseBean).setToUserName(reqMap.get("FromUserName"));
			((TextMsgResp) responseBean).setFromUserName(reqMap.get("ToUserName"));
			((TextMsgResp) responseBean).setMsgType(ComConst.REQ_MESSAGE_TYPE_TEXT);
			((TextMsgResp) responseBean).setCreateTime(CastUtil.castInt(DateUtil.getCurrentDatetime()));
			// responseMsg = XmlUtil.textMessageToXml(textMsgResp);
			// 获取被动 回复消息-回复图片消息
			// responseBean = new ImageMsgResp();
			// Image image = new Image();
			// image.setMediaId("mediaId");
			// ((ImageMsgResp) responseBean).setImage(image);
			// 获取被动 回复消息-回复语音消息
			// responseBean = new VoiceMsgResp();
			// Voice voice = new Voice();
			// voice.setMediaId("MediaId");
			// ((VoiceMsgResp) responseBean).setVoice(voice);
			// 获取被动 回复消息-回复视频消息
			// responseBean = new VideoMsgResp();
			// Video video = new Video();
			// video.setMediaId("MediaId");
			// video.setDescription("描述");
			// ((VideoMsgResp) responseBean).setVideo(video);
			// ((VideoMsgResp) responseBean).setFromUserName("222");
			// 获取被动 回复消息-回复音乐消息
			// responseBean = new MusicMsgResp();
			// Music music = new Music();
			// music.setDescription("Dexcription1");
			// ((MusicMsgResp) responseBean).setMusic(music);
			// 获取被动 回复消息-多图文消息
			// NewsMsgResp newsMsgResp = new NewsMsgResp();
			// Article art = new Article();
			// art.setDescription("test");
			// Article art1 = new Article();
			// art1.setDescription("test1");
			// art1.setPicUrl("test11");
			// List<Article> list = new ArrayList<Article>();
			// list.add(art);
			// list.add(art1);
			// Item item = new Item();
			// item.setItem(list);
			// newsMsgResp.setArticles(item);
			// newsMsgResp.setArticleCount(100);

		} else if (WEComConst.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {
			// 接受消息消息-回复图片消息

		} else if (WEComConst.REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {
			// 接受消息消息-回复语音消息
		} else if (WEComConst.REQ_MESSAGE_TYPE_VIDEO.equals(msgType)) {
			// 接受消息消息-回复视频消息
		} else if (WEComConst.REQ_MESSAGE_TYPE_SHORTVIDEO.equals(msgType)) {
			// 接受消息消息-回复小视频消息
		} else if (WEComConst.REQ_MESSAGE_TYPE_LOCATION.equals(msgType)) {
			// 接受消息消息-回复地理位置消息
		} else if (WEComConst.REQ_MESSAGE_TYPE_LINK.equals(msgType)) {
			// 接受消息消息-回复链接消息
		} else if (WEComConst.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {
			if (WEComConst.EVENT_TYPE_VIEW.equals(eventType)) {
				// 事件类型：VIEW(自定义菜单点击事件)
			} else if (WEComConst.EVENT_TYPE_CLICK.equals(eventType)) {
				// 事件类型：CLICK(自定义菜单点击事件)
			} else if (WEComConst.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {
//				// 事件类型：subscribe(订阅)
//				// 获取被动 回复消息-多图文消息
//				NewsMsgResp newsMsgResp = new NewsMsgResp();
//				
//				newsMsgResp.setCreateTime(DateUtil.getCurrentTimestamp());
//				newsMsgResp.setMsgType("news");
//				newsMsgResp.setFromUserName(reqMap.get("ToUserName"));
//				newsMsgResp.setToUserName(reqMap.get("FromUserName"));
//				newsMsgResp.setArticleCount(3);
//				
//				
//				Article art = new Article();
//				art.setDescription("");
//				art.setPicUrl("http://weixin.fheb.cn/ZJOA/www/wechat_images/news1-1.png");
//				//art.setPicUrl("https://qy.weixin.qq.com/cgi-bin/getmediadata?type=image&media_id=2WEeBQM1BgEDpUQuRd3cDRxlKZbwEBYWvL6azdNvI-VMWfaV3d7JQQMQ3ZWnPc_9l");
//				art.setTitle("欢迎使用中交一公局“微办公平台”");
//				art.setUrl("http://weixin.fheb.cn/ZJOA/initIndex.do");
//				
//				Article art1 = new Article();
//				art1.setDescription("");
//				art1.setPicUrl("http://weixin.fheb.cn/ZJOA/www/wechat_images/news1-2.png");
//				//art1.setPicUrl("https://qy.weixin.qq.com/cgi-bin/getmediadata?type=image&media_id=2MTrLlzZk6lQzVqutP-J3SgAi9j1xC7hrCzhdntVavYGZEixJLNTMtbTZqd9jX_qK");
//				art1.setTitle("快转发你的同事加入企业号吧！");
//				art1.setUrl("https://qy.weixin.qq.com/cgi-bin/wap_getnewsmsg?action=get&__biz=MzI3MDM2OTY2MA==&mixuin=MjE3NzU5MjQ4NjQ5NDQ5OTI4NA==&mid=10000056&idx=2&sn=5e8bd0deed611693be40a22067be0ae0");
//				
//				Article art2 = new Article();
//				art2.setDescription("");
//				art2.setPicUrl("http://weixin.fheb.cn/ZJOA/www/wechat_images/news1-3.jpg");
//				//art2.setPicUrl("https://qy.weixin.qq.com/cgi-bin/getmediadata?type=image&media_id=2vHqYGaGXJgRfUge9sTHd94sQ9WjCuiC2I7aJvYI0IBNWm9FJ6kfbx52f3wQP7sQ-");
//				art2.setTitle("中交一公局简介");
//				art2.setUrl("https://qy.weixin.qq.com/cgi-bin/wap_getnewsmsg?action=get&__biz=MzI3MDM2OTY2MA==&mixuin=MjE3NzU5MjQ4NjQ5NDQ5OTI4NA==&mid=10000060&idx=3&sn=b4ac534bad408b7816b55ee2bbe99824");
//				
//				List<Article> list = new ArrayList<Article>();
//				list.add(art);
//				list.add(art1);
//				list.add(art2);
//				
////				Item item = new Item();
////				item.setItem(list);
//				newsMsgResp.setArticles(list);
//
//				responseBean = newsMsgResp;
			} else if (WEComConst.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {
				// 事件类型：unsubscribe(取消订阅)
			} else if (WEComConst.EVENT_TYPE_SCAN.equals(eventType)) {
				// 事件类型：扫描二维码
			} else if (WEComConst.EVENT_TYPE_SCAN_WARNINNG.equals(eventType)) {
				// 事件类型：扫描二维码弹出提示框
			} else if (WEComConst.EVENT_TYPE_LOCATION.equals(eventType)) {
				// 事件类型：地理位置通知事件
			} else if (WEComConst.EVENT_TYPE_PIC_SYSPHOTO.equals(eventType)) {
				// 事件类型：弹出系统拍照发图的事件推送
			} else if (WEComConst.EVENT_TYPE_PIC_SYSPHOTO_ALBUM.equals(eventType)) {
				// 事件类型：弹出系统拍照发图的事件推送弹出拍照或者相册发图的事件推送
			} else if (WEComConst.EVENT_TYPE_PIC_WEIXIN.equals(eventType)) {
				// 事件类型：弹出微信相册发图器的事件推送
			} else if (WEComConst.EVENT_TYPE_LOCATION_SELECT.equals(eventType)) {
				// 事件类型：弹出地理位置选择器的事件推送
			} else if (WEComConst.EVENT_TYPE_ENTER_AGENT.equals(eventType)) {

				// 获取被动 回复消息-多图文消息
				NewsMsgResp newsMsgResp = new NewsMsgResp();
				
				newsMsgResp.setCreateTime(DateUtil.getCurrentTimestamp());
				newsMsgResp.setMsgType("news");
				newsMsgResp.setFromUserName(reqMap.get("ToUserName"));
				newsMsgResp.setToUserName(reqMap.get("FromUserName"));
				newsMsgResp.setArticleCount(3);
				
				
				Article art = new Article();
				art.setDescription("");
				art.setPicUrl("http://weixin.fheb.cn/ZJOA/www/wechat_images/news1-1.png");
				//art.setPicUrl("https://qy.weixin.qq.com/cgi-bin/getmediadata?type=image&media_id=2WEeBQM1BgEDpUQuRd3cDRxlKZbwEBYWvL6azdNvI-VMWfaV3d7JQQMQ3ZWnPc_9l");
				art.setTitle("欢迎使用中交一公局“微办公平台”");
				art.setUrl("http://weixin.fheb.cn/ZJOA/initIndex.do");
				
				Article art1 = new Article();
				art1.setDescription("");
				art1.setPicUrl("http://weixin.fheb.cn/ZJOA/www/wechat_images/news1-2.png");
				//art1.setPicUrl("https://qy.weixin.qq.com/cgi-bin/getmediadata?type=image&media_id=2MTrLlzZk6lQzVqutP-J3SgAi9j1xC7hrCzhdntVavYGZEixJLNTMtbTZqd9jX_qK");
				art1.setTitle("快转发你的同事加入企业号吧！");
				art1.setUrl("https://qy.weixin.qq.com/cgi-bin/wap_getnewsmsg?action=get&__biz=MzI3MDM2OTY2MA==&mixuin=MjE3NzU5MjQ4NjQ5NDQ5OTI4NA==&mid=10000056&idx=2&sn=5e8bd0deed611693be40a22067be0ae0");
				
				Article art2 = new Article();
				art2.setDescription("");
				art2.setPicUrl("http://weixin.fheb.cn/ZJOA/www/wechat_images/news1-3.jpg");
				//art2.setPicUrl("https://qy.weixin.qq.com/cgi-bin/getmediadata?type=image&media_id=2vHqYGaGXJgRfUge9sTHd94sQ9WjCuiC2I7aJvYI0IBNWm9FJ6kfbx52f3wQP7sQ-");
				art2.setTitle("中交一公局简介");
				art2.setUrl("https://qy.weixin.qq.com/cgi-bin/wap_getnewsmsg?action=get&__biz=MzI3MDM2OTY2MA==&mixuin=MjE3NzU5MjQ4NjQ5NDQ5OTI4NA==&mid=10000060&idx=3&sn=b4ac534bad408b7816b55ee2bbe99824");
				
				List<Article> list = new ArrayList<Article>();
				list.add(art);
				list.add(art1);
				list.add(art2);
				
//				Item item = new Item();
//				item.setItem(list);
				newsMsgResp.setArticles(list);

				responseBean = newsMsgResp;

//				// 事件类型：成员进入应用的事件推送
//				// 主动发送图文消息
//				MpnewsDtailReq mpnewsReq = new MpnewsDtailReq();
//				mpnewsReq.setAgentid("1");
//				mpnewsReq.setMsgtype("mpnews");
//				mpnewsReq.setToparty("");
//				mpnewsReq.setTotag("");
//				mpnewsReq.setTouser(reqMap.get("FromUserName"));
//
//				MpnewsInfoDtail mpnewsInfo = new MpnewsInfoDtail();
//
//				List<MpnewsArticleInfo> mpnewsArticleInfoList = new ArrayList<MpnewsArticleInfo>();
//
//				MpnewsArticleInfo mpnewsArticleInfo1 = new MpnewsArticleInfo();
//				mpnewsArticleInfo1.setAuthor("中交一公局");
//				mpnewsArticleInfo1.setContent("中交一公局");
//				mpnewsArticleInfo1.setContent_source_url("");
//				mpnewsArticleInfo1.setDigest("");
//				mpnewsArticleInfo1.setShow_cover_pic("1");
//				mpnewsArticleInfo1
//						.setThumb_media_id("2WEeBQM1BgEDpUQuRd3cDRxlKZbwEBYWvL6azdNvI-VMWfaV3d7JQQMQ3ZWnPc_9l");
//				mpnewsArticleInfo1.setTitle("欢迎使用中交一公局“微办公平台”");
//				mpnewsArticleInfoList.add(mpnewsArticleInfo1);
//
//				MpnewsArticleInfo mpnewsArticleInfo2 = new MpnewsArticleInfo();
//				mpnewsArticleInfo2.setAuthor("中交一公局");
//				mpnewsArticleInfo2.setContent("扫一扫上面的二维码，关注中交一公局企业号并进行企业验证后方可进行使用。");
//				mpnewsArticleInfo2.setContent_source_url("");
//				mpnewsArticleInfo2.setDigest("");
//				mpnewsArticleInfo2.setShow_cover_pic("1");
//				mpnewsArticleInfo2
//						.setThumb_media_id("2MTrLlzZk6lQzVqutP-J3SgAi9j1xC7hrCzhdntVavYGZEixJLNTMtbTZqd9jX_qK");
//				mpnewsArticleInfo2.setTitle("快转发你的同事加入企业号吧！");
//				mpnewsArticleInfoList.add(mpnewsArticleInfo2);
//
//				MpnewsArticleInfo mpnewsArticleInfo3 = new MpnewsArticleInfo();
//				mpnewsArticleInfo3.setAuthor("中交一公局");
//				mpnewsArticleInfo3.setContent(
//						"<body style=\"overflow-y: hidden; height: 656px; cursor: text;\" spellcheck=\"false\" class=\"view\" contenteditable=\"true\"><p style=\";font-family: 宋体;line-height: 24px;white-space: normal;background-color: rgb(255, 255, 255)\"><span style=\"font-size: 16px\">​中交第一公路工程局有限公司（简称中交一公局）<span style=\"font-size:16px\">,</span>于<span style=\"font-size:16px\">1963</span></span><span style=\"font-size: 16px\">年成立，其前身为中国人民解放军公路一师。本部位于北京。</span></p><p style=\";font-family: 宋体;line-height: 24px;white-space: normal;background-color: rgb(255, 255, 255);text-indent: 32px\"><span style=\"font-size:16px\">中交一公局从起初承建国内战备公路、国外经援工程的一支筑路队伍，发展成为如今以承建基础设施工程为主，集投资、设计、咨询、施工、监理、科研、检测、机械制造为一体的国家大型公路工程施工总承包特级企业。现下拥有3个省部级技术中心；19</span><span style=\"font-size:16px\">个类别88</span><span style=\"font-size:16px\">项建筑业企业资质，其中</span><span style=\"font-size:16px\">10家公路一级、市政一级总承包企业</span><span style=\"font-size:16px\">，1</span><span style=\"font-size:16px\">家房屋建筑工程施工一级总承包企业。涵盖公路、桥梁、隧道、铁路、市政、房建、港口、航道、机场、管道、</span><span style=\"font-size:16px\">交通工程、钢结构等业务，</span><span style=\"font-size:16px\">资产总额600</span><span style=\"font-size:16px\">多亿元，年新签合同额、完成营业收入均超过</span><span style=\"font-size:16px\">550</span><span style=\"font-size:16px\">亿元。创建国家、企业级施工工法</span><span style=\"font-size:16px\">200</span><span style=\"font-size:16px\">项，拥有国家专利</span><span style=\"font-size:16px\">180</span><span style=\"font-size:16px\">项，被认定为国家级“高新技术企业”，是一家综合实力雄厚的业内知名品牌企业。</span></p><p style=\";font-family: 宋体;line-height: 24px;white-space: normal;background-color: rgb(255, 255, 255);text-indent: 32px\"><span style=\"font-size: 16px\">中交一公局拥有</span><span style=\"font-size: 16px\">国内外</span><span style=\"font-size: 16px\">36</span><span style=\"font-size: 16px\">个全资子公司、</span><span style=\"font-size: 16px\">3</span><span style=\"font-size: 16px\">个参股公司、</span><span style=\"font-size: 16px\">17</span><span style=\"font-size: 16px\">个分公司、<span style=\"font-size:16px\">6</span>个国内区域总部、</span><span style=\"font-size: 16px\">5</span><span style=\"font-size: 16px\">个驻外机构</span><span style=\"font-size: 16px\">。在建</span><span style=\"font-size: 16px\">工程项目</span><span style=\"font-size: 16px\">300</span><span style=\"font-size: 16px\">多个，</span><span style=\"font-size: 16px\">遍布国内31个省、市、自治区，以及非洲、东南亚、中东等20多个国家。共<span style=\"font-size:16px\">修建各种等级公路18000多公里，其中高速公路占国内高速公路通车里程的15%；修建各类桥梁6000余座、隧道900公里、铁路1000公里、市政工程2000多公里、轨道百公里。创造多项“第一”和“之最”工程，使“一公局承建”成为“一公局品牌”。</span></span></p><p style=\";font-family: 宋体;line-height: 24px;white-space: normal;background-color: rgb(255, 255, 255);text-indent: 32px\"><span style=\"font-size: 16px\">中交一公局荣获</span><span style=\"font-size: 16px\">“全国优秀施工企业”、 “全国文明单位”、<span style=\"font-size:16px\">“全国五一劳动奖状”、“全国工人先锋号”等40</span><span style=\"font-size:16px\">多项企业最高奖，荣获中国施工企业工程建设企业管理现代化管理成果奖</span><span style=\"font-size:16px\">11</span><span style=\"font-size:16px\">项，</span></span><span style=\"font-size: 16px\">中国建筑工程鲁班奖10项、中国土木工程詹天佑大奖8项、国家优质工程银质奖14项、火车头奖杯10项、中国</span><span style=\"font-size: 16px\">企业新纪录奖5项。</span></p><p style=\";font-family: 宋体;line-height: 24px;white-space: normal;background-color: rgb(255, 255, 255);text-indent: 32px\"><span style=\"font-size: 16px\">中交一公局<span style=\"font-size:16px\">继续秉承“固基修道，履方致远”的企业使命，肩负行业“建设者、引导者、提高者”的时代重任，</span></span><span style=\"font-size: 16px\">立足国内国外基础设施，做强承包商、做好发展商、做优运营商，</span><span style=\"font-size: 16px\">以“自强奋进，永争第一”的企业精神，</span><span style=\"font-size: 16px\">继续巩固并提高在中国交建的领先优势，助推国家交通事业大发展、国民经济更繁荣！</span></p><p><br></p></body>");
//				mpnewsArticleInfo3.setContent_source_url("");
//				mpnewsArticleInfo3.setDigest("");
//				mpnewsArticleInfo3.setShow_cover_pic("1");
//				mpnewsArticleInfo3
//						.setThumb_media_id("2vHqYGaGXJgRfUge9sTHd94sQ9WjCuiC2I7aJvYI0IBNWm9FJ6kfbx52f3wQP7sQ-");
//				mpnewsArticleInfo3.setTitle("中交一公局简介");
//				mpnewsArticleInfoList.add(mpnewsArticleInfo3);
//				mpnewsInfo.setArticles(mpnewsArticleInfoList);
//				mpnewsReq.setMpnews(mpnewsInfo);
//
//				AccountInfo accountInfo = (AccountInfo) getAccountInfo();
//				Map<String, String> getParamMap = new HashMap<String, String>();
//				getParamMap.put("access_token", accountInfo.getAccountAccesstoken());
//
//				wechatEnterpriseService.coreServiceBySendMsg(1, getParamMap, mpnewsReq);
			} else if (WEComConst.EVENT_TYPE_BATCH_JOB_RESULT.equals(eventType)) {
				// 事件类型：异步任务完成事件推送
				// 接受消息消息-回复文本消息
				responseBean = new TextMsgResp();
				((TextMsgResp) responseBean).setContent("异步任务完成！");
				((TextMsgResp) responseBean).setCreateTime(DateUtil.getCurrentTimestamp());
				((TextMsgResp) responseBean).setFromUserName("wx324d746ab634abf3");
				((TextMsgResp) responseBean).setToUserName("wuhongquan");
				((TextMsgResp) responseBean).setMsgType("text");
			} else {
			}
		} else {
		}

		return responseBean;
	}

//	@Override
//	public Object getAccountInfo() {
//		AccountInfo accountInfoResult = new AccountInfo();
//		// 获取第一个账户信息
//		BaseAccount account = accountService.getAccountInfo();
//
//		accountInfoResult.setAccountAppid(account.getCorpId());
//		accountInfoResult.setAccountAccess(account.getCorpSecret());
//		accountInfoResult.setAccountAccesstoken(account.getAccessToken());
//
//		return accountInfoResult;
//	}

	@Override
	public boolean isRigestInfo(String userId) {
		return false;
	}

	@Override
	public int addUserInfo(String userId, String openId) {
		return 0;
	}

//	@Override
//	public MemberInfo getMemberInfoByOpenId(String openId) {
//		return null;
//	}
}
