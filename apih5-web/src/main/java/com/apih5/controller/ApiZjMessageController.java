package com.apih5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.entity.OtherMessageEntity;
import com.apih5.entity.OtherTokenEntity;
import com.apih5.framework.components.CheckToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 系统对外提供的接口zj-Controller
 */
@RestController
//@RequestMapping("/user")
public class ApiZjMessageController {
	@Autowired
    private CheckToken checkToken;
	
	@Autowired
	private ResponseEntity repEntity;

//	/**
//	 * 第三方平台调用此接口发消息
//	 */
//	@ApiOperation(value = "发消息", notes = "发消息")
//	@ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
//	@PostMapping("/sendOtherMsg")
//	public ResponseEntity sendOtherMsg(@RequestBody OtherMessageEntity otherMessageEntity,
//			@RequestBody OtherTokenEntity otherTokenEntity) {
//		// 检查token是否合法
//		checkToken.checkToken(otherTokenEntity.getAccessToken());
//		// 检查
//		if(StrUtil.isEmpty(otherMessageEntity.getToUsers())){
//			return repEntity.error("sys.other.message.user");
//		}
//		if(StrUtil.isEmpty(otherMessageEntity.getMsgContent())){
//			return repEntity.error("sys.other.message.content");
//		}
//		String str = HttpUtil.sendGet("http://weixin.fheb.cn/ZJOA/sendMsg.do?touser="
//					+ otherMessageEntity.getToUsers() + "&content=" + otherMessageEntity.getMsgContent());
//		JSONObject jsonObject = new JSONObject(str);
//		if(jsonObject.isEmpty() || jsonObject.isNull("")
//				|| StrUtil.equals("1", jsonObject.get("").toString())){
//			return repEntity.error("sys.other.message");
//		}
//		else {
//			// 发送成功
//			return repEntity.ok("");
//		}
//	}
}
