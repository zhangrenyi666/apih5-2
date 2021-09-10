package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehTeamInformationMapper;
import com.apih5.mybatis.pojo.ZjLzehTeam;
import com.apih5.mybatis.pojo.ZjLzehTeamInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTeamInformationTeamMapper;
import com.apih5.mybatis.pojo.ZjLzehTeamInformationTeam;
import com.apih5.service.ZjLzehTeamInformationTeamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTeamInformationTeamService")
public class ZjLzehTeamInformationTeamServiceImpl implements ZjLzehTeamInformationTeamService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTeamInformationTeamMapper zjLzehTeamInformationTeamMapper;

    @Autowired(required = true)
    private ZjLzehTeamInformationMapper zjLzehTeamInformationMapper;

    @Override
    public ResponseEntity getZjLzehTeamInformationTeamListByCondition(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        if (zjLzehTeamInformationTeam == null) {
            zjLzehTeamInformationTeam = new ZjLzehTeamInformationTeam();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTeamInformationTeam.getPage(),zjLzehTeamInformationTeam.getLimit());
        // 获取数据
        List<ZjLzehTeamInformationTeam> zjLzehTeamInformationTeamList = zjLzehTeamInformationTeamMapper.selectByZjLzehTeamInformationTeamList(zjLzehTeamInformationTeam);
        // 得到分页信息
        PageInfo<ZjLzehTeamInformationTeam> p = new PageInfo<>(zjLzehTeamInformationTeamList);

        return repEntity.okList(zjLzehTeamInformationTeamList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTeamInformationTeamDetails(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        if (zjLzehTeamInformationTeam == null) {
            zjLzehTeamInformationTeam = new ZjLzehTeamInformationTeam();
        }
        // 获取数据
        ZjLzehTeamInformationTeam dbZjLzehTeamInformationTeam = zjLzehTeamInformationTeamMapper.selectByPrimaryKey(zjLzehTeamInformationTeam.getTeamInformationTeamId());
        // 数据存在
        if (dbZjLzehTeamInformationTeam != null) {
            return repEntity.ok(dbZjLzehTeamInformationTeam);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehTeamInformationTeam(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTeamInformationTeam.setTeamInformationTeamId(UuidUtil.generate());
        zjLzehTeamInformationTeam.setCreateUserInfo(userKey, realName);
        int flag = zjLzehTeamInformationTeamMapper.insert(zjLzehTeamInformationTeam);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehTeamInformationTeam);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTeamInformationTeam(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

//        int flag = 0;
        //先删
        this.batchDeleteUpdateZjLzehTeamInformationAndTeam(zjLzehTeamInformationTeam);

        //初始化人数
        int persons = 0;
        //后加
        if(zjLzehTeamInformationTeam.getSection().equals("a")){
            if(zjLzehTeamInformationTeam.getTeamList()!=null){
                for (ZjLzehTeam zjLzehTeam : zjLzehTeamInformationTeam.getTeamList()) {
                    ZjLzehTeamInformationTeam zjLzehTeamInformationTeam1 = new ZjLzehTeamInformationTeam();
                    zjLzehTeamInformationTeam1.setTeamInformationId(zjLzehTeamInformationTeam.getTeamInformationId());
                    zjLzehTeamInformationTeam1.setTeamId(zjLzehTeam.getTeamId());
                    zjLzehTeamInformationTeam1.setSection(zjLzehTeamInformationTeam.getSection());
                    //返回的数据没管
                    this.saveZjLzehTeamInformationTeam(zjLzehTeamInformationTeam1);
                    persons+=zjLzehTeam.getPeopleNumber();
                }
                //修改数量
                ZjLzehTeamInformation zjLzehTeamInformation = new ZjLzehTeamInformation();
                zjLzehTeamInformation.setTeamInformationId(zjLzehTeamInformationTeam.getTeamInformationId());
                zjLzehTeamInformation.setaSectionPeopleNumber(persons);
                zjLzehTeamInformationMapper.updateZjLzehTeamInformationNumber(zjLzehTeamInformation);
            }
        }else {
            if(zjLzehTeamInformationTeam.getTeamList()!=null){
                for (ZjLzehTeam zjLzehTeam : zjLzehTeamInformationTeam.getTeamList()) {
                    ZjLzehTeamInformationTeam zjLzehTeamInformationTeam1 = new ZjLzehTeamInformationTeam();
                    zjLzehTeamInformationTeam1.setTeamInformationId(zjLzehTeamInformationTeam.getTeamInformationId());
                    zjLzehTeamInformationTeam1.setTeamId(zjLzehTeam.getTeamId());
                    zjLzehTeamInformationTeam1.setSection(zjLzehTeamInformationTeam.getSection());
                    this.saveZjLzehTeamInformationTeam(zjLzehTeamInformationTeam1);
                    persons+=zjLzehTeam.getPeopleNumber();
                }
                //修改数量
                ZjLzehTeamInformation zjLzehTeamInformation = new ZjLzehTeamInformation();
                zjLzehTeamInformation.setTeamInformationId(zjLzehTeamInformationTeam.getTeamInformationId());
                zjLzehTeamInformation.setbSectionPeopleNumber(persons);
                zjLzehTeamInformationMapper.updateZjLzehTeamInformationNumber(zjLzehTeamInformation);
            }
        }


        // 失败
//        if (flag == 0) {
//            return repEntity.errorSave();
//        }
//        else {
            return repEntity.ok("sys.data.update",zjLzehTeamInformationTeam);
//        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTeamInformationTeam(List<ZjLzehTeamInformationTeam> zjLzehTeamInformationTeamList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);


        int flag = 0;
        if (zjLzehTeamInformationTeamList != null && zjLzehTeamInformationTeamList.size() > 0) {
           ZjLzehTeamInformationTeam zjLzehTeamInformationTeam = new ZjLzehTeamInformationTeam();
           zjLzehTeamInformationTeam.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamInformationTeamMapper.batchDeleteUpdateZjLzehTeamInformationTeam(zjLzehTeamInformationTeamList, zjLzehTeamInformationTeam);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehTeamInformationTeamList);
        }
    }


    //新增删除
    private int batchDeleteUpdateZjLzehTeamInformationAndTeam(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if (zjLzehTeamInformationTeam != null) {
            ZjLzehTeamInformationTeam zjLzehTeamInformationTeam2 = new ZjLzehTeamInformationTeam();
            zjLzehTeamInformationTeam2.setModifyUserInfo(userKey, realName);
            return zjLzehTeamInformationTeamMapper.batchDeleteUpdateZjLzehTeamInformationAndTeam(zjLzehTeamInformationTeam, zjLzehTeamInformationTeam2);
        }
        return 0;
    }

    //新增添加
    private int saveZjLzehTeamInformationAndTeam(ZjLzehTeamInformationTeam zjLzehTeamInformationTeam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTeamInformationTeam.setCreateUserInfo(userKey, realName);
        return zjLzehTeamInformationTeamMapper.insert(zjLzehTeamInformationTeam);
    }

    //变更数量

}
