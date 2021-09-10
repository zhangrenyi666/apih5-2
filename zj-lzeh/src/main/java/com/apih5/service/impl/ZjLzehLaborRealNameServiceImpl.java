package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehLaborRealNameMapper;
import com.apih5.mybatis.pojo.ZjLzehLaborRealName;
import com.apih5.service.ZjLzehLaborRealNameService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehLaborRealNameService")
public class ZjLzehLaborRealNameServiceImpl implements ZjLzehLaborRealNameService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehLaborRealNameMapper zjLzehLaborRealNameMapper;

    @Override
    public ResponseEntity getZjLzehLaborRealNameListByCondition(ZjLzehLaborRealName zjLzehLaborRealName) {
        if (zjLzehLaborRealName == null) {
            zjLzehLaborRealName = new ZjLzehLaborRealName();
        }

        // 分页查询
        PageHelper.startPage(zjLzehLaborRealName.getPage(),zjLzehLaborRealName.getLimit());
        //写死ID
        zjLzehLaborRealName.setLaborRealNameId("1EES1THLS00H0201000200000487F8E0");
        // 获取数据
        List<ZjLzehLaborRealName> zjLzehLaborRealNameList = zjLzehLaborRealNameMapper.selectByZjLzehLaborRealNameList(zjLzehLaborRealName);
        // 得到分页信息
        PageInfo<ZjLzehLaborRealName> p = new PageInfo<>(zjLzehLaborRealNameList);

        return repEntity.okList(zjLzehLaborRealNameList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehLaborRealNameDetails(ZjLzehLaborRealName zjLzehLaborRealName) {
        if (zjLzehLaborRealName == null) {
            zjLzehLaborRealName = new ZjLzehLaborRealName();
        }
        // 获取数据
        ZjLzehLaborRealName dbZjLzehLaborRealName = zjLzehLaborRealNameMapper.selectByPrimaryKey(zjLzehLaborRealName.getLaborRealNameId());
        // 数据存在
        if (dbZjLzehLaborRealName != null) {
            return repEntity.ok(dbZjLzehLaborRealName);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehLaborRealName(ZjLzehLaborRealName zjLzehLaborRealName) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehLaborRealName.setLaborRealNameId(UuidUtil.generate());
        zjLzehLaborRealName.setCreateUserInfo(userKey, realName);
        int flag = zjLzehLaborRealNameMapper.insert(zjLzehLaborRealName);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehLaborRealName);
        }
    }

    @Override
    public ResponseEntity updateZjLzehLaborRealName(ZjLzehLaborRealName zjLzehLaborRealName) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehLaborRealName dbzjLzehLaborRealName = zjLzehLaborRealNameMapper.selectByPrimaryKey(zjLzehLaborRealName.getLaborRealNameId());
        if (dbzjLzehLaborRealName != null && StrUtil.isNotEmpty(dbzjLzehLaborRealName.getLaborRealNameId())) {
           // 参见单位数量
           dbzjLzehLaborRealName.setSeeUnitNumber(zjLzehLaborRealName.getSeeUnitNumber());
           // 班组数量
           dbzjLzehLaborRealName.setTeamNumber(zjLzehLaborRealName.getTeamNumber());
           // 在场人员数量
           dbzjLzehLaborRealName.setPresencePersonnelNumber(zjLzehLaborRealName.getPresencePersonnelNumber());
           // 本月工资发放总和
           dbzjLzehLaborRealName.setCurrentMonthSalaryTotal(zjLzehLaborRealName.getCurrentMonthSalaryTotal());
           // 共通
           dbzjLzehLaborRealName.setModifyUserInfo(userKey, realName);
           flag = zjLzehLaborRealNameMapper.updateByPrimaryKey(dbzjLzehLaborRealName);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehLaborRealName);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehLaborRealName(List<ZjLzehLaborRealName> zjLzehLaborRealNameList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehLaborRealNameList != null && zjLzehLaborRealNameList.size() > 0) {
           ZjLzehLaborRealName zjLzehLaborRealName = new ZjLzehLaborRealName();
           zjLzehLaborRealName.setModifyUserInfo(userKey, realName);
           flag = zjLzehLaborRealNameMapper.batchDeleteUpdateZjLzehLaborRealName(zjLzehLaborRealNameList, zjLzehLaborRealName);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehLaborRealNameList);
        }
    }
}
