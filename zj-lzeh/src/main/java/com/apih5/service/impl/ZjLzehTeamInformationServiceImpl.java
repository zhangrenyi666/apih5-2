package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehTeamInformationTeamMapper;
import com.apih5.mybatis.dao.ZjLzehTeamMapper;
import com.apih5.mybatis.pojo.ZjLzehTeam;
import com.apih5.mybatis.pojo.ZjLzehTeamInformationTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTeamInformationMapper;
import com.apih5.mybatis.pojo.ZjLzehTeamInformation;
import com.apih5.service.ZjLzehTeamInformationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTeamInformationService")
public class ZjLzehTeamInformationServiceImpl implements ZjLzehTeamInformationService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTeamInformationMapper zjLzehTeamInformationMapper;

    @Autowired(required = true)
    private ZjLzehTeamInformationTeamMapper zjLzehTeamInformationTeamMapper;

    @Autowired(required = true)
    private ZjLzehTeamMapper zjLzehTeamMapper;


    @Override
    public ResponseEntity getZjLzehTeamInformationListByCondition(ZjLzehTeamInformation zjLzehTeamInformation) {
        if (zjLzehTeamInformation == null) {
            zjLzehTeamInformation = new ZjLzehTeamInformation();
        }
        // 分页查询
//        PageHelper.startPage(zjLzehTeamInformation.getPage(),zjLzehTeamInformation.getLimit());
        // 获取数据
        List<ZjLzehTeamInformation> zjLzehTeamInformationList = zjLzehTeamInformationMapper.selectByZjLzehTeamInformationList(zjLzehTeamInformation);

        for (ZjLzehTeamInformation lzehTeamInformation : zjLzehTeamInformationList) {
            //a段
            ZjLzehTeamInformationTeam zjLzehTeamInformationTeama = new ZjLzehTeamInformationTeam();
            zjLzehTeamInformationTeama.setTeamInformationId(lzehTeamInformation.getTeamInformationId());
            zjLzehTeamInformationTeama.setSection("a");
            List<ZjLzehTeamInformationTeam> zjLzehTeamInformationTeamsa = zjLzehTeamInformationTeamMapper.selectByZjLzehTeamInformationTeamList(zjLzehTeamInformationTeama);
            List<ZjLzehTeam> zjLzehTeamLista = new ArrayList<>();
            for (ZjLzehTeamInformationTeam lzehTeamInformationTeam : zjLzehTeamInformationTeamsa) {
                ZjLzehTeam zjLzehTeams = zjLzehTeamMapper.selectByPrimaryKey(lzehTeamInformationTeam.getTeamId());
                zjLzehTeamLista.add(zjLzehTeams);
            }
            lzehTeamInformation.setaTeamList(zjLzehTeamLista);
            //b段
            ZjLzehTeamInformationTeam zjLzehTeamInformationTeamb = new ZjLzehTeamInformationTeam();
            zjLzehTeamInformationTeamb.setTeamInformationId(lzehTeamInformation.getTeamInformationId());
            zjLzehTeamInformationTeamb.setSection("b");
            List<ZjLzehTeamInformationTeam> zjLzehTeamInformationTeamsb = zjLzehTeamInformationTeamMapper.selectByZjLzehTeamInformationTeamList(zjLzehTeamInformationTeamb);
            List<ZjLzehTeam> zjLzehTeamListb = new ArrayList<>();
            for (ZjLzehTeamInformationTeam lzehTeamInformationTeam : zjLzehTeamInformationTeamsb) {
                ZjLzehTeam zjLzehTeams = zjLzehTeamMapper.selectByPrimaryKey(lzehTeamInformationTeam.getTeamId());
                zjLzehTeamListb.add(zjLzehTeams);
            }
            lzehTeamInformation.setbTeamList(zjLzehTeamListb);
        }

        // 得到分页信息
//        PageInfo<ZjLzehTeamInformation> p = new PageInfo<>(zjLzehTeamInformationList);

        return repEntity.ok(zjLzehTeamInformationList);
    }

    @Override
    public ResponseEntity getZjLzehTeamInformationDetails(ZjLzehTeamInformation zjLzehTeamInformation) {
        if (zjLzehTeamInformation == null) {
            zjLzehTeamInformation = new ZjLzehTeamInformation();
        }
        // 获取数据
        ZjLzehTeamInformation dbZjLzehTeamInformation = zjLzehTeamInformationMapper.selectByPrimaryKey(zjLzehTeamInformation.getTeamInformationId());
        // 数据存在
        if (dbZjLzehTeamInformation != null) {
            return repEntity.ok(dbZjLzehTeamInformation);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehTeamInformation(ZjLzehTeamInformation zjLzehTeamInformation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTeamInformation.setTeamInformationId(UuidUtil.generate());
        zjLzehTeamInformation.setCreateUserInfo(userKey, realName);
        int flag = zjLzehTeamInformationMapper.insert(zjLzehTeamInformation);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehTeamInformation);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTeamInformation(ZjLzehTeamInformation zjLzehTeamInformation) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTeamInformation dbzjLzehTeamInformation = zjLzehTeamInformationMapper.selectByPrimaryKey(zjLzehTeamInformation.getTeamInformationId());
        if (dbzjLzehTeamInformation != null && StrUtil.isNotEmpty(dbzjLzehTeamInformation.getTeamInformationId())) {
           // 排序
           dbzjLzehTeamInformation.setOrderFlag(zjLzehTeamInformation.getOrderFlag());
           // 班组名称
           dbzjLzehTeamInformation.setTeamName(zjLzehTeamInformation.getTeamName());
           // A段人数
           dbzjLzehTeamInformation.setaSectionPeopleNumber(zjLzehTeamInformation.getaSectionPeopleNumber());
           // B段人数
           dbzjLzehTeamInformation.setbSectionPeopleNumber(zjLzehTeamInformation.getbSectionPeopleNumber());
           // 共通
           dbzjLzehTeamInformation.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamInformationMapper.updateByPrimaryKey(dbzjLzehTeamInformation);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehTeamInformation);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTeamInformation(List<ZjLzehTeamInformation> zjLzehTeamInformationList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTeamInformationList != null && zjLzehTeamInformationList.size() > 0) {
           ZjLzehTeamInformation zjLzehTeamInformation = new ZjLzehTeamInformation();
           zjLzehTeamInformation.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamInformationMapper.batchDeleteUpdateZjLzehTeamInformation(zjLzehTeamInformationList, zjLzehTeamInformation);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehTeamInformationList);
        }
    }
}
