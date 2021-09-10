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
import com.apih5.mybatis.dao.ZjXmSalaryFamilyBackgroundMapper;
import com.apih5.mybatis.pojo.ZjXmSalaryFamilyBackground;
import com.apih5.service.ZjXmSalaryFamilyBackgroundService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmSalaryFamilyBackgroundService")
public class ZjXmSalaryFamilyBackgroundServiceImpl implements ZjXmSalaryFamilyBackgroundService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjXmSalaryFamilyBackgroundMapper zjXmSalaryFamilyBackgroundMapper;

    @Override
    public ResponseEntity getZjXmSalaryFamilyBackgroundListByCondition(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        if (zjXmSalaryFamilyBackground == null) {
            zjXmSalaryFamilyBackground = new ZjXmSalaryFamilyBackground();
        }
        // 分页查询
        PageHelper.startPage(zjXmSalaryFamilyBackground.getPage(),zjXmSalaryFamilyBackground.getLimit());
        // 获取数据
        List<ZjXmSalaryFamilyBackground> zjXmSalaryFamilyBackgroundList = zjXmSalaryFamilyBackgroundMapper.selectByZjXmSalaryFamilyBackgroundList(zjXmSalaryFamilyBackground);
        // 得到分页信息
        PageInfo<ZjXmSalaryFamilyBackground> p = new PageInfo<>(zjXmSalaryFamilyBackgroundList);

        return repEntity.okList(zjXmSalaryFamilyBackgroundList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjXmSalaryFamilyBackgroundDetails(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        if (zjXmSalaryFamilyBackground == null) {
            zjXmSalaryFamilyBackground = new ZjXmSalaryFamilyBackground();
        }
        // 获取数据
        ZjXmSalaryFamilyBackground dbZjXmSalaryFamilyBackground = zjXmSalaryFamilyBackgroundMapper.selectByPrimaryKey(zjXmSalaryFamilyBackground.getFamilyId());
        // 数据存在
        if (dbZjXmSalaryFamilyBackground != null) {
            return repEntity.ok(dbZjXmSalaryFamilyBackground);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjXmSalaryFamilyBackground(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjXmSalaryFamilyBackground.setFamilyId(UuidUtil.generate());
        zjXmSalaryFamilyBackground.setCreateUserInfo(userKey, realName);
        int flag = zjXmSalaryFamilyBackgroundMapper.insert(zjXmSalaryFamilyBackground);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjXmSalaryFamilyBackground);
        }
    }

    @Override
    public ResponseEntity updateZjXmSalaryFamilyBackground(ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjXmSalaryFamilyBackground dbzjXmSalaryFamilyBackground = zjXmSalaryFamilyBackgroundMapper.selectByPrimaryKey(zjXmSalaryFamilyBackground.getFamilyId());
        if (dbzjXmSalaryFamilyBackground != null && StrUtil.isNotEmpty(dbzjXmSalaryFamilyBackground.getFamilyId())) {
           // sysUser扩展表主键
           dbzjXmSalaryFamilyBackground.setExtensionId(zjXmSalaryFamilyBackground.getExtensionId());
           // 与本人关系
           dbzjXmSalaryFamilyBackground.setRelationship(zjXmSalaryFamilyBackground.getRelationship());
           // 姓名
           dbzjXmSalaryFamilyBackground.setName(zjXmSalaryFamilyBackground.getName());
           // 工作单位及职务
           dbzjXmSalaryFamilyBackground.setUnitPosition(zjXmSalaryFamilyBackground.getUnitPosition());
           // 住址
           dbzjXmSalaryFamilyBackground.setAddress(zjXmSalaryFamilyBackground.getAddress());
           // 联系电话
           dbzjXmSalaryFamilyBackground.setPhoneNumber(zjXmSalaryFamilyBackground.getPhoneNumber());
           // 是否为紧急联系人
           dbzjXmSalaryFamilyBackground.setIsUrgentLinkMan(zjXmSalaryFamilyBackground.getIsUrgentLinkMan());
           // 备注
           dbzjXmSalaryFamilyBackground.setRemarks(zjXmSalaryFamilyBackground.getRemarks());
           // 排序
           dbzjXmSalaryFamilyBackground.setSort(zjXmSalaryFamilyBackground.getSort());
           // 共通
           dbzjXmSalaryFamilyBackground.setModifyUserInfo(userKey, realName);
           flag = zjXmSalaryFamilyBackgroundMapper.updateByPrimaryKey(dbzjXmSalaryFamilyBackground);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjXmSalaryFamilyBackground);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjXmSalaryFamilyBackground(List<ZjXmSalaryFamilyBackground> zjXmSalaryFamilyBackgroundList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjXmSalaryFamilyBackgroundList != null && zjXmSalaryFamilyBackgroundList.size() > 0) {
           ZjXmSalaryFamilyBackground zjXmSalaryFamilyBackground = new ZjXmSalaryFamilyBackground();
           zjXmSalaryFamilyBackground.setModifyUserInfo(userKey, realName);
           flag = zjXmSalaryFamilyBackgroundMapper.batchDeleteUpdateZjXmSalaryFamilyBackground(zjXmSalaryFamilyBackgroundList, zjXmSalaryFamilyBackground);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjXmSalaryFamilyBackgroundList);
        }
    }
}
