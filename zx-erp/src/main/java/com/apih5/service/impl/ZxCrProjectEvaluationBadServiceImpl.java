package com.apih5.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxCrProjCreditBadBehaMapper;
import com.apih5.mybatis.dao.ZxCrProjCreditScoreMapper;
import com.apih5.mybatis.pojo.ZxCrProjCreditBadBeha;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrProjectEvaluationBadMapper;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationBad;
import com.apih5.service.ZxCrProjectEvaluationBadService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxCrProjectEvaluationBadService")
public class ZxCrProjectEvaluationBadServiceImpl implements ZxCrProjectEvaluationBadService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrProjectEvaluationBadMapper zxCrProjectEvaluationBadMapper;

    @Autowired(required = true)
    private ZxCrProjCreditScoreMapper zxCrProjCreditScoreMapper;

    @Autowired(required = true)
    private ZxCrProjCreditBadBehaMapper zxCrProjCreditBadBehaMapper;



    @Override
    public ResponseEntity getZxCrProjectEvaluationBadListByCondition(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        if (zxCrProjectEvaluationBad == null) {
            zxCrProjectEvaluationBad = new ZxCrProjectEvaluationBad();
        }
        // 分页查询
        PageHelper.startPage(zxCrProjectEvaluationBad.getPage(),zxCrProjectEvaluationBad.getLimit());
        // 获取数据
        List<ZxCrProjectEvaluationBad> zxCrProjectEvaluationBadList = zxCrProjectEvaluationBadMapper.selectByZxCrProjectEvaluationBadList(zxCrProjectEvaluationBad);
        // 得到分页信息
        PageInfo<ZxCrProjectEvaluationBad> p = new PageInfo<>(zxCrProjectEvaluationBadList);

        return repEntity.okList(zxCrProjectEvaluationBadList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrProjectEvaluationBadDetail(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        if (zxCrProjectEvaluationBad == null) {
            zxCrProjectEvaluationBad = new ZxCrProjectEvaluationBad();
        }
        // 获取数据
        ZxCrProjectEvaluationBad dbZxCrProjectEvaluationBad = zxCrProjectEvaluationBadMapper.selectByPrimaryKey(zxCrProjectEvaluationBad.getZxCrProjectEvaluationBadId());
        // 数据存在
        if (dbZxCrProjectEvaluationBad != null) {
            return repEntity.ok(dbZxCrProjectEvaluationBad);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrProjectEvaluationBad(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrProjectEvaluationBad.setZxCrProjectEvaluationBadId(UuidUtil.generate());
        zxCrProjectEvaluationBad.setCreateUserInfo(userKey, realName);
        int flag = zxCrProjectEvaluationBadMapper.insert(zxCrProjectEvaluationBad);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrProjectEvaluationBad);
        }
    }

    @Override
    public ResponseEntity updateZxCrProjectEvaluationBad(ZxCrProjectEvaluationBad zxCrProjectEvaluationBad) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrProjectEvaluationBad dbZxCrProjectEvaluationBad = zxCrProjectEvaluationBadMapper.selectByPrimaryKey(zxCrProjectEvaluationBad.getZxCrProjectEvaluationBadId());
        if (dbZxCrProjectEvaluationBad != null && StrUtil.isNotEmpty(dbZxCrProjectEvaluationBad.getZxCrProjectEvaluationBadId())) {
           // mainID
           dbZxCrProjectEvaluationBad.setMainID(zxCrProjectEvaluationBad.getMainID());
           // 评价内容
           dbZxCrProjectEvaluationBad.setEvalContent(zxCrProjectEvaluationBad.getEvalContent());
           // 评价细目
           dbZxCrProjectEvaluationBad.setScoreItem(zxCrProjectEvaluationBad.getScoreItem());
           // 是否存在严重不良行为
           dbZxCrProjectEvaluationBad.setIsBad(zxCrProjectEvaluationBad.getIsBad());
           // editTime
           dbZxCrProjectEvaluationBad.setEditTime(zxCrProjectEvaluationBad.getEditTime());
           // comID
           dbZxCrProjectEvaluationBad.setComID(zxCrProjectEvaluationBad.getComID());
           // comName
           dbZxCrProjectEvaluationBad.setComName(zxCrProjectEvaluationBad.getComName());
           // comOrders
           dbZxCrProjectEvaluationBad.setComOrders(zxCrProjectEvaluationBad.getComOrders());
           // 打分考核id
           dbZxCrProjectEvaluationBad.setEvalID(zxCrProjectEvaluationBad.getEvalID());
           // 备注
           dbZxCrProjectEvaluationBad.setRemarks(zxCrProjectEvaluationBad.getRemarks());
           // 排序
           dbZxCrProjectEvaluationBad.setSort(zxCrProjectEvaluationBad.getSort());
           // 共通
           dbZxCrProjectEvaluationBad.setModifyUserInfo(userKey, realName);
           flag = zxCrProjectEvaluationBadMapper.updateByPrimaryKey(dbZxCrProjectEvaluationBad);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrProjectEvaluationBad);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluationBad(List<ZxCrProjectEvaluationBad> zxCrProjectEvaluationBadList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrProjectEvaluationBadList != null && zxCrProjectEvaluationBadList.size() > 0) {
           ZxCrProjectEvaluationBad zxCrProjectEvaluationBad = new ZxCrProjectEvaluationBad();
           zxCrProjectEvaluationBad.setModifyUserInfo(userKey, realName);
           flag = zxCrProjectEvaluationBadMapper.batchDeleteUpdateZxCrProjectEvaluationBad(zxCrProjectEvaluationBadList, zxCrProjectEvaluationBad);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrProjectEvaluationBadList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity getZxCrProjectEvaluationBadDetailOne(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        if (zxCrProjCreditBadBeha == null) {
            zxCrProjCreditBadBeha = new ZxCrProjCreditBadBeha();
        }
        zxCrProjCreditBadBeha.setIsUse("1");
        // 分页查询
        PageHelper.startPage(zxCrProjCreditBadBeha.getPage(),zxCrProjCreditBadBeha.getLimit());
        // 获取数据
        List<ZxCrProjCreditBadBeha> zxCrProjCreditBadBehaList = zxCrProjCreditBadBehaMapper.selectByZxCrProjCreditBadBehaList(zxCrProjCreditBadBeha);
        // 得到分页信息
        PageInfo<ZxCrProjCreditBadBeha> p = new PageInfo<>(zxCrProjCreditBadBehaList);

        return repEntity.okList(zxCrProjCreditBadBehaList, p.getTotal());
    }
}
