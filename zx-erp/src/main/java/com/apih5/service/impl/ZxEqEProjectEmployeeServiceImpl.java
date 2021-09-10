package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEProjectEmployeeMapper;
import com.apih5.mybatis.dao.ZxEqEemployeeMapper;
import com.apih5.mybatis.pojo.ZxEqEProjectEmployee;
import com.apih5.mybatis.pojo.ZxEqEProjectEmployee;
import com.apih5.mybatis.pojo.ZxEqEemployee;
import com.apih5.service.ZxEqEProjectEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEProjectEmployeeService")
public class ZxEqEProjectEmployeeServiceImpl implements ZxEqEProjectEmployeeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEProjectEmployeeMapper zxEqEProjectEmployeeMapper;
    
    @Autowired(required = true)
    private ZxEqEemployeeMapper zxEqEemployeeMapper;

    @Override
    public ResponseEntity getZxEqEProjectEmployeeListByCondition(ZxEqEProjectEmployee zxEqEProjectEmployee) {
        if (zxEqEProjectEmployee == null) {
            zxEqEProjectEmployee = new ZxEqEProjectEmployee();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqEProjectEmployee.setComID("");
        	zxEqEProjectEmployee.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqEProjectEmployee.setComID(zxEqEProjectEmployee.getOrgID());
        	zxEqEProjectEmployee.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqEProjectEmployee.setOrgID(zxEqEProjectEmployee.getOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxEqEProjectEmployee.getPage(),zxEqEProjectEmployee.getLimit());
        // 获取数据
        List<ZxEqEProjectEmployee> zxEqEProjectEmployeeList = zxEqEProjectEmployeeMapper.selectByZxEqEProjectEmployeeList(zxEqEProjectEmployee);
        for (ZxEqEProjectEmployee zxEqEProjectEmployee2 : zxEqEProjectEmployeeList) {
        	ZxEqEemployee item = new ZxEqEemployee();
        	item.setParentID(zxEqEProjectEmployee2.getId());
        	List<ZxEqEemployee> EemployeeList = zxEqEemployeeMapper.selectByZxEqEemployeeList(item);
        	zxEqEProjectEmployee2.setEemployeeList(EemployeeList);
        }
        // 得到分页信息
        PageInfo<ZxEqEProjectEmployee> p = new PageInfo<>(zxEqEProjectEmployeeList);

        return repEntity.okList(zxEqEProjectEmployeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEProjectEmployeeDetails(ZxEqEProjectEmployee zxEqEProjectEmployee) {
        if (zxEqEProjectEmployee == null) {
            zxEqEProjectEmployee = new ZxEqEProjectEmployee();
        }
        // 获取数据
        ZxEqEProjectEmployee dbZxEqEProjectEmployee = zxEqEProjectEmployeeMapper.selectByPrimaryKey(zxEqEProjectEmployee.getId());
        // 数据存在
        if (dbZxEqEProjectEmployee != null) {
            return repEntity.ok(dbZxEqEProjectEmployee);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEProjectEmployee(ZxEqEProjectEmployee zxEqEProjectEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEProjectEmployee.setId(UuidUtil.generate());
        zxEqEProjectEmployee.setCreateUserInfo(userKey, realName);
        int flag = zxEqEProjectEmployeeMapper.insert(zxEqEProjectEmployee);
        if(zxEqEProjectEmployee.getEemployeeList() != null && zxEqEProjectEmployee.getEemployeeList().size() >0) {	
        	for (ZxEqEemployee lib : zxEqEProjectEmployee.getEemployeeList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setParentID(zxEqEProjectEmployee.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqEemployeeMapper.insert(lib);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEProjectEmployee);
        }
    }

    @Override
    public ResponseEntity updateZxEqEProjectEmployee(ZxEqEProjectEmployee zxEqEProjectEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEProjectEmployee dbzxEqEProjectEmployee = zxEqEProjectEmployeeMapper.selectByPrimaryKey(zxEqEProjectEmployee.getId());
        if (dbzxEqEProjectEmployee != null && StrUtil.isNotEmpty(dbzxEqEProjectEmployee.getId())) {
           // 机构名称
           dbzxEqEProjectEmployee.setOrgName(zxEqEProjectEmployee.getOrgName());
           // 机构id
           dbzxEqEProjectEmployee.setOrgID(zxEqEProjectEmployee.getOrgID());
           // 项目名称
           dbzxEqEProjectEmployee.setProjectName(zxEqEProjectEmployee.getProjectName());
           // 项目id
           dbzxEqEProjectEmployee.setProjectID(zxEqEProjectEmployee.getProjectID());
           // 备注
           dbzxEqEProjectEmployee.setRemark(zxEqEProjectEmployee.getRemark());
           //公司id
           dbzxEqEProjectEmployee.setComID(zxEqEProjectEmployee.getComID());
           //公司name
           dbzxEqEProjectEmployee.setComName(zxEqEProjectEmployee.getComName());
           // 共通
           dbzxEqEProjectEmployee.setModifyUserInfo(userKey, realName);
           flag = zxEqEProjectEmployeeMapper.updateByPrimaryKey(dbzxEqEProjectEmployee);
           //先删除再新增
           ZxEqEemployee delLib = new ZxEqEemployee();
           delLib.setParentID(zxEqEProjectEmployee.getId());
           List<ZxEqEemployee> delLibList = zxEqEemployeeMapper.selectByZxEqEemployeeList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqEemployeeMapper.batchDeleteUpdateZxEqEemployee(delLibList, delLib);
           }
           if(zxEqEProjectEmployee.getEemployeeList() != null && zxEqEProjectEmployee.getEemployeeList().size() >0) {	
        	   for (ZxEqEemployee lib : zxEqEProjectEmployee.getEemployeeList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setParentID(zxEqEProjectEmployee.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqEemployeeMapper.insert(lib);
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEProjectEmployee);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEProjectEmployee(List<ZxEqEProjectEmployee> zxEqEProjectEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEProjectEmployeeList != null && zxEqEProjectEmployeeList.size() > 0) {
        	for (ZxEqEProjectEmployee zxEqEProjectEmployee : zxEqEProjectEmployeeList) {
    			ZxEqEemployee delLib = new ZxEqEemployee();
    			delLib.setParentID(zxEqEProjectEmployee.getId());
    			List<ZxEqEemployee> delLibList = zxEqEemployeeMapper.selectByZxEqEemployeeList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqEemployeeMapper.batchDeleteUpdateZxEqEemployee(delLibList, delLib);
    			}
    		}
        	ZxEqEProjectEmployee zxEqEProjectEmployee = new ZxEqEProjectEmployee();
           zxEqEProjectEmployee.setModifyUserInfo(userKey, realName);
           flag = zxEqEProjectEmployeeMapper.batchDeleteUpdateZxEqEProjectEmployee(zxEqEProjectEmployeeList, zxEqEProjectEmployee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEProjectEmployeeList);
        }
    }
}
