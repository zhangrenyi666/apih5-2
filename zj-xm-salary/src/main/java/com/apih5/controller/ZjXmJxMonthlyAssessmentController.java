package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import com.apih5.framework.annotation.DuplicateCommit;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmJxAssessmentUserScore;
import com.apih5.mybatis.pojo.ZjXmJxMonthlyAssessment;
import com.apih5.service.ZjXmJxMonthlyAssessmentService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@RestController
public class ZjXmJxMonthlyAssessmentController {

	@Autowired(required = true)
	private ZjXmJxMonthlyAssessmentService zjXmJxMonthlyAssessmentService;
	@Autowired(required = true)
    private UserService userService;
    @Autowired(required = true)
    private ResponseEntity repEntity;

	@ApiOperation(value = "查询项目月度考核", notes = "查询项目月度考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlyAssessmentList")
	public ResponseEntity getZjXmJxMonthlyAssessmentList(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.getZjXmJxMonthlyAssessmentListByCondition(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "查询详情项目月度考核", notes = "查询详情项目月度考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlyAssessmentDetails")
	public ResponseEntity getZjXmJxMonthlyAssessmentDetails(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.getZjXmJxMonthlyAssessmentDetails(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "新增项目月度考核", notes = "新增项目月度考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@PostMapping("/addZjXmJxMonthlyAssessment")
	public ResponseEntity addZjXmJxMonthlyAssessment(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.saveZjXmJxMonthlyAssessment(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "更新项目月度考核", notes = "更新项目月度考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@PostMapping("/updateZjXmJxMonthlyAssessment")
	public ResponseEntity updateZjXmJxMonthlyAssessment(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.updateZjXmJxMonthlyAssessment(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "删除项目月度考核", notes = "删除项目月度考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessmentList", value = "项目月度考核List", dataType = "List<ZjXmJxMonthlyAssessment>")
	@RequireToken
	@PostMapping("/batchDeleteUpdateZjXmJxMonthlyAssessment")
	public ResponseEntity batchDeleteUpdateZjXmJxMonthlyAssessment(
			@RequestBody(required = false) List<ZjXmJxMonthlyAssessment> zjXmJxMonthlyAssessmentList) throws Exception {
		return zjXmJxMonthlyAssessmentService.batchDeleteUpdateZjXmJxMonthlyAssessment(zjXmJxMonthlyAssessmentList);
	}

	// +++++++++++++++++++++业务接口start++++++++++++++++++++++++++++++++++++++++++++++

	@ApiOperation(value = "级联新增项目月度考核", notes = "级联新增项目月度考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/cascadeAddZjXmJxMonthlyAssessment")
	public ResponseEntity cascadeAddZjXmJxMonthlyAssessment(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) throws Exception {
		return zjXmJxMonthlyAssessmentService.cascadeAddZjXmJxMonthlyAssessment(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "在月度考核中获取任务考核详情", notes = "在月度考核中获取任务考核详情")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlyAssessmentTaskDetails")
	public ResponseEntity getZjXmJxMonthlyAssessmentTaskDetails(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.getZjXmJxMonthlyAssessmentTaskDetails(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "在月度考核中设置任务考核", notes = "在月度考核中设置任务考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/submitZjXmJxMonthlyAssessmentForTask")
	public ResponseEntity submitZjXmJxMonthlyAssessmentForTask(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.submitZjXmJxMonthlyAssessmentForTask(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "在月度考核中设置周边考核", notes = "在月度考核中设置周边考核")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/submitZjXmJxMonthlyAssessmentForPeriphery")
	public ResponseEntity submitZjXmJxMonthlyAssessmentForPeriphery(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.submitZjXmJxMonthlyAssessmentForPeriphery(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "在月度考核中获取项目正职评分详情", notes = "在月度考核中获取项目正职评分详情")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@PostMapping("/getZjXmJxMonthlyAssessmentPrincipalDetails")
	public ResponseEntity getZjXmJxMonthlyAssessmentPrincipalDetails(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.getZjXmJxMonthlyAssessmentPrincipalDetails(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "在月度考核中设置项目正职评分", notes = "在月度考核中设置项目正职评分")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/submitZjXmJxMonthlyAssessmentForPrincipal")
	public ResponseEntity submitZjXmJxMonthlyAssessmentForPrincipal(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) {
		return zjXmJxMonthlyAssessmentService.submitZjXmJxMonthlyAssessmentForPrincipal(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "项目月度考核发送通知", notes = "项目月度考核发送通知")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@DuplicateCommit
	@PostMapping("/sendZjXmJxMonthlyAssessmentNotice")
	public ResponseEntity sendZjXmJxMonthlyAssessmentNotice(
			@RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) throws Exception {
		return zjXmJxMonthlyAssessmentService.sendZjXmJxMonthlyAssessmentNotice(zjXmJxMonthlyAssessment);
	}

	@ApiOperation(value = "通过项目参数获取弹出选择人页面", notes = "通过项目参数获取弹出选择人页面")
	@ApiImplicitParam(name = "zjXmJxMonthlyAssessment", value = "项目月度考核entity", dataType = "ZjXmJxMonthlyAssessment")
	@RequireToken
	@PostMapping("/getZjXmProUserListBySysUser")
	public ResponseEntity getZjXmProUserListBySysUser(
	        @RequestBody(required = false) ZjXmJxMonthlyAssessment zjXmJxMonthlyAssessment) throws Exception {
	    JSONObject jsonObject = new JSONObject();
	    // 查询内容
	    SysUser sysUser = new SysUser();
        sysUser.setCompanyId(zjXmJxMonthlyAssessment.getProjectId());
	    List<SysUser> userList = userService.getUserListByRoleAndCompanyIdXMJX(sysUser);
	    if (userList.size() > 0) {
	        JSONArray deputyArray = JSONUtil.createArray();
            JSONArray leaderArray = JSONUtil.createArray();
            JSONArray employeeArray = JSONUtil.createArray();
            // 过滤掉项目经理和书记
            for (int i = 0; i < userList.size(); i++) {
                if (StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY, userList.get(i).getJobType())
                        || StrUtil.equals(SysConst.SYS_JOB_TYPE_SECRETARY03, userList.get(i).getJobType())
                        || StrUtil.equals(SysConst.SYS_JOB_TYPE_MANAGER, userList.get(i).getJobType())) {
                    userList.remove(i);
                    i--;
                } else if (StrUtil.isEmpty(userList.get(i).getExt2()) 
                        || StrUtil.isEmpty(userList.get(i).getExt3())
                        || StrUtil.isEmpty(userList.get(i).getExt4())) {
                    // check所有被评分者的评分领导不能为空
                    return repEntity.layerMessage("no", "该项目有员工还未设置岗位或评分领导。");
                }
            
                JSONObject jsonObj = JSONUtil.createObj();
                jsonObj.set("value", userList.get(i).getUserKey());
                jsonObj.set("label", userList.get(i).getRealName());
                if (StrUtil.equals("0", userList.get(i).getExt2())) {
                    deputyArray.add(jsonObj);
                } else if (StrUtil.equals("1", userList.get(i).getExt2())) {
                    leaderArray.add(jsonObj);
                } else if (StrUtil.equals("2", userList.get(i).getExt2())) {
                    employeeArray.add(jsonObj);
                }
                jsonObject.set("deputyArray", deputyArray);
                jsonObject.set("leaderArray", leaderArray);
                jsonObject.set("employeeArray", employeeArray);
            }
        }
	    return repEntity.ok(jsonObject);
	}
	// +++++++++++++++++++++业务接口end++++++++++++++++++++++++++++++++++++++++++++++++
}
