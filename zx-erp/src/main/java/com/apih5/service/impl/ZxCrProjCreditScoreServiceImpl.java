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
import com.apih5.mybatis.dao.ZxCrProjCreditScoreMapper;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import com.apih5.service.ZxCrProjCreditScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCrProjCreditScoreService")
public class ZxCrProjCreditScoreServiceImpl implements ZxCrProjCreditScoreService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrProjCreditScoreMapper zxCrProjCreditScoreMapper;

    @Override
    public ResponseEntity getZxCrProjCreditScoreListByCondition(ZxCrProjCreditScore zxCrProjCreditScore) {
        if (zxCrProjCreditScore == null) {
            zxCrProjCreditScore = new ZxCrProjCreditScore();
        }
        // 分页查询
        PageHelper.startPage(zxCrProjCreditScore.getPage(),zxCrProjCreditScore.getLimit());
        // 获取数据
        List<ZxCrProjCreditScore> zxCrProjCreditScoreList = zxCrProjCreditScoreMapper.selectByZxCrProjCreditScoreList(zxCrProjCreditScore);
        // 得到分页信息
        PageInfo<ZxCrProjCreditScore> p = new PageInfo<>(zxCrProjCreditScoreList);

        return repEntity.okList(zxCrProjCreditScoreList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrProjCreditScoreDetail(ZxCrProjCreditScore zxCrProjCreditScore) {
        if (zxCrProjCreditScore == null) {
            zxCrProjCreditScore = new ZxCrProjCreditScore();
        }
        // 获取数据
        ZxCrProjCreditScore dbZxCrProjCreditScore = zxCrProjCreditScoreMapper.selectByPrimaryKey(zxCrProjCreditScore.getId());
        // 数据存在
        if (dbZxCrProjCreditScore != null) {
            return repEntity.ok(dbZxCrProjCreditScore);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrProjCreditScore(ZxCrProjCreditScore zxCrProjCreditScore) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrProjCreditScore.setId(UuidUtil.generate());
        zxCrProjCreditScore.setCreateUserInfo(userKey, realName);
        int flag = zxCrProjCreditScoreMapper.insert(zxCrProjCreditScore);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrProjCreditScore);
        }
    }

    @Override
    public ResponseEntity updateZxCrProjCreditScore(ZxCrProjCreditScore zxCrProjCreditScore) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrProjCreditScore dbZxCrProjCreditScore = zxCrProjCreditScoreMapper.selectByPrimaryKey(zxCrProjCreditScore.getId());
        if (dbZxCrProjCreditScore != null && StrUtil.isNotEmpty(dbZxCrProjCreditScore.getId())) {
           // 评价内容
           dbZxCrProjCreditScore.setEvalContent(zxCrProjCreditScore.getEvalContent());
           // 评分细目
           dbZxCrProjCreditScore.setScoreItem(zxCrProjCreditScore.getScoreItem());
           // 标准分值
           dbZxCrProjCreditScore.setStandardScore(zxCrProjCreditScore.getStandardScore());
           // 是否启用
           dbZxCrProjCreditScore.setIsUse(zxCrProjCreditScore.getIsUse());
           // 所属公司ID
           dbZxCrProjCreditScore.setComID(zxCrProjCreditScore.getComID());
           // 所属公司名称
           dbZxCrProjCreditScore.setComName(zxCrProjCreditScore.getComName());
           // 所属公司排序
           dbZxCrProjCreditScore.setComOrders(zxCrProjCreditScore.getComOrders());
           // department
           dbZxCrProjCreditScore.setDepartment(zxCrProjCreditScore.getDepartment());
           // appraiser
           dbZxCrProjCreditScore.setAppraiser(zxCrProjCreditScore.getAppraiser());
           //排序
           dbZxCrProjCreditScore.setOrderStr(zxCrProjCreditScore.getOrderStr());
           // 共通
           dbZxCrProjCreditScore.setModifyUserInfo(userKey, realName);
           flag = zxCrProjCreditScoreMapper.updateByPrimaryKey(dbZxCrProjCreditScore);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrProjCreditScore);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrProjCreditScore(List<ZxCrProjCreditScore> zxCrProjCreditScoreList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrProjCreditScoreList != null && zxCrProjCreditScoreList.size() > 0) {
           ZxCrProjCreditScore zxCrProjCreditScore = new ZxCrProjCreditScore();
           zxCrProjCreditScore.setModifyUserInfo(userKey, realName);
           flag = zxCrProjCreditScoreMapper.batchDeleteUpdateZxCrProjCreditScore(zxCrProjCreditScoreList, zxCrProjCreditScore);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrProjCreditScoreList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
