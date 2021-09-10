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
import com.apih5.mybatis.dao.ZjLzehTeamMapper;
import com.apih5.mybatis.pojo.ZjLzehTeam;
import com.apih5.service.ZjLzehTeamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTeamService")
public class ZjLzehTeamServiceImpl implements ZjLzehTeamService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTeamMapper zjLzehTeamMapper;

    @Override
    public ResponseEntity getZjLzehTeamListByCondition(ZjLzehTeam zjLzehTeam) {
        if (zjLzehTeam == null) {
            zjLzehTeam = new ZjLzehTeam();
        }
        // 分页查询
//        PageHelper.startPage(zjLzehTeam.getPage(),zjLzehTeam.getLimit());
        // 获取数据
        List<ZjLzehTeam> zjLzehTeamList = zjLzehTeamMapper.selectByZjLzehTeamList(zjLzehTeam);
        // 得到分页信息
//        PageInfo<ZjLzehTeam> p = new PageInfo<>(zjLzehTeamList);

        return repEntity.ok(zjLzehTeamList);
    }

    @Override
    public ResponseEntity getZjLzehTeamDetails(ZjLzehTeam zjLzehTeam) {
        if (zjLzehTeam == null) {
            zjLzehTeam = new ZjLzehTeam();
        }
        // 获取数据
        ZjLzehTeam dbZjLzehTeam = zjLzehTeamMapper.selectByPrimaryKey(zjLzehTeam.getTeamId());
        // 数据存在
        if (dbZjLzehTeam != null) {
            return repEntity.ok(dbZjLzehTeam);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehTeam(ZjLzehTeam zjLzehTeam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTeam.setTeamId(UuidUtil.generate());
        zjLzehTeam.setCreateUserInfo(userKey, realName);
        int flag = zjLzehTeamMapper.insert(zjLzehTeam);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehTeam);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTeam(ZjLzehTeam zjLzehTeam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTeam dbzjLzehTeam = zjLzehTeamMapper.selectByPrimaryKey(zjLzehTeam.getTeamId());
        if (dbzjLzehTeam != null && StrUtil.isNotEmpty(dbzjLzehTeam.getTeamId())) {
           // 班组名称
           dbzjLzehTeam.setTeamName(zjLzehTeam.getTeamName());
           // 班组人数
           dbzjLzehTeam.setPeopleNumber(zjLzehTeam.getPeopleNumber());
           // 共通
           dbzjLzehTeam.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamMapper.updateByPrimaryKey(dbzjLzehTeam);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehTeam);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTeam(List<ZjLzehTeam> zjLzehTeamList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTeamList != null && zjLzehTeamList.size() > 0) {
           ZjLzehTeam zjLzehTeam = new ZjLzehTeam();
           zjLzehTeam.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamMapper.batchDeleteUpdateZjLzehTeam(zjLzehTeamList, zjLzehTeam);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehTeamList);
        }
    }
}
