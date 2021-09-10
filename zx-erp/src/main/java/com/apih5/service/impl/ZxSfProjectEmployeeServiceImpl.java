package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfProjectEmployeeMapper;
import com.apih5.mybatis.pojo.ZxSfProjectEmployee;
import com.apih5.service.ZxSfProjectEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfProjectEmployeeService")
public class ZxSfProjectEmployeeServiceImpl implements ZxSfProjectEmployeeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfProjectEmployeeMapper zxSfProjectEmployeeMapper;

    @Override
    public ResponseEntity getZxSfProjectEmployeeListByCondition(ZxSfProjectEmployee zxSfProjectEmployee) {
        if (zxSfProjectEmployee == null) {
            zxSfProjectEmployee = new ZxSfProjectEmployee();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfProjectEmployee.setCompanyId("");
            zxSfProjectEmployee.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfProjectEmployee.setCompanyId(zxSfProjectEmployee.getOrgID());
            zxSfProjectEmployee.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfProjectEmployee.setOrgID(zxSfProjectEmployee.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSfProjectEmployee.getPage(), zxSfProjectEmployee.getLimit());
        // 获取数据
        List<ZxSfProjectEmployee> zxSfProjectEmployeeList = zxSfProjectEmployeeMapper.selectByZxSfProjectEmployeeList(zxSfProjectEmployee);
        // 得到分页信息
        PageInfo<ZxSfProjectEmployee> p = new PageInfo<>(zxSfProjectEmployeeList);

        return repEntity.okList(zxSfProjectEmployeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfProjectEmployeeDetail(ZxSfProjectEmployee zxSfProjectEmployee) {
        if (zxSfProjectEmployee == null) {
            zxSfProjectEmployee = new ZxSfProjectEmployee();
        }
        // 获取数据
        ZxSfProjectEmployee dbZxSfProjectEmployee = zxSfProjectEmployeeMapper.selectByPrimaryKey(zxSfProjectEmployee.getZxSfProjectEmployeeId());
        // 数据存在
        if (dbZxSfProjectEmployee != null) {
            return repEntity.ok(dbZxSfProjectEmployee);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfProjectEmployee(ZxSfProjectEmployee zxSfProjectEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfProjectEmployee.setZxSfProjectEmployeeId(UuidUtil.generate());
        zxSfProjectEmployee.setCreateUserInfo(userKey, realName);
        int flag = zxSfProjectEmployeeMapper.insert(zxSfProjectEmployee);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfProjectEmployee);
        }
    }

    @Override
    public ResponseEntity updateZxSfProjectEmployee(ZxSfProjectEmployee zxSfProjectEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfProjectEmployee dbZxSfProjectEmployee = zxSfProjectEmployeeMapper.selectByPrimaryKey(zxSfProjectEmployee.getZxSfProjectEmployeeId());
        if (dbZxSfProjectEmployee != null && StrUtil.isNotEmpty(dbZxSfProjectEmployee.getZxSfProjectEmployeeId())) {
            // 机构名称
            dbZxSfProjectEmployee.setOrgName(zxSfProjectEmployee.getOrgName());
            // 机构ID
            dbZxSfProjectEmployee.setOrgID(zxSfProjectEmployee.getOrgID());
            // 姓名
            dbZxSfProjectEmployee.setName(zxSfProjectEmployee.getName());
            // 性别
            dbZxSfProjectEmployee.setSex(zxSfProjectEmployee.getSex());
            // 年龄
            dbZxSfProjectEmployee.setAge(zxSfProjectEmployee.getAge());
            // 学历
            dbZxSfProjectEmployee.setEduLevel(zxSfProjectEmployee.getEduLevel());
            // 职称
            dbZxSfProjectEmployee.setTitle(zxSfProjectEmployee.getTitle());
            // 注安师证号
            dbZxSfProjectEmployee.setCardNo(zxSfProjectEmployee.getCardNo());
            // 从事安全工作累计时间(年)
            dbZxSfProjectEmployee.setWorkAge(zxSfProjectEmployee.getWorkAge());
            // 交通部安全证书编号
            dbZxSfProjectEmployee.setSafeCardNo(zxSfProjectEmployee.getSafeCardNo());
            // 发证日期
            dbZxSfProjectEmployee.setSendCardDate(zxSfProjectEmployee.getSendCardDate());
            // 到期日期
            dbZxSfProjectEmployee.setUseEndDate(zxSfProjectEmployee.getUseEndDate());
            // 正在从事安全工作
            dbZxSfProjectEmployee.setIsWorking(zxSfProjectEmployee.getIsWorking());
            // 编辑时间
            dbZxSfProjectEmployee.setEditTime(zxSfProjectEmployee.getEditTime());
            // 是否为注安师
            dbZxSfProjectEmployee.setIsExpert(zxSfProjectEmployee.getIsExpert());
            // 主表ID
            dbZxSfProjectEmployee.setMainID(zxSfProjectEmployee.getMainID());
            // 建设部安全证书编号
            dbZxSfProjectEmployee.setBuildCardNo(zxSfProjectEmployee.getBuildCardNo());
            // 发证日期（建）
            dbZxSfProjectEmployee.setBuildSendDate(zxSfProjectEmployee.getBuildSendDate());
            // 到期日期（建）
            dbZxSfProjectEmployee.setBuildEndDate(zxSfProjectEmployee.getBuildEndDate());
            // 备注
            dbZxSfProjectEmployee.setRemarks(zxSfProjectEmployee.getRemarks());
            // 排序
            dbZxSfProjectEmployee.setSort(zxSfProjectEmployee.getSort());
            // 共通
            dbZxSfProjectEmployee.setModifyUserInfo(userKey, realName);
            flag = zxSfProjectEmployeeMapper.updateByPrimaryKey(dbZxSfProjectEmployee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfProjectEmployee);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfProjectEmployee(List<ZxSfProjectEmployee> zxSfProjectEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfProjectEmployeeList != null && zxSfProjectEmployeeList.size() > 0) {
            ZxSfProjectEmployee zxSfProjectEmployee = new ZxSfProjectEmployee();
            zxSfProjectEmployee.setModifyUserInfo(userKey, realName);
            flag = zxSfProjectEmployeeMapper.batchDeleteUpdateZxSfProjectEmployee(zxSfProjectEmployeeList, zxSfProjectEmployee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfProjectEmployeeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity getuReportFormList(ZxSfProjectEmployee zxSfProjectEmployee) {
        if (zxSfProjectEmployee == null) {
            zxSfProjectEmployee = new ZxSfProjectEmployee();
        }
        // 分页查询
        PageHelper.startPage(zxSfProjectEmployee.getPage(), zxSfProjectEmployee.getLimit());
        // 获取数据
        List<ZxSfProjectEmployee> zxSfProjectEmployeeList = zxSfProjectEmployeeMapper.uReportForm(zxSfProjectEmployee);
        // 得到分页信息
        PageInfo<ZxSfProjectEmployee> p = new PageInfo<>(zxSfProjectEmployeeList);

        return repEntity.okList(zxSfProjectEmployeeList, p.getTotal());
    }

    @Override
    public List<ZxSfProjectEmployee> uReportForm(ZxSfProjectEmployee zxSfProjectEmployee) {
        if (zxSfProjectEmployee == null) {
            zxSfProjectEmployee = new ZxSfProjectEmployee();
        }
        // 分页查询
        PageHelper.startPage(zxSfProjectEmployee.getPage(), zxSfProjectEmployee.getLimit());
        // 获取数据
        List<ZxSfProjectEmployee> zxSfProjectEmployeeList = zxSfProjectEmployeeMapper.uReportForm(zxSfProjectEmployee);
        // 得到分页信息
        PageInfo<ZxSfProjectEmployee> p = new PageInfo<>(zxSfProjectEmployeeList);

        return zxSfProjectEmployeeList;
    }

    @Override
    public ResponseEntity getuReportFormComList(ZxSfProjectEmployee zxSfProjectEmployee) {
        if (zxSfProjectEmployee == null) {
            zxSfProjectEmployee = new ZxSfProjectEmployee();
        }
        // 分页查询
        PageHelper.startPage(zxSfProjectEmployee.getPage(), zxSfProjectEmployee.getLimit());
        // 获取数据
        List<ZxSfProjectEmployee> zxSfProjectEmployeeList = zxSfProjectEmployeeMapper.uReportFormCom(zxSfProjectEmployee);
        // 得到分页信息
        PageInfo<ZxSfProjectEmployee> p = new PageInfo<>(zxSfProjectEmployeeList);

        return repEntity.okList(zxSfProjectEmployeeList, p.getTotal());
    }

    @Override
    public List<ZxSfProjectEmployee> uReportFormCom(ZxSfProjectEmployee zxSfProjectEmployee) {
        if (zxSfProjectEmployee == null) {
            zxSfProjectEmployee = new ZxSfProjectEmployee();
        }
        // 分页查询
        PageHelper.startPage(zxSfProjectEmployee.getPage(), zxSfProjectEmployee.getLimit());
        // 获取数据
        List<ZxSfProjectEmployee> zxSfProjectEmployeeList = zxSfProjectEmployeeMapper.uReportFormCom(zxSfProjectEmployee);
        // 得到分页信息
        PageInfo<ZxSfProjectEmployee> p = new PageInfo<>(zxSfProjectEmployeeList);

        return zxSfProjectEmployeeList;
    }

}
