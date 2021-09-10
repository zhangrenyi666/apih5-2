package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.DuplicateCommit;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysDepartmentController {

	@Autowired(required = true)
	private SysDepartmentService sysDepartmentService;

	@ApiOperation(value = "查询部门", notes = "查询部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentListByCondition")
	public ResponseEntity getSysDepartmentListByCondition(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysDepartmentListByCondition(sysDepartment);
	}

	@ApiOperation(value = "新增部门", notes = "新增部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@DuplicateCommit
	@RequireToken
	@PostMapping("/addSysDepartment")
	public ResponseEntity addSysDepartment(@RequestBody(required = false) SysDepartment sysDepartment) throws Exception{
		return sysDepartmentService.saveSysDepartment(sysDepartment);
	}

	@ApiOperation(value = "更新部门", notes = "更新部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@DuplicateCommit
	@RequireToken
	@PostMapping("/updateSysDepartment")
	public ResponseEntity updateSysDepartment(@RequestBody(required = false)
			SysDepartment sysDepartment) throws Exception {
		return sysDepartmentService.updateSysDepartment(sysDepartment);
	}

	@ApiOperation(value = "移动部门顺序", notes = "移动部门顺序")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@DuplicateCommit
	@RequireToken
	@PostMapping("/moveUpdateSysDepartment")
	public ResponseEntity moveUpdateSysDepartment(@RequestBody(required = false) SysDepartment sysDepartment)
			throws Exception {
		return sysDepartmentService.moveUpdateSysDepartment(sysDepartment);
	}

	@ApiOperation(value = "删除部门", notes = "删除部门")
	@ApiImplicitParam(name = "sysDepartmentList", value = "部门List", dataType = "List<SysDepartment>")
	@DuplicateCommit
	@RequireToken
	@PostMapping("/batchDeleteUpdateSysDepartment")
	public ResponseEntity batchDeleteUpdateSysDepartment(@RequestBody(required = false) 
			List<SysDepartment> sysDepartmentList) throws Exception{
		return sysDepartmentService.batchDeleteUpdateSysDepartment(sysDepartmentList);
	}

	@ApiOperation(value = "删除部门", notes = "删除部门")
	@ApiImplicitParam(name = "sysDepartmentList", value = "部门List", dataType = "List<SysDepartment>")
	@DuplicateCommit
	@RequireToken
	@PostMapping("/deleteSysDepartment")
	public ResponseEntity deleteSysDepartment(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.deleteSysDepartment(sysDepartment);
	}

	/**
     * 组织机构一步一步点击获取
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value = "查询部门", notes = "查询部门")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/getSysDepartmentProListByCondition")
    public ResponseEntity getSysDepartmentProListByCondition(@RequestBody(required = false) SysDepartment sysDepartment) {
        return sysDepartmentService.getSysDepartmentProListByCondition(sysDepartment);
    }

    /**
     * 获取当前登录者的所有所在项目信息
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value = "查询部门", notes = "查询部门")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/getSysDepartmentProList")
    public ResponseEntity getSysDepartmentProList(@RequestBody(required = false) SysDepartment sysDepartment) {
        return sysDepartmentService.getSysDepartmentProList(sysDepartment);
    }
    
    /**
     * 获取当前登录者的所有所在项目信息
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value = "查询部门", notes = "查询部门")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/changeSysDepartmentPro")
    public ResponseEntity changeSysDepartmentPro(@RequestBody(required = false) SysDepartment sysDepartment) {
        return sysDepartmentService.changeSysDepartmentPro(sysDepartment);
    }
    
//    /**
//     * 获取当前登陆者所选项目所在的部门
//     * 
//     * @param sysDepartment
//     * @return
//     */
//    @ApiOperation(value = "查询部门", notes = "查询部门")
//    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
//    @RequireToken
//    @PostMapping("/getSysDepartmentProDept")
//    public ResponseEntity getSysDepartmentProDept(@RequestBody(required = false) SysDepartment sysDepartment) {
//        return sysDepartmentService.getSysDepartmentProDept(sysDepartment);
//    }

    /**
	 * 获取当前登录者的所有部门信息（数组模式即List）
	 * 
	 * @param sysDepartment
	 * @return
	 */
	@ApiOperation(value = "查询部门", notes = "查询部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentList")
	public ResponseEntity getSysDepartmentList(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysDepartmentList(sysDepartment);
	}

	/**
	 * 获取当前登录者的所有部门信息（数组模式即List）
	 * 
	 * @param sysDepartment
	 * @return
	 */
	@ApiOperation(value = "查询部门", notes = "查询部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@GetMapping("/apiSysDepartmentList")
	public ResponseEntity apiSysDepartmentList(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.apiSysDepartmentList(sysDepartment);
	}

//	/**
//	 * 获取部门树形部门信息
//	 * 
//	 * @param sysDepartment
//	 * @return
//	 */
//	@ApiOperation(value = "获取部门树形部门信息", notes = "获取部门树形部门信息")
//	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
//	// @RequireToken
//	@PostMapping("/getSysDepartmentTree")
//	public ResponseEntity getSysDepartmentTree(@RequestBody(required = false) SysDepartment sysDepartment) {
//		return sysDepartmentService.getSysDepartmentTree(sysDepartment);
//	}

	/**
	 * 获取当前用户登录的公司节点下的信息（树形结构）
	 * 
	 * @param sysDepartment
	 * @return
	 */
	@ApiOperation(value = "查询部门", notes = "查询部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentUserAllTree")
	public ResponseEntity getSysDepartmentUserAllTree(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysDepartmentUserAllTree(sysDepartment);
	}

	/**
	 * 获取【所有部门】并形成树形结构（通用版本）
	 * 
	 * @param sysDepartment
	 * @return 【所有部门】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门", notes = "所有部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentTree")
	public ResponseEntity getSysDepartmentTree(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysDepartmentTree(sysDepartment);
	}

	/**
	 * 获取【所有部门、人员】并形成树形结构（通用版本）
	 * 
	 * @param sysDepartment
	 * @return 【所有部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTree")
	public ResponseEntity getSysUserTree(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysUserTree(sysDepartment);
	}

	/**
	 * 获取【所有部门】并形成树形结构（通用版本）
	 * 
	 * @param sysDepartment
	 * @return 【所有部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentCurrentTree")
	public ResponseEntity getSysDepartmentCurrentTree(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysDepartmentCurrentTree(sysDepartment);
	}

	/**
	 * 无权下方式获取所有部门数据，并形成树形结构（通用版本）
	 * 
	 * @param sysDepartment
	 * @return 【所有部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentCurrentTreeAll")
	public ResponseEntity getSysDepartmentCurrentTreeAll(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysDepartmentCurrentTreeAll(sysDepartment);
	}
	
	/**
	 * 获取【所有部门、人员】并形成树形结构（通用版本）
	 * 
	 * @param sysDepartment
	 * @return 【所有部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserCurrentTree")
	public ResponseEntity getSysUserCurrentTree(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserCurrentTree(sysDepartment);
	}
	
	/**
	 * 一层一层请求:获取【所有当前所属公司人员】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【所有部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTreeByCompany")
	public ResponseEntity getSysUserTreeByCompany(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserTreeByCompany(sysDepartment);
	}
	
	/**
	 * 一层一层请求:获取【所有局（机关）人员】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【所有部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTreeByJu")
	public ResponseEntity getSysUserTreeByJu(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserTreeByJu(sysDepartment);
	}
	
	/**
	 * 一层一层请求:获取【总部人员】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【所有部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTreeByZb")
	public ResponseEntity getSysUserTreeByZb(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserTreeByZb(sysDepartment);
	}
	
	/**
	 * 一层一层请求:获取【流程权限左侧树结构】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【流程权限左侧树结构】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserFlowLeftTree")
	public ResponseEntity getSysUserFlowLeftTree(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserFlowLeftTree(sysDepartment);
	}
	
	/**
	 * 一层一层请求:获取【流程权限左侧树结构】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【流程权限左侧树结构】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserFlowSetUpTree")
	public ResponseEntity getSysUserFlowSetUpTree(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserFlowSetUpTree(sysDepartment);
	}
	
	/**
	 * 一层一层请求:获取【流程人员树结构】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【流程人员树结构】，并形成树形结构
	 */
	@ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserFlowRoleTree")
	public ResponseEntity getSysUserFlowRoleTree(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserFlowRoleTree(sysDepartment);
	}

	//---------------↓↓↓ 中交 ↓↓↓------------
    /**
     * 获取中交【所有部门、人员】并形成树形结构（中交）
     * 
     * @param sysDepartment
     * @return 【所有部门】，并形成树形结构
     */
    @ApiOperation(value = "所有部门", notes = "所有部门")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/getSysDepartmentCurrentTreeByZj")
    public ResponseEntity getSysDepartmentCurrentTreeByZj(@RequestBody(required = false) SysDepartment sysDepartment) {
        return sysDepartmentService.getSysDepartmentCurrentTreeByZj(sysDepartment);
    }

    /**
     * 获取中交【所有部门、人员】并形成树形结构（通用版本）
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @ApiOperation(value = "所有部门、人员", notes = "所有部门、人员")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/getSysUserCurrentTreeByZj")
    public ResponseEntity getSysUserCurrentTreeByZj(@RequestBody(required = false) SysDepartment sysDepartment) {
        return sysDepartmentService.getSysUserCurrentTreeByZj(sysDepartment);
    }
    
	   /**
     * 获取当前用户登录的公司节点下的信息（树形结构）
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value = "查询部门", notes = "查询部门")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/getSysDepartmentUserAllTreeByZj")
    public ResponseEntity getSysDepartmentUserAllTreeByZj(@RequestBody(required = false) SysDepartment sysDepartment) {
        return sysDepartmentService.getSysDepartmentUserAllTreeByZj(sysDepartment, 0);
    }
    
	   /**
     * 获取当前用户登录的公司节点下的信息（树形结构）
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value = "查询部门", notes = "查询部门")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncCacheSysDepartmentUserAllTreeByZj")
    public ResponseEntity syncCacheSysDepartmentUserAllTreeByZj(
            @RequestBody(required = false) SysDepartment sysDepartment) {
        return sysDepartmentService.getSysDepartmentUserAllTreeByZj(sysDepartment, 1);
    }
    
	/**
	 * 中交·获取【本部门】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【本部门】，并形成树形结构
	 */
	@ApiOperation(value = "本部门", notes = "本部门")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentTreeByZj")
	public ResponseEntity getSysDepartmentTreeByZj(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysDepartmentTreeByZj(sysDepartment);
	}

	/**
	 * 中交·获取【本部门+局】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【本部门+局】，并形成树形结构
	 */
	@ApiOperation(value = "本部门+局", notes = "本部门+局")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentTreeByZjAddOther")
	public ResponseEntity getSysDepartmentTreeByZjAddOther(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysDepartmentTreeByZjAddOther(sysDepartment);
	}

	/**
	 * 中交·获取【本公司人员】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【本公司人员】，并形成树形结构
	 */
	@ApiOperation(value = "本公司人员", notes = "本公司人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTreeByZj")
	public ResponseEntity getSysUserTreeByZj(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysUserTreeByZj(sysDepartment);
	}
	
	/**
	 * 中交·获取【本公司人员+局】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 【本公司人员+局】，并形成树形结构
	 */
	@ApiOperation(value = "本公司人员", notes = "本公司人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTreeByZjAddOther")
	public ResponseEntity getSysUserTreeByZjAddOther(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysUserTreeByZjAddOther(sysDepartment);
	}
	
	/**
	 * 中交·获取所有【部门、人员】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 获取所有【部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "本公司人员", notes = "本公司人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTreeByZjAll")
	public ResponseEntity getSysUserTreeByZjAll(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserTreeByZjAll(sysDepartment);
	}
	
	/**
	 * 中交厦门人资，处理多项目问题·获取所有【部门、人员】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 获取所有【部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "本公司人员", notes = "本公司人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentTreeByZjXmrz")
	public ResponseEntity getSysDepartmentTreeByZjXmrz(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysDepartmentTreeByZjXmrz(sysDepartment);
	}
	
	/**
	 * 中交厦门人资，处理多项目问题·获取所有【部门、人员】并形成树形结构
	 * 
	 * @param sysDepartment
	 * @return 获取所有【部门、人员】，并形成树形结构
	 */
	@ApiOperation(value = "本公司人员", notes = "本公司人员")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysUserTreeByZjXmrz")
	public ResponseEntity getSysUserTreeByZjXmrz(@RequestBody(required = false) SysDepartment sysDepartment) {
	    return sysDepartmentService.getSysUserTreeByZjXmrz(sysDepartment);
	}

	
	@ApiOperation(value = "根据id查询项目", notes = "根据id查询项目")
	@ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
	@RequireToken
	@PostMapping("/getSysDepartmentListByProjectXmrz")
	public ResponseEntity getSysDepartmentListByProjectXmrz(@RequestBody(required = false) SysDepartment sysDepartment) {
		return sysDepartmentService.getSysDepartmentListByProjectXmrz(sysDepartment);
	}
	
	
	
	
	
	
	   /**
     * 中交·获取所有【部门、人员】并形成树形结构
     * 
     * @param sysDepartment
     * @return 获取所有【部门、人员】，并形成树形结构
     */
	@Autowired(required = true)
    private SysUserDepartmentMapper sysUserDepartmentMapper;
    @ApiOperation(value = "本公司人员", notes = "本公司人员")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @GetMapping("/getXmrzDepartment")
    public void getXmrzDepartment(@RequestBody(required = false) SysDepartment sysDepartment) {
        sysDepartment = new SysDepartment();
        sysDepartment.setDepartmentParentId("1EC58H8C00017601A8C00000E1ED84DD");
        List<SysDepartment> SysDepartmentList = sysDepartmentService.selectBySysDepartmentList(sysDepartment);
       for(SysDepartment dbsysDepartment:SysDepartmentList) {
           // 材设部（1）、经营部  计划合同部
           if(true) {
               String del = "";
               String update = "";
               if(true) {
                   SysDepartment sysDepartment2 = new SysDepartment();
                   sysDepartment2.setDepartmentParentId(dbsysDepartment.getDepartmentId());
                   sysDepartment2.setDepartmentName("材设部");
                   List<SysDepartment> sysDepartmentList2 = sysDepartmentService.selectBySysDepartmentList(sysDepartment2);
                   if(sysDepartmentList2 == null || sysDepartmentList2.size()==0) {
                       continue;
                   }
                   del = sysDepartmentList2.get(0).getDepartmentId();
               }
               if(true) {
                   SysDepartment sysDepartment2 = new SysDepartment();
                   sysDepartment2.setDepartmentParentId(dbsysDepartment.getDepartmentId());
                   sysDepartment2.setDepartmentName("计划合同部");
                   List<SysDepartment> sysDepartmentList2 = sysDepartmentService.selectBySysDepartmentList(sysDepartment2);
                   if(sysDepartmentList2 == null || sysDepartmentList2.size()==0) {
                       continue;
                   }
                   update = sysDepartmentList2.get(0).getDepartmentId();
               }
               if(StrUtil.isNotEmpty(del) && StrUtil.isNotEmpty(update) ) {
                   SysUserDepartment record = new SysUserDepartment();
                   record.setDepartmentId(del);
                   List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper.selectBySysUserDepartmentList(record);
                   for(SysUserDepartment dbSysUserDepartment:sysUserDepartmentList) {
                       dbSysUserDepartment.setDepartmentId(update);
                       sysUserDepartmentMapper.updateByPrimaryKey(dbSysUserDepartment);
                   }
               }
           }
           
           
           // 试验实（1）、测量队  检测部
           if(true) {
               String del = "";
               String update = "";
               if(true) {
                   SysDepartment sysDepartment2 = new SysDepartment();
                   sysDepartment2.setDepartmentParentId(dbsysDepartment.getDepartmentId());
                   sysDepartment2.setDepartmentName("试验室");
                   List<SysDepartment> sysDepartmentList2 = sysDepartmentService.selectBySysDepartmentList(sysDepartment2);
                   if(sysDepartmentList2 == null || sysDepartmentList2.size()==0) {
                       continue;
                   }
                   del = sysDepartmentList2.get(0).getDepartmentId();
               }
               if(true) {
                   SysDepartment sysDepartment2 = new SysDepartment();
                   sysDepartment2.setDepartmentParentId(dbsysDepartment.getDepartmentId());
                   sysDepartment2.setDepartmentName("检测部");
                   List<SysDepartment> sysDepartmentList2 = sysDepartmentService.selectBySysDepartmentList(sysDepartment2);
                   if(sysDepartmentList2 == null || sysDepartmentList2.size()==0) {
                       continue;
                   }
                   update = sysDepartmentList2.get(0).getDepartmentId();
               }
               if(StrUtil.isNotEmpty(del) && StrUtil.isNotEmpty(update) ) {
                   SysUserDepartment record = new SysUserDepartment();
                   record.setDepartmentId(del);
                   List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper.selectBySysUserDepartmentList(record);
                   for(SysUserDepartment dbSysUserDepartment:sysUserDepartmentList) {
                       dbSysUserDepartment.setDepartmentId(update);
                       sysUserDepartmentMapper.updateByPrimaryKey(dbSysUserDepartment);
                   }
               }
           }

           //         // 查询要删除的departmentId
           //         // 将departmentId更新user_department表的departmentId=计划合同部  where departmentId=删除departmentId
       }
    }
}
