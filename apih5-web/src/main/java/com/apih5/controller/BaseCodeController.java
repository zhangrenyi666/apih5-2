package com.apih5.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.DuplicateCommit;
import com.apih5.framework.annotation.RequirePermissions;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.BaseCode;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.constant.PGIdConst;
import com.apih5.framework.entity.ResponseEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class BaseCodeController {

	@Autowired(required = true)
	private BaseCodeService baseCodeService;

	@ApiOperation(value = "查询数据字典", notes = "查询数据字典")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
	@RequirePermissions(value = PGIdConst.BASE_CODE_MGMT)
	@RequireToken
	@PostMapping("/getBaseCodeList")
	public ResponseEntity getBaseCodeList(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.getBaseCodeListByCondition(baseCode);
	}

	@ApiOperation(value = "新增数据字典[通用]", notes = "新增数据字典")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
	@RequirePermissions(value = PGIdConst.BASE_CODE_MGMT)
	@DuplicateCommit
	@RequireToken
	@PostMapping("/addBaseCode")
	public ResponseEntity addBaseCode(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.saveBaseCode(baseCode);
	}

	@ApiOperation(value = "更新数据字典[在表单中编辑]", notes = "更新数据字典")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
	@RequirePermissions(value = PGIdConst.BASE_CODE_MGMT)
	@RequireToken
	@PostMapping("/updateBaseCode")
	public ResponseEntity updateBaseCode(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.updateBaseCode(baseCode);
	}

	@ApiOperation(value = "删除数据字典", notes = "删除数据字典")
	@ApiImplicitParam(name = "baseCodeList", value = "数据字典List", dataType = "List<BaseCode>")
	@RequirePermissions(value = PGIdConst.BASE_CODE_MGMT)
	@RequireToken
	@PostMapping("/batchDeleteUpdateBaseCode")
	public ResponseEntity batchDeleteUpdateBaseCode(@RequestBody(required = false) List<BaseCode> baseCodeList)
			throws Exception {
		return baseCodeService.batchDeleteUpdateBaseCode(baseCodeList);
	}

	// ---扩展
	@ApiOperation(value = "查询数据字典", notes = "查询数据字典")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
//	@RequireToken
	@PostMapping("/getBaseCodeSelect")
	public ResponseEntity getBaseCodeSelect(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.getBaseCodeSelect(baseCode);
	}

	@ApiOperation(value = "查询数据字典", notes = "查询数据字典")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
//	@RequireToken
	@PostMapping("/getBaseCodeTree")
	public ResponseEntity getBaseCodeTree(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.getBaseCodeTree(baseCode);
	}
	
    @ApiOperation(value = "查询数据字典-指定层级", notes = "查询数据字典")
    @ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
    @PostMapping("/getBaseCodeTreeByLevel")
    public ResponseEntity getBaseCodeTreeByLevel(@RequestBody(required = false) BaseCode baseCode) {
        return baseCodeService.getBaseCodeTreeByLevel(baseCode);
    }

	@ApiOperation(value = "查询数据字典前台页面配置[代码写死(后期多了在数据库配置)]", notes = "查询数据字典前台页面配置")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
	@RequirePermissions(value = PGIdConst.BASE_CODE_MGMT)
	@RequireToken
	@PostMapping("/getBaseCodeUIConfig")
	public ResponseEntity getBaseCodeUIConfig(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.getBaseCodeUIConfig(baseCode);
	}

	@ApiOperation(value = "更新数据字典[在树结构上编辑]", notes = "更新数据字典[在树结构上编辑]")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
	@RequireToken
	@PostMapping("/pcUpdateBaseCodeOnTree")
	public ResponseEntity pcUpdateBaseCodeOnTree(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.pcUpdateBaseCodeOnTree(baseCode);
	}

	@ApiOperation(value = "pc端层级交换排序", notes = "pc端层级交换排序")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
	@RequireToken
	@PostMapping("/pcExchangeBaseCode")
	public ResponseEntity pcExchangeBaseCode(@RequestBody(required = false) BaseCode baseCode) {
		return baseCodeService.pcExchangeBaseCode(baseCode);
	}

	@ApiOperation(value = "移动字典排序", notes = "移动字典排序")
	@ApiImplicitParam(name = "baseCode", value = "数据字典entity", dataType = "BaseCode")
	@RequireToken
	@PostMapping("/moveUpdateBaseCode")
	public ResponseEntity moveUpdateBaseCode(@RequestBody(required = false) BaseCode baseCode) throws Exception {
		return baseCodeService.moveUpdateBaseCode(baseCode);
	}
}
